package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static final String XML_FILE = "patients.xml";
    static final String JSON_FILE = "patients.json";
    static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static final PatientList patientList = new PatientList();

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("""
                ===== PATIENT MANAGER =====
                1. Input data
                2. Write data to XML
                3. Write data to JSON
                4. Read from XML and display
                5. Read from JSON and display
                0. Exit
                ============================
                """);
            System.out.print("Choose: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> inputData();
                case 2 -> writeDataToFileXML();
                case 3 -> writeDataToFileJSON();
                case 4 -> readDataFromFileXML();
                case 5 -> readDataFromFileJSON();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public static void inputData() {
        System.out.print("Enter number of patients: ");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.println("Patient #" + (i + 1));
            try {
                System.out.print("ID: ");
                int id = Integer.parseInt(scanner.nextLine());

                String name;
                do {
                    System.out.print("Name (2-32 chars): ");
                    name = scanner.nextLine();
                } while (name.length() < 2 || name.length() > 32);

                int weight;
                do {
                    System.out.print("Weight (kg, max 150): ");
                    weight = Integer.parseInt(scanner.nextLine());
                } while (weight > 150);

                float height;
                do {
                    System.out.print("Height (m, max 2.5): ");
                    height = Float.parseFloat(scanner.nextLine());
                } while (height > 2.5f);

                char blood;
                do {
                    System.out.print("Blood Type (A/B/O): ");
                    blood = scanner.nextLine().toUpperCase().charAt(0);
                } while ("ABO".indexOf(blood) == -1);

                System.out.print("Gender (true=male, false=female): ");
                boolean gender = Boolean.parseBoolean(scanner.nextLine());

                System.out.print("Birthdate (YYYY-MM-DD): ");
                LocalDate dob = LocalDate.parse(scanner.nextLine());

                patientList.add(new Patient(id, name, weight, height, blood, gender, dob));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                i--;
            }
        }
    }

    public static void writeDataToFileXML() throws Exception {
        JAXBContext context = JAXBContext.newInstance(PatientList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(patientList, new File(XML_FILE));
        System.out.println("✅ XML file saved: " + XML_FILE);
    }

    public static void writeDataToFileJSON() throws Exception {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(patientList.getPatients(), writer);
            System.out.println("✅ JSON file saved: " + JSON_FILE);
        }
    }

    public static void readDataFromFileXML() throws Exception {
        JAXBContext context = JAXBContext.newInstance(PatientList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PatientList list = (PatientList) unmarshaller.unmarshal(new File(XML_FILE));
        System.out.println("📄 Patients from XML:");
        list.getPatients().forEach(System.out::println);
    }

    public static void readDataFromFileJSON() throws Exception {
        try (FileReader reader = new FileReader(JSON_FILE)) {
            List<Patient> patients = gson.fromJson(reader, new TypeToken<List<Patient>>() {}.getType());
            System.out.println("📄 Patients from JSON:");
            patients.forEach(System.out::println);
        }
    }
}