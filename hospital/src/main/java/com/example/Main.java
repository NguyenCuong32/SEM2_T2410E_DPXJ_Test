package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static List<Patient> inputData() {
        List<Patient> patients = new ArrayList<>();
        System.out.print("Enter number of patients: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.println("Enter information for patient " + (i + 1) + ":");

            System.out.print("Id: ");
            int id = Integer.parseInt(sc.nextLine());
            String fullName;
            do {
                System.out.print("Full name (2-32 characters): ");
                fullName = sc.nextLine();
            } while (fullName.length() < 2 || fullName.length() > 32);
            int weight;
            do {
                System.out.print("Weight (<=150kg): ");
                weight = Integer.parseInt(sc.nextLine());
            } while (weight > 150);

            float height;
            do {
                System.out.print("Height (<=2.5m): ");
                height = Float.parseFloat(sc.nextLine());
            } while (height > 2.5f);

            char bloodType;
            do {
                System.out.print("Blood type (A/B/O): ");
                bloodType = sc.nextLine().toUpperCase().charAt(0);
            } while (bloodType != 'A' && bloodType != 'B' && bloodType != 'O');

            System.out.print("Gender (true = Male, false = Female): ");
            boolean gender = Boolean.parseBoolean(sc.nextLine());

            System.out.print("Birth date (yyyy-mm-dd): ");
            LocalDate birthDate = LocalDate.parse(sc.nextLine());

            patients.add(new Patient(id, fullName, weight, height, bloodType, gender, birthDate));
        }
        return patients;
    }

    public static void writeDataToXmlFile(List<Patient> patients, String filename) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.writeValue(new File(filename), patients);
    }

    public static void readDataFromXmlFile(String filename) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        List<Patient> patients = xmlMapper.readValue(new File(filename), new TypeReference<List<Patient>>() {
        });
        patients.forEach(System.out::println);
    }

    public static void writeDataToJsonFile(List<Patient> patients, String filename) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writeValue(new File(filename), patients);
    }

    public static void readDataFromJsonFile(String filename) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<Patient> patients = objectMapper.readValue(new File(filename), new TypeReference<List<Patient>>() {
        });
        patients.forEach(System.out::println);
    }

    public static void main(String[] args) throws Exception {
        List<Patient> patients = inputData();

        writeDataToXmlFile(patients, "patients.xml");
        writeDataToJsonFile(patients, "patients.json");

        System.out.println("\n XML Data");
        readDataFromXmlFile("patients.xml");

        System.out.println("\n JSON Data");
        readDataFromJsonFile("patients.json");
    }
}