package com.example.patient.model;

import jakarta.xml.bind.annotation.*;
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

    private LocalDate birthDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public char getBloodType() {
        return bloodType;
    }

    public void setBloodType(char bloodType) {
        this.bloodType = bloodType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isValid() {
        return fullname != null && fullname.length() >= 2 && fullname.length() <= 32
                && weight < 150
                && height < 2.5
                && (bloodType == 'A' || bloodType == 'B' || bloodType == 'O');
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Weight: %dkg, Height: %.2fm, BloodType: %c, Gender: %s, DOB: %s",
                id, fullname, weight, height, bloodType, gender ? "Male" : "Female", birthDate);
    }

}
