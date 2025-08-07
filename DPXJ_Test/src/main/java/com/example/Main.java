package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Patient> patients = new ArrayList<>();

    public static void main(String[] args) {
        inputData();
        writeDataToFileXML();
        writeDataToFileJSON();
        readDataFromFileXML();
        readDataFromFileJSON();
    }

    public static void inputData() {
        patients.add(new Patient(1, "Larry Ellison", 70, 1.75f, 'A', true, LocalDate.of(1998, 12, 25)));
        patients.add(new Patient(2, "Elon Musk", 80, 1.85f, 'B', true, LocalDate.of(1980, 6, 28)));
        patients.add(new Patient(3, "Ada Lovelace", 55, 1.65f, 'O', false, LocalDate.of(1990, 3, 1)));
    }

    public static void writeDataToFileXML() {
        try {
            PatientListWrapper wrapper = new PatientListWrapper();
            wrapper.setPatients(patients);

            JAXBContext context = JAXBContext.newInstance(PatientListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(wrapper, new File("patients.xml"));
            System.out.println(" XML file created: patients.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeDataToFileJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules(); // for LocalDate
            mapper.writeValue(new File("patients.json"), patients);
            System.out.println(" JSON file created: patients.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readDataFromFileXML() {
        try {
            JAXBContext context = JAXBContext.newInstance(PatientListWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            PatientListWrapper wrapper = (PatientListWrapper) unmarshaller.unmarshal(new File("patients.xml"));
            System.out.println("📄 XML Data:");
            for (Patient p : wrapper.getPatients()) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readDataFromFileJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules(); // for LocalDate

            List<Patient> readPatients = mapper.readValue(new File("patients.json"), new TypeReference<List<Patient>>() {});
            System.out.println("📄 JSON Data:");
            for (Patient p : readPatients) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
