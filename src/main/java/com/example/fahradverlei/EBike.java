package com.example.fahradverlei;

public class EBike extends Bike {

    private Integer batteryCapacity;
    private Integer performance;

    public EBike(int ID, String name, String frameSize, String designType, Double pricePerDay, String condition, String conditionComment, Integer batteryCapacity, Integer performance) {
        super(ID, name, frameSize, designType, pricePerDay, condition, conditionComment);
        this.batteryCapacity = batteryCapacity;
        this.performance = performance;

    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }
}
