package hu.icellmobilsoft.atr.sample.action;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

import hu.icellmobilsoft.atr.sample.converter.DepartmentConverter;
import hu.icellmobilsoft.atr.sample.converter.InstituteConverter;
import hu.icellmobilsoft.atr.sample.converter.PatientConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.rest.RequestDataImpl;
import hu.icellmobilsoft.atr.sample.service.PatientService;
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
public class LoadDataAction extends RequestDataImpl {

    @Inject
    private PatientAction patientAction;

    @Inject
    private InstituteAction instituteAction;

    @Inject
    private DepartmentAction departmentAction;

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
    private PatientConverter patientConverter;

//    @Inject
//    private TagNameEnum tagNameEnum;

    // BaseResponse a visszatérítési érték, funcCode beállítjuk a végén setFunctionCode type = ok
    // return mindenhol hibával visszaválaszolunk BaseResponse setF. error
    // xml marshall exceptionnél lehet dob hibát és xml-t alakítsuk át, xsd-hez ne nyúljak hozzá!!
//    with beállítjuk és a visszatérési értéke nem void, ugyanazt beállítjuk

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
                departmentRepository.saveDep(departmentEntity);
            });
            InstituteListType instituteListType = sample.getInstitutes();
            if (instituteListType == null) {
                return baseResponse;
            }
            instituteListType.getInstitute().forEach(instituteType -> {
                InstituteEntity instituteEntity = instituteConverter.convert(instituteType);
                instituteRepository.saveInst(instituteEntity);
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
            throw new BaseException(SimplePatientConstans.FILE_NOT_FOUND_MSG);
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

    // private DepartmentRepository depRep;
    // private PatientRepository patRep;
    // private InstituteRepository instRep;
    //
    // // validator elhelyezése?
    //
    // /**
    // * Gets dep rep.
    // *
    // * @return the dep rep
    // */
    // public DepartmentRepository getDepRep() {
    // return depRep;
    // }
    //
    // /**
    // * Gets pat rep.
    // *
    // * @return the pat rep
    // */
    // public PatientRepository getPatRep() {
    // return patRep;
    // }
    //
    // /**
    // * Gets inst rep.
    // *
    // * @return the inst rep
    // */
    // public InstituteRepository getInstRep() {
    // return instRep;
    // }
    //
    // public void loadFromXml(String xmlFileName) {
    //
    //
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
    //
    // /**
    // * Load from json.
    // *
    // *
    // * the json
    // */
    // public void loadFromJson(String jsonFileName) {
    //
    // ParseHelper.ParseJson oParseJson = new ParseHelper.ParseJson();
    // oParseJson.run(jsonFileName);
    //
    // depRep = oParseJson.getDepRepo();
    // patRep = oParseJson.getPatRepo();
    // instRep = oParseJson.getInstRepo();
    // }
    //
    // /**
    // * Query patient data patient.
    // *
    // * @param userName
    // * the user name
    // * @param department
    // * the department
    // * @return the patient
    //// */
    //// public Patient queryPatientData(String userName, String department) {
    //// return patRep.getAllPatient().stream().filter(x -> {
    //// return x.getUsername().equals(userName) && x.getDepartmentId().equals(department);
    //// }).findFirst().orElse(null);
    //// }
    //
    // /**
    // * Delete patient.
    // *
    // * @param id
    // * the id
    // */
    //// public void deletePatient(String id) {
    ////
    //// PatientRepository tempPatRepo = new PatientRepository();
    //// patRep.getAllPatient().stream().filter(x -> x.getId().equals(id)).forEach(y -> {
    //// tempPatRepo.savePatient(y);
    //// });
    //// patRep.getAllPatient().removeAll(tempPatRepo.getAllPatient());
    ////
    //// }
}
