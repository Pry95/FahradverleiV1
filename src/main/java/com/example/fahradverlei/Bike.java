package com.example.fahradverlei;

public class Bike {

    private int ID;
    private String name;
    private String frameSize;
    private String designType;
    private Double pricePerDay;
    private String condition;
    private String conditionComment;

    public Bike(int ID, String name, String frameSize, String designType, Double pricePerDay, String condition, String conditionComment) {
        this.ID = ID;
        this.name = name;
        this.frameSize = frameSize;
        this.designType = designType;
        this.pricePerDay = pricePerDay;
        this.condition = condition;
        this.conditionComment = conditionComment;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(String frameSize) {
        this.frameSize = frameSize;
    }

    public String getDesignType() {
        return designType;
    }

    public void setDesignType(String designType) {
        this.designType = designType;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConditionComment() {
        return conditionComment;
    }

    public void setConditionComment(String conditionComment) {
        this.conditionComment = conditionComment;
    }
}
