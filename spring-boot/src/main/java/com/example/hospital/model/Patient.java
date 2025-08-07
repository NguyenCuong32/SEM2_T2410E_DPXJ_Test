package com.example.hospital.model;
import java.time.LocalDate;
public class Patient {
    private int id;
    private String full_name;
    private int weight;
    private float height;
    private char BloodType;
    private boolean gender;
    private LocalDate BirthDate;

    public int getId(){
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public int getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public char getBloodType() {
        return BloodType;
    }

    public boolean isGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return BirthDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setBloodType(char bloodType) {
        BloodType = bloodType;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setBirthDate(LocalDate birthDate) {
        BirthDate = birthDate;
    }
    public boolean isValid() {
        return full_name != null && full_name.length() >= 2 && full_name.length() <= 32
                && weight < 150
                && height < 2.5
                && (BloodType == 'A' || BloodType == 'B' || BloodType == 'O');
    }

}
