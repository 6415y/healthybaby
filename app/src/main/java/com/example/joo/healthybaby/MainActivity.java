package com.example.joo.healthybaby;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button growthbtn;
    Button chgprobtn;
    //  Button allergybtn;
    Button dietbtn;
    Button vaccinbtn;
    Button totalreportbtn;
    ImageView babyface;
    TextView babyprofile;
    final int REQ_CODE_SELECT_IMAGE = 100;
    String babysex;
    int biryear, birmonth, birday;

    @Override
    protected void onStart() {//첫 실행시 프로필 출력
        super.onStart();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference profileput = database.getReference();
        profileput.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile getprofile;
                for (DataSnapshot message : dataSnapshot.getChildren()) {
                    if (message.getKey().equals("profile")) {
                        getprofile = message.getValue(profile.class);
                        if (getprofile != null) {
                            babyprofile = (TextView) findViewById(R.id.babyprofile);//프로필 텍스트뷰
                            babyprofile.setText(getprofile.getName() + "\n" + agecheck(getprofile.getYear(), getprofile.getMonth(), getprofile.getDay()));
                            babysex=getprofile.getSex();
                            biryear=getprofile.getYear();
                            birmonth=getprofile.getMonth();
                            birday=getprofile.getDay();
                        }
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       /* profileimage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                File path=dataSnapshot.getValue(File.class);
                if(path!=null){
                    Bitmap mbi=BitmapFactory.decodeFile(path.getAbsolutePath());
                    babyface.setImageBitmap(mbi);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //위젯들 연결
        growthbtn = (Button) findViewById(R.id.growthbtn);//발육기록 버튼
        chgprobtn = (Button) findViewById(R.id.chgprobtn);//프로필 변경버튼
        dietbtn = (Button) findViewById(R.id.dietbtn);//식단 기록 버튼
        //  allergybtn = (Button) findViewById(R.id.allergybtn);//알러지 기록 버튼
        vaccinbtn = (Button) findViewById(R.id.vaccinbtn);//예방 접종 버튼
        totalreportbtn = (Button) findViewById(R.id.totalreportbtn);//종합 레포트 버튼

        //메인액티비티 위젯들
        //아이 얼굴이 저장된게 나오게...
        babyface = (ImageView) findViewById(R.id.mainbaby);//프로필 이미지뷰
        babyprofile = (TextView) findViewById(R.id.babyprofile);//프로필 텍스트뷰


        //위젯들 동작
        babyface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });
        //프로필 변경버튼 눌렀을 시 동작
        chgprobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder name = new AlertDialog.Builder(MainActivity.this);
                name.setTitle("프로필 입력");
                name.setMessage("아이의 이름을 입력해주세요.");
                final EditText et = new EditText(MainActivity.this);
                name.setView(et);
                name.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //이름입력 edittext 긁어오기 et.getText().toString();
                        dialogInterface.dismiss();
                        final AlertDialog.Builder sexcheck = new AlertDialog.Builder(MainActivity.this);
                        sexcheck.setTitle("아이의 성별을 입력해주세요.");
                        final String[] sex = {"남자", "여자"};
                        sexcheck.setSingleChoiceItems(sex, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                babysex = sex[which];//이거로 성별측정가능 sex[0]은 남자 1은 여자
                                //Toast.makeText(MainActivity.this, babysex, Toast.LENGTH_SHORT);
                            }
                        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //여기부터 생일입력다이얼로그
                                AlertDialog.Builder birth = new AlertDialog.Builder(MainActivity.this);
                                birth.setTitle("프로필 입력");
                                birth.setMessage("아이의 생일을 입력해주세요.");
                                birth.setCancelable(false);
                                birth.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        DatePickerDialog agedialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                                                biryear = year;
                                                birmonth = month;
                                                birday = date;
                                                babyprofile.setText(et.getText().toString() + "\n" + agecheck(year, month, date));
                                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                                DatabaseReference profile = db.getReference();
                                                profile putprofile = new profile(profileage(year, month, date), year, month, date, et.getText().toString(), babysex);//프로필 db에 저장
                                                profile.child("profile").setValue(putprofile);
                                            }
                                        }, 2018, 12, 4);
                                        agedialog.show();
                                        //달력표시
                                    }
                                });
                                birth.show();
                                //생일입력다이얼로그 끝
                            }
                        });
                        sexcheck.show();
                    }
                });
                name.show();


            }
        });
        //발육측정 버튼 눌렀을시 동작
        growthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GrowthActivity.class);
                intent.putExtra("babysex", babysex);
                intent.putExtra("year", biryear);
                intent.putExtra("month", birmonth);
                intent.putExtra("day", birday);
                Toast.makeText(MainActivity.this, babysex + biryear, Toast.LENGTH_SHORT);
                startActivity(intent);
            }
        });
        //식단 기록버튼 눌렀을 시 동작
        dietbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllergyActivity.class);
                startActivity(intent);
            }
        });

        //예방 접종버튼 눌렀을 시 동작
        vaccinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VaccinActivity.class);
                startActivity(intent);
            }
        });
/*
        //알러지 기록버튼 눌렀을 시 동작
        allergybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllergyActivity.class);
                startActivity(intent);
            }
        });
*/
        //종합 기록버튼 눌렀을 시 동작
        totalreportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TotalReportActivity.class);
                startActivity(intent);
            }
        });

    }

    public String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //결과코드 Toast.makeText(getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == MainActivity.RESULT_OK) {
                try {
                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    //배치해놓은 ImageView에 set
                    babyface.setImageBitmap(image_bitmap);

                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userpic = database.getReference();
                    userpic.child("babyimg").setValue(getPath(data.getData()));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String agecheck(int year, int month, int day) {//생일 입력받아서 txt박스에 넣을 텍스트 반환해줌
        try {
            String start = year + "-" + month + "-" + day;
            Calendar current = Calendar.getInstance();
            String end = current.get(Calendar.YEAR) + "-" + current.get(Calendar.MONTH) + "-" + current.get(Calendar.DAY_OF_MONTH);
            int currentYear = current.get(Calendar.YEAR);
            int currentMonth = current.get(Calendar.MONTH);
            int currentDay = current.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff = endDate.getTime() - beginDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);//생후 일수 나옴

            int ageyear = currentYear - year;
            int agemonth;
            if (currentMonth >= month) {
                agemonth = currentMonth - month;//생일이 지났으면 그냥뺀다
            } else {
                agemonth = currentMonth - month + 12;//생일이 지나지 않았다면 +12
            }

            int ageday;
            if (currentDay >= day) {
                ageday = currentDay - day;
            } else {
                ageday = currentDay - day + 30;//생일 일이 지나지않았다면 +30해준다 현재 2000년 7월 30일이고 생일이 1998 8월 10일이라면 1년 11개월 20일 살은 것으로 친다.
            }

            if (ageyear < 1 && agemonth < 1) {
                return "태어난지 " + diffDays + "일 째\n" + "생후" + ageday + "일";
            } else if (ageyear < 1) {
                return "태어난지 " + diffDays + "일 째\n" + "생후" + agemonth + "개월";
            } else {
                return "태어난지 " + diffDays + "일 째\n" + (ageyear + 1) + "살(만" + ageyear + "살)";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

    public int profileage(int year, int month, int day) {//생후만 계산
        try {
            String start = year + "-" + month + "-" + day;
            Calendar current = Calendar.getInstance();
            String end = current.get(Calendar.YEAR) + "-" + current.get(Calendar.MONTH) + "-" + current.get(Calendar.DAY_OF_MONTH);
            int currentYear = current.get(Calendar.YEAR);
            int currentMonth = current.get(Calendar.MONTH);
            int currentDay = current.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff = endDate.getTime() - beginDate.getTime();
            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));//생후 일수 나옴
            return diffDays;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }
}

