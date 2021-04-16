package com.example.covid19app.feature.informCovid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19app.R;
import com.example.covid19app.localStorege.country;

import java.util.ArrayList;
import java.util.List;

public class AdapterRoom extends RecyclerView.Adapter<AdapterRoom.MessageViewHolder> {
    List<country> countryList = new ArrayList<>();

    public AdapterRoom(List<country> countryList) {
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_estimated_layout, parent, false);


        return new AdapterRoom.MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.nameText.setText("name: "+countryList.get(position).getNameCountry());
        holder.nameHealth.setText("TotalConfirmed"+" "+String.valueOf(countryList.get(position).getTextHealth()));
        holder.numberDeath.setText("TotalDeaths"+" "+String.valueOf(countryList.get(position).getTextDeath()));

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nameText, nameHealth,numberDeath;



        public MessageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.nameCountry);
            nameHealth = (TextView) itemView.findViewById(R.id.nameHealth);
            numberDeath = itemView.findViewById(R.id.namberDeath);

        }
    }
}
