package com.example.silver_desk.interfactest.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.Daos.AlerteDao;
import com.example.silver_desk.interfactest.database.Daos.CalendrierDao;
import com.example.silver_desk.interfactest.database.Daos.EvenementDao;

import java.util.concurrent.Executors;

@Database(entities = {Calendrier.class,Evenement.class,Alerte.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CalendrierDao calendrierDao ();
    public abstract EvenementDao evenementDao();
    public abstract AlerteDao alerteDao();
    private static AppDatabase INSTANCE;

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "AppDatabase")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {

                                Calendrier calendrier = new Calendrier("mon calendrier",true,R.color.color2,"");
                                getInstance(context).calendrierDao().insert(calendrier);
                            }
                        });
                    }
                })
                .build();
    }


}
