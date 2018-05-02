package com.example.silver_desk.interfactest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silver_desk.interfactest.AjoutEvenmentActivity;
import com.example.silver_desk.interfactest.CalendrierActivity;
import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.SelectedCalendrierActivity;
import com.example.silver_desk.interfactest.database.Calendrier;
import com.example.silver_desk.interfactest.database.Evenement;

import java.util.Calendar;
import java.util.List;

/**
 * Created by silver-desk on 26/04/2018.
 */

public class EvenmentAdapter extends RecyclerView.Adapter<EvenmentAdapter.ViewHolder> {
    private List<Evenement> evenementList ;
    private Context context ;

    public EvenmentAdapter(List<Evenement> evenementList, Context context) {
        this.evenementList = evenementList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evenment,parent,false);
        return new EvenmentAdapter.ViewHolder( view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final Evenement evenement = evenementList.get(position);
        Calendar c =Calendar.getInstance();
        c.setTimeInMillis(evenement.getJour());

        holder.tv_libel.setText(evenement.getLibele());
        holder.tv_description.setText(c.getTime().toString());
        //supprimer un evenment
        holder.imb_delete.setOnClickListener(new View.OnClickListener() {
            //recuperation des information pour la suppression
            public  int id_evenment = evenementList.get(position).getId();
            public  int id_cal = evenementList.get(position).getCalendrierId();
            @Override
            public void onClick(View view) {
                CalendrierActivity.DATABASE.evenementDao().deleteEvenementByidCalAndIdEvent(id_evenment,id_cal);
                Toast.makeText(context, "suppression avec succse", Toast.LENGTH_SHORT).show();

            }
        });
        //modifer un evenment
        holder.imb_set.setOnClickListener(new View.OnClickListener() {
            public int id_evenment =evenementList.get(position).getId();
            public int id_cal =evenementList.get(position).getCalendrierId();

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AjoutEvenmentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id_evenment",id_evenment);
                intent.putExtra("id_cal",id_cal);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

            return evenementList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_libel, tv_description ;
        ImageButton imb_delete,imb_set;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_libel=(TextView)itemView.findViewById(R.id.tv_libele_evenment);
            tv_description=(TextView)itemView.findViewById(R.id.tv_description_evenment);
            imb_delete=(ImageButton)itemView.findViewById(R.id.imb_delete);
            imb_set=(ImageButton)itemView.findViewById(R.id.imb_set);


        }
    }
}
