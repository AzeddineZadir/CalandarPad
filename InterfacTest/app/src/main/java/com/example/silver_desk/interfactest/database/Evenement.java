package com.example.silver_desk.interfactest.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


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

    private long jour ;

    @ColumnInfo(name = "heure_debut")
    @NonNull

    private long heure_debut ;

    @ColumnInfo(name = "heure_fin")

    private long heure_fin;


    @ColumnInfo(name = "lieu")
    private String lieu;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "recurrence")
    private boolean recurrence;

    @ColumnInfo(name = "alerte")
    private boolean alerte;

    @ColumnInfo(name="calendrierId")
    private int calendrierId;


    //Constructeurs
    public Evenement() {
    }

    public Evenement(int id, @NonNull String libele, @NonNull long jour, @NonNull long heure_debut, long heure_fin, String lieu, String description, boolean recurrence, boolean alerte, int calendrierId) {
        this.id = id;
        this.libele = libele;
        this.jour = jour;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.lieu = lieu;
        this.description = description;
        this.recurrence = recurrence;
        this.alerte = alerte;
        this.calendrierId = calendrierId;
    }

    public Evenement(@NonNull String libele, @NonNull long jour, @NonNull long heure_debut, long heure_fin, String lieu, String description, boolean recurrence, boolean alerte, int calendrierId) {
        this.libele = libele;
        this.jour = jour;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.lieu = lieu;
        this.description = description;
        this.recurrence = recurrence;
        this.alerte = alerte;
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
    public long getJour() {
        return jour;
    }

    public void setJour(@NonNull long jour) {
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

    public boolean getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(boolean recurrence) {
        this.recurrence = recurrence;
    }

    public int getCalendrierId() {
        return calendrierId;
    }

    public boolean getAlerte() {
        return alerte;
    }

    public void setAlerte(boolean alerte) {
        this.alerte = alerte;
    }

    public void setCalendrierId(int calendrierId) {
        this.calendrierId = calendrierId;
    }
}
