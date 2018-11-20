package com.example.joo.healthybaby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class VaccinDetailActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    int age = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccin_detail);

        /*
         *  final Intent intent = getIntent();
         *  final VaccinInfo vaccinInfo = (VaccinInfo)intent.getSerializableExtra("VaccinInfo");
         *
         * VaccinActivity에서 넘겨준 VaccinInfo class를 가저옴
         */

        final Intent intent = getIntent();
        final VaccinInfo vaccinInfo = (VaccinInfo)intent.getSerializableExtra("VaccinInfo");

        /*
         *  VaccinInfoDetail[] vaccinInfoDetail = new VaccinInfoDetail[vaccinInfo.getInoculateDate().length];
         *
         *  VaccinInfoDetail를 백신 맞는 횟수만큼 생성
         */
        VaccinInfoDetail[] vaccinInfoDetail = new VaccinInfoDetail[vaccinInfo.getInoculateDate().length];

        for(int i = 0; i < vaccinInfo.getInoculateDate().length; i++)
             vaccinInfoDetail[i] = new VaccinInfoDetail((vaccinInfo.getVaccinName() + " " + i + "차"),vaccinInfo.getVaccinInfo(),vaccinInfo.getInoculateDate()[i]);

        mRecyclerView = (RecyclerView)findViewById(R.id.VaccinDetail_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<VaccinInfoDetail> vaccinInfoList = new ArrayList<VaccinInfoDetail>();

        int index;

        /*
         *  for(index =0; index < vaccinInfoDetail.length; index++)
         *      if(age <= vaccinInfoDetail[index].getInoculateDate())
         *          break;
         *
         *  이미 맞을 시기가 지난것과 아닌것을 찾기위한 for문
         */
        for(index =0; index < vaccinInfoDetail.length; index++)
            if(age <= vaccinInfoDetail[index].getInoculateDate())
                break;

        for(int i = 0; i < vaccinInfoDetail.length; i++)
            vaccinInfoList.add(vaccinInfoDetail[i]);

        RecyclerAdapForVacinnDetail adap = new RecyclerAdapForVacinnDetail(vaccinInfoList, index, age);
        mRecyclerView.setAdapter(adap);

        /*
         *  ItemClickSupport.addTo(mRecyclerView, R.id.VaccinDetail_recyclerView).....
         *
         *  RecyclerView item마다 클릭 이벤트를 만들어 주는 클래스
         */
        ItemClickSupport.addTo(mRecyclerView, R.id.VaccinDetail_recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent1 = new Intent(getApplicationContext(),VaccinInfoActivity.class);
                intent1.putExtra("VaccinInfoDetail",vaccinInfoList.get(position));

                startActivity(intent1);

            }
        });
    }
}
