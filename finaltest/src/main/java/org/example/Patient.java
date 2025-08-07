package org.example;
import java.time.LocalDate;

public class Patient {
    private int id;
    private String fullname;
    private int weight;
    private float height;
    private char bloodType;
    private boolean gender;
    private LocalDate birthDate;

    public Patient(){}
    public Patient(int id, String fullname, int weight, float height, char bloodType, boolean gender, LocalDate birthDate)
    {
        this.id =  id;
        this.fullname = fullname;
        this.weight = weight;
        this.height =  height;
        this.bloodType = bloodType;
        this.gender = gender;
        this.birthDate = birthDate;
    }

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
    public String toString(){
        return String.format("Patient[id=%d, name=%s, weight=%d, height=%.2f, bloodType=%c, gender=%s, birthDate=%s]",id, fullname, weight, height, bloodType, gender ? "Male": "Female", birthDate);
    }
}
