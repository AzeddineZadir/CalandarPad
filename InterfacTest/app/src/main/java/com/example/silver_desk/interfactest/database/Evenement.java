package com.example.silver_desk.interfactest.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.silver_desk.interfactest.database.TypeConverters.DateConverter;
import com.example.silver_desk.interfactest.database.TypeConverters.TimeConverter;

import java.sql.Time;
import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by silver-desk on 06/04/2018.
 */
@Entity(tableName = "evenement_table",
        foreignKeys = {
        @ForeignKey(entity = Calendrier.class,
                    parentColumns = "id",
                    childColumns = "calendrierId",
                    onDelete = CASCADE)}
        )
public class Evenement {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id ;

    @ColumnInfo(name = "libele")
    @NonNull
    private String libele ;

    @ColumnInfo(name = "jour")
    @NonNull
    @TypeConverters({DateConverter.class})
    private Date jour ;

    @ColumnInfo(name = "heure_debut")
    @NonNull
    @TypeConverters({TimeConverter.class})
    private long heure_debut ;

    @ColumnInfo(name = "heure_fin")
    @TypeConverters({TimeConverter.class})
    private long heure_fin;


    @ColumnInfo(name = "lieu")
    private String lieu;



    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "recurrence")
    private String recurrence;

    @ColumnInfo(name="calendrierId")
    private int calendrierId;


    //Constructeurs
    public Evenement() {
    }


    public Evenement(@NonNull String libele, @NonNull Date jour, @NonNull long heure_debut, long heure_fin, String lieu, String description, String recurrence, int calendrierId) {
        this.libele = libele;
        this.jour = jour;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.lieu = lieu;
        this.description = description;
        this.recurrence = recurrence;
        this.calendrierId = calendrierId;
    }

    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @NonNull
    public String getLibele() {
        return libele;
    }

    public void setLibele(@NonNull String libele) {
        this.libele = libele;
    }

    @NonNull
    public Date getJour() {
        return jour;
    }

    public void setJour(@NonNull Date jour) {
        this.jour = jour;
    }

    @NonNull
    public long getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(@NonNull long heure_debut) {
        this.heure_debut = heure_debut;
    }

    public long getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(long heure_fin) {
        this.heure_fin = heure_fin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public int getCalendrierId() {
        return calendrierId;
    }

    public void setCalendrierId(int calendrierId) {
        this.calendrierId = calendrierId;
    }
}
