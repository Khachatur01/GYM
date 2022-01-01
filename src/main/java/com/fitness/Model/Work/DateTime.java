package com.fitness.Model.Work;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateTime extends Date {
    public DateTime(long date) {
        super(date);
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(this);
    }
}
