package com.fitness.Model.Configuration;

import com.fitness.Constant.Week;

public class WeekDay {
    private Week week;
    private boolean isWorkingDay;

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public boolean isWorkingDay() {
        return isWorkingDay;
    }

    public void setWorkingDay(boolean workingDay) {
        isWorkingDay = workingDay;
    }
}
