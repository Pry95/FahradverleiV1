package com.example.fahradverlei;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class Database {

    // Zugangsdaten für die Datenbank
    public static String url = "jdbc:mysql://localhost:3306/fahradverlei";
    public static  String user = "root";
    public static String pass = "";

    // Listen für die TableViews, werden aus der Datenbank eingelesen
    public static ObservableList<Employee> employeeList = FXCollections.observableArrayList();
    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    public static ObservableList<Bike> bikeList = FXCollections.observableArrayList();
    public static ObservableList<Rental> rentalList = FXCollections.observableArrayList();
    public static ObservableList<WorkingHours> workingHoursList = FXCollections.observableArrayList();





    /**Liest die Mitarbeiterinformationen von der Datenbank und speichert sie in die Liste Database.employeeList
     */
    public static void readEmployeeFromDatabase(){
        try {
            Database.employeeList.clear();
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
            Database.customerList.clear();
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
            Database.bikeList.clear();
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM bike");
            while(rs.next()){

                if (Objects.equals(rs.getString("Design").toLowerCase(), "ebike")){
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
            Database.rentalList.clear();
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
            Database.workingHoursList.clear();
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
    /**Schreibt einen neuen Datenstatz in die Tabelle `Bike´
     */
    public static void writeNewBikeInDatabase(String name, int frameSize, String design, double pricePerDay, String bikeCondition, String conditionComment, int battery, int performance){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            stm.execute("INSERT INTO bike( `Name`, `FrameSize`, `Design`, `PricePerDay`, `BikeCondition`, `ConditionComment`" +
                    ", `BatteryCapacity`, `Performance`) VALUES ('"+name+"','"+frameSize+"','"+design+"','"+pricePerDay+"','"+bikeCondition+"','" +
                    ""+conditionComment+"','"+battery+"','"+performance+"')");
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Löscht das übergebene Bike aus der Datenbank
     * @param tempBike Bike das aus der Datenbank gelöscht werden soll
     */
    public static void delBikeFromDatabase(Bike tempBike){
        try {
            Database.rentalList.clear();
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            stm.execute("DELETE FROM bike where Id='" + tempBike.getID()+ "'");
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
