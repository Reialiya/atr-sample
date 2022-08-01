package hu.icellmobilsoft;

import hu.icellmobilsoft.atr.sample.parseXml;
import hu.icellmobilsoft.atr.sample.action.SamplePatientAction;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        parseXml opx = new parseXml();
        // patientTest(opx);
        SamplePatientAction osp = new SamplePatientAction();
        osp.loadFromJson("example.json");
    }

    public static void patientTest(parseXml opx) {
        System.out.println("######################## allDepartment");
        opx.getDepRepo().toString();
        System.out.println("######################## allDepartment End ");

        System.out.println("######################## allInstitute");
        opx.getInstRepo().toString();
        System.out.println("######################## allInstitute End ");

        System.out.println("######################## allPatient");
        opx.getPatRepo().toString();
        System.out.println("######################## allPatient End ");
    }

}
