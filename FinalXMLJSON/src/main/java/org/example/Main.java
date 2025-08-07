package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    private List<Patient> patientList;
    private static final String XML_FILE = "patients.xml";
    private static final String JSON_FILE = "patients.json";

    public Main() {
        this.patientList = new ArrayList<>();
    }

    // 1. inputData()
    public void inputData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of patients to input: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Entering data for patient " + (i + 1) + " ---");
            try {
                System.out.print("ID: ");
                int id = Integer.parseInt(sc.nextLine());

                System.out.print("Fullname: ");
                String fullname = sc.nextLine();

                System.out.print("Weight (kg): ");
                int weight = Integer.parseInt(sc.nextLine());

                System.out.print("Height (m): ");
                float height = Float.parseFloat(sc.nextLine());

                System.out.print("Blood Type (A, B, O): ");
                char bloodType = sc.nextLine().charAt(0);

                System.out.print("Gender (true for Male, false for Female): ");
                boolean gender = Boolean.parseBoolean(sc.nextLine());

                System.out.print("Birth Date (YYYY-MM-DD): ");
                LocalDate birthDate = LocalDate.parse(sc.nextLine());

                Patient p = new Patient(id, fullname, weight, height, bloodType, gender, birthDate);
                patientList.add(p);
                System.out.println("Patient added successfully!");

            } catch (IllegalArgumentException | DateTimeParseException | IndexOutOfBoundsException e) {
                System.err.println("Invalid data entered: " + e.getMessage() + ". Please try again for this patient.");
                i--;
            }
        }
    }
    public void writeDataToFileXML() {
        if (patientList.isEmpty()) {
            System.out.println("Patient list is empty. Nothing to write.");
            return;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(PatientsWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            PatientsWrapper wrapper = new PatientsWrapper(patientList);
            marshaller.marshal(wrapper, new File(XML_FILE));
            System.out.println("Successfully exported data to " + XML_FILE);
        } catch (Exception e) {
            System.err.println("Error writing to XML file: " + e.getMessage());
        }
    }
    public void writeDataToFileJSON() {
        if (patientList.isEmpty()) {
            System.out.println("Patient list is empty. Nothing to write.");
            return;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); //
            mapper.enable(SerializationFeature.INDENT_OUTPUT); //

            mapper.writeValue(new File(JSON_FILE), patientList);
            System.out.println("Successfully exported data to " + JSON_FILE);
        } catch (Exception e) {
            System.err.println("Error writing to JSON file: " + e.getMessage());
        }
    }public void readDataFromFileXML() {
        try {
            File file = new File(XML_FILE);
            if (!file.exists()) {
                System.out.println("XML file not found.");
                return;
            }

            JAXBContext context = JAXBContext.newInstance(PatientsWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            PatientsWrapper wrapper = (PatientsWrapper) unmarshaller.unmarshal(file);
            this.patientList = wrapper.getPatients();

            System.out.println("\n--- Data imported from " + XML_FILE + " ---");
            patientList.forEach(System.out::println);
            System.out.println("------------------------------------");

        } catch (Exception e) {
            System.err.println("Error reading from XML file: " + e.getMessage());
        }
    }
    public void readDataFromFileJSON() {
        try {
            File file = new File(JSON_FILE);
            if (!file.exists()) {
                System.out.println("JSON file not found.");
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            this.patientList = mapper.readValue(file, new TypeReference<List<Patient>>() {});

            System.out.println("\n--- Data imported from " + JSON_FILE + " ---");
            patientList.forEach(System.out::println);
            System.out.println("------------------------------------");

        } catch (Exception e) {
            System.err.println("Error reading from JSON file: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        Main manager = new Main();
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n========== PATIENT MANAGEMENT SYSTEM ==========");
            System.out.println("1. Input patient data");
            System.out.println("2. Export list to XML file");
            System.out.println("3. Export list to JSON file");
            System.out.println("4. Import data from XML and display");
            System.out.println("5. Import data from JSON and display");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    manager.inputData();
                    break;
                case 2:
                    manager.writeDataToFileXML();
                    break;
                case 3:
                    manager.writeDataToFileJSON();
                    break;
                case 4:
                    manager.readDataFromFileXML();
                    break;
                case 5:
                    manager.readDataFromFileJSON();
                    break;
                case 0:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        sc.close();
    }
}
