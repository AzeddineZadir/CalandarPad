package com.example.silver_desk.interfactest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.silver_desk.interfactest.Adapters.CalendrierAdapter;
import com.example.silver_desk.interfactest.Adapters.EvenmentAdapter;
import com.example.silver_desk.interfactest.database.Evenement;
import com.example.silver_desk.interfactest.fragment.DatePickerFragment;
import com.example.silver_desk.interfactest.fragment.TimePickerFragment;

public class AjoutEvenmentActivity extends AppCompatActivity implements View.OnClickListener ,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
    EditText t_libele,t_description,t_lieu;
    CheckBox cb_recurrance ,cb_alerte ;
    Button b_debut,b_fin,b_joure;
    FloatingActionButton fab_save_event ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_evenment);
        // les Button
        b_debut=(Button)findViewById(R.id.b_debut);
        b_debut.setOnClickListener(this);
        b_fin=(Button)findViewById(R.id.b_fin);
        b_fin.setOnClickListener(this);
        b_joure=(Button)findViewById(R.id.b_joure);
        b_joure.setOnClickListener(this);

        // les edittext
        t_libele=(EditText) findViewById(R.id.e_libele);
        t_description=(EditText) findViewById(R.id.e_description);
        t_lieu=(EditText)findViewById(R.id.e_lieu);

        // fab fab_save event
        fab_save_event=(FloatingActionButton)findViewById(R.id.fab_save_event);
        fab_save_event.setOnClickListener(this);

        //les check bow
        cb_alerte=(CheckBox)findViewById(R.id.cb_alerte);
        cb_recurrance=(CheckBox)findViewById(R.id.cb_recurrence);


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
        if(view.getId()==R.id.b_joure){
            DialogFragment datePickerFragment=new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(),"date picker");
        }
        if(view.getId()==R.id.fab_save_event){
            Evenement evenement= new Evenement();
            // recuperation des onformation de l evenment
            evenement.setLibele(t_libele.getText().toString());
            evenement.setDescription(t_description.getText().toString());
            evenement.setLieu(t_lieu.getText().toString());
            evenement.setRecurrence(cb_recurrance.isChecked());
            evenement.setAlerte(cb_alerte.isChecked());
            evenement.setCalendrierId(getincomingInten_idcal());
            // le joure
            evenement.setJour(0);
            // heure debut
            evenement.setHeure_debut(0);
            // heure fin
            evenement.setHeure_fin(0);
            //lisertion dans la base
            CalendrierActivity.DATABASE.evenementDao().insert(evenement);
            Toast.makeText(this,"ajout avec succse",Toast.LENGTH_SHORT).show();
            //onBackPressed();
              Intent intent =new Intent (this,SelectedCalendrierActivity.class);
             intent.putExtra("id_cal",getincomingInten_idcal());
            startActivity(intent);


        }

    }

    private  int getincomingInten_idcal() {
        if (getIntent().hasExtra("id_cal")) {
            int id_cal_selected = getIntent().getIntExtra("id_cal", 0);

            return id_cal_selected;
        }
        return 0;
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

