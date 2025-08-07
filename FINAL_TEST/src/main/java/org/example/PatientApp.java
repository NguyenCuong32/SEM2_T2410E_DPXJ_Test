package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.xml.bind.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatientApp {
    private static List<Patient> patientList = new ArrayList<>();

    public static void inputData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số lượng bệnh nhân: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("Bệnh nhân #" + (i + 1));
            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());

            String name;
            do {
                System.out.print("Họ tên (2-32 ký tự): ");
                name = sc.nextLine();
            } while (name.length() < 2 || name.length() > 32);

            int weight;
            do {
                System.out.print("Cân nặng (kg, < 150): ");
                weight = Integer.parseInt(sc.nextLine());
            } while (weight >= 150);

            float height;
            do {
                System.out.print("Chiều cao (m, < 2.5): ");
                height = Float.parseFloat(sc.nextLine());
            } while (height >= 2.5f);

            char bloodType;
            do {
                System.out.print("Nhóm máu (A, B, O): ");
                bloodType = sc.nextLine().toUpperCase().charAt(0);
            } while (!(bloodType == 'A' || bloodType == 'B' || bloodType == 'O'));

            System.out.print("Giới tính (true=Nam, false=Nữ): ");
            boolean gender = Boolean.parseBoolean(sc.nextLine());

            System.out.print("Ngày sinh (yyyy-mm-dd): ");
            LocalDate birthDate = LocalDate.parse(sc.nextLine());

            patientList.add(new Patient(id, name, weight, height, bloodType, gender, birthDate));
        }
    }

    public static void writeDataToFileXML() throws Exception {
        JAXBContext context = JAXBContext.newInstance(PatientListWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        PatientListWrapper wrapper = new PatientListWrapper();
        wrapper.setPatients(patientList);

        marshaller.marshal(wrapper, new File("patients.xml"));
        System.out.println(" Ghi file XML thành công!");
    }

    public static void writeDataToFileJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("patients.json"), patientList);
        System.out.println(" Ghi file JSON thành công!");
    }

    public static void readDataFromFileXML() throws Exception {
        JAXBContext context = JAXBContext.newInstance(PatientListWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        PatientListWrapper wrapper = (PatientListWrapper) unmarshaller.unmarshal(new File("patients.xml"));
        wrapper.getPatients().forEach(System.out::println);
    }

    public static void readDataFromFileJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<Patient> list = mapper.readValue(new File("patients.json"), new TypeReference<List<Patient>>() {});
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {
        try {
            inputData();
            writeDataToFileXML();
            writeDataToFileJSON();
            System.out.println("\n Đọc từ file XML:");
            readDataFromFileXML();
            System.out.println("\n Đọc từ file JSON:");
            readDataFromFileJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
