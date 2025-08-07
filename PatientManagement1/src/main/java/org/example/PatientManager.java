package org.example;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import jakarta.xml.bind.*;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

public class PatientManager {

    private static final String XML_FILENAME = "patients.xml";
    private static final String JSON_FILENAME = "patients.json";
    private List<Patient> patientList = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    // Phương thức 1: Nhập dữ liệu từ bàn phím
    public List<Patient> inputData() {
        List<Patient> list = new ArrayList<>();
        System.out.println("--- Nhap so benh nhan ---");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.println("\nNhap thong tin benh nhan thu " + (i + 1) + ":");

            int id;
            String fullname;
            int weight;
            float height;
            char bloodType;
            boolean gender;
            LocalDate birthDate;

            // Nhập ID
            while (true) {
                try {
                    System.out.print("ID: ");
                    id = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Loi: ID phai la so. Vui long nhap lai.");
                }
            }

            // Nhập Ho và tên
            while (true) {
                System.out.print("Ho va ten: ");
                fullname = scanner.nextLine();
                if (fullname.length() >= 2 && fullname.length() <= 32) {
                    break;
                } else {
                    System.out.println("Loi: Ho va ten phai tu 2 den 32 ky tu. Vui long nhap lai.");
                }
            }

            // Nhập Cân nặng
            while (true) {
                try {
                    System.out.print("Can nang (kg, <150): ");
                    weight = Integer.parseInt(scanner.nextLine());
                    if (weight > 0 && weight < 150) {
                        break;
                    } else {
                        System.out.println("Loi: Can nang phai lon hon 0 va nho hon 150. Vui long nhap lai.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Loi: Can nang phai la so. Vui long nhap lai.");
                }
            }

            // Nhập Chiều cao
            while (true) {
                try {
                    System.out.print("Chieu cao (m, <2.5): ");
                    height = Float.parseFloat(scanner.nextLine());
                    if (height > 0 && height < 2.5f) {
                        break;
                    } else {
                        System.out.println("Loi: Chieu cao phai lon hon 0 va nho hon 2.5. Vui long nhap lai.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Loi: Chieu cao phai la so. Vui long nhap lai.");
                }
            }

            // Nhập Nhóm máu
            while (true) {
                System.out.print("Nhom mau ('A', 'B', 'O'): ");
                String bloodTypeInput = scanner.nextLine().toUpperCase();
                if (bloodTypeInput.length() == 1 && (bloodTypeInput.charAt(0) == 'A' || bloodTypeInput.charAt(0) == 'B' || bloodTypeInput.charAt(0) == 'O')) {
                    bloodType = bloodTypeInput.charAt(0);
                    break;
                } else {
                    System.out.println("Loi: Nhom mau khong hop le. Vui long nhap 'A', 'B' hoac 'O'.");
                }
            }

            // Nhập Giới tính
            while (true) {
                System.out.print("Gioi tinh (true=Nam, false=Nu): ");
                String genderInput = scanner.nextLine().toLowerCase();
                if (genderInput.equals("true") || genderInput.equals("false")) {
                    gender = Boolean.parseBoolean(genderInput);
                    break;
                } else {
                    System.out.println("Loi: Gioi tinh khong hop le. Vui long nhap 'true' hoac 'false'.");
                }
            }

            // Nhập Ngày sinh
            while (true) {
                System.out.print("Ngay sinh (yyyy-MM-dd): ");
                String birthDateInput = scanner.nextLine();
                try {
                    birthDate = LocalDate.parse(birthDateInput);
                    break;
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Loi: Dinh dang ngay sinh khong hop le. Vui long nhap theo dinh dang yyyy-MM-dd.");
                }
            }

            list.add(new Patient(id, fullname, weight, height, bloodType, gender, birthDate));
        }
        return list;
    }

    // Phương thức 2: Ghi ra file XML
    public void writeDataToFileXML(List<Patient> list, String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(PatientListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            PatientListWrapper wrapper = new PatientListWrapper();
            wrapper.setPatients(list);
            File file = new File(filename);
            marshaller.marshal(wrapper, file);
            System.out.println("Data successfully written to " + file.getAbsolutePath());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    // Phương thức 3: Ghi ra file JSON
    public void writeDataToFileJSON(List<Patient> list, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(list, writer);
            System.out.println("Data successfully written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức 4: Đọc từ file XML
    public void readDataFromFileXML(String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(PatientListWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("File not found: " + filename);
                return;
            }
            PatientListWrapper wrapper = (PatientListWrapper) unmarshaller.unmarshal(file);
            System.out.println("Data successfully imported from " + filename + ":");
            wrapper.getPatients().forEach(System.out::println);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    // Phương thức 5: Đọc từ file JSON
    public void readDataFromFileJSON(String filename) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateDeserializer()).create();
        try (FileReader reader = new FileReader(filename)) {
            Type patientListType = new TypeToken<ArrayList<Patient>>(){}.getType();
            List<Patient> list = gson.fromJson(reader, patientListType);
            System.out.println("Data successfully imported from " + filename + ":");
            list.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Các class nội bộ cho Gson
    private static class LocalDateSerializer implements JsonSerializer<LocalDate> {
        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    private static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString());
        }
    }

    // Phần logic để chạy menu
    public void runMenu() {
        int choice = 0;
        while (choice != 6) {
            displayMenu();
            System.out.print("Chon: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                processChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so.");
            }
        }
        System.out.println("Chuong trinh ket thuc.");
    }

    private void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Nhap du lieu");
        System.out.println("2. Ghi file XML");
        System.out.println("3. Ghi file JSON");
        System.out.println("4. Doc file XML");
        System.out.println("5. Doc file JSON");
        System.out.println("6. Thoat");
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                patientList = inputData();
                System.out.println("Da nhap " + patientList.size() + " benh nhan.");
                break;
            case 2:
                if (patientList.isEmpty()) {
                    System.out.println("Vui long nhap du lieu truoc (Chon 1).");
                } else {
                    writeDataToFileXML(patientList, XML_FILENAME);
                }
                break;
            case 3:
                if (patientList.isEmpty()) {
                    System.out.println("Vui long nhap du lieu truoc (Chon 1).");
                } else {
                    writeDataToFileJSON(patientList, JSON_FILENAME);
                }
                break;
            case 4:
                readDataFromFileXML(XML_FILENAME);
                break;
            case 5:
                readDataFromFileJSON(JSON_FILENAME);
                break;
            case 6:
                break;
            default:
                System.out.println("Lua chon khong hop le. Vui long nhap tu 1 den 6.");
        }
    }

    // Hàm main để chạy chương trình
    public static void main(String[] args) {
        PatientManager manager = new PatientManager();
        manager.runMenu();
    }
}