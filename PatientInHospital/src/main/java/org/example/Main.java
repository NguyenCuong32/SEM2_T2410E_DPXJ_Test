package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class to manage person data.
 * Provides a command-line interface to perform operations like
 * adding persons, and reading/writing person data to JSON and XML files.
 */
public class Main {

    private List<Person> personList = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final ObjectMapper objectMapper;
    private static final String JSON_FILE = "persons.json";
    private static final String XML_FILE = "persons.xml";

    public Main() {

        this.objectMapper = new ObjectMapper();

        this.objectMapper.registerModule(new JavaTimeModule());

        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.runMenu();
    }

    /**
     * Displays the main menu and handles user input.
     */
    public void runMenu() {
        while (true) {
            System.out.println("\n--- Person Data Management System ---");
            System.out.println("1. Input Person Data");
            System.out.println("2. Write Data to JSON File");
            System.out.println("3. Write Data to XML File");
            System.out.println("4. Read Data from JSON File");
            System.out.println("5. Read Data from XML File");
            System.out.println("6. Display Current Person List");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        inputData();
                        break;
                    case 2:
                        writeDataToFileJSON();
                        break;
                    case 3:
                        writeDataToFileXML();
                        break;
                    case 4:
                        readDataFromFileJSON();
                        break;
                    case 5:
                        readDataFromFileXML();
                        break;
                    case 6:
                        displayPersonList();
                        break;
                    case 0:
                        System.out.println("Exiting application. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }


    public void inputData() {
        System.out.println("\n--- Enter New Person Data ---");
        try {
            System.out.print("Enter ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Full Name: ");
            String fullname = scanner.nextLine();

            System.out.print("Enter Weight (in Kg, e.g., 75 for 75kg): ");
            int weight = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Height (in meters, e.g., 1.85): ");
            float height = Float.parseFloat(scanner.nextLine());

            System.out.print("Enter Blood Type (A, B, or O): ");
            char bloodType = scanner.nextLine().charAt(0);

            System.out.print("Enter Gender (true for Male, false for Female): ");
            boolean gender = Boolean.parseBoolean(scanner.nextLine());

            System.out.print("Enter Birth Date (yyyy-MM-dd): ");
            LocalDate birthDate = LocalDate.parse(scanner.nextLine());

            Person newPerson = new Person(id, fullname, weight, height, bloodType, gender, birthDate);
            personList.add(newPerson);
            System.out.println("Person added successfully!");

        } catch (DateTimeParseException e) {
            System.err.println("Error: Invalid date format. Please use yyyy-MM-dd.");
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format for ID, weight, or height.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid data - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    public void writeDataToFileJSON() {
        if (personList.isEmpty()) {
            System.out.println("Person list is empty. Nothing to write.");
            return;
        }
        try {
            objectMapper.writeValue(new File(JSON_FILE), personList);
            System.out.println("Successfully wrote " + personList.size() + " person(s) to " + JSON_FILE);
        } catch (IOException e) {
            System.err.println("Error writing to JSON file: " + e.getMessage());
        }
    }


    public void writeDataToFileXML() {
        if (personList.isEmpty()) {
            System.out.println("Person list is empty. Nothing to write.");
            return;
        }
        try {
            // JAXB context needs to know about the classes to be marshalled
            JAXBContext context = JAXBContext.newInstance(PersonList.class, Person.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Pretty print XML

            // Wrap the list in the PersonList class for correct XML structure
            PersonList wrapper = new PersonList(personList);
            marshaller.marshal(wrapper, new File(XML_FILE));
            System.out.println("Successfully wrote " + personList.size() + " person(s) to " + XML_FILE);
        } catch (JAXBException e) {
            System.err.println("Error writing to XML file: " + e.getMessage());
        }
    }


    public void readDataFromFileXML() {
        File xmlFile = new File(XML_FILE);
        if (!xmlFile.exists()) {
            System.err.println("Error: " + XML_FILE + " not found.");
            return;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(PersonList.class, Person.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Unmarshal the XML file back to the PersonList wrapper object
            PersonList wrapper = (PersonList) unmarshaller.unmarshal(xmlFile);
            this.personList = wrapper.getPersonList();
            System.out.println("Successfully read " + personList.size() + " person(s) from " + XML_FILE);
            displayPersonList();
        } catch (JAXBException e) {
            System.err.println("Error reading or parsing XML file: " + e.getMessage());
        }
    }


    public void readDataFromFileJSON() {
        File jsonFile = new File(JSON_FILE);
        if (!jsonFile.exists()) {
            System.err.println("Error: " + JSON_FILE + " not found.");
            return;
        }
        try {
            // Read the JSON file and convert it to a List of Person objects
            List<Person> loadedPersons = objectMapper.readValue(jsonFile, new TypeReference<List<Person>>() {});
            this.personList = loadedPersons;
            System.out.println("Successfully read " + personList.size() + " person(s) from " + JSON_FILE);
            displayPersonList();
        } catch (IOException e) {
            System.err.println("Error reading or parsing JSON file: " + e.getMessage());
        }
    }


    private void displayPersonList() {
        if (personList.isEmpty()) {
            System.out.println("The person list is currently empty.");
        } else {
            System.out.println("\n--- Current Person List ---");
            personList.forEach(System.out::println);
            System.out.println("----------------------------");
        }
    }
}