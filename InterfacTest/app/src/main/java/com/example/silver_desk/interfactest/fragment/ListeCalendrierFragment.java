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

import com.example.silver_desk.interfactest.Adapters.CalendrierAdapter;
import com.example.silver_desk.interfactest.CalendrierActivity;
import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Calendrier;

import java.util.List;

import static com.example.silver_desk.interfactest.HomeActivity.DATABASE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListeCalendrierFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton fab_add ;
    Button b_evenment ;

    private TextView t_listecalendrier;
    private String chaine_daffichage ;
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter ;

    public ListeCalendrierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_liste_calendrier, container, false);
        fab_add=(FloatingActionButton) view.findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);
        List<Calendrier> calendrierList=DATABASE.calendrierDao().loadAllCalendrier();



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

        // initialisation du rycycler view
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerviewcal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new CalendrierAdapter(calendrierList,view.getContext());
        recyclerView.setAdapter(adapter);
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

    }
}
