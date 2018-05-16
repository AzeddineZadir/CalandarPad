package com.example.silver_desk.interfactest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.silver_desk.interfactest.database.Alerte;
import com.example.silver_desk.interfactest.database.Calendrier;
import com.example.silver_desk.interfactest.database.Evenement;
import com.example.silver_desk.interfactest.fragment.ListeCalendrierFragment;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;

import static com.example.silver_desk.interfactest.HomeActivity.DATABASE;

public class AjoutCalendrierActivity extends AppCompatActivity implements View.OnClickListener {
    // les compoansant manipuler dans ce fragmant
    Spinner spinner_priorite ;

    EditText e_titre,e_description ;
    CheckBox c_activite,c_visibilite ;
    Button b_couleur ;
    FloatingActionButton fab_add_cal ;
    ArrayAdapter<CharSequence> arrayAdapter ;
    Evenement event;
    Alerte alerte;
    Time td,tf;
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_calendrier);
        spinner_priorite = (Spinner)findViewById(R.id.s_prio);
        e_titre=(EditText)findViewById(R.id.e_titre);
        e_description=(EditText)findViewById(R.id.et_descritpion);

        b_couleur=(Button) findViewById(R.id.b_couleur);
        c_activite=(CheckBox)findViewById(R.id.c_activite);
        c_visibilite=(CheckBox)findViewById(R.id.c_visibilite);
        fab_add_cal=(FloatingActionButton)findViewById(R.id.fab_add_cal) ;
        fab_add_cal.setOnClickListener(this);
        b_couleur.setOnClickListener(this);
        // une couleure pardefaut
        //   b_couleur.setBackgroundColor();

        // configuration du spinner
        arrayAdapter=ArrayAdapter.createFromResource(this,R.array.priorite,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_priorite.setAdapter(arrayAdapter);


        viewSetInfo();
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
        if (view.getId()==R.id.fab_add_cal){
         Boolean modification ;
            if(verifyIncomingIntent()) {  modification= true ;} else { modification=false ;}

            if (modification==true){
                // modification
              updatCalendrier(getincomingInten_idCalendrier());
              backCalendrierActivity();
            }else {
                // Ajout
                Calendrier calendrier= new Calendrier() ;
                inserCalendrier(calendrier);
                backCalendrierActivity();

            }
          /*  if (e_titre.getText().toString().equals("")){
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
                builder.create().show();*/



            Toast.makeText(view.getContext()," ajout",Toast.LENGTH_LONG).show();


            Toast.makeText(this,"calendrier",Toast.LENGTH_LONG).show();
            Intent intent_calendrier = new Intent(this,CalendrierActivity.class);
            startActivity(intent_calendrier);





        }
        if (view.getId()==R.id.b_couleur){
            ColorPicker colorPicker=new ColorPicker(this);
            colorPicker.setRoundColorButton(true);
            colorPicker.show();
            colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                @Override
                public void onChooseColor(int position, int color) {
                    b_couleur.setBackgroundColor(color);
                }

                @Override
                public void onCancel() {

                }
            });
        }


    }
    // verifier si on a ressu un intent
    private boolean verifyIncomingIntent(){
        if (getincomingInten_idCalendrier()!=0)
            return  true;
        else
            return false;
    }


    // recuperer  id du calendrier selectioner apartire de l intent
    private int getincomingInten_idCalendrier() {
        if (getIntent().hasExtra("id_cal")) {
            int id_cal = getIntent().getIntExtra("id_cal", 1);

            return id_cal;
        }
        return 0;
    }

    //initialiser la vue en cas de modification
    private void viewSetInfo(){
        if (verifyIncomingIntent()){
           Calendrier calendrier= DATABASE.calendrierDao().selecCalendrierById(getincomingInten_idCalendrier());
         // init titre
           e_titre.setText(calendrier.getTitre().toString());
         // init visibilité
            c_visibilite.setChecked(calendrier.isVisibilite());
         // init activité
            c_activite.setChecked(calendrier.isActivite());
         // init priorite
            String cal_priorite= calendrier.getPriorite();
            String[] stringArray=getResources().getStringArray(R.array.priorite);
            List<String> stringList = new ArrayList<String>();
            stringList= stringArrayToList(stringArray);
            int pos= getPositionOfitemByValu(cal_priorite,stringList);
            spinner_priorite.setSelection(pos);
         // init couleur
            b_couleur.setBackgroundColor(calendrier.getCouleur());





        }

    }

    private List<String> stringArrayToList(String[] stringArray) {
        List<String> stringList=new ArrayList<String>() ;
        for (int i=0 ;i < stringArray.length ;i++){
            stringList.add(i,stringArray[i]);
        }


        return stringList ;
    }

    private int getPositionOfitemByValu(String valu,List<String> stringList){
        int position = 0 ;
        for (int i=0 ;i < stringList.size();i++){
            // si un element de la liste correspond a la valeure
            // return his position
            Log.d("dbg if teste ", "condition :  "+stringList.get(i)+ "="+valu);
            if (stringList.get(i).equals(valu)){
                position=i;

            }

        }
        return position;


    }

    // inserer un calendrier
    private void inserCalendrier(Calendrier calendrier){
        // recuperer la couleure
        ColorDrawable b_couleurBackground=(ColorDrawable) b_couleur.getBackground();
        // recuperation des information du calendrier
        calendrier.setTitre(e_titre.getText().toString());
        calendrier.setVisibilite(c_visibilite.isChecked());
        calendrier.setActivite(c_activite.isChecked());
        calendrier.setPriorite(spinner_priorite.getSelectedItem().toString());
        calendrier.setDescription(e_description.getText().toString());
        calendrier.setCouleur(b_couleurBackground.getColor());
        DATABASE.calendrierDao().insert(calendrier);
        Toast.makeText(this, "ajout avec succse", Toast.LENGTH_SHORT).show();
    }
    // modifier un calendrier
    private void updatCalendrier (int id_cal){

        ColorDrawable b_couleurBackground=(ColorDrawable) b_couleur.getBackground();

      String titer =e_titre.getText().toString();
      Boolean activite=c_activite.isChecked();
      Boolean visibilite=c_visibilite.isChecked();
      String priorite = spinner_priorite.getSelectedItem().toString();
      String description =e_description.getText().toString();
      int couleur=b_couleurBackground.getColor();
        // creation de lobjet

        Calendrier calendrier= new Calendrier(id_cal,titer,visibilite,activite,couleur,priorite,description) ;
       DATABASE.calendrierDao().upDateCalendrier(calendrier);
        Toast.makeText(this, "modification avec succse", Toast.LENGTH_SHORT).show();


    }

    // retourner  calendrier  Activity
    private void backCalendrierActivity(){
        Intent intent = new Intent(this, CalendrierActivity.class);

        startActivity(intent);
    }

}
