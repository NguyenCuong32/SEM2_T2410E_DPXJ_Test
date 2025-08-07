package org.example;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "patient")
@XmlAccessorType(XmlAccessType.FIELD)
public class Patient {
    private int id;
    private String fullname;
    private int weight;
    private float height;
    private char bloodType;
    private boolean gender; // true = Male, false = Female
    @XmlJavaTypeAdapter(LocalDate.class)
    private java.time.LocalDate birthDate;
    public Patient() {}
    public Patient(int id, String fullname, int weight, float height, char bloodType, boolean gender, java.time.LocalDate birthDate) {
        this.setId(id);
        this.setFullname(fullname);
        this.setWeight(weight);
        this.setHeight(height);
        this.setBloodType(bloodType);
        this.setGender(gender);
        this.setBirthDate(birthDate);
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) {
        if (fullname == null || fullname.length() < 2 || fullname.length() > 32) {
            throw new IllegalArgumentException("Fullname must be between 2 and 32 characters.");
        }
        this.fullname = fullname;
    }
    public int getWeight() { return weight; }
    public void setWeight(int weight) {
        if (weight >= 150) {
            throw new IllegalArgumentException("Weight must be less than 150 kg.");
        }
        this.weight = weight;
    }
    public float getHeight() { return height; }
    public void setHeight(float height) {
        if (height >= 2.5) {
            throw new IllegalArgumentException("Height must be less than 2.5 m.");
        }
        this.height = height;
    }
    public char getBloodType() { return bloodType; }
    public void setBloodType(char bloodType) {
        char upperBloodType = Character.toUpperCase(bloodType);
        if (upperBloodType != 'A' && upperBloodType != 'B' && upperBloodType != 'O') {
            throw new IllegalArgumentException("BloodType must be 'A', 'B', or 'O'.");
        }
        this.bloodType = upperBloodType;
    }
    public boolean isGender() { return gender; }
    public void setGender(boolean gender) { this.gender = gender; }
    public java.time.LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(java.time.LocalDate birthDate) { this.birthDate = birthDate; }
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", weight=" + weight + "kg" +
                ", height=" + height + "m" +
                ", bloodType=" + bloodType +
                ", gender=" + (gender ? "Male" : "Female") +
                ", birthDate=" + birthDate +
                '}';
    }
}
