package com.example.silver_desk.interfactest;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Evenement;
import com.example.silver_desk.interfactest.fragment.ListeCalendrierFragment;

import org.w3c.dom.Text;

import static com.example.silver_desk.interfactest.HomeActivity.actionBarDrawerToggle;
import static com.example.silver_desk.interfactest.HomeActivity.drawerLayout;

public class CalendrierActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static FragmentManager fragmentManager ;
    public static android.support.v4.app.FragmentTransaction  transaction;
    Toolbar toolbar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);
        fragmentManager = getSupportFragmentManager();
        //action bar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.addcalendar);
        setSupportActionBar(toolbar);

        //le drawer menu
        drawerLayout=(DrawerLayout)findViewById(R.id.calendrierdrawer);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this ,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView =(NavigationView)findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);




       //l affichage de la liste des calendrier sur un frag
        if (findViewById(R.id.conteneur)!=null){
            if(savedInstanceState!=null){
                return;
            }
        transaction=fragmentManager.beginTransaction().setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ListeCalendrierFragment fragment = new ListeCalendrierFragment() ;
        transaction.add(R.id.conteneur,fragment,null);
        transaction.commit();
        }
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //affichage du dashboard
        if (id==R.id.db){
            Toast.makeText(this,getString(R.string.home),Toast.LENGTH_LONG).show();
            goHome();
        }
        //affichage du calendrier
        if (id==R.id.calendrier){
            Toast.makeText(this,getString(R.string.calendar),Toast.LENGTH_LONG).show();
           refresCalendrierActivity();
        }


        return false ;

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void refresCalendrierActivity (){
        onRestart();
    }

    public void goHome (){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


}

