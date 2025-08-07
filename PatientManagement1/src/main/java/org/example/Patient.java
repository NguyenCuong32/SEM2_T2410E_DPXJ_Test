package org.example;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "patient")
@XmlAccessorType(XmlAccessType.FIELD)
public class Patient {
    private int id;
    private String fullname;
    private int weight;
    private float height;
    private char bloodType;
    private boolean gender;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
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

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }
    public char getBloodType() { return bloodType; }
    public void setBloodType(char bloodType) { this.bloodType = bloodType; }
    public boolean isGender() { return gender; }
    public void setGender(boolean gender) { this.gender = gender; }
    public LocalDate getBirthDate() { return birthDate; }
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