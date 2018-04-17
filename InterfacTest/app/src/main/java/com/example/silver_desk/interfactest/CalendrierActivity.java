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
import com.example.silver_desk.interfactest.fragment.ListeCalendrierFragment;

import java.util.List;

public class CalendrierActivity extends AppCompatActivity  {
    public static FragmentManager fragmentManager ;
    public static AppDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);
        fragmentManager = getSupportFragmentManager();

       //l affichage de la liste des calendrier sur un frag
        android.support.v4.app.FragmentTransaction transaction= CalendrierActivity.fragmentManager.beginTransaction();
        ListeCalendrierFragment fragment = new ListeCalendrierFragment() ;
        transaction.add(R.id.conteneur_defragments,fragment,null);
        transaction.commit();

    }




}

