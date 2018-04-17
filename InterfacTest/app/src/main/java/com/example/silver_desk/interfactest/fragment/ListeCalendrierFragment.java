package com.example.silver_desk.interfactest.fragment;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silver_desk.interfactest.CalendrierActivity;
import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Calendrier;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListeCalendrierFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton fab_add ;
    public static AppDatabase database ;
    TextView t_listecalendrier;
    String chaine_daffichage ;

    public ListeCalendrierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_liste_calendrier, container, false);
        fab_add=(FloatingActionButton) view.findViewById(R.id.fab_add);
        t_listecalendrier=(TextView)view.findViewById(R.id.t_listecalendrier);
        fab_add.setOnClickListener(this);
        database= Room.databaseBuilder(view.getContext(),AppDatabase.class,"AppDatabase").allowMainThreadQueries().build();
        List<Calendrier> calendrierList= database.calendrierDao().loadAllCalendrier();
        chaine_daffichage = "";
        for  (Calendrier calendrier :calendrierList){
            int id = calendrier.getId();
            String titre= calendrier.getTitre();
            String visibilite= calendrier.getVisibilite();
            String activite= calendrier.getActivite();
            String priorite= calendrier.getPriorite();
            String couleur= calendrier.getCouleur();
            chaine_daffichage= chaine_daffichage +"\n\n\n\n"+"id :"+id+"\n\n titre :"+titre;
        }
        t_listecalendrier.setText(chaine_daffichage);
        return  view ;
    }




// fab_add

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.fab_add) {

           android.support.v4.app.FragmentTransaction transaction= CalendrierActivity.fragmentManager.beginTransaction();
            AjoutCalendrierFragment fragment = new AjoutCalendrierFragment();
            transaction.replace(R.id.conteneur_defragments,fragment,null);
            transaction.commit();
            fab_add.setVisibility(View.INVISIBLE);
        }
    }
}
