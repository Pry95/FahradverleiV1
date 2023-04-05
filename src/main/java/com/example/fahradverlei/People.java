package com.example.fahradverlei;

import java.sql.Date;
import java.time.LocalDate;

public class People {
    private String FirstName;
    private String Name;
    private LocalDate BirthDate;
    private String Street;
    private String Housenumber;
    private int PostalCode;
    private int Tel;


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        BirthDate = birthDate;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getHousenumber() {
        return Housenumber;
    }

    public void setHousenumber(String housenumber) {
        Housenumber = housenumber;
    }

    public int getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(int postalCode) {
        PostalCode = postalCode;
    }

    public int getTel() {
        return Tel;
    }

    public void setTel(int tel) {
        Tel = tel;
    }

    public People(String firstName, String name, LocalDate birthDate, String street, String housenumber, int postalCode, int tel) {
        FirstName = firstName;
        Name = name;
        BirthDate = birthDate;
        Street = street;
        Housenumber = housenumber;
        PostalCode = postalCode;
        Tel = tel;
    }
}
