package com.example.silver_desk.interfactest.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.silver_desk.interfactest.database.Daos.AlerteDao;
import com.example.silver_desk.interfactest.database.Daos.CalendrierDao;
import com.example.silver_desk.interfactest.database.Daos.EvenementDao;

@Database(entities = {Calendrier.class,Evenement.class,Alerte.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CalendrierDao calendrierDao ();
    public abstract EvenementDao evenementDao();
    public abstract AlerteDao alerteDao();

    private static AppDatabase INSTANCE;
    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "AppDatabase")
                            .build();

                }
            }
        }
        return INSTANCE;
    }

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
          /*  Calendrier calendrier = new Calendrier(1,"rouge","important","Hello","rouge","rouge","description1");
            mDao.insert(calendrier);
            Calendrier calendrier1 = new Calendrier(2,"rouge","important","Hello","rouge","rouge","description2");
            mDao.insert(calendrier1);
            Calendrier calendrier2 = new Calendrier(3,"rouge","important","Hello","rouge","rouge","description3");
            mDao.insert(calendrier2);*/
            return null;
        }
    }
}
