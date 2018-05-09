package com.example.silver_desk.interfactest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.silver_desk.interfactest.database.Alerte;
import com.example.silver_desk.interfactest.database.Calendrier;
import com.example.silver_desk.interfactest.database.Evenement;
import com.example.silver_desk.interfactest.fragment.DatePickerFragment;
import com.example.silver_desk.interfactest.fragment.TimePickerFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static com.example.silver_desk.interfactest.HomeActivity.DATABASE;
import static com.example.silver_desk.interfactest.fragment.TimePickerFragment.FLAG_END_TIME;
import static com.example.silver_desk.interfactest.fragment.TimePickerFragment.FLAG_START_TIME;
import static com.example.silver_desk.interfactest.fragment.TimePickerFragment.flag;

public class AjoutEvenmentActivity extends AppCompatActivity implements View.OnClickListener ,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
    EditText t_libele,t_description,t_lieu;
    CheckBox cb_recurrance ,cb_alerte ;
    Button b_debut,b_fin,b_joure;
    FloatingActionButton fab_save_event ;
    private boolean modification ;
    Calendar date ;
    Calendar heure_deb,heure_fin ;
    TimePickerFragment timePickerFragment ;
    Spinner spinner_delai,spinner_calendrier_parent;
    ArrayAdapter arrayAdapterdelai,arrayAdaptercal ;




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

        cb_recurrance=(CheckBox)findViewById(R.id.cb_recurrence);

        //le time picker
        timePickerFragment= new TimePickerFragment();
        date=Calendar.getInstance();
        heure_deb=Calendar.getInstance();
        heure_fin=Calendar.getInstance();

        // le spinner
        spinner_delai=(Spinner)findViewById(R.id.spinner_delai);
        arrayAdapterdelai= ArrayAdapter.createFromResource(getApplicationContext(),R.array.delai_alerte,android.R.layout.simple_spinner_item);
        arrayAdapterdelai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_delai.setAdapter(arrayAdapterdelai);

        // spinner cal
        spinner_calendrier_parent=(Spinner)findViewById(R.id.spinner_calendrier_parent);
        List<String> listecal = DATABASE.calendrierDao().loadAllCalendrierTitels();
        arrayAdaptercal = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,listecal);
        spinner_calendrier_parent.setAdapter(arrayAdaptercal);

        if (verifyIncomingIntent()){
          Evenement evenement=  DATABASE.evenementDao().selectEvenmentById(getincomingInten_idevenment());
          t_libele.setText(evenement.getLibele().toString());
          t_description.setText(evenement.getDescription().toString());
          t_lieu.setText(evenement.getDescription().toString());



        }

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.b_debut){
        timePickerFragment.setFlag(FLAG_START_TIME);
        timePickerFragment.show(getSupportFragmentManager(),"timepicker");

        }
        if(view.getId()==R.id.b_fin){

            timePickerFragment.setFlag(TimePickerFragment.FLAG_END_TIME);
            timePickerFragment.show(getSupportFragmentManager(),"timepicker");;

        }
        if(view.getId()==R.id.b_joure){
            DialogFragment datePickerFragment=new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(),"date picker");
        }
        // ajout d'un evenment  ou modification

        if(view.getId()==R.id.fab_save_event){
            boolean modification ;
            if(verifyIncomingIntent()) {  modification= true ;}
            else { modification=false ;}
            int id_cal = DATABASE.calendrierDao().getIdCalendrierByTitel(spinner_calendrier_parent.getSelectedItem().toString());
            // modification
            if (modification==true){
                // id evenment
                int id=getincomingInten_idevenment();
                // libele evenment
                String libele=t_libele.getText().toString();
                //date evenment
                long jour=date.getTimeInMillis();
                // heure debut
                long h_d=heure_deb.getTimeInMillis();
                // heure fin
                long h_f=heure_fin.getTimeInMillis();
                // lieu
                String lieu=t_lieu.getText().toString();
                // description
                String description=t_description.getText().toString() ;
                //recurrance
                boolean recurrence=cb_recurrance.isChecked() ;
                // alerte
                boolean alerte=false ;
                if (getdelaiFromSpiner(spinner_delai)==-1){
                 alerte=false ;
                }else{
                alerte=true ;
                }

                // id calendrier parent
                int id_calendrier=id_cal;
                // heure alerte
                long heure_alerte=generatAlertTime();
                // delai alerte
                long  delai = getdelaiFromSpiner(spinner_delai);

                Evenement evenement = new Evenement(id,libele,jour,h_d,h_f,lieu,description,recurrence,alerte,id_calendrier,heure_alerte,delai);

                DATABASE.evenementDao().upDateEvenment(evenement);
                Toast.makeText(this, "modification avec succse", Toast.LENGTH_SHORT).show();

              backToHome();
            }else {
                // Ajout d'un evenment
                Evenement evenement = new Evenement();


                // recuperation des information de l évenement
                evenement.setLibele(t_libele.getText().toString());
                evenement.setDescription(t_description.getText().toString());
                evenement.setLieu(t_lieu.getText().toString());
                evenement.setRecurrence(cb_recurrance.isChecked());
                evenement.setCalendrierId(id_cal);
                // le joure
                evenement.setJour(date.getTimeInMillis());
                // heure debut;
                evenement.setHeure_debut(heure_deb.getTimeInMillis());
                // heure fin
                evenement.setHeure_fin(heure_fin.getTimeInMillis());
                // alerte
                if (getdelaiFromSpiner(spinner_delai)==-1){
                    evenement.setAlerte(false);
                }else{
                    evenement.setAlerte(true);

                }
                //heure_alerte
                    evenement.setHeure_alerte( generatAlertTime()-getdelaiFromSpiner(spinner_delai));
                // delai alerte
                    evenement.setDelai_alerte(getdelaiFromSpiner(spinner_delai));
                //lisertion dans la base
                DATABASE.evenementDao().insert(evenement);
                Toast.makeText(this, "ajout avec succse", Toast.LENGTH_SHORT).show();
                backToHome();

            }
        }

    }
    private  long generatAlertTime(){

            Calendar timealerte = Calendar.getInstance();
            long time ;
            timealerte.set(Calendar.YEAR,date.get(Calendar.YEAR));
            timealerte.set(Calendar.MONTH,date.get(Calendar.MONTH));
            timealerte.set(Calendar.DAY_OF_MONTH,date.get(Calendar.DAY_OF_MONTH));
            timealerte.set(Calendar.HOUR_OF_DAY,date.get(Calendar.HOUR_OF_DAY));
            timealerte.set(Calendar.MINUTE,date.get(Calendar.MINUTE));
            time=timealerte.getTimeInMillis();
            return  time;
    }
    private  int getincomingInten_idcal() {
        if (getIntent().hasExtra("id_cal")) {
            int id_cal_selected = getIntent().getIntExtra("id_cal", 0);

            return id_cal_selected;
        }
        return 0;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minut) {
        Calendar calendar = Calendar.getInstance();


        if (flag == FLAG_START_TIME) {
            heure_deb.set(Calendar.HOUR,hour);
            heure_deb.set(Calendar.MINUTE,minut);

        } else if (flag == FLAG_END_TIME) {
            heure_fin.set(Calendar.HOUR,hour);
            heure_fin.set(Calendar.MINUTE,minut);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int Y, int M, int D) {
        Toast.makeText(this,"year "+Y+"month "+M+"day "+D,Toast.LENGTH_LONG).show();
        date.set(Calendar.YEAR,Y);
        date.set(Calendar.MONTH,M);
        date.set(Calendar.DAY_OF_MONTH,D);
    }

    // pour verifier si ond dois initialiser les champe ou non (modification)
    private boolean verifyIncomingIntent(){
        if (getincomingInten_idevenment()!=0)
           return  true;
        else
        return false;
    }
    // verifer et recuperer lid _evenment apartire de lintent
    private  int getincomingInten_idevenment() {
        if (getIntent().hasExtra("id_evenment")) {
            int id_evenment = getIntent().getIntExtra("id_evenment", 1);

            return id_evenment;
        }
        return 0;
    }
    public long getdelaiFromSpiner(Spinner spinner){
        long delai=0 ;
        if (spinner.getSelectedItem().equals("non")){
            delai=-1;
        }
        if (spinner.getSelectedItem().equals("5 m")){
            delai=5*60*1000;
        }
        if (spinner.getSelectedItem().equals("10 m")){
            delai=10*60*1000;

        }
        if (spinner.getSelectedItem().equals("15 m")){
            delai=15*60*1000;

        }
        return  delai;
    }
    public void backToHome (){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("id_cal", getincomingInten_idcal());
        startActivity(intent);
    }
}

