import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class Patient {
    private int id;
    private String fullname;
    private int weight;
    private float height;
    private char bloodType;
    private boolean gender;
    private LocalDate birthDate;

    public Patient() {}

    public Patient(int id, String fullname, int weight, float height, char bloodType, boolean gender, LocalDate birthDate) {
        this.id = id;
        this.fullname = fullname;
        this.weight = weight;
        this.height = height;
        this.bloodType = bloodType;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    @XmlElement
    public int getId() { return id; }

    @XmlElement
    public String getFullname() { return fullname; }

    @XmlElement
    public int getWeight() { return weight; }

    @XmlElement
    public float getHeight() { return height; }

    @XmlElement
    public char getBloodType() { return bloodType; }

    @XmlElement
    public boolean isGender() { return gender; }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    public LocalDate getBirthDate() { return birthDate; }

    // --- SETTERS ---
    public void setId(int id) { this.id = id; }

    public void setFullname(String fullname) { this.fullname = fullname; }

    public void setWeight(int weight) { this.weight = weight; }

    public void setHeight(float height) { this.height = height; }

    public void setBloodType(char bloodType) { this.bloodType = bloodType; }

    public void setGender(boolean gender) { this.gender = gender; }

    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", bloodType=" + bloodType +
                ", gender=" + (gender ? "Male" : "Female") +
                ", birthDate=" + birthDate +
                '}';
    }
}
