package hu.icellmobilsoft.atr.sample.action;

import hu.icellmobilsoft.atr.sample.model.Patient;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;
import hu.icellmobilsoft.atr.sample.rest.RequestDataImpl;
import hu.icellmobilsoft.atr.sample.util.ParseHelper;
import hu.icellmobilsoft.atr.sample.util.XSDValidator;

/**
 * @author juhaszkata
 * @version 1
 */
public class SamplePatientAction extends RequestDataImpl {
    private DepartmentRepository depRep;
    private PatientRepository patRep;
    private InstituteRepository instRep;

    // validator elhelyezése?

    /**
     * Gets dep rep.
     *
     * @return the dep rep
     */
    public DepartmentRepository getDepRep() {
        return depRep;
    }

    /**
     * Gets pat rep.
     *
     * @return the pat rep
     */
    public PatientRepository getPatRep() {
        return patRep;
    }

    /**
     * Gets inst rep.
     *
     * @return the inst rep
     */
    public InstituteRepository getInstRep() {
        return instRep;
    }

    public void loadFromXml(String xmlFileName) {


        XSDValidator validator = new XSDValidator();
        if (validator.Validate(xmlFileName, "samplepatient.xsd")) {
            ParseHelper.ParseXml oParseXml = new ParseHelper.ParseXml();
            oParseXml.run(xmlFileName);

            depRep = oParseXml.getDepRepo();
            patRep = oParseXml.getPatRepo();
            instRep = oParseXml.getInstRepo();
        } else {
            throw new Error("invalid xml");
        }

    }

    /**
     * Load from json.
     *
     *
     *            the json
     */
    public void loadFromJson(String jsonFileName) {

        ParseHelper.ParseJson oParseJson = new ParseHelper.ParseJson();
        oParseJson.run(jsonFileName);

        depRep = oParseJson.getDepRepo();
        patRep = oParseJson.getPatRepo();
        instRep = oParseJson.getInstRepo();
    }

    /**
     * Query patient data patient.
     *
     * @param userName
     *            the user name
     * @param department
     *            the department
     * @return the patient
     */
    public Patient queryPatientData(String userName, String department) {
        return patRep.getAllPatient().stream().filter(x -> {
            return x.getUsername().equals(userName) && x.getDepartment().getId().equals(department);
        }).findFirst().orElse(null);
    }

    /**
     * Delete patient.
     *
     * @param id
     *            the id
     */
    public void deletePatient(String id) {
/
        PatientRepository tempPatRepo = new PatientRepository();
        patRep.getAllPatient().stream().filter(x -> x.getId().equals(id)).forEach(y -> {
            tempPatRepo.savePatient(y);
        });
        patRep.getAllPatient().removeAll(tempPatRepo.getAllPatient());

    }
}
