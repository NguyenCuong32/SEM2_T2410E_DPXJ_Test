package com.example;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;

@XmlRootElement(name = "patient")
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

    public void setId(int id) { this.id = id; }

    @XmlElement
    public String getFullname() { return fullname; }

    public void setFullname(String fullname) { this.fullname = fullname; }

    @XmlElement
    public int getWeight() { return weight; }

    public void setWeight(int weight) { this.weight = weight; }

    @XmlElement
    public float getHeight() { return height; }

    public void setHeight(float height) { this.height = height; }

    @XmlElement
    public char getBloodType() { return bloodType; }

    public void setBloodType(char bloodType) { this.bloodType = bloodType; }

    @XmlElement
    public boolean isGender() { return gender; }

    public void setGender(boolean gender) { this.gender = gender; }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthDate() { return birthDate; }

    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    @Override
    public String toString() {
        return id + " - " + fullname + " - " + weight + "kg - " + height + "m - Blood: " + bloodType +
                " - " + (gender ? "Male" : "Female") + " - Born: " + birthDate;
    }
}