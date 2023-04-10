package com.example.fahradverlei;

public class Payroll {
    private int EmployeeId;
    private int Month;
    private int Year;
    private int HoursPerMonth;
    private double TotalHours;
    private double OverTime;
    private double HourlyWage;
    private double NetSalary;
    private double GrossSalary;
    private double Deductions;

    public Payroll(int employeeId,int month, int year, int hoursPerMonth, double totalHours, double overTime, double hourlyWage, double netSalary, double grossSalary, double deductions) {
        EmployeeId = employeeId;
        Month = month;
        Year = year;
        HoursPerMonth = hoursPerMonth;
        TotalHours = totalHours;
        OverTime = overTime;
        HourlyWage = hourlyWage;
        NetSalary = netSalary;
        GrossSalary = grossSalary;
        Deductions = deductions;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int employeeId) {
        EmployeeId = employeeId;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getHoursPerMonth() {
        return HoursPerMonth;
    }

    public void setHoursPerMonth(int hoursPerMonth) {
        HoursPerMonth = hoursPerMonth;
    }

    public double getTotalHours() {
        return TotalHours;
    }

    public void setTotalHours(double totalHours) {
        TotalHours = totalHours;
    }

    public double getOverTime() {
        return OverTime;
    }

    public void setOverTime(double overTime) {
        OverTime = overTime;
    }

    public double getHourlyWage() {
        return HourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        HourlyWage = hourlyWage;
    }

    public double getNetSalary() {
        return NetSalary;
    }

    public void setNetSalary(double netSalary) {
        NetSalary = netSalary;
    }

    public double getGrossSalary() {
        return GrossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        GrossSalary = grossSalary;
    }

    public double getDeductions() {
        return Deductions;
    }

    public void setDeductions(double deductions) {
        Deductions = deductions;
    }
}
