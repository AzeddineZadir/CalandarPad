package com.example.silver_desk.interfactest.fragment;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.silver_desk.interfactest.CalendrierActivity;
import com.example.silver_desk.interfactest.EvenementActivity;
import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Evenement;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListeEvenmentFragment extends Fragment {
    TextView t_listeEvenment;
    String chaine_daffichage ;

    public ListeEvenmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liste_evenment, container, false);
        t_listeEvenment=(TextView)view.findViewById(R.id.recyclerView);
        List<Evenement> EvenmentList= CalendrierActivity.database.evenementDao().loadAllevenement();
        chaine_daffichage = "";



        for  (Evenement evenement :EvenmentList){
            int id = evenement.getId();
            String libele= evenement.getLibele();
            Date jour= evenement.getJour();
            Time heur_debut=evenement.getHeure_debut();


            chaine_daffichage= chaine_daffichage +"\n\n\n\n"+"id :"+id+"\n\n Libele:"+libele+"\n\njour:"+"heure :"+heur_debut;
        }
        t_listeEvenment.setText(chaine_daffichage);
        return view ;
    }

}
