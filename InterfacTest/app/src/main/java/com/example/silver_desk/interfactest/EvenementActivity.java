package com.example.silver_desk.interfactest;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EvenementActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager ;
    TextView t_listeEvenment;
    String chaine_daffichage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenement);
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction= EvenementActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //lajout du fragment de la liste des evenment
       /* fragment = new AjoutEvenmentActivity();
        transaction.add(R.id.conteneur_eve,fragment,null);
        transaction.commit();*/
        //declaration dun evenment




    }
}
