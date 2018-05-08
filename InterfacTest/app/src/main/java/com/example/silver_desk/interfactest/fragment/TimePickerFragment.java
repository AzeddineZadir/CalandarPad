package com.example.silver_desk.interfactest.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by silver-desk on 18/04/2018.
 */

public class TimePickerFragment extends DialogFragment implements  TimePickerDialog.OnTimeSetListener {
    public static final int FLAG_START_TIME = 0;
    public static final int FLAG_END_TIME = 1;

   public static int flag = 0;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int heure= c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);
     return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(),heure,minute, DateFormat.is24HourFormat(getActivity()));

    }
    public void setFlag(int i) {
        flag = i;
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minut) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(hour,minut);

        if (flag == FLAG_START_TIME) {

        } else if (flag == FLAG_END_TIME) {

        }

    }
}
