package com.example;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.time.LocalDate;

@JacksonXmlRootElement(localName = "Patient")
public class Patient {
    private int id;
    private String fullName;
    private int weight;
    private float height;
    private char bloodType;
    private boolean gender;
    private LocalDate birthDate;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public char getBloodType() {
        return this.bloodType;
    }

    public void setBloodType(char bloodType) {
        this.bloodType = bloodType;
    }

    public boolean isGender() {
        return this.gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Patient() {
    }

    public Patient(int id, String fullName, int weight, float height, char bloodType, boolean gender,
            LocalDate birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.weight = weight;
        this.height = height;
        this.bloodType = bloodType;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", Full name='" + fullName + '\'' +
                ", Weight=" + weight +
                ", Height=" + height +
                ", Blood type=" + bloodType +
                ", Gender=" + (gender ? "Male" : "Female") +
                ", Birth date=" + birthDate +
                '}';
    }
}
