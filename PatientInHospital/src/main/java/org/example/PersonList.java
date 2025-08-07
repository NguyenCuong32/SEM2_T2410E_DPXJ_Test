package org.example;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "persons")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonList {

    @XmlElement(name = "person")
    private List<Person> personList;

    public PersonList() {
    }

    public PersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}