package com.example.silver_desk.interfactest.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.Daos.AlerteDao;
import com.example.silver_desk.interfactest.database.Daos.CalendrierDao;
import com.example.silver_desk.interfactest.database.Daos.EvenementDao;

import java.util.concurrent.Executors;

import static android.appwidget.AppWidgetManager.getInstance;

@Database(entities = {Calendrier.class,Evenement.class,Alerte.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CalendrierDao calendrierDao ();
    public abstract EvenementDao evenementDao();
    public abstract AlerteDao alerteDao();

    private static AppDatabase INSTANCE;

    public synchronized static AppDatabase getInstance(Context context) {
        Log.d("dbg", "avent la creation de la base");
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
            Log.d("dbg        2", "pendent la creation de la base");

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
                                Calendrier calendrier = new Calendrier("mon calendrier",true,true,R.color.color2,"moyenne","");
                                getInstance(context).calendrierDao().insert(calendrier);
                                Log.d("dbg", "run: yaaaaaaaaaaahhhhhhh");
                            }
                        });
                    }
                })
                .build();
    }











/*
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CalendrierDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.calendrierDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();


            return null;
        }
    }*/

}
