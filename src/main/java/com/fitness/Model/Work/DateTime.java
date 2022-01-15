package com.fitness.Model.Work;

import com.fitness.Constant.Week;
import javafx.util.StringConverter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateTime extends Timestamp {
    public static Week getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return Week.SUNDAY;
            case Calendar.MONDAY:
                return Week.MONDAY;
            case Calendar.TUESDAY:
                return Week.TUESDAY;
            case Calendar.WEDNESDAY:
                return Week.WEDNESDAY;
            case Calendar.THURSDAY:
                return Week.THURSDAY;
            case Calendar.FRIDAY:
                return Week.FRIDAY;
            case Calendar.SATURDAY:
                return Week.SATURDAY;
        }
        return null;
    }
    public DateTime(long date) {
        super(date);
    }

    public DateTime(LocalDateTime dateTime) {
        super(Timestamp.valueOf(dateTime).getTime());
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
