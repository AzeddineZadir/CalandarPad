package com.example.silver_desk.interfactest.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "alerte_table",
        foreignKeys = {
                @ForeignKey(entity = Evenement.class,
                        parentColumns = "id",
                        childColumns = "evenementId",
                        onDelete = CASCADE)}
)
public class Alerte {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;


    @ColumnInfo(name = "heure_declenchement")
    private long heure_declenchement;

    @ColumnInfo(name = "delai")
    private long delai;

    @ColumnInfo(name = "evenementId")
    private int evenementId;

    public Alerte() {
    }

    public Alerte(long heure_declenchement, long delai, int evenementId) {
        this.heure_declenchement = heure_declenchement;
        this.delai = delai;
        this.evenementId = evenementId;
    }

    public Alerte(int id, long heure_declenchement, long delai, int evenementId) {
        this.id = id;
        this.heure_declenchement = heure_declenchement;
        this.delai = delai;
        this.evenementId = evenementId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getHeure_declenchement() {
        return heure_declenchement;
    }

    public void setHeure_declenchement(long heure_declenchement) {
        this.heure_declenchement = heure_declenchement;
    }

    public long getDelai() {
        return delai;
    }

    public void setDelai(long delai) {
        this.delai = delai;
    }

    public int getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(int evenementId) {
        this.evenementId = evenementId;
    }
}
