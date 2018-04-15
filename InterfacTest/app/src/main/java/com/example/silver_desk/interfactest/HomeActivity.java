package com.example.silver_desk.interfactest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
 private DrawerLayout drawerLayout ;
 private ActionBarDrawerToggle actionBarDrawerToggle;
 private  FloatingActionButton fab_nav,fab_home,fab_calendrier,fab_aujourdhui;
 private  Animation ani_open,ani_close,ani_rotateclockwise,ani_rotateanticlockwise;
 boolean is_clicked = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //le drawer menu
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this ,drawerLayout,R.string.open,R.string.close);
       drawerLayout.addDrawerListener(actionBarDrawerToggle);
       actionBarDrawerToggle.syncState();
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       NavigationView navigationView =(NavigationView)findViewById(R.id.nav);
       navigationView.setNavigationItemSelectedListener(this);
       // le menus flotan
        fab_nav=(FloatingActionButton)findViewById(R.id.fab_nav);
        fab_home=(FloatingActionButton)findViewById(R.id.fab_home);
        fab_calendrier=(FloatingActionButton)findViewById(R.id.fab_calendrier);
        fab_aujourdhui=(FloatingActionButton)findViewById(R.id.fab_aujoudhui);
        ani_open= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        ani_close= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        ani_rotateclockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_roteatclockwise);
        ani_rotateanticlockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_roteatanticlockwis);
        //le boutton  fab
        fab_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_nav_bclik();
                // le button home
                fab_home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(HomeActivity.this,"home ",Toast.LENGTH_LONG).show();
                    }
                });
                // le button calendrier
                fab_calendrier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       openCalendrierActivity();
                    }
                });
                // le button aujourdhui
                fab_aujourdhui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(HomeActivity.this,"aujourd\'hui ",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
    // hamburger
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //affichage du dashboard
        if (id==R.id.db){
            Toast.makeText(this,"this is the dashboard activity",Toast.LENGTH_LONG).show();
        }
        //affichage du calendrier
        if (id==R.id.calendrier){
            Toast.makeText(this,"this is the dashboard activity",Toast.LENGTH_LONG).show();
         openCalendrierActivity();
        }
        return false ;

    }
    // la methode daffichage du  menu flotan
    public void fab_nav_bclik(){
        if (is_clicked){
            fab_nav.startAnimation(ani_close);
            fab_nav.startAnimation(ani_rotateanticlockwise);
            fab_home.startAnimation(ani_close);
            fab_calendrier.startAnimation(ani_close);
             fab_aujourdhui.startAnimation(ani_close);
            fab_home.setClickable(false);
            fab_calendrier.setClickable(false);
            fab_aujourdhui.setClickable(false);
            is_clicked= false;
        }else {
            fab_nav.startAnimation(ani_open);
            fab_nav.startAnimation(ani_rotateclockwise);
            fab_home.startAnimation(ani_open);
            fab_calendrier.startAnimation(ani_open);
            fab_aujourdhui.startAnimation(ani_open);
            fab_home.isClickable();
            fab_calendrier.isClickable();
            fab_aujourdhui.isClickable();
            is_clicked = true;
        }
    }

    //open calendrier activiti
    public void openCalendrierActivity(){
        Toast.makeText(HomeActivity.this,"calendrier",Toast.LENGTH_LONG).show();
        Intent intent_calendrier = new Intent(HomeActivity.this,CalendrierActivity.class);
        startActivity(intent_calendrier);

    }
}
