package com.example.silver_desk.interfactest.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silver_desk.interfactest.AjoutCalendrierActivity;
import com.example.silver_desk.interfactest.HomeActivity;
import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.Calendrier;

import java.util.List;

import static com.example.silver_desk.interfactest.R.color.itemtous;

/**
 * Created by silver-desk on 26/04/2018.
 */

public class CalendrierAdapter extends RecyclerView.Adapter<CalendrierAdapter.ViewHolder> {
    private  List<Calendrier> calendrierList ;
    private  Context context ;

    public CalendrierAdapter(List<Calendrier> calendrierList, Context context) {
        this.calendrierList = calendrierList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext())
             .inflate(R.layout.item_liste,parent,false);
     return new ViewHolder( view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(position==0){
            holder.imb_setting.setVisibility(View.INVISIBLE);
            int i ;

        }
        final Calendrier calendrier = calendrierList.get(position);
        holder.tv_titre.setText(calendrier.getTitre());
        holder.tv_description.setText(calendrier.getDescription());
       final int id_clecked = calendrierList.get(position).getId();


        // levenment qui arrive lorsque on click sur un item du rycyclerview
        holder.myitem.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
               Toast.makeText(context,"youclicked"+calendrier.getId(),Toast.LENGTH_LONG).show();
               Log.d("dbg", "position :  "+ id_clecked);
                Intent intent = new Intent(context, HomeActivity.class);
                intent.putExtra("id_cal",id_clecked);
                context.startActivity(intent);
            }
        });
        // si on click sur les 3 points
        holder.imb_setting.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Log.d("dbg", "position :  "+ id_clecked);

                Toast.makeText(context,"youclicked"+calendrier.getId(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, AjoutCalendrierActivity.class);
                intent.putExtra("id_cal",id_clecked);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return calendrierList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_titre, tv_description ;
        LinearLayout myitem , item_cal ;
        ImageButton imb_setting ;
        CardView cardView ;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_titre=(TextView)itemView.findViewById(R.id.tv_titrecal);
            tv_description=(TextView)itemView.findViewById(R.id.tv_descriptioncal);
            myitem=(LinearLayout)itemView.findViewById(R.id.myitem);
            imb_setting=(ImageButton)itemView.findViewById(R.id.imb_setting_cal);
            cardView=(CardView)itemView.findViewById(R.id.mycard);
        }
    }

}
