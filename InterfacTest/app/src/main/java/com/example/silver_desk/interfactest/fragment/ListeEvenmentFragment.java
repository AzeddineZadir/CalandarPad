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
import java.util.Calendar;
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
<<<<<<< HEAD
        t_listeEvenment=(TextView)view.findViewById(R.id.listevenemnt);
        // creation dun evenment

        Date date=new Date(148645645);
        long td= 21849879;
        long tf= 21849879;
        String lib="rdv";
        Evenement event=new Evenement(lib,date,td, tf, "Bejaia", "Medecin", "Souvent", 1);
        CalendrierActivity.database.evenementDao().insert(event);

=======
        t_listeEvenment=(TextView)view.findViewById(R.id.t_listecalendrier);
>>>>>>> parent of dda5dd2... Ajout RecyclerView
        List<Evenement> EvenmentList= CalendrierActivity.database.evenementDao().loadAllevenement();
        chaine_daffichage = "";



        for  (Evenement evenement :EvenmentList){
            int id = evenement.getId();
            String libele= evenement.getLibele();
            Date jour= evenement.getJour();
            long heur_debut=evenement.getHeure_debut();
            Calendar dat= Calendar.getInstance() ;


            chaine_daffichage= chaine_daffichage +"\n\n"+"id :"+id+"\n Libele:"+libele+"\njour:"+jour;
        }
        t_listeEvenment.setText(chaine_daffichage);
        return view ;
    }

}
