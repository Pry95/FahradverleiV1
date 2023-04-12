package com.example.fahradverlei.ObjectStruktures;

import java.sql.Date;

public class Rental {

    private Integer ID;
    private Integer BikeID;
    private String BikeName;
    private String Type;
    private Integer CustomerNumber;
    private String CustomerName;
    private Date Birth;
    private Date StartDate;
    private Date EndDate;
    private String Payed;
    private  String Duplikate;



    public Rental(Integer ID, Integer bikeID, String bikeName, String type, Integer customerNumber, String customerName, Date birth, Date startDate, Date endDate, String payed, String duplikate) {
        this.ID = ID;
        BikeID = bikeID;
        BikeName = bikeName;
        Type = type;
        CustomerNumber = customerNumber;
        CustomerName = customerName;
        Birth = birth;
        StartDate = startDate;
        EndDate = endDate;
        Payed = payed;
        Duplikate = duplikate;
    }


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getBikeID() {
        return BikeID;
    }

    public void setBikeID(Integer bikeID) {
        BikeID = bikeID;
    }

    public String getBikeName() {
        return BikeName;
    }

    public void setBikeName(String bikeName) {
        BikeName = bikeName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Integer getCustomerNumber() {
        return CustomerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        CustomerNumber = customerNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public Date getBirth() {
        return Birth;
    }

    public void setBirth(Date birth) {
        Birth = birth;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }
    public String isPayed() {
        return Payed;
    }

    public void setPayed(String payed) {
        Payed = payed;
    }

    public String isDuplikate() {
        return Duplikate;
    }

    public void setDuplikate(String duplikate) {
        Duplikate = duplikate;
    }
}



