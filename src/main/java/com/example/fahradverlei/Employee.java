package com.example.fahradverlei;

import java.time.LocalDate;

public class Employee extends People{
    private double HourlyWage;
    private int HoursPerMonth;
    private int AccountNumber;
    private int EmployeeNumber;
    public Employee(int id, String firstName, String name, LocalDate birthDate, String street, String housenumber, int postalCode, int tel) {
        super(id, firstName, name, birthDate, street, housenumber, postalCode, tel);
    }

    public Employee(int id, String firstName, String name, LocalDate birthDate, String street, String housenumber, int postalCode, int tel, double hourlyWage, int hoursPerMonth, int accountNumber, int employeeNumber) {
        super(id, firstName, name, birthDate, street, housenumber, postalCode, tel);
        HourlyWage = hourlyWage;
        HoursPerMonth = hoursPerMonth;
        AccountNumber = accountNumber;
        EmployeeNumber = employeeNumber;
    }

    public double getHourlyWage() {
        return HourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        HourlyWage = hourlyWage;
    }

    public int getHoursPerMonth() {
        return HoursPerMonth;
    }

    public void setHoursPerMonth(int hoursPerMonth) {
        HoursPerMonth = hoursPerMonth;
    }

    public int getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        AccountNumber = accountNumber;
    }

    public int getEmployeeNumber() {
        return EmployeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        EmployeeNumber = employeeNumber;
    }
}
