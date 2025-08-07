package org.example;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.util.Set;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    @XmlElement
    private int id;

    @XmlElement
    private String fullname;

    @XmlElement
    private double weightKg; // Stored in kilograms now

    @XmlElement
    private float height; // Stored in meters

    @XmlElement
    private char bloodType;

    @XmlElement
    private boolean gender; // true = Male, false = Female

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthDate;

    public Person() {
    }

    public Person(int id, String fullname, double weightKg, float height, char bloodType, boolean gender, LocalDate birthDate) {
        this.setId(id);
        this.setFullname(fullname);
        this.setWeightKg(weightKg);
        this.setHeight(height);
        this.setBloodType(bloodType);
        this.setGender(gender);
        this.setBirthDate(birthDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number.");
        }
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        if (fullname == null || fullname.trim().length() < 2 || fullname.trim().length() > 32) {
            throw new IllegalArgumentException("Fullname must be between 2 and 32 characters.");
        }
        this.fullname = fullname;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        if (weightKg <= 0 || weightKg > 150) {
            throw new IllegalArgumentException("Weight must be between 0 and 150 kg.");
        }
        this.weightKg = weightKg;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        if (height <= 0 || height >= 2.5) {
            throw new IllegalArgumentException("Height must be between 0 and 2.5 meters.");
        }
        this.height = height;
    }

    public char getBloodType() {
        return bloodType;
    }

    public void setBloodType(char bloodType) {
        char upperBloodType = Character.toUpperCase(bloodType);
        Set<Character> validBloodTypes = Set.of('A', 'B', 'O');
        if (!validBloodTypes.contains(upperBloodType)) {
            throw new IllegalArgumentException("Blood type must be 'A', 'B', or 'O'.");
        }
        this.bloodType = upperBloodType;
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
        return "Person{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", weight=" + weightKg + "kg" +
                ", height=" + height + "m" +
                ", bloodType=" + bloodType +
                ", gender=" + (gender ? "Male" : "Female") +
                ", birthDate=" + birthDate +
                '}';
    }
}
