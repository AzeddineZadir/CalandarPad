package com.example.silver_desk.interfactest;

import android.arch.persistence.room.Room;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Evenement;
import com.example.silver_desk.interfactest.fragment.ListeCalendrierFragment;

import org.w3c.dom.Text;

public class CalendrierActivity extends AppCompatActivity  {
    public static FragmentManager fragmentManager ;
    public static android.support.v4.app.FragmentTransaction  transaction;
    public static AppDatabase DATABASE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);
        fragmentManager = getSupportFragmentManager();

        //inisialisation de la BDD
        CalendrierActivity.DATABASE= Room.databaseBuilder(this,AppDatabase.class,"AppDatabase").allowMainThreadQueries().build();
        // ajout dun evenment pour le teste
        Evenement evt= new Evenement("jesdsdsdsdeee",0,0,0,"erer","dfdfdsfdf",true,true,2);
       //CalendrierActivity.DATABASE.evenementDao().insert(evt);


       //l affichage de la liste des calendrier sur un frag
        if (findViewById(R.id.conteneur)!=null){
            if(savedInstanceState!=null){
                return;
            }
        transaction=fragmentManager.beginTransaction().setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ListeCalendrierFragment fragment = new ListeCalendrierFragment() ;
        transaction.add(R.id.conteneur,fragment,null);
        transaction.commit();
        }
    }


}

