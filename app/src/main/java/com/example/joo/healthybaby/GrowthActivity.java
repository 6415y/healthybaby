package com.example.joo.healthybaby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
* 발육 관련 액티비티입니다.-H
*
*
*
* */
public class GrowthActivity extends AppCompatActivity {
    EditText gro_height;//신장 몸무게 입력창
    EditText gro_weight;
    DatePicker gro_date;//날짜선택
    Button gro_savebtn;//입력버튼
    TextView checktext;//아이크기비교 텍스트
    TextView dataview;//데이터 존재유무 텍스트
    ImageView leftimage, rightimage;


    double[] heightmeanboy = {49.9,
            54.7,
            58.4,
            61.4,
            63.9,
            65.9,
            67.6,
            69.2,
            70.6,
            72,
            73.3,
            74.5,
            75.7,
            76.9,
            78,
            79.1,
            80.2,
            81.2,
            82.3,
            83.2,
            84.2,
            85.1,
            86,
            86.9,
            87.1,
            88,
            88.8,
            89.6,
            90.4,
            91.2,
            91.9,
            92.7,
            93.4,
            94.1,
            94.8,
            95.4,
            96.5,
            97,
            97.6,
            98.1,
            98.7,
            99.2,
            99.8,
            100.3,
            100.9,
            101.4,
            102,
            102.5,
            103.1,
            103.6,
            104.2,
            104.7,
            105.3,
            105.8,
            106.3,
            106.9,
            107.4,
            108,
            108.5,
            109.1,
            109.6,
            110.1,
            110.7,
            111.2,
            111.7,
            112.2,
            112.8,
            113.3,
            113.8,
            114.4,
            114.9,
            115.4,
            115.9,
            116.4,
            117,
            117.5,
            118,
            118.5,
            119,
            119.5,
            120,
            120.5,
            121,
            121.6,
            122.1,
            122.5,
            123,
            123.5,
            124,
            124.5,
            125,
            125.5,
            126,
            126.5,
            126.9,
            127.4,
            127.9,
            128.3,
            128.8,
            129.3,
            129.7,
            130.2,
            130.7,
            131.1,
            131.6,
            132.1,
            132.5,
            133,
            133.4,
            133.9,
            134.3,
            134.8,
            135.2,
            135.6,
            136.1,
            136.6,
            137,
            137.5,
            137.9,
            138.4,
            138.8,
            139.3,
            139.8,
            140.3,
            140.7,
            141.2,
            141.7,
            142.2,
            142.7,
            143.2,
            143.7,
            144.2,
            144.7,
            145.2,
            145.8,
            146.3,
            146.8,
            147.4,
            147.9,
            148.5,
            149.1,
            149.7,
            150.2,
            150.8,
            151.4,
            152,
            152.6,
            153.2,
            153.8,
            154.4,
            155,
            155.6,
            156.2,
            156.8,
            157.4,
            158.1,
            158.6,
            159.2,
            159.8,
            160.3,
            160.9,
            161.5,
            162,
            162.5,
            163,
            163.5,
            164,
            164.5,
            165,
            165.4,
            165.8,
            166.2,
            166.6,
            167,
            167.4,
            167.7,
            168,
            168.3,
            168.6,
            169,
            169.2,
            169.4,
            169.6,
            169.9,
            170.1,
            170.3,
            170.5,
            170.6,
            170.8,
            171,
            171.1,
            171.3,
            171.4,
            171.5,
            171.6,
            171.8,
            171.9,
            172,
            172.1,
            172.2,
            172.3,
            172.4,
            172.5,
            172.6,
            172.6,
            172.7,
            172.8,
            172.9,
            173,
            173,
            173.1,
            173.2,
            173.3,
            173.4,
            173.4,
            173.5,
            173.6,
            173.7,
            173.8,
            173.8,
            173.9,
            174,
            174.1,
            174.2,
            174.2,
            174.3,
            174.4,
            174.5,
    };//키 남아 입력할 배열
    double[] heightmeangirl = {49.1,
            53.7,
            57.1,
            59.8,
            62.1,
            64,
            65.7,
            67.3,
            68.7,
            70.1,
            71.5,
            72.8,
            74,
            75.2,
            76.4,
            77.5,
            78.6,
            79.7,
            80.7,
            81.7,
            82.7,
            83.7,
            84.6,
            85.5,
            85.7,
            86.6,
            87.4,
            88.3,
            89.1,
            89.9,
            90.7,
            91.4,
            92.2,
            92.9,
            93.6,
            94.4,
            95.4,
            95.9,
            96.5,
            97,
            97.6,
            98.1,
            98.6,
            99.2,
            99.7,
            100.3,
            100.8,
            101.4,
            101.9,
            102.4,
            103,
            103.5,
            104.1,
            104.6,
            105.1,
            105.7,
            106.2,
            106.8,
            107.3,
            107.8,
            108.4,
            108.9,
            109.4,
            110,
            110.5,
            111,
            111.6,
            112.1,
            112.6,
            113.2,
            113.7,
            114.2,
            114.7,
            115.2,
            115.8,
            116.3,
            116.8,
            117.3,
            117.8,
            118.3,
            118.8,
            119.3,
            119.8,
            120.3,
            120.8,
            121.3,
            121.8,
            122.3,
            122.8,
            123.3,
            123.8,
            124.2,
            124.7,
            125.2,
            125.7,
            126.2,
            126.7,
            127.2,
            127.6,
            128.1,
            128.6,
            129.1,
            129.6,
            130.1,
            130.6,
            131.1,
            131.6,
            132.1,
            132.6,
            133.2,
            133.7,
            134.2,
            134.7,
            135.3,
            135.8,
            136.4,
            136.9,
            137.5,
            138,
            138.6,
            139.1,
            139.7,
            140.2,
            140.8,
            141.4,
            141.9,
            142.5,
            143,
            143.6,
            144.1,
            144.7,
            145.2,
            145.8,
            146.3,
            146.8,
            147.3,
            147.9,
            148.4,
            148.9,
            149.4,
            149.8,
            150.3,
            150.8,
            151.3,
            151.7,
            152.1,
            152.5,
            152.9,
            153.3,
            153.7,
            154,
            154.3,
            154.7,
            155,
            155.3,
            155.7,
            155.9,
            156.2,
            156.4,
            156.7,
            156.9,
            157.2,
            157.3,
            157.5,
            157.7,
            157.8,
            158,
            158.2,
            158.3,
            158.4,
            158.6,
            158.7,
            158.8,
            158.9,
            159,
            159.1,
            159.2,
            159.3,
            159.4,
            159.4,
            159.5,
            159.5,
            159.6,
            159.7,
            159.7,
            159.8,
            159.8,
            159.9,
            159.9,
            159.9,
            160,
            160,
            160,
            160,
            160.1,
            160.1,
            160.1,
            160.1,
            160.1,
            160.2,
            160.2,
            160.2,
            160.2,
            160.2,
            160.2,
            160.3,
            160.3,
            160.4,
            160.4,
            160.4,
            160.5,
            160.5,
            160.5,
            160.5,
            160.6,
            160.6,
            160.6,
            160.7,
            160.7,
            160.8,
            160.8,
            160.8,
            160.9,
            160.9,
            160.9,
            161,
            161,
            161.1,
    };//키 여아

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth);
        dataview = (TextView) findViewById(R.id.dataview);
        checktext = (TextView) findViewById(R.id.checktext);
        gro_height = (EditText) findViewById(R.id.growthheight);
        gro_weight = (EditText) findViewById(R.id.growthweight);
        gro_date = (DatePicker) findViewById(R.id.growthdatespin);//오늘날짜선택스피너
        gro_savebtn = (Button) findViewById(R.id.growthsavebtn);
        leftimage = (ImageView) findViewById(R.id.leftimage);
        rightimage = (ImageView) findViewById(R.id.rightimage);
        Intent intent = getIntent();
        final int year1 = intent.getExtras().getInt("year");//아이 생일 년 월 일
        final int month1 = intent.getExtras().getInt("month");
        final int day1 = intent.getExtras().getInt("day");
        final String babysex = intent.getStringExtra("babysex");


        gro_savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gro_check;
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference uploadgrowthresult = database.getReference("growthresult");
                Integer age = agecheck(gro_date.getYear(), gro_date.getMonth(), gro_date.getDayOfMonth(), year1, month1, day1);//기록 날자로 생후 일수 계산
                heightage getheight = new heightage();
                weightage getweight = new weightage();
                Double height = Double.parseDouble(gro_height.getText().toString());
                Double weight = Double.parseDouble(gro_weight.getText().toString());//텍스트 받아와서 더블형으로 파싱

                getheight.height = height;
                getweight.weight = weight;
                getheight.age = age;
                getweight.age = age;
                uploadgrowthresult.child("heightresult").child(age.toString()).setValue(getheight);
                uploadgrowthresult.child("weightresult").child(age.toString()).setValue(getweight);

                if (babysex == "남자") {//남아의 신장
                    float mean = (float) heightmeanboy[(int) age / 30];//생후 일수로 또래아이 신장평균 구해옴
                    if (height / mean > 1) {
                        checktext.setText("아이가 또래들보다 키가" + (int) ((height / mean - 1) * 100) + "%만큼 큽니다.");
                    } else {
                        checktext.setText("아이가 또래들보다 키가" + (int) ((1 - height / mean) * 100) + "%만큼 작습니다.");
                    }
                    leftimage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    rightimage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) (height / mean) * 10));

                } else {//여아의 신장
                    float mean = (float) heightmeangirl[(int) age / 30];//생후 일수로 또래아이 신장평균 구해옴
                    if (height / mean > 1) {
                        checktext.setText("아이가 또래들보다 키가" + (int) ((height / mean - 1) * 100) + "%만큼 큽니다.");
                    } else {
                        checktext.setText("아이가 또래들보다 키가" + (int) ((1 - height / mean) * 100) + "%만큼 작습니다.");
                    }
                    leftimage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    rightimage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) (height / mean) * 10));

                }

            }
        });
        gro_date.init(gro_date.getYear(), gro_date.getMonth(), gro_date.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {//날짜가 변경되었을시 출력될 데이터들
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference getgro = db.getReference("growthresult");
                        final Integer age = agecheck(i, i1, i2, year1, month1, day1);//기록 날자로 생후 일수 계산
                        getgro.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                heightage heit;
                                weightage weit;

                                for (DataSnapshot message : dataSnapshot.child("heightresult").getChildren()) {
                                    heit = message.getValue(heightage.class);
                                    if (heit != null) {
                                        if (heit.getAge() == age) {
                                            for (DataSnapshot message2 : dataSnapshot.child("weightresult").getChildren()) {
                                                weit = message2.getValue(weightage.class);
                                                if (weit != null) {
                                                    if (heit.getAge() == age) {//생후 일자로 계산해서 그날의 데이터값 가져옴
                                                        dataview.setText(gro_date.getYear() + "년" + (gro_date.getMonth() + 1) + "월" + gro_date.getDayOfMonth() + "일" + " 신장:" + heit.getHeight() + "cm 몸무게:" + weit.getWeight() + "kg");//날짜 선택시 아래 텍스트로 해당날짜 데이터 출력
                                                        break;
                                                    } else {
                                                        dataview.setText("데이터가 없습니다.");
                                                    }
                                                }
                                            }
                                            break;
                                        } else {
                                            dataview.setText("데이터가 없습니다.");
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                });

    }

    public int agecheck(int year, int month, int day, int year1, int month1, int day1) {
        try {
            String start = year1 + "-" + month1 + "-" + day1;
            Calendar current = Calendar.getInstance();
            String end = year + "-" + month + "-" + day;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff = endDate.getTime() - beginDate.getTime();
            int diffDays = (int)(diff / (24 * 60 * 60 * 1000));


            return diffDays;


        } catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }
}


