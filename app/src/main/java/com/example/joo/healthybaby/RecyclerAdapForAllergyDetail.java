package com.example.joo.healthybaby;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapForAllergyDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<AllergyResult> allergyResultsList;

    public RecyclerAdapForAllergyDetail(ArrayList<AllergyResult> allergyResultsArrayList) {
        this.allergyResultsList = allergyResultsArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.allergy_detail_recycler, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String str = "";
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;

        recyclerViewHolder.tvOccurrenceDate.setText(allergyResultsList.get(position).getOccurrenceDate());
        for(String temp : allergyResultsList.get(position).getFoodIngredients()){
            str = temp + str;
        }
        recyclerViewHolder.tvFoodIngredients.setText(str);
    }

    @Override
    public int getItemCount() {
        return allergyResultsList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvOccurrenceDate;
        TextView tvFoodIngredients;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvOccurrenceDate = (TextView)itemView.findViewById(R.id.occurrenceDate_tv_AllergyDetailRecycler);
            tvFoodIngredients = (TextView)itemView.findViewById(R.id.foodIngredients_tv_AllergyDetailRecycler);
        }
    }
}
