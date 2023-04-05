package com.example.fahradverlei;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class Database {

    // Zugangsdaten für die Datenbankn
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

    /**Schreibt einen neuen Datenstatz vom typ Ebike in die Tabelle `Bike´
     */
    public static void writeNewElectroBikeInDatabase(String name, int frameSize, String design, double pricePerDay, String bikeCondition, String conditionComment, int battery, int performance){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            PreparedStatement stm = con.prepareStatement("INSERT INTO bike(`Name`, `FrameSize`, `Design`, `PricePerDay`, `BikeCondition`, `ConditionComment`, `BatteryCapacity`, `Performance`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setString(1, name);
            stm.setInt(2, frameSize);
            stm.setString(3, design);
            stm.setDouble(4, pricePerDay);
            stm.setString(5, bikeCondition);
            stm.setString(6, conditionComment);
            stm.setInt(7, battery);
            stm.setInt(8, performance);
            stm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**Schreibt einen neuen Datenstatz vom typ bike in die Tabelle `Bike´
     */
    public static void writeNewBikeInDatabase(String name, int frameSize, String design, double pricePerDay, String bikeCondition, String conditionComment) {
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            PreparedStatement stm = con.prepareStatement("INSERT INTO bike(`Name`, `FrameSize`, `Design`, `PricePerDay`, `BikeCondition`, `ConditionComment`) VALUES (?, ?, ?, ?, ?, ?)");
            stm.setString(1, name);
            stm.setInt(2, frameSize);
            stm.setString(3, design);
            stm.setDouble(4, pricePerDay);
            stm.setString(5, bikeCondition);
            stm.setString(6, conditionComment);
            stm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**Schreibt einen neuen Datenstatz vom typ customer in die Tabelle `Customer´
     */
    public static void writeNewCustomerInDatabase(Customer myCustomer){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            PreparedStatement stm = con.prepareStatement("INSERT INTO `customer`(`Name`, `FirstName`, `BirthDate`, `Street`, `HouseNumber`, `PostalCode`, `Tel`," +
                    " `AccountNumber`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setString(1, myCustomer.getName());
            stm.setString(2, myCustomer.getFirstName());
            stm.setDate(3, java.sql.Date.valueOf(myCustomer.getBirthDate()));
            stm.setString(4, myCustomer.getStreet());
            stm.setString(5, myCustomer.getHousenumber());
            stm.setInt(6, myCustomer.getPostalCode());
            stm.setInt(7, myCustomer.getTel());
            stm.setString(8, myCustomer.getAccountNumber());
            stm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**Schreibt einen neuen Datenstatz vom typ employee in die Tabelle `employee´
     */
    public static void writeNewEmployeeInDatabase(Employee myEmployee){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            PreparedStatement stm = con.prepareStatement("INSERT INTO `employee`(`Name`, `FirstName`, `BirthDate`, `Street`, `HouseNumber`, `PostalCode`, `Tel`, `HourlyWage`, `HoursPerMonth`" +
                    ", `AccountNumber`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setString(1, myEmployee.getName());
            stm.setString(2, myEmployee.getFirstName());
            stm.setDate(3, java.sql.Date.valueOf(myEmployee.getBirthDate()));
            stm.setString(4, myEmployee.getStreet());
            stm.setString(5, myEmployee.getHousenumber());
            stm.setInt(6, myEmployee.getPostalCode());
            stm.setInt(7, myEmployee.getTel());
            stm.setDouble(8, myEmployee.getHourlyWage());
            stm.setInt(9, myEmployee.getHoursPerMonth());
            stm.setString(10,myEmployee.getAccountNumber());
            stm.executeUpdate();
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

    /** Ändert die Daten des Bikes in der Datenbank
     */
    public static void changeBikeDataFromDataBase(Integer Id ,String name,Integer frameSize,String design,Double pricePerDay,String condition,String ConditionComment,Integer batteryCapacity, Integer performance){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            if (Objects.equals(design, "EBike")){
                String update = "UPDATE bike SET Name ='" + name +
                        "',FrameSize ='" + frameSize +
                        "',Design ='" + design +
                        "',PricePerDay ='" + pricePerDay +
                        "',BikeCondition ='" + condition +
                        "',ConditionComment ='" + ConditionComment +
                        "',BatteryCapacity ='" + batteryCapacity +
                        "',Performance ='" + performance +
                        "' WHERE Id='" + Id + "'";
                stm.execute(update);
            }
            else{
                String update = "UPDATE bike SET Name ='" + name +
                        "',FrameSize ='" + frameSize +
                        "',Design ='" + design +
                        "',PricePerDay ='" + pricePerDay +
                        "',BikeCondition ='" + condition +
                        "',ConditionComment ='" + ConditionComment +
                        "',BatteryCapacity = NULL" +
                        ",Performance = NULL" +
                        " WHERE Id='" + Id + "'";
                stm.execute(update);
            }

            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Löscht den übergebenen Kunden aus der Datenbank
     * @param tempCustomer Kunde der aus der Datenbank gelöscht werden soll
     */
    public static void delCustomerFromDatabase(Customer tempCustomer){
        try {
            Database.rentalList.clear();
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            stm.execute("DELETE FROM customer where CustomerNumber ='" + tempCustomer.getCustomerNumber() + "'");
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Ändert die Daten des Kunden in der Datenbank
     * @param customer Custumer Objekt das übergeben wird zum überschreiben der Daten in der Datenbank
     */
    public static void changeCustomerDataFromDataBase(Customer customer){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            String update = "UPDATE customer SET Name ='" + customer.getName() +
                        "',FirstName ='" + customer.getFirstName() +
                        "',BirthDate ='" + customer.getBirthDate() +
                        "',Street ='" + customer.getStreet() +
                        "',HouseNumber ='" + customer.getHousenumber() +
                        "',PostalCode ='" + customer.getPostalCode() +
                        "',Tel ='" + customer.getTel() +
                        "',AccountNumber ='" + customer.getAccountNumber() +
                        "' WHERE CustomerNumber='" + customer.getCustomerNumber() + "'";
            stm.execute(update);
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Löscht den übergebenen Mitarbeiter aus der Datenbank
     * @param tempEmployee Mitarbeiter der aus der Datenbank gelöscht werden soll
     */
    public static void delEmployeeFromDatabase(Employee tempEmployee){
        try {
            Database.rentalList.clear();
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            stm.execute("DELETE FROM employee where EmployeeNumber ='" + tempEmployee.getEmployeeNumber() + "'");
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Ändert die Daten des Mitarbeiters in der Datenbank
     * @param employee Employee Objekt das übergeben wird zum überschreiben der Daten in der Datenbank
     */
    public static void changeEmployeeDataFromDataBase(Employee employee) {
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            String update = "UPDATE employee SET Name ='" + employee.getName() +
                    "',FirstName ='" + employee.getFirstName() +
                    "',BirthDate ='" + employee.getBirthDate() +
                    "',Street ='" + employee.getStreet() +
                    "',HouseNumber ='" + employee.getHousenumber() +
                    "',PostalCode ='" + employee.getPostalCode() +
                    "',Tel ='" + employee.getTel() +
                    "',HourlyWage ='" + employee.getHourlyWage() +
                    "',HoursPerMonth ='" + employee.getHoursPerMonth() +
                    "',AccountNumber ='" + employee.getAccountNumber() +
                    "' WHERE EmployeeNumber='" + employee.getEmployeeNumber() + "'";
            stm.execute(update);
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
