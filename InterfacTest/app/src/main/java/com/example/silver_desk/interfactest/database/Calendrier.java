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
    private boolean visibilite ;

    @ColumnInfo(name = "activite")
    @NonNull
    private boolean activite ;

    @ColumnInfo(name = "couleur")
    private int couleur;

    @ColumnInfo(name = "priorite")
    private String priorite ;

    @ColumnInfo(name="description")
    private String description;

    public Calendrier() {
    }

    public Calendrier(int id, @NonNull String titre, @NonNull boolean visibilite, @NonNull boolean activite, int couleur, String priorite,String description) {
        this.id = id;
        this.titre = titre;
        this.visibilite = visibilite;
        this.activite = activite;
        this.couleur = couleur;
        this.priorite = priorite;
        this.description=description;
    }

    @NonNull
    public boolean isVisibilite() {
        return visibilite;
    }

    public void setVisibilite(@NonNull boolean visibilite) {
        this.visibilite = visibilite;
    }

    @NonNull
    public boolean isActivite() {
        return activite;
    }

    public void setActivite(@NonNull boolean activite) {
        this.activite = activite;
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


    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
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
