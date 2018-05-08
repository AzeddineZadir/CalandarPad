package com.example.silver_desk.interfactest.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.silver_desk.interfactest.CalendrierActivity;
import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.Alerte;
import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Calendrier;
import com.example.silver_desk.interfactest.database.Evenement;
import com.example.silver_desk.interfactest.fragment.ListeCalendrierFragment;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import static com.example.silver_desk.interfactest.CalendrierActivity.fragmentManager;
import static com.example.silver_desk.interfactest.HomeActivity.DATABASE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AjoutCalendrierFragment extends Fragment implements View.OnClickListener{
// les compoansant manipuler dans ce fragmant
Spinner spinner ;

EditText e_titre,e_couleur ;
CheckBox c_activite,c_visibilite ;
Button b_ajouter ;
ArrayAdapter <CharSequence> arrayAdapter ;
Evenement event;
Alerte alerte;
Time td,tf;
Date date;
    public AjoutCalendrierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=   inflater.inflate(R.layout.fragment_ajout_calendrier, container, false);
     spinner = (Spinner)view.findViewById(R.id.s_prio);
     e_titre=(EditText) view.findViewById(R.id.e_titre);
     e_couleur=(EditText) view.findViewById(R.id.e_titre);
     c_activite=(CheckBox)view.findViewById(R.id.c_activite);
     c_visibilite=(CheckBox)view.findViewById(R.id.c_visibilite);
     b_ajouter=(Button)view.findViewById(R.id.b_ajouter);
     b_ajouter.setOnClickListener(this);
        // configuration du spinner
        arrayAdapter=ArrayAdapter.createFromResource(view.getContext(),R.array.priorite,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        return  view ;

    }

    //traitment des chek box pour  l'enregistremant dans  la base
    public int c_clicked (CheckBox checkBox){
        if (checkBox.isChecked())
            return 1 ;
        else
        return 0 ;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.b_ajouter){

                if (e_titre.getText().toString().equals("")){
                    AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                    builder.setCancelable(false);
                    builder.setTitle("ajouts impossible");
                    builder.setMessage(" vous n'avez pas saisie de  Titre ");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create().show();
                }else{
                   String PRIORITE = spinner.getSelectedItem().toString();

                    Calendrier  cal= new Calendrier() ;
                    cal.setTitre(e_titre.getText().toString());
                   // cal.setVisibilite(c_clicked(c_visibilite));
                    cal.setActivite(c_clicked(c_activite)+"");
                    cal.setPriorite(PRIORITE);
                   // cal.setCouleur(e_couleur.getText());

                    Toast.makeText(view.getContext()," ajout",Toast.LENGTH_LONG).show();
                   DATABASE.calendrierDao().insert(cal);
                  /*
                  */


                }

                Toast.makeText(view.getContext()," ajout",Toast.LENGTH_LONG).show();

            android.support.v4.app.FragmentTransaction transaction= CalendrierActivity.fragmentManager.beginTransaction();
            ListeCalendrierFragment fragment = new ListeCalendrierFragment();
            transaction.replace(R.id.conteneur,fragment,null);
            transaction.commit();





            }


    }
}
