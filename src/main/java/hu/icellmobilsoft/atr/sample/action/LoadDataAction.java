package hu.icellmobilsoft.atr.sample.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
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
 * The type Load data action.
 *
 * @author juhaszkata
 *
 */
@Model
public class LoadDataAction {

    @Inject
    private DepartmentConverter departmentConverter;

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

    /**
     * Load from xml base response.
     *
     * @param fileName
     *            the file name
     * @return the base response
     * @throws BaseException
     *             the base exception
     */
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

            // amennyi entity lesz, annyi department lesz
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

        baseResponse.setFuncCode(FunctionCodeType.OK);
        return baseResponse;
    }

    /**
     * Load from json base response.
     *
     * @param fileName
     *            the file name
     * @return the base response
     * @throws BaseException
     *             the base exception
     */
    public BaseResponse loadFromJson(String fileName) throws BaseException {
        BaseResponse baseResponse = new BaseResponse().withFuncCode(FunctionCodeType.ERROR);
        if (StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        JSONParser parser = new JSONParser();
        try {
            InputStream resourceAsStream = LoadDataAction.class.getResourceAsStream("/" + fileName);
            JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));

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
                    try {
                        instituteService.findByIds(departmentId, id);
                    } catch (NoResultException exception) {
                        InstituteEntity instituteEntity = new InstituteEntity();
                        instituteEntity.setId(RandomUtil.generateId());
                        instituteEntity.setName(name);
                        instituteEntity.setStatus(ActiveInactiveStatus.ACTIVE);
                        instituteEntity.setInstituteId(id);
                        instituteEntity.setDepartmentId(departmentId);
                        instituteEntityList.add(instituteEntity);
                    }
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

            CDI.current().select(LoadDataAction.class).get().saveCollections(departmentEntityList, instituteEntityList, patientEntityList);

        } catch (Exception e) {
            throw new BaseException(e);
        }

        baseResponse.setFuncCode(FunctionCodeType.OK);
        return baseResponse;
    }

    /**
     * Save collections.
     *
     * @param departmentEntityList
     *            the department entity list
     * @param instituteEntityList
     *            the institute entity list
     * @param patientEntityList
     *            the patient entity list
     */
    @Transactional
    public void saveCollections(List<DepartmentEntity> departmentEntityList, List<InstituteEntity> instituteEntityList,
            List<PatientEntity> patientEntityList) {
        departmentService.saveCollection(departmentEntityList);
        instituteService.saveCollection(instituteEntityList);
        patientService.saveCollection(patientEntityList);
    }

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

}
