package com.example.silver_desk.interfactest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.silver_desk.interfactest.Adapters.EvenmentAdapter;
import com.example.silver_desk.interfactest.database.Evenement;

import java.util.List;

import static com.example.silver_desk.interfactest.HomeActivity.DATABASE;

public class SelectedCalendrierActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView ;
    private  RecyclerView.Adapter adapter ;
    FloatingActionButton fab_add_event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_calendrier);
        //recuperation du titre du calendier selectioner et de son id
        //getincomingInten_idcal
        //la liste des evenment par raport au calendrier selectioner
        List<Evenement> evenementList=DATABASE.evenementDao().loadEvenmentById(getincomingInten_idcal());
        //recycler view
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview_evenment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter=new EvenmentAdapter( evenementList,getApplicationContext());
        recyclerView.setAdapter(adapter);
        // fab_add_event
        fab_add_event=(FloatingActionButton)findViewById(R.id.fab_add_event);
        fab_add_event.setOnClickListener(this);



    }

    

   private  int getincomingInten_idcal() {
        if (getIntent().hasExtra("id_cal") ) {
            int id_cal_selected = getIntent().getIntExtra("id_cal", 1);

            return id_cal_selected;
        }
        return 0;
    }
    private  String getincomingInten_titrecal() {
        if (getIntent().hasExtra("id_cal") && getIntent().hasExtra("titre_cal")) {
            int id_cal_selected = getIntent().getIntExtra("id_cal", 1);
            String titre_cal_selected = getIntent().getStringExtra("titre_cal");

            return titre_cal_selected;
        }
        return "";
    }

    public static void refreshSelectedCalendrier (AppCompatActivity appCompatActivity){
        appCompatActivity.recreate();
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.fab_add_event){
           Intent intent =new Intent (this,AjoutEvenmentActivity.class);
           intent.putExtra("id_cal",getincomingInten_idcal());
           startActivity(intent);
        }

    }
}
