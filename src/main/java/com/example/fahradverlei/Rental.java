package com.example.fahradverlei;

import java.time.LocalDate;

public class Rental {

    private Integer ID;
    private Integer bikeID;
    private Integer customerNumber;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;


    public Rental(Integer ID, Integer bikeID, Integer customerNumber, String type, LocalDate startDate, LocalDate endDate) {
        this.ID = ID;
        this.bikeID = bikeID;
        this.customerNumber = customerNumber;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getBikeID() {
        return bikeID;
    }

    public void setBikeID(Integer bikeID) {
        this.bikeID = bikeID;
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
