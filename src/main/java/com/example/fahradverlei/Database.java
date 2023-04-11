package com.example.fahradverlei;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public static ObservableList<WorkingHours> montWorkingHoursList = FXCollections.observableArrayList();
    public static ObservableList<Payroll> payrollsList = FXCollections.observableArrayList();





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
                if (!Objects.equals(rs.getString("Name"), "WARTUNG")){
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

            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    /**Liest die Gehaltsabrechnungen von der Datenbank und speichert sie in die Liste Database.payrollList
     */
    public static void readPayrolsFromaDatabase(int employeeId){
        try{
            Database.payrollsList.clear();
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM `payroll` WHERE EmployId = "+employeeId+"");
            while ((rs.next())){
                Database.payrollsList.add(new Payroll(rs.getInt("EmployId"),
                        rs.getInt("Month"),
                        rs.getInt("Year"),
                        rs.getInt("HoursPerMonth"),
                        rs.getDouble("TotalHours"),
                        rs.getDouble("OverTime"),
                        rs.getDouble("HourlyWage"),
                        rs.getDouble("NetSalary"),
                        rs.getDouble("GrossSalary"),
                        rs.getDouble("Deductions")
                ));
            }
            con.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
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
                    "SELECT * FROM rental " +
                            "INNER JOIN bike ON rental.BikeID = bike.Id " +
                            "INNER JOIN customer ON rental.CustomerNumber = customer.CustomerNumber " +
                            "WHERE BikeId='"+bikeID+"'" + " ORDER BY StartDate DESC");
            while(rs.next()){
                Database.rentalList.add(new Rental(
                        rs.getInt("Id"),
                        rs.getInt("BikeID"),
                        rs.getString("Name"),
                        rs.getString("Typ"),
                        rs.getInt("CustomerNumber"),
                        rs.getString("customer.Name") + " " + rs.getString("customer.FirstName"),
                        rs.getDate("BirthDate"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getString("payed"),
                        rs.getString("duplikate")
                ));
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**Liest die Arbeitszeiten je Mitarbeiter / Jahr / Monat von der Datenbank und speichert sie in die Liste Database.workingHoursList
     * @param employeeID MitarbeiterID nach der die Datenbank gefiltert werden soll
     */
    public static void readWorkingHoursFromDatabase(Integer employeeID){
        try {
            Database.workingHoursList.clear();
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM workinghours where EmployeeId='" + employeeID+ "'" + " ORDER BY WorkDate DESC");
            while(rs.next()){
                Database.workingHoursList.add(new WorkingHours(
                        LocalDate.parse(rs.getString("WorkDate")),
                        rs.getTime("StartOfWork"),
                        rs.getTime("StartOfBreak"),
                        rs.getTime("EndOfBreak"),
                        rs.getTime("EndOfWork"),
                        rs.getDouble("TotalHours")
                ));
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void readMonthlyWorkingHoursFromDatabase(int month, int year,int id){
        try {
            Database.montWorkingHoursList.clear();
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM `workinghours` WHERE EmployeeId = "+id+" AND Month = "+month+" AND Year = "+year+";");
            while(rs.next()){
                Database.montWorkingHoursList.add(new WorkingHours(
                        LocalDate.parse(rs.getString("WorkDate")),
                        rs.getTime("StartOfWork"),
                        rs.getTime("StartOfBreak"),
                        rs.getTime("EndOfBreak"),
                        rs.getTime("EndOfWork"),
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
    public static void writeNewElectroBikeInDatabase(EBike myEBike){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            PreparedStatement stm = con.prepareStatement("INSERT INTO bike(`Name`, `FrameSize`, `Design`, `PricePerDay`, `BikeCondition`, `ConditionComment`, `BatteryCapacity`, `Performance`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setString(1, myEBike.getName());
            stm.setInt(2, Integer.parseInt(myEBike.getFrameSize()));
            stm.setString(3, myEBike.getDesignType());
            stm.setDouble(4, myEBike.getPricePerDay());
            stm.setString(5, myEBike.getCondition());
            stm.setString(6, myEBike.getConditionComment());
            stm.setInt(7, myEBike.getBatteryCapacity());
            stm.setInt(8, myEBike.getPerformance());
            stm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**Schreibt einen neuen Datenstatz vom typ bike in die Tabelle `Bike´
     */
    public static void writeNewBikeInDatabase(Bike myBike) {
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            PreparedStatement stm = con.prepareStatement("INSERT INTO bike(`Name`, `FrameSize`, `Design`, `PricePerDay`, `BikeCondition`, `ConditionComment`) VALUES (?, ?, ?, ?, ?, ?)");
            stm.setString(1, myBike.getName());
            stm.setInt(2, Integer.parseInt(myBike.getFrameSize()));
            stm.setString(3, myBike.getDesignType());
            stm.setDouble(4, myBike.getPricePerDay());
            stm.setString(5, myBike.getCondition());
            stm.setString(6, myBike.getConditionComment());
            stm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**Schreibt einen neuen Datenstatz vom typ customer in die Tabelle `Customer´
     */
    public static void writeNewPayrollInDatabase(Payroll myPayroll){
        try{
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            PreparedStatement stm = con.prepareStatement("INSERT INTO `payroll`(`EmployId`, `Year`, `Month`, `HourlyWage`, `HoursPerMonth`, `TotalHours`, `OverTime`, `NetSalary`, `GrossSalary`, `Deductions`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setInt(1, myPayroll.getEmployeeId());
            stm.setInt(2, myPayroll.getYear());
            stm.setInt(3, myPayroll.getMonth());
            stm.setDouble(4, myPayroll.getHourlyWage());
            stm.setInt(5, myPayroll.getHoursPerMonth());
            stm.setDouble(6, myPayroll.getTotalHours());
            stm.setDouble(7, myPayroll.getOverTime());
            stm.setDouble(8, myPayroll.getNetSalary());
            stm.setDouble(9,myPayroll.getGrossSalary());
            stm.setDouble(10,myPayroll.getDeductions());
            stm.executeUpdate();
            con.close();
        } catch (Exception e) {
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

    /** Fügt einen Eintrag in die Datenbank WokringHours hinzu
     */
    public static void writeWorkingHoursToDatabase(Employee employee, WorkingHours workingHours){
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            String insert = "INSERT INTO workinghours(EmployeeId,WorkDate,Year,Month,Day,StartOfWork,StartOfBreak,EndOfBreak,EndOfWork,TotalHours) " +
                    "VALUES ('"
                    +employee.getEmployeeNumber()+"','"
                    +workingHours.getWorkingDate()+"','"
                    +workingHours.getWorkingDate().getYear()+"','"
                    +workingHours.getWorkingDate().getMonthValue()+"','"
                    +workingHours.getWorkingDate().getDayOfMonth()+"','"
                    +workingHours.getWorkingStart()+"','"
                    +workingHours.getBreakStart()+"','"
                    +workingHours.getBreakEnd()+"','"
                    +workingHours.getWorkEnd()+"','"
                    +workingHours.getTotalHours()+"')";
            stm.execute(insert);
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Fügt einen Eintrag in die Datenbank Rental hinzu
     */
    public static void writeRentalToDatabase(Bike tempBike, Integer customerNumber, LocalDate start, LocalDate end,String prompt) {
        try {
            Connection con = DriverManager.getConnection(Database.url, Database.user, Database.pass);
            Statement stm = con.createStatement();
            if(Objects.equals(prompt, "WARTUNG")){
                String insert = "INSERT INTO rental(BikeId,CustomerNumber,Typ,StartDate,EndDate) " +
                        "VALUES ('"
                        +tempBike.getID()+"','"
                        +customerNumber+"','"
                        +prompt+"','"
                        +start+"','"
                        +end+"')";
                stm.execute(insert);
            }
            else{
                String insert = "INSERT INTO rental(BikeId,CustomerNumber,Typ,StartDate,EndDate,payed,duplikate) " +
                        "VALUES ('"
                        +tempBike.getID()+"','"
                        +customerNumber+"','"
                        +prompt+"','"
                        +start+"','"
                        +end+"','nein','nein')";
                stm.execute(insert);
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
