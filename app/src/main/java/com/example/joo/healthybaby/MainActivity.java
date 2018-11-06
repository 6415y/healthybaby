package com.example.joo.healthybaby;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button growthbtn;
    Button chgprobtn;
    Button allergybtn;
    Button dietbtn;
    Button vaccinbtn;
    Button totalreportbtn;
    ImageView babyface;
    TextView babyprofile;
    final int REQ_CODE_SELECT_IMAGE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //위젯들 연결
        growthbtn = (Button)findViewById(R.id.growthbtn);//발육기록 버튼
        chgprobtn = (Button)findViewById(R.id.chgprobtn);//프로필 변경버튼
        dietbtn =(Button)findViewById(R.id.dietbtn);//식단 기록 버튼
        allergybtn=(Button)findViewById(R.id.allergybtn);//알러지 기록 버튼
        vaccinbtn=(Button)findViewById(R.id.vaccinbtn);//예방 접종 버튼
        totalreportbtn=(Button)findViewById(R.id.totalreportbtn);//종합 레포트 버튼

        //메인액티비티 위젯들
        babyface = (ImageView)findViewById(R.id.mainbaby);//프로필 이미지뷰
        babyprofile = (TextView)findViewById(R.id.babyprofile);//프로필 텍스트뷰


        //위젯들 동작

        //프로필 변경버튼 눌렀을 시 동작
        chgprobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });
        //발육측정 버튼 눌렀을시 동작
        growthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GrowthActivity.class );
                startActivity(intent);
            }
        });
        //식단 기록버튼 눌렀을 시 동작
        dietbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DietRecordActivity.class );
                startActivity(intent);
            }
        });

        //예방 접종버튼 눌렀을 시 동작
        vaccinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,VaccinActivity.class );
                startActivity(intent);
            }
        });

        //알러지 기록버튼 눌렀을 시 동작
        allergybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AllergyActivity.class );
                startActivity(intent);
            }
        });

        //종합 기록버튼 눌렀을 시 동작
        totalreportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,TotalReportActivity.class );
                startActivity(intent);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //결과코드 Toast.makeText(getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode==MainActivity.RESULT_OK)
            {
                try {
                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    //배치해놓은 ImageView에 set
                    babyface.setImageBitmap(image_bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
