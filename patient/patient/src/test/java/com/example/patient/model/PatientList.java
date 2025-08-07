package com.example.patient.model;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "patients")
@XmlAccessorType(XmlAccessType.FIELD)
public class PatientList {

    @XmlElement(name = "patient")
    private List<Patient> patients;

    public PatientList() {}

    public PatientList(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
