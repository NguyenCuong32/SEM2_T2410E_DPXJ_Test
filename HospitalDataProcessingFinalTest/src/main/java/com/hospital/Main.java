package com.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.hospital.model.Patient;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Patient> patients = DataProcessor.inputData(); // nhập tay từ bàn phím

        // Ghi XML
        DataProcessor.writeDataToFileXML(patients, "patients.xml");

        // Ghi JSON (kèm xử lý LocalDate)
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("patients.json"), patients);

        // Đọc từ XML
        System.out.println("=== Read from XML ===");
        List<Patient> xmlPatients = DataProcessor.readDataFromFileXML("patients.xml");
        xmlPatients.forEach(System.out::println);

        // Đọc từ JSON (kèm xử lý LocalDate)
        System.out.println("=== Read from JSON ===");
        List<Patient> jsonPatients = mapper.readValue(new File("patients.json"),
                mapper.getTypeFactory().constructCollectionType(List.class, Patient.class));
        jsonPatients.forEach(System.out::println);
    }
}