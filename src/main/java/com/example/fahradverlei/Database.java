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
    public static ObservableList<WorkingHours> workingHoursList = FXCollections.observableArrayList();



    /**Liest die Mitarbeiterinformationen von der Datenbank und speichert sie in die Liste Database.employeeList
     */
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

    /**Liest die Kundeninformationen von der Datenbank und speichert sie in die Liste Database.customerList
     */
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
                        rs.getInt("CustomerNumber")
                ));
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**Liest die Fahrradinformationen von der Datenbank und speichert sie in die Liste Database.bikeList
     */
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

    /**Liest die Verleihinformationen je Fahrrad von der Datenbank und speichert sie in die Liste Database.rentalList
     * @param bikeID FahrradID nach der die Datenbank gefiltert werden soll
     */
    public static void readRentalFromDatabase(Integer bikeID){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM rental where BikeId='" + bikeID+ "'");
            while(rs.next()){
                Database.rentalList.add(new Rental(
                        rs.getInt("Id"),
                        rs.getInt("BikeId"),
                        rs.getInt ("CustomerNumber"),
                        rs.getString("Typ"),
                        LocalDate.parse(rs.getString("StartDate")),
                        LocalDate.parse(rs.getString("EndDate"))
                ));
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**Liest die Arbeitszeiten je Mitarbeiter / Jahr / Monat von der Datenbank und speichert sie in die Liste Database.workingHoursList
     * @param employeeID MitarbeiterID nach der die Datenbank gefiltert werden soll
     * @param year Jahr nach dem die Datenbank gefiltert werden soll
     * @param month Monat nach dem die Datenbank gefiltert werden soll
     */
    public static void readWorkingHoursFromDatabase(Integer employeeID,Integer year, Integer month ){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM workinghours where EmployeeId='" + employeeID+ "' AND Year='" + year + "' AND Month='" + month +"'");
            while(rs.next()){
                Database.workingHoursList.add(new WorkingHours(
                        LocalDate.parse(rs.getString("WorkDate")),
                        rs.getTimestamp("StartOfWork"),
                        rs.getTimestamp("StartOfBreak"),
                        rs.getTimestamp("EndOfBreak"),
                        rs.getTimestamp("EndOfWork"),
                        rs.getDouble("TotalHours")
                ));
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
