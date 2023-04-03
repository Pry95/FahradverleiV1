package com.example.fahradverlei;

import java.time.LocalDate;

public class Rental {

    private Integer ID;
    private Integer BikeID;
    private Integer CustomerNumber;
    private String Type;
    private LocalDate StartDate;
    private LocalDate EndDate;


    public Rental(Integer ID, Integer bikeID, Integer customerNumber, String type, LocalDate startDate, LocalDate endDate) {
        this.ID = ID;
        this.BikeID = bikeID;
        this.CustomerNumber = customerNumber;
        this.Type = type;
        this.StartDate = startDate;
        this.EndDate = endDate;
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
        this.BikeID = bikeID;
    }

    public Integer getCustomerNumber() {
        return CustomerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.CustomerNumber = customerNumber;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.EndDate = endDate;
    }
}
