package com.example.silver_desk.interfactest.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.Calendrier;
import com.example.silver_desk.interfactest.fragment.ListeCalendrierFragment;

import java.util.List;

public class CalendrierListeAdapter  extends RecyclerView.Adapter<CalendrierListeAdapter.MyHolder> {



    List<Calendrier> calendrierList;

    public CalendrierListeAdapter(List<Calendrier> calendrierList) {
        this.calendrierList = calendrierList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_calendrier,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        Calendrier c=calendrierList.get(position);

        holder.titre.setText(c.getTitre());
        holder.descri.setText(c.getDescription());



    }

    @Override
    public int getItemCount() {
        return calendrierList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titre,descri;
        LinearLayout item ;

        public MyHolder(View itemView) {
            super(itemView);

            titre=(TextView)itemView.findViewById(R.id.calTitre);
            descri=(TextView)itemView.findViewById(R.id.calDescri);
            item=(LinearLayout)itemView.findViewById(R.id.myitem);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
