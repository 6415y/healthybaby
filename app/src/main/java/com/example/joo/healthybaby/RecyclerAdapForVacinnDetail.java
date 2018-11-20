package com.example.joo.healthybaby;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
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

        /*
         *  databaseReference.child("InoculateResult").addChildEventListener(new ChildEventListener() {........
         *
         *  Firebase에서 예방접종 내용을 가져와서 지금 설정하고 있는 item의 백신 이름과 같으면 파란색으로 표시
         */
        databaseReference.child("InoculateResult").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                InoculateInfo inoInfo = dataSnapshot.getValue(InoculateInfo.class);
                if(inoInfo != null) {
                    if (vaccinInfoList.get(position).getVaccinName().equals(inoInfo.getVaccinName())) {
                        recyclerViewHolder.itemView.setBackgroundColor(Color.BLUE);
                        recyclerViewHolder.tvVaccinNameDetail.setTextColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*
         *  if(vaccinInfoList.size() - index <= position) { ......
         *
         *  VaccinDetailActivity에서 구한 index를 사용해 만약 이미 접종시기가 지난것은 검은색으로
         *  age랑 접종시기가 같거나 한달 뒤에 맞아야 한다면 빨간색으로 표현
         */
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
