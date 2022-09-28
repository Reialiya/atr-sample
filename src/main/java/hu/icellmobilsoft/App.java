package hu.icellmobilsoft;


import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import hu.icellmobilsoft.atr.sample.action.DepartmentAction;
import hu.icellmobilsoft.atr.sample.action.SamplePatientAction;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import javassist.NotFoundException;

/**
 * Hello world!
 *
 */

/**
 *
 */
public class App {

    @Inject
      private DepartmentAction departmentAction;

    public static void main(String[] args) throws BaseException, NotFoundException {

        CDI.current().select(DepartmentAction.class).get().getDepartment("123");

//        ILoadData loadData = new LoadDataImpl();
//        IRequestData requestData = new RequestDataImpl();
//        SamplePatientAction ospXml = loadData.loadFromXml();
//        SamplePatientAction ospJson = loadData.loadFromJson();
//
//        patientXmlTest(ospXml);
//        patientJsonTest(ospJson);
//
//        Patient patient = ospXml.queryPatientData("kv", "000008");
//        ospXml.deletePatient("PATIENT8");
    }
    public void valami() throws BaseException, NotFoundException {
        departmentAction.getDepartment("123");
    }

    public static void patientXmlTest(SamplePatientAction opx) {
//        System.out.println("######################## allDepartment");
//        opx.getDepRep().toString();
//        System.out.println("######################## allDepartment End \n");
//
//        System.out.println("######################## allInstitute");
//        opx.getInstRep().toString();
//        System.out.println("######################## allInstitute End \n");
//
//        System.out.println("######################## allPatient");
//        opx.getPatRep().toString();
//        System.out.println("######################## allPatient End \n");
    }

    public static void patientJsonTest(SamplePatientAction spa) {
//        System.out.println("######################## allDepartment");
//        spa.getDepRep().toString();
//        System.out.println("######################## allDepartment End \n");
//
//        System.out.println("######################## allInstitute");
//        spa.getInstRep().toString();
//        System.out.println("######################## allInstitute End \n");
//
//        System.out.println("######################## allPatient");
//        spa.getPatRep().toString();
//        System.out.println("######################## allPatient End \n");
    }

}
