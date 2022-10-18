package hu.icellmobilsoft.atr.sample.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.xml.sax.SAXException;

import hu.icellmobilsoft.atr.sample.converter.DepartmentConverter;
import hu.icellmobilsoft.atr.sample.converter.InstituteConverter;
import hu.icellmobilsoft.atr.sample.converter.PatientConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.NotFoundException;
import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.service.DepartmentService;
import hu.icellmobilsoft.atr.sample.service.InstituteService;
import hu.icellmobilsoft.atr.sample.service.PatientService;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.RandomUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.atr.sample.util.TagNameEnum;
import hu.icellmobilsoft.dto.sample.patient.BaseResponse;
import hu.icellmobilsoft.dto.sample.patient.DepartmentListType;
import hu.icellmobilsoft.dto.sample.patient.FunctionCodeType;
import hu.icellmobilsoft.dto.sample.patient.InstituteListType;
import hu.icellmobilsoft.dto.sample.patient.PatientsListType;
import hu.icellmobilsoft.dto.sample.patient.Sample;

/**
 * @author juhaszkata
 * @version 1
 */

@Model
public class LoadDataAction {

    // @Inject
    // private PatientAction patientAction;
    //
    // @Inject
    // private InstituteAction instituteAction;
    //
    // @Inject
    // private DepartmentAction departmentAction;

    @Inject
    private DepartmentConverter departmentConverter;

    @Inject
    private DepartmentRepository departmentRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstituteConverter instituteConverter;

    @Inject
    private PatientService patientService;

    @Inject
    private InstituteService instituteService;

    @Inject
    private DepartmentService departmentService;

    @Inject
    private PatientConverter patientConverter;

    // BaseResponse a visszatérítési érték, funcCode beállítjuk a végén setFunctionCode type = ok
    // return mindenhol hibával visszaválaszolunk BaseResponse setF. error
    // xml marshall exceptionnél lehet dob hibát és xml-t alakítsuk át, xsd-hez ne nyúljak hozzá!!
    // with beállítjuk és a visszatérési értéke nem void, ugyanazt beállítjuk

    public BaseResponse loadFromXml(String fileName) throws BaseException {
        BaseResponse baseResponse = new BaseResponse().withFuncCode(FunctionCodeType.ERROR);
        if (StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        // Baseresponse egyet létrehozunk, aztán azt adom vissza minden returnnél

        File xmlFile = new File(getResource(fileName));
        xmlValidation(fileName);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Sample.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Sample sample = (Sample) jaxbUnmarshaller.unmarshal(new StreamSource(xmlFile));
            DepartmentListType departmentListType = sample.getDepartments();
            if (departmentListType == null) {
                return baseResponse;
            }
            departmentListType.getDepartment().forEach(departmentType -> {
                DepartmentEntity departmentEntity = departmentConverter.convert(departmentType);
                try {
                    departmentService.saveDepartment(departmentEntity);
                } catch (BaseException e) {
                    throw new RuntimeException(e);
                }
            });
            InstituteListType instituteListType = sample.getInstitutes();
            if (instituteListType == null) {
                return baseResponse;
            }

            // ahány entity lesz, ahány department lesz
            instituteListType.getInstitute().forEach(instituteType -> {
                InstituteEntity instituteEntity = instituteConverter.convert(instituteType);
                try {
                    instituteService.saveInstitute(instituteEntity);
                } catch (BaseException e) {
                    throw new RuntimeException(e);
                }
            });
            PatientsListType patientsListType = sample.getPatients();
            if (patientsListType == null) {
                return baseResponse;
            }
            patientsListType.getPatient().forEach(patientType -> {
                PatientEntity patientEntity = patientConverter.convert(patientType);

                try {
                    patientService.savePatient(patientEntity);
                } catch (BaseException e) {
                    throw new RuntimeException(e);
                }

            });

        } catch (Exception e) {
            throw new BaseException(SimplePatientConstans.PARSING_ERROR_MSG + e.getMessage(), e);
        }

        // return response.ok
        baseResponse.setFuncCode(FunctionCodeType.OK);
        return baseResponse;
    }

    // not found exception kell
    private String getResource(String filename) throws BaseException {
        URL resource = getClass().getClassLoader().getResource(filename);
        if (resource == null) {
            throw new NotFoundException(SimplePatientConstans.FILE_NOT_FOUND_MSG);
        }
        return resource.getFile();
    }

    private String getElement(JSONObject o, TagNameEnum tagName) {
        String value = "";
        if (o != null) {
            Object obj = o.get(tagName.getTagName());
            value = obj != null ? obj.toString() : " ";
        }
        return value;
    }

