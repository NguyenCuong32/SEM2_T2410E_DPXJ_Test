import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Patient> patients = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void inputData() {
        System.out.print("Enter number of patients: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.println("Patient #" + (i + 1));
            System.out.print("Id: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Fullname: ");
            String name = sc.nextLine();
            System.out.print("Weight (kg): ");
            int weight = Integer.parseInt(sc.nextLine());
            System.out.print("Height (m): ");
            float height = Float.parseFloat(sc.nextLine());
            System.out.print("Blood Type (A/B/O): ");
            char bloodType = sc.nextLine().charAt(0);
            System.out.print("Gender (true = male, false = female): ");
            boolean gender = Boolean.parseBoolean(sc.nextLine());
            System.out.print("Birthdate (yyyy-mm-dd): ");
            LocalDate birthDate = LocalDate.parse(sc.nextLine());

            patients.add(new Patient(id, name, weight, height, bloodType, gender, birthDate));
        }
    }

    public static void writeDataToFileXML() {
        try {
            JAXBContext context = JAXBContext.newInstance(PatientList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            PatientList plist = new PatientList(patients);
            marshaller.marshal(plist, new File("patients.xml"));
            System.out.println("Data written to patients.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeDataToFileJSON() {
        try (Writer writer = new FileWriter("patients.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // << THÊM DÒNG NÀY
                    .setPrettyPrinting()
                    .create();

            gson.toJson(patients, writer);
            System.out.println("Data written to patients.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void readDataFromFileXML() {
        try {
            JAXBContext context = JAXBContext.newInstance(PatientList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            PatientList plist = (PatientList) unmarshaller.unmarshal(new File("patients.xml"));
            plist.getPatients().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readDataFromFileJSON() {
        try (Reader reader = new FileReader("patients.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .setPrettyPrinting()
                    .create();

            Patient[] arr = gson.fromJson(reader, Patient[].class);
            for (Patient p : arr) {
                System.out.println(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        inputData();
        writeDataToFileXML();
        writeDataToFileJSON();
        System.out.println("\n--- Read from XML ---");
        readDataFromFileXML();
        System.out.println("\n--- Read from JSON ---");
        readDataFromFileJSON();
    }
}
