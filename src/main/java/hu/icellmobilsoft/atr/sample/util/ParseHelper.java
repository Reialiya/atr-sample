package hu.icellmobilsoft.atr.sample.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.model.Patient;
import hu.icellmobilsoft.atr.sample.model.Institute;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.service.PatientService;

/**
 * The type Parse helper.
 */
public class ParseHelper {

    public static class ParseXml {
        private DepartmentRepository depRepo;
        private PatientService patRepo;
        private InstituteRepository instRepo;

        public PatientService getPatRepo() {
            return patRepo;
        }

        public InstituteRepository getInstRepo() {
            return instRepo;
        }

        public DepartmentRepository getDepRepo() {
            return depRepo;
        }

        public ParseXml() {
            this.depRepo = new DepartmentRepository();
            this.patRepo = new PatientService();
            this.instRepo = new InstituteRepository();
        }

        public void run(java.lang.String fileName) {
            try {
                this.readSample(this.parse(fileName));
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            }
        }

        public XMLEventReader parse(java.lang.String filename) {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            try {
                XMLEventReader reader = xmlInputFactory.createXMLEventReader(in);
                return reader;
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void readSample(XMLEventReader reader) throws XMLStreamException {
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                switch (nextEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:

                    java.lang.String elementName = nextEvent.asStartElement().getName().getLocalPart();
                    if (!elementName.equals("sample")) {
                        readSampleChilds(reader, elementName);
                    }

                    break;
                case XMLStreamConstants.END_ELEMENT:
                    break;
                }
            }
        }

        public void readSampleChilds(XMLEventReader reader, java.lang.String tag) throws XMLStreamException {
            while (reader.hasNext()) {
                switch (tag) {
                case "departments":
                    System.out.println("-------------> " + tag + " <-> ");
                    depRepo = getDeps(reader, tag);
                    return;
                case "institutes":
                    System.out.println("-------------> " + tag + " <-> ");
                    instRepo = getInst(reader, tag);
                    return;
                case "patients":
                    System.out.println("-------------> " + tag + " <-> ");
                    patRepo = getPats(reader, tag);
                    return;
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DepartmentEntity parseDepartmentTags(XMLEventReader reader, java.lang.String elementName, DepartmentEntity tempDep)
                throws XMLStreamException {
            switch (elementName) {
            case "department":
                tempDep = new DepartmentEntity();
                break;
            case "id":
                String id = reader.nextEvent().toString();
                if (StringUtils.isBlank(id)) {
                    if (tempDep != null)
                        tempDep.setId(id);
                }
                break;
            case "name":
                java.lang.String name = reader.nextEvent().asCharacters().getData();
                if (!name.isEmpty()) {
                    if (tempDep != null)
                        tempDep.setName(name);
                }
                break;
            }
            return tempDep;
        }

        DepartmentRepository saveDepartmentToRepository(DepartmentRepository tempRep, DepartmentEntity temp, String endelementName,
                String tag) {
            if (endelementName.equals("department")) {
                System.out.println(" [" + tag + "] getDeps END -> " + endelementName);
                if (temp != null) {
                    // tempRep.saveDepartment(temp);
                }
            }
            return tempRep;
        }

        DepartmentRepository getDeps(XMLEventReader reader, java.lang.String tag) throws XMLStreamException {

            DepartmentEntity tempDep = null;
            DepartmentRepository tempDepRep = new DepartmentRepository();

            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                switch (nextEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:

                    java.lang.String elementName = nextEvent.asStartElement().getName().getLocalPart();
                    System.out.println(" [" + tag + "] getDeps start -> " + elementName);
                    tempDep = parseDepartmentTags(reader, elementName, tempDep);

                    break;
                case XMLStreamConstants.END_ELEMENT:

                    java.lang.String endelementName = nextEvent.asEndElement().getName().getLocalPart();
                    tempDepRep = saveDepartmentToRepository(tempDepRep, tempDep, endelementName, tag);
                    if (endelementName.equals(tag)) {
                        System.out.println("<------------- " + tag + " <-> ");
                        return tempDepRep;
                    }

                    break;
                }
            }
            return tempDepRep;

        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Institute parseInstitutesTags(XMLEventReader reader, java.lang.String elementName, Institute tempDep) throws XMLStreamException {
            switch (elementName) {
            case "institute":
                tempDep = new Institute();
                break;
            case "id":
                java.lang.String id = reader.nextEvent().asCharacters().getData();
                if (!id.isEmpty() && tempDep != null) {
                    tempDep.setId(id);
                }
                break;
            case "name":
                java.lang.String name = reader.nextEvent().asCharacters().getData();
                if (!name.isEmpty() && tempDep != null) {
                    tempDep.setName(name);
                }
                break;
            case "department":
                java.lang.String depdep = reader.nextEvent().asCharacters().getData();
                if (!depdep.isEmpty() && tempDep != null) {
//                    tempDep.addDepartments(depRepo.findDepartment(depdep));
                }
                break;
            default:
                break;
            }
            return tempDep;
        }

        InstituteRepository saveInstitutesToRepository(InstituteRepository tempRep, Institute temp, java.lang.String endelementName,
                java.lang.String tag) {
            if (endelementName.equals("department")) {
                System.out.println(" [" + tag + "] getInst END -> " + endelementName);
                if (temp != null) {
//                    tempRep.saveInstitute(temp);
                }
            }
            return tempRep;
        }

        InstituteRepository getInst(XMLEventReader reader, java.lang.String tag) throws XMLStreamException {

            Institute tempInst = null;
            InstituteRepository tempInstRep = new InstituteRepository();

            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                switch (nextEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:

                    java.lang.String elementName = nextEvent.asStartElement().getName().getLocalPart();
                    System.out.println(" [" + tag + "] getInst start -> " + elementName);
                    tempInst = parseInstitutesTags(reader, elementName, tempInst);

                    break;
                case XMLStreamConstants.END_ELEMENT:

                    java.lang.String endelementName = nextEvent.asEndElement().getName().getLocalPart();
                    tempInstRep = saveInstitutesToRepository(tempInstRep, tempInst, endelementName, tag);
                    if (endelementName.equals(tag)) {
                        System.out.println("<------------- " + tag + " <-> ");
                        return tempInstRep;
                    }

                    break;
                }
            }
            return tempInstRep;

        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        private PatientService getPats(XMLEventReader reader, java.lang.String tag) throws XMLStreamException {
            Patient tempPat = null;
            PatientService tempPatRep = new PatientService();

            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                switch (nextEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:

                    java.lang.String elementName = nextEvent.asStartElement().getName().getLocalPart();
                    System.out.println(" [" + tag + "] getDeps start -> " + elementName);
                    tempPat = parsePatientTags(reader, elementName, tempPat);

                    break;
                case XMLStreamConstants.END_ELEMENT:

                    java.lang.String endelementName = nextEvent.asEndElement().getName().getLocalPart();
                    tempPatRep = savePatientToRepository(tempPatRep, tempPat, endelementName, tag);
                    if (endelementName.equals(tag)) {
                        System.out.println("<------------- " + tag + " <-> ");
                        return tempPatRep;
                    }

                    break;
                }
            }
            return tempPatRep;
        }

        private PatientService savePatientToRepository(PatientService tempPatRep, Patient tempPat, java.lang.String endelementName,
                                                       java.lang.String tag) {
            if (endelementName.equals("patient")) {
                System.out.println(" [" + tag + "] getPat END -> " + endelementName);
                if (tempPat != null) {
//                    tempPatRep.savePatient(tempPat);
                }
            }
            return tempPatRep;
        }

        private Patient parsePatientTags(XMLEventReader reader, java.lang.String elementName, Patient tempPat) throws XMLStreamException {
//            switch (elementName) {
//            case "patient":
//                tempPat = new Patient();
//                break;
//            case "id":
//                java.lang.String id = reader.nextEvent().asCharacters().getData();
//                if (!id.isEmpty()) {
//                    if (tempPat != null)
//                        tempPat.setId(id);
//                }
//                break;
//            case "name":
//                java.lang.String name = reader.nextEvent().asCharacters().getData();
//                if (!name.isEmpty()) {
//                    if (tempPat != null)
//                        tempPat.setName(name);
//                }
//                break;
//            case "email":
//                java.lang.String email = reader.nextEvent().asCharacters().getData();
//                if (!email.isEmpty()) {
//                    if (tempPat != null)
//                        tempPat.setEmail(email);
//                }
//                break;
//            case "username":
//                java.lang.String username = reader.nextEvent().asCharacters().getData();
//                if (!username.isEmpty()) {
//                    if (tempPat != null)
//                        tempPat.setUsername(username);
//                }
//                break;
//            case "department":
//                java.lang.String departmentId = reader.nextEvent().asCharacters().getData();
//                if (!departmentId.isEmpty()) {
//                    if (tempPat != null);{
////                        tempPat.setDepartmentId(depRepo.findDepartment(departmentId));
//                }
//                break;
//            case "institute":
//                java.lang.String stringId = reader.nextEvent().asCharacters().getData();
//                if (!stringId.isEmpty()) {
//                    if (tempPat != null){
////                        tempPat.setInstituteId(instRepo.findInstitute(stringId));
//                }
//                break;
//            }
//            return tempPat;
//        }
return null;
    }

    public static class ParseJson {

        private DepartmentRepository depRepo;
        private PatientService patRepo;
        private InstituteRepository instRepo;

        public PatientService getPatRepo() {
            return patRepo;
        }

        public InstituteRepository getInstRepo() {
            return instRepo;
        }

        public DepartmentRepository getDepRepo() {
            return depRepo;
        }

        public ParseJson() {

        }

        public void run(java.lang.String fileName) {
            try {
                this.readSample(this.parse(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public JsonParser parse(java.lang.String filename) throws JsonParseException, IOException {

            InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
            JsonFactory jfactory = new JsonFactory();
            JsonParser jParser = jfactory.createParser(in);

            return jParser;
        }

        void readSample(JsonParser jParser) throws IOException {
            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                java.lang.String fieldname = jParser.getCurrentName();

                if ("departments".equals(fieldname)) {
                    jParser.nextToken();
                    depRepo = readDepartments(jParser);
                }
                if ("institutes".equals(fieldname)) {
                    jParser.nextToken();
                    instRepo = readInstitute(jParser);
                }
                if ("patients".equals(fieldname)) {
                    jParser.nextToken();
                    patRepo = readPatients(jParser);
                }
            }
            jParser.close();
        }

        DepartmentRepository readDepartments(JsonParser jParser) throws IOException {
            DepartmentRepository tempDepRep = new DepartmentRepository();
            DepartmentEntity tempDep = null;

            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                java.lang.String fieldname = jParser.getCurrentName();

                if ("department".equals(fieldname)) {
                    jParser.nextToken();
                    JsonToken nextToken;

                    while ((nextToken = jParser.nextToken()) != JsonToken.END_ARRAY) {
                        switch (jParser.getText()) {
                        case "id":
                            tempDep = new DepartmentEntity();
                            jParser.nextToken();
                            tempDep.setId(jParser.getValueAsString());

                            break;
                        case "name":
                            jParser.nextToken();
                            tempDep.setName(jParser.getValueAsString());

                            break;
                        default:
                            if (nextToken == JsonToken.END_OBJECT) {
                                tempDepRep.saveDepartment(tempDep);
                            }
                            break;
                        }
                    }
                }
            }
            return tempDepRep;
        }

        InstituteRepository readInstitute(JsonParser jParser) throws IOException {
//            InstituteRepository tempInstRep = new InstituteRepository();
//            Institute tempInst = null;
//
//            while (jParser.nextToken() != JsonToken.END_OBJECT) {
//                java.lang.String fieldname = jParser.getCurrentName();
//
//                if ("institute".equals(fieldname)) {
//                    JsonToken nextToken;
//
//                    while ((nextToken = jParser.nextToken()) != JsonToken.END_ARRAY) {
//                        if ("id".equals(jParser.getValueAsString())) {
//                            jParser.nextToken();
//                            tempInst = new Institute();
//                            tempInst.setId(jParser.getValueAsString());
//                        }
//                        if ("name".equals(jParser.getValueAsString())) {
//                            jParser.nextToken();
//                            tempInst.setName(jParser.getValueAsString());
//                        }
//                        if ("departments".equals(jParser.getValueAsString())) {
//                            nextToken = jParser.nextToken();
//                            while (nextToken != JsonToken.END_ARRAY && nextToken != JsonToken.END_OBJECT) {
//                                java.lang.String depId = jParser.getValueAsString();
//                                if (depId != null && depId != "department") {
//                                    tempInst.addDepartments(depRepo.findDepartment(depId));
//                                }
//                                nextToken = jParser.nextToken();
//                            }
//                            tempInstRep.saveInstitute(tempInst);
//                        }
//                    }
//                }
//            }
            return null;
        }

        PatientService readPatients(JsonParser jParser) throws IOException {
            PatientService tempPatRep = new PatientService();
//            Patient tempPat = null;
//
//            while (jParser.nextToken() != JsonToken.END_OBJECT) {
//                java.lang.String fieldname = jParser.getCurrentName();
//
//                if ("patient".equals(fieldname)) {
//                    jParser.nextToken();
//
//                    while (jParser.nextToken() != JsonToken.END_ARRAY) {
//
//                        switch (jParser.getText()) {
//                        case "id":
//                            jParser.nextToken();
//                            tempPat = new Patient();
//                            tempPat.setId(jParser.getValueAsString());
//
//                            break;
//                        case "name":
//                            jParser.nextToken();
//                            tempPat.setName(jParser.getValueAsString());
//
//                            break;
//                        case "email":
//                            jParser.nextToken();
//                            tempPat.setEmail(jParser.getValueAsString());
//
//                            break;
//                        case "username":
//                            jParser.nextToken();
//                            tempPat.setUsername(jParser.getValueAsString());
//
//                            break;
//                        case "department":
//                            jParser.nextToken();
//                            tempPat.setDepartmentId(depRepo.findDepartment(jParser.getValueAsString()));
//
//                            break;
//                        case "institute":
//                            jParser.nextToken();
//                            tempPat.setInstituteId(instRepo.findInstitute(jParser.getValueAsString()));
//                            tempPatRep.savePatient(tempPat);
//
//                            break;
//                        default:
//                            break;
//                        }
//                    }
//                }
//            }
            return tempPatRep;
        }
    }}

}