    // TODO: átnézni nálam is a validatort, Jsonra is ellenőrzést készítsünk
    private void xmlValidation(String fileName) throws BaseException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            File xmlFile = new File(getResource(fileName));
            Schema schema = schemaFactory.newSchema(new File(getResource(SimplePatientConstans.SCHEMA_FILE)));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
        } catch (SAXException | IOException e) {
            throw new BaseException(fileName + SimplePatientConstans.FILE_NOT_VALID_MSG, e);
        }
    }

    // public static void main(String[] args) throws BaseException {
    // // loadFromJson("example.json");
    // LoadDataAction ld = new LoadDataAction();
    // ld.loadFromJson("example.json");
    // }

    public BaseResponse loadFromJson(String fileName) throws BaseException {
        BaseResponse baseResponse = new BaseResponse().withFuncCode(FunctionCodeType.ERROR);
        if (StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        JSONParser parser = new JSONParser();
        try {
            InputStream resourceAsStream = LoadDataAction.class.getResourceAsStream("/" + fileName);
            JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(resourceAsStream, "UTF-8"));

            JSONObject sample = (JSONObject) jsonObject.get(TagNameEnum.SAMPLE.getTagName());

            List<DepartmentEntity> departmentEntityList = new ArrayList<>();
            List<InstituteEntity> instituteEntityList = new ArrayList<>();
            List<PatientEntity> patientEntityList = new ArrayList<>();

            // department
            JSONObject departments = (JSONObject) sample.get(TagNameEnum.DEPARTMENTS.getTagName());
            if (departments == null) {
                return baseResponse;
            }
            JSONArray departmentJsonArray = (JSONArray) departments.get(TagNameEnum.DEPARTMENT.getTagName());
            if (departmentJsonArray == null) {
                return baseResponse;
            }

            departmentJsonArray.forEach(department -> {

                JSONObject objDepartment = (JSONObject) department;
                String name = getElement(objDepartment, TagNameEnum.NAME);
                String id = getElement(objDepartment, TagNameEnum.ID);

                // db-be mentés miatt entityt használok, settelést lehetne convertálásra kiemelni
                DepartmentEntity departmentEntity = new DepartmentEntity();
                departmentEntity.setName(name);
                departmentEntity.setId(id);
                departmentEntity.setStatus(ActiveInactiveStatus.ACTIVE);
                departmentEntityList.add(departmentEntity);
            });

            // institute
            JSONObject institutes = (JSONObject) sample.get(TagNameEnum.INSTITUTES.getTagName());
            if (institutes == null) {
                return baseResponse;
            }
            JSONArray instituteJsonArray = (JSONArray) institutes.get(TagNameEnum.INSTITUTE.getTagName());
            if (instituteJsonArray == null) {
                return baseResponse;
            }

            instituteJsonArray.forEach(institute -> {
                JSONObject objInstitute = (JSONObject) institute;
                String id = getElement(objInstitute, TagNameEnum.ID);
                String name = getElement(objInstitute, TagNameEnum.NAME);
                JSONObject instDepartments = (JSONObject) objInstitute.get(TagNameEnum.DEPARTMENTS.getTagName());
                ArrayList<String> instDepartmentStringList = (ArrayList<String>) instDepartments.get(TagNameEnum.DEPARTMENT.getTagName());

                for (String departmentId : instDepartmentStringList) {

                    InstituteEntity existingInstitute = instituteService.findByIds(departmentId, id);
                    if (existingInstitute != null) {
                        continue;
                    }

                    InstituteEntity instituteEntity = new InstituteEntity();
                    instituteEntity.setId(RandomUtil.generateId());
                    instituteEntity.setName(name);
                    instituteEntity.setStatus(ActiveInactiveStatus.ACTIVE);
                    instituteEntity.setInstituteId(id);
                    instituteEntity.setDepartmentId(departmentId);
                    instituteEntityList.add(instituteEntity);
                }
            });

            // patient
            JSONObject patients = (JSONObject) sample.get(TagNameEnum.PATIENTS.getTagName());
            if (patients == null) {
                return baseResponse;
            }
            JSONArray patientJsonArray = (JSONArray) patients.get(TagNameEnum.PATIENT.getTagName());
            if (patientJsonArray == null) {
                return baseResponse;
            }
            patientJsonArray.forEach(patient -> {
                JSONObject objPatient = (JSONObject) patient;
                String id = getElement(objPatient, TagNameEnum.ID);
                String name = getElement(objPatient, TagNameEnum.NAME);
                String email = getElement(objPatient, TagNameEnum.EMAIL);
                String username = getElement(objPatient, TagNameEnum.USERNAME);

                String departmentId = (String) objPatient.get(TagNameEnum.DEPARTMENT.getTagName());
                String instituteId = (String) objPatient.get(TagNameEnum.INSTITUTE.getTagName());

                PatientEntity patientEntity = new PatientEntity();
                patientEntity.setId(id);
                patientEntity.setName(name);
                patientEntity.setEmail(email);
                patientEntity.setUsername(username);
                patientEntity.setStatus(ActiveInactiveStatus.ACTIVE);
                patientEntity.setDepartmentId(departmentId);
                patientEntity.setInstituteId(instituteId);

                patientEntityList.add(patientEntity);
            });

            for (DepartmentEntity departmentEntity : departmentEntityList) {
                departmentService.saveDepartment(departmentEntity);
            }

            for (InstituteEntity instituteEntity : instituteEntityList) {
                instituteService.saveInstitute(instituteEntity);
            }

            for (PatientEntity patientEntity : patientEntityList) {
                patientService.savePatient(patientEntity);
            }

            // patientEntityList.forEach(patientEntity -> {
            // try {
            // patientService.savePatient(patientEntity);
            // } catch (BaseException e) {
            // throw new RuntimeException(e);
            // }
            // });

        } catch (Exception e) {
            throw new BaseException(e);
        }

        baseResponse.setFuncCode(FunctionCodeType.OK);
        return baseResponse;
    }

    // public void loadFromXml(String xmlFileName) {
    // XSDValidator validator = new XSDValidator();
    // if (validator.Validate(xmlFileName, "samplepatient.xsd")) {
    // ParseHelper.ParseXml oParseXml = new ParseHelper.ParseXml();
    // oParseXml.run(xmlFileName);
    //
    // depRep = oParseXml.getDepRepo();
    // patRep = oParseXml.getPatRepo();
    // instRep = oParseXml.getInstRepo();
    // } else {
    // throw new Error("invalid xml");
    // }
    //
    // }

}
