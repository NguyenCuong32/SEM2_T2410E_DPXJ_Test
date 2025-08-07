package org.example;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDate extends XmlAdapter<String, java.time.LocalDate> {

    @Override
    public java.time.LocalDate unmarshal(String v) throws Exception {
        return java.time.LocalDate.parse(v);
    }

    @Override
    public String marshal(java.time.LocalDate v) throws Exception {
        return v.toString();
    }
}
