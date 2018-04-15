package com.example.silver_desk.interfactest.database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeConverter {

    static DateFormat df=new SimpleDateFormat("HH:mm:ss");

    @TypeConverter
    public static Time toTime(String value){
        if (value!=null){
            try {
                return (Time) df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }return null;
        }else {
            return null;
        }
    }

    @TypeConverter
    public static String fromTime(Time value){
        if (value!=null){
            return df.format(value);
        }else {
            return null;
        }
    }
}
