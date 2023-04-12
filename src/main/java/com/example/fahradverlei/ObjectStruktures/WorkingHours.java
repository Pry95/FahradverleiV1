package com.example.fahradverlei.ObjectStruktures;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class WorkingHours {
    private LocalDate WorkingDate;
    private Time WorkingStart;
    private Time BreakStart;
    private Time BreakEnd;
    private Time WorkEnd;
    private double TotalHours;

    public WorkingHours(LocalDate workingDate, Time workingStart, Time breakStart, Time breakEnd, Time workEnd, double totalHours) {
        WorkingDate = workingDate;
        WorkingStart = workingStart;
        BreakStart = breakStart;
        BreakEnd = breakEnd;
        WorkEnd = workEnd;
        TotalHours = totalHours;
    }

    public LocalDate getWorkingDate() {
        return WorkingDate;
    }

    public void setWorkingDate(LocalDate workingDate) {
        WorkingDate = workingDate;
    }

    public Time getWorkingStart() {
        return WorkingStart;
    }

    public void setWorkingStart(Time workingStart) {
        WorkingStart = workingStart;
    }

    public Time getBreakStart() {
        return BreakStart;
    }

    public void setBreakStart(Time breakStart) {
        BreakStart = breakStart;
    }

    public Time getBreakEnd() {
        return BreakEnd;
    }

    public void setBreakEnd(Time breakEnd) {
        BreakEnd = breakEnd;
    }

    public Time getWorkEnd() {
        return WorkEnd;
    }

    public void setWorkEnd(Time workEnd) {
        WorkEnd = workEnd;
    }

    public double getTotalHours() {
        return TotalHours;
    }

    public void setTotalHours(double totalHours) {
        TotalHours = totalHours;
    }
}
