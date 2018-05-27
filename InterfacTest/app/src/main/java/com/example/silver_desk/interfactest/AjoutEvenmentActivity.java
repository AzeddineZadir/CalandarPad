package com.example.silver_desk.interfactest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.silver_desk.interfactest.database.Evenement;
import com.example.silver_desk.interfactest.fragment.DatePickerFragment;
import com.example.silver_desk.interfactest.fragment.TimePickerFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static com.example.silver_desk.interfactest.HomeActivity.DATABASE;
import static com.example.silver_desk.interfactest.HomeActivity.actionBarDrawerToggle;
import static com.example.silver_desk.interfactest.HomeActivity.drawerLayout;
import static com.example.silver_desk.interfactest.fragment.TimePickerFragment.FLAG_END_TIME;
import static com.example.silver_desk.interfactest.fragment.TimePickerFragment.FLAG_START_TIME;
import static com.example.silver_desk.interfactest.fragment.TimePickerFragment.flag;

public class AjoutEvenmentActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, NavigationView.OnNavigationItemSelectedListener {
    EditText e_libele, e_description, e_lieu;
    CheckBox cb_recurrance;
    Button b_debut, b_fin, b_joure;
    FloatingActionButton fab_save_event;
    FloatingActionButton fab_delet_event;
    Calendar date;
    Calendar heure_deb, heure_fin;
    TimePickerFragment timePickerFragment;
    DatePickerFragment datePickerFragment;
    Spinner spinner_delai, spinner_calendrier_parent;
    ArrayAdapter arrayAdapterdelai, arrayAdaptercal;
    List<String> listecal,listedelai;

