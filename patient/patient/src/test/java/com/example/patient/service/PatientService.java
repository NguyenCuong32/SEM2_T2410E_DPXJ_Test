package com.example.patient.service;

import com.example.patient.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Patient> inputData() {
        List<Patient> list = new ArrayList<>();

        Patient p1 = new Patient();
        p1.setId(1);
        p1.setFullname("Larry Ellison");
        p1.setWeight(70);
        p1.setHeight(1.75f);
        p1.setBloodType('A');
        p1.setGender(true);
        p1.setBirthDate(LocalDate.of(1998, 12, 25));

        if (p1.isValid()) {
            list.add(p1);
        }

        return list;
    }

    public void writeToXML(List<Patient> patients, String filePath) throws Exception {
        PatientList patientList = new PatientList(patients);
        JAXBContext context = JAXBContext.newInstance(PatientList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(patientList, new File(filePath));
    }

    public void writeToJSON(List<Patient> patients, String filePath) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), patients);
    }

    public List<Patient> readFromXML(String filePath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(PatientList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PatientList list = (PatientList) unmarshaller.unmarshal(new File(filePath));
        list.getPatients().forEach(System.out::println);
        return list.getPatients();
    }

    public List<Patient> readFromJSON(String filePath) throws IOException {
        List<Patient> list = List.of(objectMapper.readValue(new File(filePath), Patient[].class));
        list.forEach(System.out::println);
        return list;
    }
}
