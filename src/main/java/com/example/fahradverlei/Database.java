package com.example.fahradverlei;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class Database {

    public static String url = "jdbc:mysql://localhost:3306/fahradverlei";
    public static  String user = "root";
    public static String pass = "";
    public static ObservableList<Employee> employeeList = FXCollections.observableArrayList();
    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    public static ObservableList<Bike> bikeList = FXCollections.observableArrayList();
    public static ObservableList<Rental> rentalList = FXCollections.observableArrayList();

    public static void readEmployeeFromDatabase(){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM employee");
            while(rs.next()){
                Database.employeeList.add(new Employee(
                        rs.getString("FirstName"),
                        rs.getString("Name"),
                        LocalDate.parse(rs.getString("BirthDate")),
                        rs.getString("Street"),
                        rs.getString("Housenumber"),
                        rs.getInt("PostalCode"),
                        rs.getInt("Tel"),
                        rs.getDouble("HourlyWage"),
                        rs.getInt("HoursPerMonth"),
                        rs.getString("AccountNumber"),
                        rs.getInt("EmployeeNumber")
                        ));
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void readCustomerFromDatabase(){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM customer");
            while(rs.next()){
                Database.customerList.add(new Customer(
                        rs.getString("FirstName"),
                        rs.getString("Name"),
                        LocalDate.parse(rs.getString("BirthDate")),
                        rs.getString("Street"),
                        rs.getString("Housenumber"),
                        rs.getInt("PostalCode"),
                        rs.getInt("Tel"),
                        rs.getString("AccountNumber"),
                        rs.getInt("CustomerNumber ")
                ));
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public static void readBikesFromDatabase(){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM bike");
            while(rs.next()){

                if (Objects.equals(rs.getString("Design"), "EBike")){
                    Database.bikeList.add(new EBike(
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getString("FrameSize"),
                            rs.getString("Design"),
                            rs.getDouble("PricePerDay"),
                            rs.getString("BikeCondition"),
                            rs.getString("ConditionComment"),
                            rs.getInt("BatteryCapacity"),
                            rs.getInt("Performance")
                    ));
                }
                else{
                    Database.bikeList.add(new Bike(
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getString("FrameSize"),
                            rs.getString("Design"),
                            rs.getDouble("PricePerDay"),
                            rs.getString("BikeCondition"),
                            rs.getString("ConditionComment")
                    ));
                }

            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
