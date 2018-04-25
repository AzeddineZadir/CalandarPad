package com.example.silver_desk.interfactest.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by silver-desk on 01/04/2018.
 */
@Entity(tableName = "calendrier_table")
public class Calendrier {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id ;

    @ColumnInfo(name = "titre")
    @NonNull
    private String titre ;

    @ColumnInfo(name = "visibilite")
    @NonNull
    private String visibilite ;

    @ColumnInfo(name = "activite")
    @NonNull
    private String activite ;

    @ColumnInfo(name = "couleur")
    private String couleur;

    @ColumnInfo(name = "priorite")
    private String priorite ;

    @ColumnInfo(name="description")
    private String description;


    public Calendrier(int id, @NonNull String titre, @NonNull String visibilite, @NonNull String activite, String couleur, String priorite,String description) {
        this.id = id;
        this.titre = titre;
        this.visibilite = visibilite;
        this.activite = activite;
        this.couleur = couleur;
        this.priorite = priorite;
        this.description=description;
    }

    @NonNull

    public String getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(@NonNull String visibilite) {
        this.visibilite = visibilite;
    }

    @NonNull
    public String getActivite() {
        return activite;
    }

    public void setActivite(@NonNull String activite) {
        this.activite = activite;
    }




    public Calendrier() {
    }

    public Calendrier(String titre) {
this.titre=titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitre() {
        return titre;
    }

    public void setTitre(@NonNull String titre) {
        this.titre = titre;
    }


    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorité) {
        this.priorite = priorité;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
