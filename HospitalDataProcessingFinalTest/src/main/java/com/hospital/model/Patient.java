package com.hospital.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hospital.adapter.LocalDateAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;

@XmlRootElement(name = "patient")
@XmlAccessorType(XmlAccessType.FIELD)
public class Patient {

    @XmlElement
    private int id;

    @XmlElement
    private String fullname;

    @XmlElement
    private int weight;

    @XmlElement
    private float height;

    @XmlElement
    private char bloodType;

    @XmlElement
    private boolean gender;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonFormat(pattern = "yyyy-MM-dd")  // Cho JSON
    private LocalDate birthDate;

    // ⚠ JAXB sẽ dùng trực tiếp field (do XmlAccessType.FIELD), nên chỉ cần getter/setter đơn giản

    public Patient() {
    }

    public Patient(int id, String fullname, int weight, float height, char bloodType, boolean gender, LocalDate birthDate) {
        this.id = id;
        this.fullname = fullname;
        this.weight = weight;
        this.height = height;
        this.bloodType = bloodType;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    // Getter và setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public char getBloodType() {
        return bloodType;
    }

    public void setBloodType(char bloodType) {
        this.bloodType = bloodType;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

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