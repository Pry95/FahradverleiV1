package com.example.fahradverlei;

import java.time.LocalDate;

public class Customer extends  People{
    private int AccountNumber;
    private int CustomerNumber;

    public Customer(int id, String firstName, String name, LocalDate birthDate, String street, String housenumber, int postalCode, int tel, int accountNumber, int customerNumber) {
        super(id, firstName, name, birthDate, street, housenumber, postalCode, tel);
        AccountNumber = accountNumber;
        CustomerNumber = customerNumber;
    }

    public int getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        AccountNumber = accountNumber;
    }

    public int getCustomerNumber() {
        return CustomerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        CustomerNumber = customerNumber;
    }
}
