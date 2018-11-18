package com.example.joo.healthybaby;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VaccinInfoActivity extends AppCompatActivity {

    TextView vaccinName_tv;
    TextView vaccinInoculate_tv;
    TextView diseaseName_tv;
    TextView vaccinYesOrNo;
    TextView notiYesOrNo_tv;
    Button noti_btn;
    Button vaccin_btn;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccin_info);

        Intent getInent = getIntent();
        final VaccinInfoDetail vaccinInfoDetail = (VaccinInfoDetail)getInent.getSerializableExtra("VaccinInfoDetail");


        vaccinName_tv = (TextView)findViewById(R.id.vaccinName_tv_VaccinInfoActivity);
        vaccinInoculate_tv = (TextView)findViewById(R.id.vaccinInoculate_tv_VaccinInfoActivity);
        diseaseName_tv = (TextView)findViewById(R.id.diseaseName_tv_VaccinInfoActivity);
        vaccinYesOrNo = (TextView)findViewById(R.id.vaccinYesOrNo_tv_VaccinInfoActivity);
        notiYesOrNo_tv = (TextView)findViewById(R.id.notiYesOrNo_tv_VaccinInfoActivity);
        noti_btn = (Button)findViewById(R.id.noti_btn_VaccinInfoActivity);
        vaccin_btn = (Button)findViewById(R.id.vaccin_btn_VaccinInfoActivity);

        vaccinName_tv.setText(vaccinInfoDetail.getVaccinName());
        vaccinInoculate_tv.setText(Integer.toString(vaccinInfoDetail.getInoculateDate()) + " 개월");
        diseaseName_tv.setText(vaccinInfoDetail.getVaccinInfo());
        notiYesOrNo_tv.setText("설정하지 않음");
        databaseReference.child("InoculateResult").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                InoculateInfo inoInfo = dataSnapshot.getValue(InoculateInfo.class);
                if(inoInfo != null) {
                    if (inoInfo.getVaccinName().equals(vaccinInfoDetail.getVaccinName()))
                        vaccinYesOrNo.setText(inoInfo.getInoculateDate());
                }
                else
                    vaccinYesOrNo.setText("접종하지 않음");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        vaccin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(VaccinInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        InoculateInfo info = new InoculateInfo(vaccinInfoDetail.getVaccinName(),i + "-" + i1 + "-" + i2);
                        databaseReference.child("InoculateResult").setValue(info);


                    }
                },2018,11,4);
                dialog.show();

            }
        });

        noti_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(VaccinInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        TimePickerDialog timeDialog = new TimePickerDialog(VaccinInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minite) {

                            }
                        },0,0,false);
                        timeDialog.show();
                    }
                },2018,11,4);
                dialog.show();

            }
        });

    }
}
