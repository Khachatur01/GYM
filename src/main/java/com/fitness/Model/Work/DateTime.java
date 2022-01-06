package com.fitness.Model.Work;

import javafx.util.StringConverter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime extends Date {
    public DateTime(long date) {
        super(date);
    }

    public DateTime(LocalDateTime date) {
        super(Date.valueOf(date.toLocalDate()).getTime() + date.getMinute() * 60 + date.getHour() * 3600);
    }

    public static StringConverter<LocalDate> getConverter() {
        return new StringConverter<>() {
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null)
                    return dateTimeFormatter.format(localDate);
                else
                    return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty())
                    return LocalDate.parse(string, dateTimeFormatter);
                else
                    return null;
            }
        };
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(this);
    }
}
