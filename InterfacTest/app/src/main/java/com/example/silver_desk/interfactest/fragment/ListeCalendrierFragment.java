package com.example.silver_desk.interfactest.fragment;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.silver_desk.interfactest.Adapters.CalendrierListeAdapter;
import com.example.silver_desk.interfactest.CalendrierActivity;
import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Calendrier;
import com.example.silver_desk.interfactest.database.Evenement;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListeCalendrierFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton fab_add ;
    Button b_evenment ;
    //public static AppDatabase database ;
    TextView t_listecalendrier;
    String chaine_daffichage ;
    RecyclerView recyclerView;

    public ListeCalendrierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_liste_calendrier, container, false);
        fab_add=(FloatingActionButton) view.findViewById(R.id.fab_add);
        b_evenment=(Button) view.findViewById(R.id.b_evenment);
        t_listecalendrier=(TextView)view.findViewById(R.id.listecalendrier);



        fab_add.setOnClickListener(this);
        b_evenment.setOnClickListener(this);
        //declaration de la BDD
        CalendrierActivity.DATABASE= Room.databaseBuilder(view.getContext(),AppDatabase.class,"AppDatabase").allowMainThreadQueries().build();
        /*
        Date date=new Date(148645645);
        Time td=new Time(21849879);
        Time tf=new Time(21849879);
        Evenement event=new Evenement("je sais pas",date,td, tf, "Bejaia", "Medecin", "Souvent", 1);
        CalendrierActivity.database.evenementDao().insert(event);*/

        List<Calendrier> calendrierList=CalendrierActivity.DATABASE.calendrierDao().loadAllCalendrier();



       /* chaine_daffichage = "";
        for  (Calendrier calendrier :calendrierList){
            int id = calendrier.getId();
            String titre= calendrier.getTitre();
            String visibilite= calendrier.getVisibilite();
            String activite= calendrier.getActivite();
            String priorite= calendrier.getPriorite();
            String couleur= calendrier.getCouleur();
            chaine_daffichage= chaine_daffichage +"\n\n\n\n"+"id :"+id+"\n\n titre :"+titre;
        }
        t_listecalendrier.setText(chaine_daffichage);*/

        recyclerView=view.findViewById(R.id.recyclerView);
        CalendrierListeAdapter calendarList_adapter=new CalendrierListeAdapter(calendrierList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(calendarList_adapter);
        return  view ;
    }




// fab_add

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.fab_add) {

           android.support.v4.app.FragmentTransaction transaction= CalendrierActivity.fragmentManager.beginTransaction();
            AjoutCalendrierFragment fragment = new AjoutCalendrierFragment();
            transaction.replace(R.id.conteneur,fragment,null);
            transaction.addToBackStack(null).commit();

        }
        if(view.getId()==R.id.b_evenment) {

            android.support.v4.app.FragmentTransaction transaction= CalendrierActivity.fragmentManager.beginTransaction();
            ListeEvenmentFragment fragment = new ListeEvenmentFragment();
            transaction.replace(R.id.conteneur,fragment,null);
            transaction.addToBackStack(null).commit();

        }
    }
}
