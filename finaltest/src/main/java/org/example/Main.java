package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thoughtworks.xstream.XStream;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Patient> patients = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Nhập dữ liệu");
            System.out.println("2. Ghi file XML");
            System.out.println("3. Ghi file JSON");
            System.out.println("4. Đọc file XML");
            System.out.println("5. Đọc file JSON");
            System.out.println("6. Thoát");
            System.out.print("Chọn: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> inputData();
                case 2 -> writeDataToFileXML("patients.xml");
                case 3 -> writeDataToFileJSON("patients.json");
                case 4 -> readDataFromFileXML("patients.xml");
                case 5 -> readDataFromFileJSON("patients.json");
                case 6 -> System.exit(0);
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }


    public static void inputData() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số bệnh nhân: ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("\n-- Bệnh nhân " + (i + 1) + " --");
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Họ tên: ");
            String name = scanner.nextLine();
            while (name.length() < 2 || name.length() > 32) {
                System.out.print("Tên không hợp lệ. Nhập lại: ");
                name = scanner.nextLine();
            }

            System.out.print("Cân nặng (kg): ");
            int weight = Integer.parseInt(scanner.nextLine());
            while (weight > 150) {
                System.out.print("Cân nặng phải < 150kg. Nhập lại: ");
                weight = Integer.parseInt(scanner.nextLine());
            }

            System.out.print("Chiều cao (m): ");
            float height = Float.parseFloat(scanner.nextLine());
            while (height > 2.5f) {
                System.out.print("Chiều cao phải < 2.5m. Nhập lại: ");
                height = Float.parseFloat(scanner.nextLine());
            }

            System.out.print("Nhóm máu (A/B/O): ");
            char blood = scanner.nextLine().toUpperCase().charAt(0);
            while (blood != 'A' && blood != 'B' && blood != 'O') {
                System.out.print("Chỉ chấp nhận A/B/O. Nhập lại: ");
                blood = scanner.nextLine().toUpperCase().charAt(0);
            }

            System.out.print("Giới tính (true=Nam, false=Nữ): ");
            boolean gender = Boolean.parseBoolean(scanner.nextLine());

            System.out.print("Ngày sinh (yyyy-mm-dd): ");
            LocalDate birth = LocalDate.parse(scanner.nextLine());

            patients.add(new Patient(id, name, weight, height, blood, gender, birth));
        }
    }


    public static void writeDataToFileXML(String filename) throws IOException {
        XStream xstream = new XStream();
        xstream.allowTypes(new Class[] { Patient.class });
        xstream.alias("patient", Patient.class);

        try (FileWriter writer = new FileWriter(filename)) {
            xstream.toXML(patients, writer);
            System.out.println("Đã ghi XML thành công.");
        }
    }


    public static void writeDataToFileJSON(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try (FileWriter writer = new FileWriter(filename)) {
            mapper.writeValue(writer, patients);
            System.out.println("Đã ghi JSON thành công.");
        }
    }


    public static void readDataFromFileXML(String filename) throws IOException {
        XStream xstream = new XStream();
        xstream.allowTypes(new Class[] { Patient.class });
        xstream.alias("patient", Patient.class);

        try (FileReader reader = new FileReader(filename)) {
            List<Patient> list = (List<Patient>) xstream.fromXML(reader);
            list.forEach(System.out::println);
        }
    }


    public static void readDataFromFileJSON(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (FileReader reader = new FileReader(filename)) {
            List<Patient> list = mapper.readValue(reader,
                    mapper.getTypeFactory().constructCollectionType(List.class, Patient.class));
            list.forEach(System.out::println);
        }
    }
}
