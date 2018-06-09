package com.example.silver_desk.interfactest.database.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.silver_desk.interfactest.database.Alerte;

import java.util.List;

@Dao
public interface AlerteDao {

    @Insert
    void insertAlerte(Alerte alerte);

    @Delete
    void deleteAlerte(Alerte alerte);

    @Update
    void updateAlerte(Alerte alerte);

    //Requête selection pour le service
    @Query("SELECT * FROM alerte_table " +
            "WHERE (datetime(heure_declenchement/1000,'unixepoch') >=datetime('now')" +
            "AND datetime(heure_declenchement/1000,'unixepoch')<=datetime('now','+30 seconds'))")
    List<Alerte> selectCurrentEvenement();

}
