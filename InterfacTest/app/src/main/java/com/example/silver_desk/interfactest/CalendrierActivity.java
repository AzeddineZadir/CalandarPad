package com.example.silver_desk.interfactest;

import android.app.FragmentTransaction;
import android.arch.persistence.room.Room;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Calendrier;
import com.example.silver_desk.interfactest.fragment.AjoutCalendrierFragment;

import java.util.List;

public class CalendrierActivity extends AppCompatActivity  {
    public static FragmentManager fragmentManager ;
    FloatingActionButton fab_add ;
    public static AppDatabase database ;
    TextView t_listecal;
    String chaine_daffichage ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);
        fragmentManager = getSupportFragmentManager();
        fab_add=(FloatingActionButton) findViewById(R.id.fab_add);
        t_listecal=(TextView) findViewById(R.id.t_listecalendrier);

        database= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"AppDatabase").allowMainThreadQueries().build();
        List<Calendrier> calendrierList= database.calendrierDao().loadAllCalendrier();
        chaine_daffichage = "";
        for  (Calendrier calendrier :calendrierList){
        int id = calendrier.getId();
        String titre= calendrier.getTitre();
        String visibilite= calendrier.getVisibilite();
        String activite= calendrier.getActivite();
        String priorite= calendrier.getPriorite();
        String couleur= calendrier.getCouleur();
        chaine_daffichage= chaine_daffichage +"\n\n\n\n"+"id :"+id+"\n\n titre :"+titre;

    }
     t_listecal.setText(chaine_daffichage);
    }

public void fab_addOnclick(View view){

    Toast.makeText(CalendrierActivity.this,"calendrier activity",Toast.LENGTH_LONG).show();
    if(findViewById(R.id.conteneur_defragments)!=null) {

      android.support.v4.app.FragmentTransaction transaction= fragmentManager.beginTransaction();
        AjoutCalendrierFragment fragment = new AjoutCalendrierFragment();
        transaction.add(R.id.conteneur_defragments,fragment,null);
        transaction.commit();
        fab_add.setVisibility(View.INVISIBLE);
    }

}

}

