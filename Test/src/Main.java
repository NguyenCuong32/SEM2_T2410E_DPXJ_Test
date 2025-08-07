public class Main {
    public static void main(String[] args) throws Exception {
        PatientManager manager = new PatientManager();

        manager.inputData(); // Nhập từ bàn phím

        manager.writeDataToFileXML("patients.xml");
        manager.writeDataToFileJSON("patients.json");

        System.out.println("\n--- DỮ LIỆU TỪ XML ---");
        manager.readDataFromFileXML("patients.xml");

        System.out.println("\n--- DỮ LIỆU TỪ JSON ---");
        manager.readDataFromFileJSON("patients.json");
    }
}
