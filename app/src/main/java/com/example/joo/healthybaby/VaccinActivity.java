package com.example.joo.healthybaby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;

/* 예방접종 관련 액티비티입니다
*
*
*
* */

public class VaccinActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    int age = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccin);

        mRecyclerView = (RecyclerView) findViewById(R.id.Vaccin_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        /*
         *   final VaccinInfo[] vaccinInfo
         *
         *   백신에 대한 데이터를 저장하는 class
         */

        final VaccinInfo[] vaccinInfo = new VaccinInfo[17];
        vaccinInfo[0] = new VaccinInfo("BCG(피내용)", "결핵", new int[] {0});
        vaccinInfo[1] = new VaccinInfo("HepB",  "B형간염", new int[] {0, 1, 6});
        vaccinInfo[2] = new VaccinInfo("DTaP", "디프테리아, 파상풍, 백일해", new int[] {2, 4, 6, 15, 36});
        vaccinInfo[3] = new VaccinInfo("Tdap", "디프테리아, 파상풍, 백일해", new int[] {144});
        vaccinInfo[4] = new VaccinInfo("IPV", "플리오", new int[] {2, 4, 6, 36});
        vaccinInfo[5] = new VaccinInfo("Hib","b형 헤모필루스인플루엔자",new int[] {2, 4, 6, 12});
        vaccinInfo[6] = new VaccinInfo("PCV","폐렴구균", new int[] {2, 4, 6, 12});
        vaccinInfo[7] = new VaccinInfo("MMR", "홍역, 유행성이하선염, 풍진", new int[] {12, 48});
        vaccinInfo[8] = new VaccinInfo("VAR", "수두", new int[] {12});
        vaccinInfo[9] = new VaccinInfo("HepA", "A형 간염", new int[] {12, 18});
        vaccinInfo[10] = new VaccinInfo("IJEV", "일본 뇌염", new int[] {12, 18, 24, 72, 144});
        vaccinInfo[11] = new VaccinInfo("LJEV", "일본 뇌염", new int[] {12, 24});
        vaccinInfo[12] = new VaccinInfo("HPV", "사람유두종바이러스 감염증", new int[] {144, 150});
        vaccinInfo[13] = new VaccinInfo("IIV 매년 접종", "인플루엔자",new int[] {6});
        vaccinInfo[14] = new VaccinInfo("BCG(경피용)","결핵", new int[] {0});
        vaccinInfo[15] = new VaccinInfo("RV1", "로타바이러스 감염증", new int[] {2,4});
        vaccinInfo[16] =new VaccinInfo("RV5", "로타바이러스 감염증", new int[] {2,4,6});


        /*
         *  final ArrayList<VaccinInfo> vaccinInfoArrayList
         *
         *  vaccinInfo를 담는 ArrayList
         */
        final ArrayList<VaccinInfo> vaccinInfoArrayList = new ArrayList<>();
        for(int i =0;i<vaccinInfo.length;i++)
            vaccinInfoArrayList.add(vaccinInfo[i]);

        /*
         *  RecyclerViewAdapter recyclerViewAdapter
         *
         *  RecylcerView가 어떻게 구성되는지 설정해주는 Adapter
         */
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(vaccinInfoArrayList);
        mRecyclerView.setAdapter(recyclerViewAdapter);

        /*
         *  ItemClickSupport.addTo(mRecyclerView, R.id.Vaccin_recyclerView).....
         *
         *  RecyclerView item마다 클릭 이벤트를 만들어 주는 클래스
         */
        ItemClickSupport.addTo(mRecyclerView, R.id.Vaccin_recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent= new Intent(getApplicationContext(),VaccinDetailActivity.class);
                intent.putExtra("VaccinInfo", vaccinInfo[position]);
                startActivity(intent);

            }
        });
    }
}
