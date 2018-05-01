package com.example.silver_desk.interfactest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.Calendrier;
import com.example.silver_desk.interfactest.database.Evenement;

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
        holder.tv_libel.setText(evenement.getLibele());
        holder.tv_description.setText(evenement.getDescription());
    }

    @Override
    public int getItemCount() {

            return evenementList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_libel, tv_description ;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_libel=(TextView)itemView.findViewById(R.id.tv_libele_evenment);
            tv_description=(TextView)itemView.findViewById(R.id.tv_description_evenment);

        }
    }
}
