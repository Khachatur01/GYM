package com.fitness.Service.BackYear;

import com.fitness.DataSource.Log.Log;
import com.fitness.Element.MaskField;
import com.fitness.Model.Work.DateTime;
import com.fitness.Service.Verify;
import javafx.scene.control.DatePicker;

public class BackYearService {
    public static DateTime getDateTime(MaskField timeMaskField, DatePicker datePicker) {
        String[] timeArray = timeMaskField.getText().split(":");
        byte hour, minute;
        try {
            hour = Byte.parseByte(timeArray[0]);
            minute = Byte.parseByte(timeArray[1]);
        } catch (NumberFormatException e) {
            Log.error("Can't parse time", e);
            timeMaskField.requestFocus();
            return null;
        }
        if(!Verify.time(hour, minute, timeMaskField)) return null;

        return new DateTime(
            datePicker.getValue().atTime(hour, minute)
        );
    }
}
