package com.example.joo.healthybaby;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<VaccinInfo> vaccinInfoArrayList;
    /*
     *  private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
     *  private DatabaseReference databaseReference = firebaseDatabase.getReference();
     *
     *  FireBase를 사용하기 위한 변수 선언
     */
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public RecyclerViewAdapter(ArrayList<VaccinInfo> vaccinInfoArrayList) {
        this.vaccinInfoArrayList = vaccinInfoArrayList;
    }

    /*
     *  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
     *
     *  recycler_view_row.xml과 연결 후  RecyclerViewHolder(v) 호출
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new RecyclerViewHolder(v);
    }

    /*
     *  public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
     *
     *  RecyclerView item마다 설정 조정
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;

        /*
         *  recyclerViewHolder.tvVaccinName.setText(vaccinInfoArrayList.get(position).getVaccinName())
         *
         *  RecyclerView item 의 tvVaccinName을 백신의 이름으로 설정
         */
        recyclerViewHolder.tvVaccinName.setText(vaccinInfoArrayList.get(position).getVaccinName());

        for(int i = 0; i < 5; i++) {
            recyclerViewHolder.injection_iv[i].setImageResource(R.drawable.injection1);
            recyclerViewHolder.injection_iv[i].setVisibility(View.VISIBLE);
        }

        for(int i = 0; i < (recyclerViewHolder.injection_iv.length - vaccinInfoArrayList.get(position).getInoculateDate().length); i++)
            recyclerViewHolder.injection_iv[i].setVisibility(View.INVISIBLE);


        databaseReference.child("InoculateResult").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 4;

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    InoculateInfo info = snapshot.getValue(InoculateInfo.class);
                    if (info.getVaccinName().contains(vaccinInfoArrayList.get(position).getVaccinName())) {
                        recyclerViewHolder.injection_iv[count].setImageResource(R.drawable.injection);
                        count--;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    @Override
    public int getItemCount() {
        return vaccinInfoArrayList.size();
    }

    /*
     *  public static class RecyclerViewHolder extends RecyclerView.ViewHolder
     *
     *  RecyclerView와 연결된 recycler_view_row.xml의 변수들 생성 및 초기화
     */
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvVaccinName;

        ImageView[] injection_iv = new ImageView[5];

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvVaccinName = itemView.findViewById(R.id.vaccinName_tv);

            injection_iv[0] = itemView.findViewById(R.id.injectionLeft_iv_recyclerViewRow);
            injection_iv[1] = itemView.findViewById(R.id.injectionMiddle1_iv_recyclerViewRow);
            injection_iv[2] = itemView.findViewById(R.id.injectionMiddle2_iv_recyclerViewRow);
            injection_iv[3] = itemView.findViewById(R.id.injectionMiddle3_iv_recyclerViewRow);
            injection_iv[4] = itemView.findViewById(R.id.injectionRight_iv_recyclerViewRow);
        }
    }
}
