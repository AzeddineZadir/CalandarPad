package com.example.silver_desk.interfactest.database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    static DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    @TypeConverter
    public static Date fromDate(String value) {
        if (value != null) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String toDate(Date value) {
        if (value != null) {
           return df.format(value);
        } else {
            return null;
        }
    }
}