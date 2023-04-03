package com.example.fahradverlei;

import java.time.LocalDate;

public class Employee extends People{
    private double HourlyWage;
    private int HoursPerMonth;
    private String AccountNumber;
    private int EmployeeNumber;

    public Employee(String firstName, String name, LocalDate birthDate, String street, String housenumber, int postalCode, int tel, double hourlyWage, int hoursPerMonth, String accountNumber, int employeeNumber) {
        super(firstName, name, birthDate, street, housenumber, postalCode, tel);
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

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public int getEmployeeNumber() {
        return EmployeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        EmployeeNumber = employeeNumber;
    }
}
