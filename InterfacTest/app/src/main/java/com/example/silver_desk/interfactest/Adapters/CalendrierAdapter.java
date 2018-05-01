package com.example.silver_desk.interfactest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.SelectedCalendrierActivity;
import com.example.silver_desk.interfactest.database.Calendrier;

import java.util.List;

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
        final Calendrier calendrier = calendrierList.get(position);
        holder.tv_id.setText(calendrier.getId()+"");
        holder.tv_titre.setText(calendrier.getTitre());
        holder.tv_description.setText(calendrier.getDescription());

        // levenment qui arrive lorsque on click sur un item du rycyclerview

        holder.myitem.setOnClickListener(new View.OnClickListener() {
            public  int id_clecked = calendrierList.get(position).getId();
            public  String titre_cal_clecked = calendrierList.get(position).getTitre();
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"youclicked"+calendrier.getId(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, SelectedCalendrierActivity.class);
                intent.putExtra("id_cal",id_clecked);
                intent.putExtra("titre_cal",titre_cal_clecked);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return calendrierList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_titre, tv_description ,tv_id;
        LinearLayout myitem ;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_titre=(TextView)itemView.findViewById(R.id.tv_titrecal);
            tv_description=(TextView)itemView.findViewById(R.id.tv_descriptioncal);
            tv_id=(TextView)itemView.findViewById(R.id.tv_idcal);
            myitem=(LinearLayout)itemView.findViewById(R.id.myitem);
        }
    }

}
