import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public class PatientManager {
    private List<Patient> patients = new ArrayList<>();

    public void inputData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số lượng bệnh nhân: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("=== Nhập bệnh nhân thứ " + (i + 1) + " ===");

            int id = prompt(sc, "ID: ", Integer::parseInt);

            String fullname = prompt(sc, "Họ tên: ", (String s) -> {
                if (s.length() < 2 || s.length() > 32) throw new IllegalArgumentException();
                return s;
            });

            int weight = prompt(sc, "Cân nặng (kg): ", (String s) -> {
                int w = Integer.parseInt(s);
                if (w >= 150) throw new IllegalArgumentException();
                return w;
            });

            float height = prompt(sc, "Chiều cao (m): ", (String s) -> {
                float h = Float.parseFloat(s);
                if (h >= 2.5f) throw new IllegalArgumentException();
                return h;
            });

            char bloodType = prompt(sc, "Nhóm máu (A/B/O): ", (String s) -> {
                if (!s.equalsIgnoreCase("A") && !s.equalsIgnoreCase("B") && !s.equalsIgnoreCase("O"))
                    throw new IllegalArgumentException();
                return s.toUpperCase().charAt(0);
            });

            boolean gender = prompt(sc, "Giới tính (true=Nam, false=Nữ): ", (String s) -> {
                if (!s.equals("true") && !s.equals("false")) throw new IllegalArgumentException();
                return Boolean.parseBoolean(s);
            });

            LocalDate birthDate = prompt(sc, "Ngày sinh (yyyy-mm-dd): ", LocalDate::parse);

            patients.add(new Patient(id, fullname, weight, height, bloodType, gender, birthDate));
        }
    }

    private <T> T prompt(Scanner sc, String msg, Function<String, T> parser) {
        while (true) {
            System.out.print(msg);
            try {
                return parser.apply(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ Nhập sai, vui lòng thử lại.");
            }
        }
    }

    public void writeDataToFileXML(String filename) throws Exception {
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("patients");
        doc.appendChild(root);

        for (Patient p : patients) {
            Element patientEl = doc.createElement("patient");
            root.appendChild(patientEl);

            patientEl.appendChild(createElement(doc, "id", String.valueOf(p.getId())));
            patientEl.appendChild(createElement(doc, "fullname", p.getFullname()));
            patientEl.appendChild(createElement(doc, "weight", String.valueOf(p.getWeight())));
            patientEl.appendChild(createElement(doc, "height", String.valueOf(p.getHeight())));
            patientEl.appendChild(createElement(doc, "bloodType", String.valueOf(p.getBloodType())));
            patientEl.appendChild(createElement(doc, "gender", String.valueOf(p.isGender())));
            patientEl.appendChild(createElement(doc, "birthDate", p.getBirthDate().toString()));
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(new File(filename)));
    }

    private Element createElement(Document doc, String tag, String value) {
        Element e = doc.createElement(tag);
        e.appendChild(doc.createTextNode(value));
        return e;
    }

    public void writeDataToFileJSON(String filename) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.writeValue(new File(filename), patients);
    }

    public void readDataFromFileXML(String filename) throws Exception {
        patients.clear();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filename));
        NodeList list = doc.getElementsByTagName("patient");

        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            Patient p = new Patient(
                    Integer.parseInt(getText(e, "id")),
                    getText(e, "fullname"),
                    Integer.parseInt(getText(e, "weight")),
                    Float.parseFloat(getText(e, "height")),
                    getText(e, "bloodType").charAt(0),
                    Boolean.parseBoolean(getText(e, "gender")),
                    LocalDate.parse(getText(e, "birthDate"))
            );
            patients.add(p);
        }

        patients.forEach(System.out::println);
    }

    private String getText(Element e, String tag) {
        return e.getElementsByTagName(tag).item(0).getTextContent();
    }

    public void readDataFromFileJSON(String filename) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        patients = mapper.readValue(new File(filename), new TypeReference<List<Patient>>() {});
        patients.forEach(System.out::println);
    }
}
