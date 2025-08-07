package org.example;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "patients")
public class PatientsWrapper {
    private List<Patient> patients;

    public PatientsWrapper() {}

    public PatientsWrapper(List<Patient> patients) {
        this.patients = patients;
    }

    @XmlElement(name = "patient")
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}