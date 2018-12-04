package com.example.james.healthybaby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.support.annotation.NonNull;

import android.util.Log;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/*
* 종합레포트 관련 액티비티입니다.-H
*
*
*
* */
public class TotalReportActivity extends AppCompatActivity {

    // 객체 선언
    FirebaseDatabase database;
    DatabaseReference reference, reference2, reference_M_H, reference_M_W, reference_W_H, reference_W_W;
    DatabaseReference reference_allergy;


    GraphView graphView, graphView2;
    LineGraphSeries series, series2, series3, series4, series5, series6;
    Integer Born_Date;
    String gender;     // 1:남자 , 2: 여자

    // 예방 접종
    private GridView m_oListView = null;
    //예방접종 배열
    ArrayList<String> name = new ArrayList<>();
    ArrayList<ItemData> oData = new ArrayList<>();


    // 알러지
    private GridView m_oListView2 = null;
    // 알러지 배열
    ArrayList<String> allergy = new ArrayList<>();
    ArrayList<ItemData> allergyData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_report);

        graphView = (GraphView) findViewById(R.id.graphView);
        graphView2 = (GraphView) findViewById(R.id.graphView2);

        // 그래프 스타일링
        series = new LineGraphSeries();
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        series2 = new LineGraphSeries();
        series2.setColor(Color.GREEN);
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);
        series2.setThickness(8);

        //-------------------------------------//
        series4 = new LineGraphSeries(); //표준남 몸무게
        series3 = new LineGraphSeries(); //표준남 키
        series5 = new LineGraphSeries(); //표준여 키
        series6 = new LineGraphSeries(); //표준여 몸무게

        // legend
        series.setTitle("우리 아이");
        series2.setTitle("우리 아이");
        series3.setTitle("표준");
        series4.setTitle("표준");
        series5.setTitle("표준");
        series6.setTitle("표준");
        graphView.getGridLabelRenderer().setHorizontalAxisTitleTextSize(50);
        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphView.getLegendRenderer().setBackgroundColor(0);
        graphView2.getLegendRenderer().setVisible(true);
        graphView2.getLegendRenderer().setBackgroundColor(0);
        graphView2.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        // 파이어 베이스 경로 설정

        database = FirebaseDatabase.getInstance();
        // 아기
        reference = database.getReference("growthresult").child("heightresult"); //키
        reference2 = database.getReference("growthresult").child("weightresult"); //몸무게
        // 표본 데이터
        reference_M_H = database.getReference("Man_H");
        reference_M_W = database.getReference("Man_W");
        reference_W_H = database.getReference("Woman_H");
        reference_W_W = database.getReference("Woman_W");

        // 알러지 경로설정
        reference_allergy = database.getReference("AllergyResult");

        // 그래프 출력
        Intent intent=getIntent();
        gender = intent.getExtras().getString("gender");  // 1:남자 , 2: 여자
        Log.d("2222222222", gender);


        graphView.addSeries(series);
        graphView2.addSeries(series2);
        if (gender.equals("남자")) // 남자의 경우
        {
            graphView.addSeries(series3);
            graphView2.addSeries(series4);
        }
        else if (gender.equals("여자")) // 여자의 경우
        {
            graphView.addSeries(series5);
            graphView2.addSeries(series6);
        }


        // 예방접종
        // 데이터 생성--------------------------------.

        Born_Date = intent.getExtras().getInt("Born_Date"); //생후일자
        Log.d("111111111", String.valueOf(Born_Date));

        if (Born_Date <= 30){
            name.add("BCG 1회");
            name.add("HepB 1차");
            addItem();

        } //1개월 이내
        else if (Born_Date >30 & Born_Date<=61) {
            name.add("HepB 2차");
            addItem();
        } // 1개월
        else if (Born_Date >61 & Born_Date<=122) {
            name.add("DTaP 1차");
            name.add("IPV 1차");
            name.add("Hib 1차");
            name.add("PCV 1차");
            addItem();
        } // 2개월
        else if (Born_Date >122 & Born_Date<=183) {
            name.add("DTaP 2차");
            name.add("IPV 2차");
            name.add("Hib 2차");
            name.add("PCV 2차");
            addItem();
        } // 4개월
        else if (Born_Date >183 & Born_Date<=365) {
            name.add("HepB 3차");
            name.add("DTaP 3차");
            name.add("IPV 3차");
            name.add("Hib 3차");
            name.add("PCV 3차");
            name.add("IIV");
            addItem();
        } // 6개월
        else if (Born_Date >365 & Born_Date<=456) {
            name.add("IPV 3차");
            name.add("Hib 4차");
            name.add("PCV 4차");
            name.add("MMR 1차");
            name.add("VAR 1회");
            name.add("HepA 1~2차");
            name.add("IJEV 1~2차");
            name.add("LJEV 1차");
            name.add("IIV");
            addItem();
        } // 12개월
        else if (Born_Date >456 & Born_Date<=548) {
            name.add("DTaP 4차");
            name.add("IPV 3차");
            name.add("Hib 4차");
            name.add("PCV 4차");
            name.add("MMR 1차");
            name.add("VAR 1회");
            name.add("HepA 1~2차");
            name.add("IJEV 1~2차");
            name.add("LJEV 1차");
            name.add("IIV");
            addItem();
        } // 15개월
        else if (Born_Date >548 & Born_Date<=578) {
            name.add("DTaP 4차");
            name.add("IPV 3차");
            name.add("HepA 1~2차");
            name.add("IJEV 1~2차");
            name.add("LJEV 1차");
            name.add("IIV");
            addItem();
        } // 18개월
        else if (Born_Date >578 & Born_Date<=730) {
            name.add("HepA 1~2차");
            name.add("IJEV 1~2차");
            name.add("LJEV 1차");
            name.add("IIV");
            addItem();
        } // 19~23개월
        else if (Born_Date >730 & Born_Date<=1095) {
            name.add("PPSV");
            name.add("IJEV 3차");
            name.add("LJEV 2차");
            name.add("IIV");
            addItem();
        } // 24~35개월
        else if (Born_Date >1095 & Born_Date<=1825) {
            name.add("DTaP 5차");
            name.add("IPV 4차");
            name.add("PPSV");
            name.add("MMR 2차");
            name.add("IIV");
            addItem();
        } // 만 4세
        else if (Born_Date >1825 & Born_Date<=2555) {
            name.add("DTaP 5차");
            name.add("IPV 4차");
            name.add("PPSV");
            name.add("MMR 2차");
            name.add("IJEV 4차");
            name.add("IIV");
            addItem();
        } // 만 6세
        else if (Born_Date >2555 & Born_Date<=4380) {
            name.add("Tdap 6차");
            name.add("PPSV");
            name.add("IIV");
            addItem();
        } // 만 11세
        else if (Born_Date >4380 & Born_Date<=4745) {
            name.add("Tdap 6차");
            name.add("PPSV");
            name.add("IJEV 5차");
            name.add("HPV 1~2차");
            name.add("IIV");
            addItem();
        } // 만 12세

        // ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = findViewById(R.id.gridView);
        ListAdapter oAdapter = new com.example.james.healthybaby.ListAdapter(oData);
        m_oListView.setAdapter(oAdapter);




    }
    @Override
    protected void onStart() {
        super.onStart();

        // 우리 아기 키
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint [] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;

                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    PointValue pointValue = myDataSnapshot.getValue(PointValue.class);
                    dp[index] = new DataPoint(pointValue.getxValue(), pointValue.getyValue());
                    index++;
                }

                series.resetData(dp);
                Log.d("111111111111111111111111111111111" , Arrays.toString(dp)); // 배열 들어간거 확인


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 우리 아기 몸무게
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint [] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;

                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    PointValue pointValue = myDataSnapshot.getValue(PointValue.class);
                    dp[index] = new DataPoint(pointValue.getxValue(), pointValue.getyValue());
                    index++;
                }

                series2.resetData(dp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 표준남자 키 출력
        reference_M_H.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint [] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;

                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    PointValue pointValue = myDataSnapshot.getValue(PointValue.class);
                    dp[index] = new DataPoint(pointValue.getxValue(), pointValue.getyValue());
                    index++;
                }


                series3.resetData(dp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // 표준남자 몸무게 출력
        reference_M_W.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint [] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;

                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    PointValue pointValue = myDataSnapshot.getValue(PointValue.class);
                    dp[index] = new DataPoint(pointValue.getxValue(), pointValue.getyValue());
                    index++;
                }


                series4.resetData(dp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // 표준여자 키 출력
        reference_W_H.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint [] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;

                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    PointValue pointValue = myDataSnapshot.getValue(PointValue.class);
                    dp[index] = new DataPoint(pointValue.getxValue(), pointValue.getyValue());
                    index++;
                }


                series5.resetData(dp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // 표준여자 몸무게 출력
        reference_W_W.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint [] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;

                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    PointValue pointValue = myDataSnapshot.getValue(PointValue.class);
                    dp[index] = new DataPoint(pointValue.getxValue(), pointValue.getyValue());
                    index++;
                }


                series6.resetData(dp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        // 알러지 음식 받기
        reference_allergy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    if(myDataSnapshot.getKey().equals("FoodResult")) {
                        FoodResult data = myDataSnapshot.getValue(FoodResult.class);
                        for(int i = 0; i < data.getFoodIngredientsStr().size(); i++)
                            allergy.add(data.getFoodIngredientsStr().get(i));
                        Log.d("111", Arrays.toString(new ArrayList[]{allergy}));
                    }

                }
                addItem2();
                // ListView, Adapter 생성 및 연결 (알러지) ------------------------
                m_oListView2 = findViewById(R.id.gridView_allergy);
                ListAdapter_allergy oAdapter2 = new ListAdapter_allergy(allergyData);
                m_oListView2.setAdapter(oAdapter2);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    // 예방접종 아이템 추가
    private void addItem() {
        for (int i=0; i< name.size(); i++)
        {
            ItemData oItem = new ItemData();
            oItem.strTitle = name.get(i);
            oData.add(oItem);
        }
    }
    // 알러지 아이템 추가
    private void addItem2() {
        for (int i=0; i< allergy.size(); i++)
        {
            ItemData oItem = new ItemData();
            oItem.strTitle = allergy.get(i);
            allergyData.add(oItem);
        }
    }


}
