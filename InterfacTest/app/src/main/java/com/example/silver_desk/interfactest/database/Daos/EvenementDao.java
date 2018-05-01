package com.example.silver_desk.interfactest.database.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.silver_desk.interfactest.database.Calendrier;
import com.example.silver_desk.interfactest.database.Evenement;

import java.util.List;


@Dao
public interface EvenementDao {
    @Insert
    public void insert(Evenement evenement);

    @Update
    public void upDateEvenment(Evenement evenement);

    @Delete
    public void deletEvenment(Evenement evenement);

    @Query("SELECT * FROM evenement_table")
    public   List<Evenement> loadAllevenement();

    //selectioner tous les evenments d"un calendrier deonné
    @Query("SELECT * FROM evenement_table WHERE calendrierId like :id")
    public  List<Evenement> loadEvenmentById(int id);

    @Query("DELETE FROM evenement_table ")
    void deleteAllEvenement();

}
