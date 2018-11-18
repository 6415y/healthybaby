package com.example.joo.healthybaby;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvVaccinName;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvVaccinName = itemView.findViewById(R.id.vaccinName_tv);
        }
    }

    private ArrayList<VaccinInfo> vaccinInfoArrayList;

    public RecyclerViewAdapter(ArrayList<VaccinInfo> vaccinInfoArrayList) {
        this.vaccinInfoArrayList = vaccinInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;

        recyclerViewHolder.tvVaccinName.setText(vaccinInfoArrayList.get(position).getVaccinName());
    }

    @Override
    public int getItemCount() {
        return vaccinInfoArrayList.size();
    }
}
