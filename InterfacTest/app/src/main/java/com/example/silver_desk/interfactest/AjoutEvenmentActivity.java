package com.example.silver_desk.interfactest;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.silver_desk.interfactest.fragment.DatePickerFragment;
import com.example.silver_desk.interfactest.fragment.TimePickerFragment;

public class AjoutEvenmentActivity extends AppCompatActivity implements View.OnClickListener ,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
   EditText e_libele,e_jour,e_description,e_lieu;
    Button b_debut,b_fin;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_evenment);
        // le"s Button
        b_debut=(Button)findViewById(R.id.b_debut);
        b_debut.setOnClickListener(this);
        b_fin=(Button)findViewById(R.id.b_fin);
        b_fin.setOnClickListener(this);
        // les texte view
       e_libele=(EditText) findViewById(R.id.e_libele);
       e_jour=(EditText) findViewById(R.id.e_jour);
       e_description=(EditText) findViewById(R.id.e_description);
       e_lieu=(EditText) findViewById(R.id.e_lieu);
        e_jour.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.b_debut){
            DialogFragment timepicker=new TimePickerFragment();
            timepicker.show(getSupportFragmentManager(),"time picker");
        }
        if(view.getId()==R.id.b_fin){
            DialogFragment timepicker=new TimePickerFragment();
            timepicker.show(getSupportFragmentManager(),"time picker");
        }
        if(view.getId()==R.id.e_jour){
            DialogFragment datepicker=new DatePickerFragment();
            datepicker.show(getSupportFragmentManager(),"date picker");
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
       Toast.makeText(this,"hour "+i+"minute "+i1,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int Y, int M, int D) {
        Toast.makeText(this,"year "+Y+"month "+M+"day "+D,Toast.LENGTH_LONG).show();
    }
}

