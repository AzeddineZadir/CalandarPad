package com.example.silver_desk.interfactest.database.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.silver_desk.interfactest.database.Calendrier;

import java.util.List;

/**
 * Created by silver-desk on 01/04/2018.
 */

@Dao
public interface CalendrierDao {
    @Insert
    public void insert(Calendrier calendrier);

    @Update
    public void upDateCalendrier(Calendrier calendrier);

    @Delete
    public void deletCalendrier(Calendrier calendrier);

    @Query("SELECT * FROM calendrier_table")
    List<Calendrier>  loadAllCalendrier();
    // selectioner un calendrier a partire de sont id
    @Query("SELECT * FROM calendrier_table where id=:id")
    Calendrier  selecCalendrierById(int id);


    @Query("SELECT titre FROM calendrier_table where id=:id")
    String  selecCalendrierTitreById(int id);

    @Query("SELECT titre FROM calendrier_table")
    List<String>  loadAllCalendrierTitels();

    @Query("SELECT id FROM calendrier_table where titre =:titre")
     int  getIdCalendrierByTitel(String titre);

    // recuperer la couleure du cal
    @Query("SELECT couleur FROM calendrier_table where id =:id")
    int  getIdCalendrierColorById(int id);

    @Query("DELETE FROM calendrier_table ")
    void deleteAll();

}

