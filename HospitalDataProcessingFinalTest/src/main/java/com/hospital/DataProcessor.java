package com.hospital;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.hospital.model.Patient;
import com.hospital.model.PatientListWrapper;

import jakarta.xml.bind.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataProcessor {

    public static List<Patient> inputData() {
        List<Patient> patients = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập số lượng bệnh nhân: ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("Nhập thông tin bệnh nhân #" + (i + 1));

            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Họ tên: ");
            String fullname = scanner.nextLine();

            System.out.print("Cân nặng (kg): ");
            int weight = Integer.parseInt(scanner.nextLine());

            System.out.print("Chiều cao (m): ");
            float height = Float.parseFloat(scanner.nextLine());

            System.out.print("Nhóm máu (A/B/AB/O): ");
            char bloodType = scanner.nextLine().toUpperCase().charAt(0);

            System.out.print("Giới tính (nam = true, nữ = false): ");
            boolean gender = Boolean.parseBoolean(scanner.nextLine());

            System.out.print("Ngày sinh (yyyy-MM-dd): ");
            LocalDate birthDate = LocalDate.parse(scanner.nextLine());

            patients.add(new Patient(id, fullname, weight, height, bloodType, gender, birthDate));
        }

        return patients;
    }

    public static void writeDataToFileXML(List<Patient> patients, String filename) throws Exception {
        JAXBContext context = JAXBContext.newInstance(PatientListWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        PatientListWrapper wrapper = new PatientListWrapper();
        wrapper.setPatients(patients);

        marshaller.marshal(wrapper, new File(filename));
    }

    public static void writeDataToFileJSON(List<Patient> patients, String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); //
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); //
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filename), patients);
    }

    public static List<Patient> readDataFromFileXML(String filename) throws Exception {
        JAXBContext context = JAXBContext.newInstance(PatientListWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PatientListWrapper wrapper = (PatientListWrapper) unmarshaller.unmarshal(new File(filename));
        return wrapper.getPatients();
    }

    public static List<Patient> readDataFromFileJSON(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); //
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readValue(new File(filename), new TypeReference<List<Patient>>() {});
    }
}