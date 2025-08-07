package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        int n;

        System.out.print("Nhập số bệnh nhân: ");
        try {
            n = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Số không hợp lệ. Mặc định = 1.");
            n = 1;
        }

        for (int i = 0; i < n; i++) {
            System.out.println("📝 Nhập thông tin bệnh nhân #" + (i + 1));
            try {
                System.out.print("ID: ");
                int id = Integer.parseInt(scanner.nextLine());

                System.out.print("Họ tên (2-32 ký tự): ");
                String name = scanner.nextLine();
                while (name.length() < 2 || name.length() > 32) {
                    System.out.print("⚠️ Nhập lại họ tên (2-32 ký tự): ");
                    name = scanner.nextLine();
                }

                System.out.print("Cân nặng (kg, < 150): ");
                int weight = Integer.parseInt(scanner.nextLine());
                while (weight >= 150) {
                    System.out.print("⚠️ Nhập lại cân nặng < 150: ");
                    weight = Integer.parseInt(scanner.nextLine());
                }

                System.out.print("Chiều cao (m, < 2.5): ");
                float height = Float.parseFloat(scanner.nextLine());
                while (height >= 2.5f) {
                    System.out.print("⚠️ Nhập lại chiều cao < 2.5: ");
                    height = Float.parseFloat(scanner.nextLine());
                }

                System.out.print("Nhóm máu (A/B/O): ");
                char bloodType = scanner.nextLine().toUpperCase().charAt(0);
                while (bloodType != 'A' && bloodType != 'B' && bloodType != 'O') {
                    System.out.print("⚠️ Nhập lại nhóm máu (A/B/O): ");
                    bloodType = scanner.nextLine().toUpperCase().charAt(0);
                }

                System.out.print("Giới tính (true = Nam, false = Nữ): ");
                boolean gender = Boolean.parseBoolean(scanner.nextLine());

                System.out.print("Ngày sinh (yyyy-MM-dd): ");
                LocalDate birthDate = LocalDate.parse(scanner.nextLine());

                Patient p = new Patient(id, name, weight, height, bloodType, gender, birthDate);
                patients.add(p);
                System.out.println("✅ Thêm bệnh nhân thành công!\n");

            } catch (Exception e) {
                System.out.println("❌ Lỗi nhập dữ liệu. Bỏ qua bệnh nhân này.\n");
            }
        }
    }

    public static void writeDataToFileXML() {
        try {
            PatientListWrapper wrapper = new PatientListWrapper();
            wrapper.setPatients(patients);

            JAXBContext context = JAXBContext.newInstance(PatientListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(wrapper, new File("patients.xml"));
            System.out.println("✅ XML file created: patients.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeDataToFileJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            mapper.writeValue(new File("patients.json"), patients);
            System.out.println("✅ JSON file created: patients.json");
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
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            List<Patient> readPatients = mapper.readValue(new File("patients.json"),
                    mapper.getTypeFactory().constructCollectionType(List.class, Patient.class));
            System.out.println("📄 JSON Data:");
            for (Patient p : readPatients) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
