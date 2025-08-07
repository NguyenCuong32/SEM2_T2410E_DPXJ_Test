package org.example;

import jakarta.xml.bind.annotation.*;
import java.time.LocalDate;

@XmlRootElement(name = "patient")
@XmlAccessorType(XmlAccessType.FIELD)
public class Patient {
    private int id;
    private String name;
    private int weight;
    private float height;
    private char bloodType;
    private boolean gender; // true = male, false = female
    private LocalDate birthDate;

    public Patient() {}

    public Patient(int id, String name, int weight, float height, char bloodType, boolean gender, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.bloodType = bloodType;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    // Getters and Setters...

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Weight: %d kg | Height: %.2f m | Blood Type: %s | Gender: %s | DOB: %s",
                id, name, weight, height, bloodType, gender ? "Male" : "Female", birthDate);
    }
}
