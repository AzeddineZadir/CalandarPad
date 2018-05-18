package com.example.silver_desk.interfactest.fragment;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silver_desk.interfactest.Adapters.CalendrierAdapter;
import com.example.silver_desk.interfactest.AjoutCalendrierActivity;
import com.example.silver_desk.interfactest.CalendrierActivity;
import com.example.silver_desk.interfactest.HomeActivity;
import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Calendrier;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.silver_desk.interfactest.HomeActivity.DATABASE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListeCalendrierFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton fab_add ,fab_back;
    Button b_evenment ;

    private TextView t_listecalendrier;
    private String chaine_daffichage ;
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter ;
    public static  List<Calendrier> maliste , calendrierList ;

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

        fab_back=(FloatingActionButton)view.findViewById(R.id.fab_back);
        fab_back.setOnClickListener(this);

        calendrierList =  new ArrayList<Calendrier>();
        maliste=  new ArrayList<Calendrier>();

        // creation du calendrier par defaut
        Calendrier calendrier= new Calendrier();
        calendrier.setTitre("tous ");
        maliste.add(calendrier);
        calendrierList=DATABASE.calendrierDao().loadAllCalendrier();
        maliste.addAll(1,calendrierList);



        // initialisation du rycycler view
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerviewcal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new CalendrierAdapter(maliste,view.getContext());
        recyclerView.setAdapter(adapter);
        return  view ;
    }




// fab_add

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.fab_add) {
          //ajouter un calendrier
            Intent intent = new Intent(getContext(), AjoutCalendrierActivity.class);
            getContext().startActivity(intent);


        }

        if(view.getId()==R.id.fab_back) {
            // retour a  home activity
          backHome();


        }

    }
    public void backHome(){
        Intent intent = new Intent(getContext(), HomeActivity.class);
        getContext().startActivity(intent);

    }
}
