package com.example.fahradverlei.ObjectStruktures;

import java.text.DecimalFormat;

public class Bike {
    private int ID;
    private String Name;
    private String FrameSize;
    private String DesignType;
    private Double PricePerDay;
    private String Condition;
    private String ConditionComment;
    private final DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00 €");

    public Bike(int ID, String name, String frameSize, String designType, Double pricePerDay, String condition, String conditionComment) {
        this.ID = ID;
        this.Name = name;
        this.FrameSize = frameSize;
        this.DesignType = designType;
        this.PricePerDay = pricePerDay;
        this.Condition = condition;
        this.ConditionComment = conditionComment;
    }
    public String stringForInvoice(){
        return "ID: " + this.getID() +
                "  |  Bezeichnung: " + this.getName() +
                "  |  Bauart: " + this.getDesignType() +
                "  |  Preis / Tag: " + decimalFormatter.format(this.getPricePerDay());
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getFrameSize() {
        return FrameSize;
    }

    public void setFrameSize(String frameSize) {
        this.FrameSize = frameSize;
    }

    public String getDesignType() {
        return DesignType;
    }

    public void setDesignType(String designType) {
        this.DesignType = designType;
    }

    public Double getPricePerDay() {
        return PricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.PricePerDay = pricePerDay;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        this.Condition = condition;
    }

    public String getConditionComment() {
        return ConditionComment;
    }

    public void setConditionComment(String conditionComment) {
        this.ConditionComment = conditionComment;
    }
}
