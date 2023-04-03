package com.example.fahradverlei;

import java.sql.Timestamp;
import java.time.LocalDate;

public class WorkingHours {
    private LocalDate WorkingDate;
    private Timestamp WorkingStart;
    private Timestamp BreakStart;
    private Timestamp BreakEnd;
    private Timestamp WorkEnd;
    private double TotalHours;

    public WorkingHours(LocalDate workingDate, Timestamp workingStart, Timestamp breakStart, Timestamp breakEnd, Timestamp workEnd, double totalHours) {
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

    public Timestamp getWorkingStart() {
        return WorkingStart;
    }

    public void setWorkingStart(Timestamp workingStart) {
        WorkingStart = workingStart;
    }

    public Timestamp getBreakStart() {
        return BreakStart;
    }

    public void setBreakStart(Timestamp breakStart) {
        BreakStart = breakStart;
    }

    public Timestamp getBreakEnd() {
        return BreakEnd;
    }

    public void setBreakEnd(Timestamp breakEnd) {
        BreakEnd = breakEnd;
    }

    public Timestamp getWorkEnd() {
        return WorkEnd;
    }

    public void setWorkEnd(Timestamp workEnd) {
        WorkEnd = workEnd;
    }

    public double getTotalHours() {
        return TotalHours;
    }

    public void setTotalHours(double totalHours) {
        TotalHours = totalHours;
    }
}
