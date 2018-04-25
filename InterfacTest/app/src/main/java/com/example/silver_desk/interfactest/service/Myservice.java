package com.example.silver_desk.interfactest.service;

import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.silver_desk.interfactest.CalendrierActivity;
import com.example.silver_desk.interfactest.database.Alerte;
import com.example.silver_desk.interfactest.database.AppDatabase;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

/**
 * Created by silver-desk on 24/04/2018.
 */

public class Myservice extends Service {
   static AppDatabase DATABASE ;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Myservice.DATABASE= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"AppDatabase").allowMainThreadQueries().build();
        List<Alerte> Alertlist= Myservice.DATABASE.alerteDao().loadAllAlerte();

                for (Alerte alerte1 : Alertlist) {
                    Time heur_debut = alerte1.getHeure();



                }

        return START_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
