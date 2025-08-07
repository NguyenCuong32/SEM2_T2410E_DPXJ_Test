import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlRootElement
public class PatientList {
    private List<Patient> patients;

    public PatientList() {}

    public PatientList(List<Patient> patients) {
        this.patients = patients;
    }

    @XmlElement
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}

