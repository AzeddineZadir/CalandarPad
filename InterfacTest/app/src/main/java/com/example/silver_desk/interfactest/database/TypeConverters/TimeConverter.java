package com.example.silver_desk.interfactest.database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeConverter {

    static DateFormat df=new SimpleDateFormat("HH:mm:ss");

    @TypeConverter
    public static Time toTime(long value){
        if (value!=0){

                return new Time(value);}

         else {
            return null;
        }
    }

    @TypeConverter
    public static long toLong(Time value){
        if (value!=null){
            return value.getTime();
        }else {
            return 0;
        }
    }
}
