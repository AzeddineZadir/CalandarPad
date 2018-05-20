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
    // insertion,
    @Insert
    public void insert(Evenement evenement);

    @Update
    public void upDateEvenment(Evenement evenement);

    @Delete
    public void deletEvenment(Evenement evenement);

    @Query("SELECT * FROM evenement_table")
    public   List<Evenement> loadAllevenement();

    //selectioner tous les evenments d"un calendrier deonné
    @Query("SELECT * FROM evenement_table WHERE calendrierId =:id")
    public  List<Evenement> loadEvenmentByIdCalendrier(int id);


    // selectiuoner un evenment donné
    @Query("SELECT * FROM evenement_table WHERE id =:id")
    public Evenement selectEvenmentById(int id);

   // selectionner le dernier evenment
   @Query("SELECT * FROM evenement_table WHERE libele =:libele")
   public Evenement selectEvenmentByLibele(String libele);


    // recuperer les evenment qui vont advenir a cette  minute
    @Query("SELECT * FROM evenement_table WHERE heure_alerte =:heur")
    public Evenement selectCurrentEvenment(long heur);

   // recuperer le delai dun evenment

    @Query("SELECT delai_alerte FROM evenement_table WHERE  id =:id")
    long loadAlertDelaiById(int id);


    @Query("DELETE FROM evenement_table ")
    void deleteAllEvenement();

    // la suppression dun evenment avec son id et li du calendrier parent
    @Query("DELETE FROM evenement_table WHERE id =:id_event AND calendrierId =:id_cal")
    void deleteEvenementByidCalAndIdEvent(int id_event,int id_cal);




    // modifier lid calendrier dun evenment
    @Query("UPDATE  evenement_table SET calendrierId =:id_cal WHERE  id =:id_evenment")
    void updateIdCalForEvenment(int id_evenment,int id_cal);


    // supprimer les evenment qui son,t relier a un calendrier parent X
    @Query("DELETE FROM evenement_table WHERE calendrierId=:id_cal")
    void deleteAllEvenmentByIdCalendrier(int id_cal);


    //Requête selection pour le service
    @Query("SELECT * FROM evenement_table WHERE heure_alerte >=:currentTime AND heure_debut<=30000+:currentTime ")
    List<Evenement> selectCurrentEvenement(long currentTime);





}
