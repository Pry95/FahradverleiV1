package com.example.fahradverlei;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {

    public static ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    public static ObservableList<Bike> bikeList = FXCollections.observableArrayList();
    public static ObservableList<Rental> rentalList = FXCollections.observableArrayList();

}