    private boolean modification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_evenment);
        // taction bar
        getSupportActionBar().setTitle(R.string.addevent);

        // le drawer menu

        drawerLayout=(DrawerLayout)findViewById(R.id.ajout_evenment_drawer);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this ,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView =(NavigationView)findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);

        // les Button
        b_debut = (Button) findViewById(R.id.b_debut);
        b_debut.setOnClickListener(this);
        b_fin = (Button) findViewById(R.id.b_fin);
        b_fin.setOnClickListener(this);
        b_joure = (Button) findViewById(R.id.b_joure);
        b_joure.setOnClickListener(this);


        // les edittext
        e_libele = (EditText) findViewById(R.id.e_libele);
        e_description = (EditText) findViewById(R.id.e_description);
        e_lieu = (EditText) findViewById(R.id.e_lieu);

        // fab fab_save event
        fab_save_event = (FloatingActionButton) findViewById(R.id.fab_save_event);
        fab_save_event.setOnClickListener(this);

        //fab delete event
        fab_delet_event = (FloatingActionButton) findViewById(R.id.fab_delet_event);
        fab_delet_event.setOnClickListener(this);

        //les check box

        cb_recurrance = (CheckBox) findViewById(R.id.cb_recurrence);

        //le time picker
        timePickerFragment = new TimePickerFragment();
        date = Calendar.getInstance();
        heure_deb = Calendar.getInstance();
        heure_fin = Calendar.getInstance();

        // le spinner
        spinner_delai = (Spinner) findViewById(R.id.spinner_delai);

       listedelai=stringArrayToList(getResources().getStringArray(R.array.delai_alerte));
        arrayAdapterdelai = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,listedelai);
        spinner_delai.setAdapter(arrayAdapterdelai);

        // spinner cal
        spinner_calendrier_parent = (Spinner) findViewById(R.id.spinner_calendrier_parent);
        listecal = DATABASE.calendrierDao().loadAllCalendrierTitels();
        arrayAdaptercal = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listecal);
        spinner_calendrier_parent.setAdapter(arrayAdaptercal);
        if (verifyIncomingIntentIdEvenment()==false){
            // masquer le button supprimer
            fab_delet_event.setVisibility(View.INVISIBLE);

        }

        viewSetInfo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_lmenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true ;
        }
        int id = item.getItemId();
        switch (id){
            case R.id.close_action :
                backToHome();
                return  true ;
        }
        return super.onOptionsItemSelected(item);
    }






    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.b_debut) {
            timePickerFragment.setFlag(FLAG_START_TIME);
            timePickerFragment.show(getSupportFragmentManager(), "timepicker");


        }
        if (view.getId() == R.id.b_fin) {

            timePickerFragment.setFlag(TimePickerFragment.FLAG_END_TIME);
            timePickerFragment.show(getSupportFragmentManager(), "timepicker");
            ;

        }
        if (view.getId() == R.id.b_joure) {
            datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), "date picker");
        }
        // ajout d'un evenment  ou modification

        if (view.getId() == R.id.fab_save_event) {
            boolean modification;
            int id_cal = DATABASE.calendrierDao().getIdCalendrierByTitel(spinner_calendrier_parent.getSelectedItem().toString());

            if (verifyIncomingIntentIdEvenment()) {
                modification = true;
            } else {
                modification = false;
            }

            if (modification == true) {
                // modification
                updatEvenment(getincomingInten_idevenment(), id_cal);
                backToHome();
            } else {
                // Ajout d'un evenment
                Evenement evenement = new Evenement();
                inserEvenment(evenement, id_cal);
                backToHome();

            }

        }

        //supprimer un evenment
        if (view.getId() == R.id.fab_delet_event) {

            if (verifyIncomingIntentIdEvenment()) {
                final Evenement evenement = DATABASE.evenementDao().selectEvenmentById(getincomingInten_idevenment());
                AlertDialog.Builder deleteDailog = new AlertDialog.Builder(this);
                deleteDailog.setTitle(getString(R.string.deleteTitleDialogEvent));
                deleteDailog.setMessage(getString(R.string.deleteMessageDialogEvent) + evenement.getLibele()+" ?");
                // positive button(confirm and delet)
                deleteDailog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DATABASE.evenementDao().deletEvenment(evenement);
                        dialogInterface.dismiss();
                        backToHome();
                    }
                });

                // negative button  (dont delet)
                deleteDailog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });

                AlertDialog alertDialog = deleteDailog.create();
                alertDialog.show();
            }


        }


    }

    private long generatAlertTime( Calendar heure_deb ,Spinner spinner) {

        Calendar timealerte = Calendar.getInstance();
        long time=0;
        long delai = getdelaiFromSpiner(spinner);
        if(delai==-1){
            time=0 ;
        }else{
        timealerte.setTimeInMillis(heure_deb.getTimeInMillis()-delai);
        time = timealerte.getTimeInMillis();}
        return  time ;
    }


    private int getincomingInten_idcal() {
        if (getIntent().hasExtra("id_cal")) {
            int id_cal_selected = getIntent().getIntExtra("id_cal", 0);

            return id_cal_selected;
        }
        return 0;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minut) {

        if (flag == FLAG_START_TIME) {
            heure_deb.set(Calendar.HOUR_OF_DAY, hour);
            heure_deb.set(Calendar.MINUTE, minut);

            b_debut.setText( genratTitelWithTime(heure_deb));
        } else if (flag == FLAG_END_TIME) {
            heure_fin.set(Calendar.HOUR_OF_DAY, hour);
            heure_fin.set(Calendar.MINUTE, minut);

            b_fin.setText(genratTitelWithTime(heure_fin));
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int Y, int M, int D) {

        date.set(Calendar.YEAR, Y);
        date.set(Calendar.MONTH, M);
        date.set(Calendar.DAY_OF_MONTH, D);

       b_joure.setText(genratTitelWithDate(date));
    }

    // pour verifier si ond dois initialiser les champe ou non (modification)
    private boolean verifyIncomingIntentIdEvenment() {
        if (getincomingInten_idevenment() != 0)
            return true;
        else
            return false;
    }

    private boolean verifyIncomingIntentDate() {
        if (getincomingInten_date() != 0)
            return true;
        else
            return false;
    }

    // verifer et recuperer lid _evenment apartire de lintent
    private int getincomingInten_idevenment() {
        if (getIntent().hasExtra("id_evenment")) {
            int id_evenment = getIntent().getIntExtra("id_evenment", 1);

            return id_evenment;
        }
        return 0;
    }

    // verifier et recuperer date pour initialiset le date de levenment
    private long getincomingInten_date() {
        long date;
        if (getIntent().hasExtra("date")) {
             date = getIntent().getLongExtra("date",0);

            return date;
        }
        return 0;
    }
    // recuperer la valeure du delai apritire de litem selectioner dans le spinner
    public long getdelaiFromSpiner(Spinner spinner) {
        long d = 0;
        if (spinner.getSelectedItemId() == 0) {
            d = -1;
        } else {
            d = 5 * 60 * 1000 * (spinner.getSelectedItemId() - 1);
        }
        return d;
    }

    // revenire a la vue week view
    public void backToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        // intent.putExtra("id_cal", getincomingInten_idcal());
        startActivity(intent);
    }

    // remplire les informations de levenment sur la vue apré click
    public void viewSetInfo() {
        // la vue doit etre formater suit au click sur un evenment

        if (verifyIncomingIntentIdEvenment()) {
            Evenement evenement = DATABASE.evenementDao().selectEvenmentById(getincomingInten_idevenment());

            e_libele.setText(evenement.getLibele().toString());
            e_description.setText(evenement.getDescription().toString());
            e_lieu.setText(evenement.getLieu().toString());
            cb_recurrance.setChecked(evenement.isRecurrence());
            // initialisation du spinner au calendrier parent de levenmùent
            int id_cal = evenement.getCalendrierId();
            String titre = DATABASE.calendrierDao().selecCalendrierTitreById(id_cal);
            // recuperer la position du titrre dans le spinner
            spinner_calendrier_parent.setSelection(getPositionOfitemByValu(titre, listecal));
            // initialisation du spinner delait
            long delai = DATABASE.evenementDao().loadAlertDelaiById(evenement.getId());
            String[] stringArray = getResources().getStringArray(R.array.delai_alerte);
            List<String> stringList = new ArrayList<String>();
            stringList = stringArrayToList(stringArray);
            int pos = getPositionOfitemByValu(longDelaiToStringItem(delai), stringList);
            spinner_delai.setSelection(pos);
            // intialisation du temps
            heure_deb.setTimeInMillis(evenement.getHeure_debut());
            heure_fin.setTimeInMillis(evenement.getHeure_fin());
            // init du jour
            date.setTimeInMillis(evenement.getJour());
        }
            // afficher les info sur les buttons
        setTimeDateButtonInfo(b_debut,b_fin,b_joure);
    }

    // recuperer la position de litem grace a ca valeure textuel  pour  fair un setselection sur le spinner
    public int getPositionOfitemByValu(String valu, List<String> stringList) {
        int position = 0;
        for (int i = 0; i < stringList.size(); i++) {
            // si un element de la liste correspond a la valeure
            // return his position
            if (stringList.get(i).equals(valu)) {
                position = i;

            }

        }
        return position;


    }

    //mapper le delai recuperer de levenment pour corespondre a un item du spnner
    public String longDelaiToStringItem(long delai) {
        String item = "";
        long temp = (delai / 300000) + 1;
        switch ((int) temp) {
            case 0:
                item = "pas d'alerte";
                break;

            case 1:
                item = "0 m";
                break;

            case 2:
                item = "5 m";
                break;

            case 3:
                item = "10 m";
                break;

            case 4:
                item = "15 m";
                break;

            default:
                item = "5 m";

        }


        return item;
    }

    // transformer un string array a une list string
    public List<String> stringArrayToList(String[] strings) {
        List<String> stringList = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++) {
            stringList.add(i, strings[i]);
        }


        return stringList;
    }

    // modfifer un evenment
    public void updatEvenment(int id_ev, int id_cal) {

        // id evenment
        int id = id_ev;
        // libele evenment
        String libele = e_libele.getText().toString();
        //date evenment
        long jour = date.getTimeInMillis();
        // heure debut
        long h_d = heure_deb.getTimeInMillis();
        // heure fin
        long h_f = heure_fin.getTimeInMillis();
        // lieu
        String lieu = e_lieu.getText().toString();
        // description
        String description = e_description.getText().toString();
        //recurrance
        boolean recurrence = cb_recurrance.isChecked();
        // alerte
        boolean alerte = false;
        if (getdelaiFromSpiner(spinner_delai) == -1) {
            alerte = false;
        } else {
            alerte = true;
        }

        // id calendrier parent
        int id_calendrier = id_cal;
        // heure alerte
        long heure_alerte = generatAlertTime(heure_deb,spinner_delai);
        // delai alerte
        long delai = getdelaiFromSpiner(spinner_delai);

        Evenement evenementModifer = new Evenement(id, libele, jour, h_d, h_f, lieu, description, recurrence, alerte, id_calendrier, heure_alerte, delai);

        DATABASE.evenementDao().upDateEvenment(evenementModifer);
        Toast.makeText(this, getString(R.string.updateSuccess), Toast.LENGTH_SHORT).show();


    }

    //inser un evenment
    public void inserEvenment(Evenement evenement, int id_cal) {
        // recuperation des information de l évenement
        evenement.setLibele(e_libele.getText().toString());
        evenement.setDescription(e_description.getText().toString());
        evenement.setLieu(e_lieu.getText().toString());
        evenement.setRecurrence(cb_recurrance.isChecked());
        evenement.setCalendrierId(id_cal);
        // le joure
        evenement.setJour(date.getTimeInMillis());
        // heure debut;
        evenement.setHeure_debut(heure_deb.getTimeInMillis());

        // heure fin
        evenement.setHeure_fin(heure_fin.getTimeInMillis());

        // alerte
        if (getdelaiFromSpiner(spinner_delai) == -1) {
            evenement.setAlerte(false);
        } else {
            evenement.setAlerte(true);

        }
        //heure_alerte
        evenement.setHeure_alerte(generatAlertTime(heure_deb,spinner_delai));
        // delai alerte
        evenement.setDelai_alerte(getdelaiFromSpiner(spinner_delai));
        //insertion dans la base
        DATABASE.evenementDao().insert(evenement);
        Toast.makeText(this, getString(R.string.addSuccess), Toast.LENGTH_SHORT).show();
    }

    // generer un titre avec heur_debu et heure fin
    public String genratTitelWithTime(Calendar h) {
        String titel = "";

        int h_d = h.get(Calendar.HOUR_OF_DAY);
        int m_d = h.get(Calendar.MINUTE);
        titel = formaterValeure(h_d)+ ":" + formaterValeure(m_d);

        return titel;
    }

    // generer un tyitre avec date
    public String genratTitelWithDate(Calendar h) {
        String titel = "";
        int D, M, Y;
        D = h.get(Calendar.DAY_OF_MONTH);
        M = h.get(Calendar.MONTH);
        M=M+1 ;
        Y = h.get(Calendar.YEAR);

       titel = "" + formaterValeure(D) + "/" + formaterValeure(M)+ "/" + formaterValeure(Y);
        return titel;
    }


    public String formatDate(int j,int m ,int y){
        String date="";
        String js="",ms="";
        if (j<10){
         js="0"+j ;
        }
        if (m<10){
          ms="0"+m;

        }
        date=""+js+"/"+ms+"/"+y;

        return  date ;
    }

    // formater les chifre inferierue a 10 pour qui ils s'affiche de la sort 0X
    public String formaterValeure( int h){
        String c ;
        if (h<10){
            c = "0"+h ;
        }else{
            c=""+h ;
        }
        return  c ;
    }

    public void setTimeDateButtonInfo(Button b_hd,Button b_hf,Button b_j){
        if (verifyIncomingIntentDate()){
            Calendar mdate= Calendar.getInstance();
            mdate.setTimeInMillis(getincomingInten_date());

            long uneheure=3600000;
            // intialisation du temps
            heure_deb.setTimeInMillis(mdate.getTimeInMillis());
            heure_fin.setTimeInMillis(mdate.getTimeInMillis()+uneheure);
            // init du jour
            date.setTimeInMillis(mdate.getTimeInMillis());

            // afficher lheure sur les button
            b_hd.setText(genratTitelWithTime(heure_deb));
            b_hf.setText(genratTitelWithTime(heure_fin));

            // afficher la dat-e sur le button
            b_j.setText(genratTitelWithDate(date));
        }else if(verifyIncomingIntentIdEvenment()){
            // afficher lheure sur les button
            b_hd.setText(genratTitelWithTime(heure_deb));
            b_hf.setText(genratTitelWithTime(heure_fin));

            // afficher la dat-e sur le button
            b_j.setText(genratTitelWithDate(date));

        }else{
            Calendar mdate= Calendar.getInstance();

            long uneheure=3600000;
            // intialisation du temps
            heure_deb.setTimeInMillis(mdate.getTimeInMillis());
            heure_fin.setTimeInMillis(mdate.getTimeInMillis()+uneheure);
            // init du jour
            date.setTimeInMillis(mdate.getTimeInMillis());

            // afficher lheure sur les button
            b_hd.setText(genratTitelWithTime(heure_deb));
            b_hf.setText(genratTitelWithTime(heure_fin));

            // afficher la dat-e sur le button
            b_j.setText(genratTitelWithDate(date));
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //affichage du dashboard
        if (id==R.id.db){
            Toast.makeText(this,getString(R.string.home),Toast.LENGTH_LONG).show();
                goHome();
        }
        //affichage du calendrier
        if (id==R.id.calendrier){
            Toast.makeText(this,getString(R.string.calendar),Toast.LENGTH_LONG).show();
            goCalendrierActivity();
        }


        return false;
    }


    public void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    //open calendrier activity
    public void goCalendrierActivity() {
        Intent intent_calendrier = new Intent(this, CalendrierActivity.class);
        startActivity(intent_calendrier);

    }

}

