package com.example.silver_desk.interfactest;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Evenement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,
        WeekView.EventClickListener, MonthLoader.MonthChangeListener,
        WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener{
 private DrawerLayout drawerLayout ;
 private ActionBarDrawerToggle actionBarDrawerToggle;
 private  FloatingActionButton fab_nav,fab_home,fab_calendrier,fab_aujourdhui;
 private  Animation ani_open,ani_close,ani_rotateclockwise,ani_rotateanticlockwise;
 boolean is_clicked = false ;
 public static AppDatabase DATABASE;

 // wekke view variables
 private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
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
        //inisialisation de la BDD
        DATABASE= Room.databaseBuilder(this,AppDatabase.class,"AppDatabase").allowMainThreadQueries().build();



        //le boutton  fab
        fab_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ajouter un evenment
                openAjoutEvenment();
            }
        });


        // le week view
        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);
         //mWeekView.setWeekViewLoader();
        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // hamburger
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true ;
        }
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

       // return super.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //affichage du dashboard
        if (id==R.id.db){
            Toast.makeText(this,"this is the dashboard activity",Toast.LENGTH_LONG).show();
            refreshHome();
        }
        //affichage du calendrier
        if (id==R.id.calendrier){
            Toast.makeText(this,"this is the dashboard activity",Toast.LENGTH_LONG).show();
         openCalendrierActivity();
        }
        //affichage des alertes
        if (id==R.id.alerte){
            Toast.makeText(this,"this is the alert  activity",Toast.LENGTH_LONG).show();

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
    //open EvenmentActivity
    public void openEvenmentActivity(){
        Toast.makeText(HomeActivity.this,"Evenment",Toast.LENGTH_LONG).show();
        Intent intent_Evenment = new Intent(HomeActivity.this,EvenementActivity.class);
        startActivity(intent_Evenment);

    }
    // open ajout evenment
    public void openAjoutEvenment (){
        Intent intent = new Intent( this ,AjoutEvenmentActivity.class);
        startActivity(intent);
    }
// week view
    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        //List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        List<Evenement> evenementList = DATABASE.evenementDao().loadAllevenement();
         //recuperation des evenment apartire de la base de donneé
        //  events=myEventToWeekEvents(evenementList);
        List<WeekViewEvent> events =myEventToWeekEvents(evenementList,newYear,newMonth)  ;
        return events ;
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {

    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
      int id_ev= (int) event.getId();
        Intent intent = new Intent(this, AjoutEvenmentActivity.class);

        intent.putExtra("id_evenment",id_ev);
       // intent.putExtra("id_cal",id_cal);
        startActivity(intent);



    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }


    protected String getEventTitle(Evenement evenement) {
        Calendar h_d = Calendar.getInstance() ;
        Calendar h_f = Calendar.getInstance() ;
        h_d.setTimeInMillis(evenement.getHeure_debut());
        h_f.setTimeInMillis(evenement.getHeure_fin());
        String titre = evenement.getLibele().toString().toUpperCase();
        String Titel = " "+titre+"\n"+"de ;"+h_d.get(Calendar.HOUR_OF_DAY)+":"+h_d.get(Calendar.MINUTE)+"\n a "+h_f.get(Calendar.HOUR_OF_DAY)+":"+h_f.get(Calendar.MINUTE);
        return evenement.getLibele().toUpperCase() ;
                //String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }
     int comp= 0;
    private List<WeekViewEvent> myEventToWeekEvents (List<Evenement> evenementList,int newYear, int newMonth){
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
     comp++;
     if (comp==1) {
         for (int i = 0; i < evenementList.size(); i++) {
             WeekViewEvent event;
             Evenement evenement = new Evenement();
             // recuperation dun evenment de la liste des evenment
             evenement = evenementList.get(i);
             // les heure debut et fin de levenment
             Calendar mytimeDebut = Calendar.getInstance();
             Calendar mytimeFin = Calendar.getInstance();
             Calendar myDate = Calendar.getInstance();
             mytimeDebut.setTimeInMillis(evenement.getHeure_debut());
             mytimeFin.setTimeInMillis(evenement.getHeure_fin());
             myDate.setTimeInMillis(evenement.getJour());
             Calendar startTime = Calendar.getInstance();

             startTime.set(Calendar.HOUR_OF_DAY, mytimeDebut.get(Calendar.HOUR_OF_DAY));
             startTime.set(Calendar.MINUTE, mytimeDebut.get(Calendar.MINUTE));
             startTime.set(Calendar.MONTH, myDate.get(Calendar.MONTH));
             //  startTime.set(Calendar.MONTH,newMonth );
             startTime.set(Calendar.DAY_OF_MONTH, myDate.get(Calendar.DAY_OF_MONTH));
             startTime.set(Calendar.YEAR, myDate.get(Calendar.YEAR));
             //startTime.set(Calendar.YEAR,newYear);

             Calendar endTime = Calendar.getInstance();

             endTime.set(Calendar.HOUR_OF_DAY, mytimeFin.get(Calendar.HOUR_OF_DAY));
             endTime.set(Calendar.MINUTE, mytimeFin.get(Calendar.MINUTE));
             endTime.set(Calendar.MONTH, myDate.get(Calendar.MONTH));
             //endTime.set(Calendar.MONTH,newMonth );

             //endTime.set(Calendar.DAY_OF_MONTH,myDate.get(Calendar.DAY_OF_MONTH) );
             endTime.set(Calendar.YEAR, myDate.get(Calendar.YEAR));
             endTime.set(Calendar.YEAR, newYear);

             // initialisation de weekviewevent grace a cet evenment
             event = new WeekViewEvent(evenement.getId(), getEventTitle(evenement), startTime, endTime);
             // recuperation de la color
             int color = DATABASE.calendrierDao().getIdCalendrierColorById(evenement.getCalendrierId());
             event.setColor(color);
             events.add(event);
         }


     }
         return events;
     }



    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    public void refreshHome (){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
