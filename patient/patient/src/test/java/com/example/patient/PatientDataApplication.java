package com.example.patient;

import com.example.patient.model.Patient;
import com.example.patient.service.PatientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class PatientDataApplication implements CommandLineRunner {

	private final PatientService service = new PatientService();

	public static void main(String[] args) {
		SpringApplication.run(PatientDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Patient> patients = service.inputData();

		service.writeToXML(patients, "patients.xml");
		service.writeToJSON(patients, "patients.json");

		System.out.println("Reading from XML:");
		service.readFromXML("patients.xml");

		System.out.println("\nReading from JSON:");
		service.readFromJSON("patients.json");
	}
}
