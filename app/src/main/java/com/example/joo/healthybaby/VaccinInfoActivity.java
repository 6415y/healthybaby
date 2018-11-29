package com.example.joo.healthybaby;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VaccinInfoActivity extends AppCompatActivity {

    TextView vaccinName_tv;
    TextView vaccinInoculate_tv;
    TextView diseaseName_tv;
    TextView vaccinYesOrNo;
    Button vaccin_btn;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccin_info);

        /*
         *  Intent getInent = getIntent(); ......
         *
         *  VaccinDetailActivity에서 넘겨준 VaccinInfoDetail class를 가져옴
         */
        Intent getInent = getIntent();
        final VaccinInfoDetail vaccinInfoDetail = (VaccinInfoDetail) getInent.getSerializableExtra("VaccinInfoDetail");


        vaccinName_tv = (TextView) findViewById(R.id.vaccinName_tv_VaccinInfoActivity);
        vaccinInoculate_tv = (TextView) findViewById(R.id.vaccinInoculate_tv_VaccinInfoActivity);
        diseaseName_tv = (TextView) findViewById(R.id.diseaseName_tv_VaccinInfoActivity);
        vaccinYesOrNo = (TextView) findViewById(R.id.vaccinYesOrNo_tv_VaccinInfoActivity);
        vaccin_btn = (Button) findViewById(R.id.vaccin_btn_VaccinInfoActivity);

        vaccinName_tv.setText(vaccinInfoDetail.getVaccinName());
        vaccinInoculate_tv.setText(Integer.toString(vaccinInfoDetail.getInoculateDate()) + " 개월");
        diseaseName_tv.setText(vaccinInfoDetail.getVaccinInfo());
        vaccinYesOrNo.setText("접종하지 않음");

        /*
         *  databaseReference.child("InoculateResult").addChildEventListener(new ChildEventListener() { ......
         *
         *  Firebase에서 접종정보를 가져와 현재 선택된 백신이름과 같으면 vaccinYesOrNo의 내용을 접종일자로 바꿈
         */
        databaseReference.child("InoculateResult").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                InoculateInfo inoInfo = dataSnapshot.getValue(InoculateInfo.class);
                if (inoInfo != null && inoInfo.getVaccinName().equals(vaccinInfoDetail.getVaccinName()))
                    vaccinYesOrNo.setText(inoInfo.getInoculateDate());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                InoculateInfo inoInfo = dataSnapshot.getValue(InoculateInfo.class);
                if (inoInfo != null && inoInfo.getVaccinName().equals(vaccinInfoDetail.getVaccinName()))
                    vaccinYesOrNo.setText(inoInfo.getInoculateDate());
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
         *  vaccin_btn.setOnClickListener(new View.OnClickListener() { .....
         *
         *  DatePicker 다이얼로그에서 날짜를 선택하게 하고 그 날짜와 백신정보를 를 Firebasedp 업로드 한다.
         */
        vaccin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(VaccinInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, final int i, final int i1, final int i2) {
                        String dayStr;
                        if(i2 < 10)
                            dayStr = "0" + i2;
                        else
                            dayStr = Integer.toString(i2);

                        InoculateInfo info = new InoculateInfo(vaccinInfoDetail.getVaccinName(), i + "-" + (i1 + 1) + "-" + dayStr);
                        databaseReference.child("InoculateResult").child(info.getVaccinName()).setValue(info);
                    }
                }, 2018, 11, 4);
                dialog.show();

            }
        });

    }
}
