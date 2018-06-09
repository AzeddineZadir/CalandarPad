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


    // recupérer une alerte grace a son id evenment
    @Query("SELECT id FROM alerte_table WHERE evenementId =:id_event")
    int getIdAlertByIdEvent(int id_event);

    @Query("SELECT delai FROM alerte_table WHERE evenementId =:id_event")
    long getdelaiFromAlertByIdEvent(int id_event);
    //Requête selection pour le service
    @Query("SELECT * FROM alerte_table " +
            "WHERE (datetime(heure_declenchement/1000,'unixepoch') >=datetime('now')" +
            "AND datetime(heure_declenchement/1000,'unixepoch')<=datetime('now','+30 seconds'))")
    List<Alerte> selectCurrentAlertes();

}
