package com.example.joo.healthybaby;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerAdapForVacinnDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvVaccinNameDetail;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvVaccinNameDetail = itemView.findViewById(R.id.vaccinNameDetail_tv);
        }
    }

    private ArrayList<VaccinInfoDetail> vaccinInfoList;
    private int index;
    private int age;

    public RecyclerAdapForVacinnDetail(ArrayList<VaccinInfoDetail> vaccinInfoList, int index, int age) {
        this.vaccinInfoList = vaccinInfoList;
        this.index = index;
        this.age = age;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccin_detail_recycler_row, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;

        recyclerViewHolder.tvVaccinNameDetail.setText(vaccinInfoList.get(position).getVaccinName());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("InoculateResult").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                InoculateInfo inoInfo = dataSnapshot.getValue(InoculateInfo.class);
                if(inoInfo != null) {
                    if (vaccinInfoList.get(position).getVaccinName().equals(inoInfo.getVaccinName())) {
                        recyclerViewHolder.itemView.setBackgroundColor(Color.BLUE);
                        recyclerViewHolder.tvVaccinNameDetail.setTextColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(vaccinInfoList.size() - index <= position) {
            recyclerViewHolder.itemView.setBackgroundColor(Color.BLACK);
            recyclerViewHolder.tvVaccinNameDetail.setTextColor(Color.WHITE);
        }
        else if(vaccinInfoList.get(position).getInoculateDate() == age ||  vaccinInfoList.get(position).getInoculateDate() == age + 1){
            recyclerViewHolder.itemView.setBackgroundColor(Color.RED);
            recyclerViewHolder.tvVaccinNameDetail.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return vaccinInfoList.size();
    }
}
