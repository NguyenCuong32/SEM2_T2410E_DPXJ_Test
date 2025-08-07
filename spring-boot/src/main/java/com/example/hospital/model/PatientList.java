package com.example.hospital.model;
import java.util.List;
public class PatientList {

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
