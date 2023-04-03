package com.example.fahradverlei;

import java.time.LocalDate;

public class Customer extends  People{
    private String AccountNumber;
    private int CustomerNumber;

    public Customer(String firstName, String name, LocalDate birthDate, String street, String housenumber, int postalCode, int tel, String accountNumber, int customerNumber) {
        super(firstName, name, birthDate, street, housenumber, postalCode, tel);
        AccountNumber = accountNumber;
        CustomerNumber = customerNumber;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public int getCustomerNumber() {
        return CustomerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        CustomerNumber = customerNumber;
    }
}
