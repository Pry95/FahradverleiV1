package com.example.fahradverlei.ObjectStruktures;

import com.example.fahradverlei.ObjectStruktures.Bike;

public class EBike extends Bike {

    private Integer BatteryCapacity;
    private Integer Performance;

    public EBike(int ID, String name, String frameSize, String designType, Double pricePerDay, String condition, String conditionComment, Integer batteryCapacity, Integer performance) {
        super(ID, name, frameSize, designType, pricePerDay, condition, conditionComment);
        this.BatteryCapacity = batteryCapacity;
        this.Performance = performance;

    }

    public Integer getBatteryCapacity() {
        return BatteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.BatteryCapacity = batteryCapacity;
    }

    public Integer getPerformance() {
        return Performance;
    }

    public void setPerformance(Integer performance) {
        this.Performance = performance;
    }
}
