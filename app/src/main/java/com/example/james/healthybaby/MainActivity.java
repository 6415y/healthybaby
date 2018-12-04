package com.example.james.healthybaby;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //테이블 레이아웃 안 4개의 텍스트뷰
    TextView growthbtn;
    TextView allergybtn;
    TextView vaccinbtn;
    TextView totalreportbtn;

    TextView babyname,babyyear ,babydateage,babymonth;

    CircleImageView babyface;//애기 얼굴 이미지뷰
    TextView babyprofile;//애기 프로필 텍뷰
    ImageButton chgprobtn;//프로필수정버튼
    public final int REQ_CODE_SELECT_IMAGE = 100;
    String babysex="남자";//기본은 남자아이로 초기화
    int biryear, birmonth, birday;
    public Uri mImageUri;
    public StorageReference mStroageRef;
    public DatabaseReference mDatabaseRef;
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
                            babyname.setText(getprofile.getName());//아기이름 수정
                            babyyear.setText(yearcheck(getprofile.getYear(), getprofile.getMonth(), getprofile.getDay()));//년도 수정
                            babydateage.setText(datecheck(getprofile.getYear(), getprofile.getMonth(), getprofile.getDay()));
                            babymonth.setText(monthcheck(getprofile.getYear(), getprofile.getMonth(), getprofile.getDay()));
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
        mDatabaseRef=FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Uploadimage upload;
                for(DataSnapshot post:dataSnapshot.getChildren()){
                    upload=post.getValue(Uploadimage.class);
                    if(upload!=null){
                        Picasso.get().load(upload.getmImageUrl()).into(babyface);
                        //babyface.setImageURI(Uri.parse(upload.getmImageUrl()));
                        Log.d("1111111",upload.getmImageUrl());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //파베 stroage연결
        final int PICK_IMAGE_REQUEST=1;

        mStroageRef= FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        //위젯들 연결
        growthbtn = (TextView) findViewById(R.id.growthbtn);//발육기록 버튼
        chgprobtn = (ImageButton) findViewById(R.id.chgprobtn);//프로필 변경버튼
        //dietbtn = (Button) findViewById(R.id.dietbtn);//식단 기록 버튼
        allergybtn = (TextView) findViewById(R.id.allergybtn);//알러지 기록 버튼
        vaccinbtn = (TextView) findViewById(R.id.vaccinbtn);//예방 접종 버튼
        totalreportbtn = (TextView) findViewById(R.id.totalreportbtn);//종합 레포트 버튼

        //메인액티비티 위젯들
        //아이 얼굴이 저장된게 나오게...
        babyface = (CircleImageView) findViewById(R.id.mainbaby);//프로필 이미지뷰

        babyname=(TextView)findViewById(R.id.babyname);
        babyyear=(TextView)findViewById(R.id.babyyear);
        babydateage=(TextView)findViewById(R.id.babydateage);
        babymonth=(TextView)findViewById(R.id.babymonth);

        //위젯들 동작
        babyface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
            }
        });
        //프로필 변경버튼 눌렀을 시 동작
        chgprobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder name = new AlertDialog.Builder(MainActivity.this);
                name.setTitle("프로필 입력");
                name.setMessage("아이의 이름을 입력해주세요.");
                name.setCancelable(false);
                final EditText et = new EditText(MainActivity.this);
                name.setView(et);
                name.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        //이름입력 edittext 긁어오기 et.getText().toString();
                        dialogInterface.dismiss();
                        final AlertDialog.Builder sexcheck = new AlertDialog.Builder(MainActivity.this);
                        sexcheck.setTitle("아이의 성별을 입력해주세요.");
                        babysex="남자";
                        sexcheck.setCancelable(false);
                        final String[] sex = {"남자", "여자"};
                        sexcheck.setSingleChoiceItems(sex, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                babysex = sex[which];//이거로 성별측정가능 sex[0]은 남자 sex[1]은 여자
                            }
                        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int i) {
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
                                                babyname.setText(et.getText().toString());//아기이름 수정
                                                babyyear.setText(yearcheck(year,month,date));//년도 수정
                                                babydateage.setText(datecheck(year,month,date));
                                                babymonth.setText(monthcheck(year,month,date));
                                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                                DatabaseReference profile = db.getReference();
                                                profile putprofile = new profile(profileage(year, month, date), year, month, date, et.getText().toString(), babysex);//프로필 db에 저장
                                                profile.child("profile").setValue(putprofile);
                                            }
                                        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                                        Calendar maxDate=Calendar.getInstance();
                                        Calendar current=Calendar.getInstance();
                                        maxDate.set(current.get(Calendar.YEAR),current.get(Calendar.MONTH),current.get(Calendar.DAY_OF_MONTH));
                                        agedialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                                        agedialog.show();


                                        //달력표시
                                    }
                                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                birth.show();
                                //생일입력다이얼로그 끝
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogInterface.dismiss();
                            }
                        });
                        sexcheck.show();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

        //예방 접종버튼 눌렀을 시 동작
        vaccinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VaccinActivity.class);
                startActivity(intent);
            }
        });

        //알러지 기록버튼 눌렀을 시 동작
        allergybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllergyActivity.class);
                startActivity(intent);
            }
        });

        //종합 기록버튼 눌렀을 시 동작
        totalreportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TotalReportActivity.class);
                intent.putExtra("gender", babysex);
                intent.putExtra("Born_Date",datecheck(biryear,birmonth,birday));
                startActivity(intent);
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==RESULT_OK&&data !=null&&data.getData()!=null){
            mImageUri=data.getData();

            uploadFile();
        }
    }
    public String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public void uploadFile(){
        if (mImageUri !=null){
            StorageReference fileReference=mStroageRef.child(getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {//성공시
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                }
                            },5000);
                            Toast.makeText(MainActivity.this,"업로드 완료",Toast.LENGTH_SHORT).show();
                            Uploadimage upload= new Uploadimage(taskSnapshot.getDownloadUrl().toString());
                            mDatabaseRef.child("upload").setValue(upload);
                            babyface.setImageURI(mImageUri);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {//실패시
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {//프로그래스바 표현
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double profress= (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            //mProgressBar.
                        }
                    });
        } else{
            Toast.makeText(this,"파일선택안됨",Toast.LENGTH_SHORT).show();
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

            if (diffDays<31) {//태어난지 한달도 안됐을때
                return "태어난지 " + diffDays + "일 째\n" + "생후" + diffDays + "일";
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
    public String yearcheck(int year, int month, int day) {//생일 입력받아서 txt박스에 넣을 텍스트 반환해줌
        try {
            String start = year + "-" + month + "-" + day;
            Calendar current = Calendar.getInstance();
            String end = current.get(Calendar.YEAR) + "-" + current.get(Calendar.MONTH) + "-" + current.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff = endDate.getTime() - beginDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);//생후 일수 나옴
            String ye= ""+diffDays/365;

            return ye;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }
    public String monthcheck(int year, int month, int day) {//생일 입력받아서 txt박스에 넣을 텍스트 반환해줌
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
         return ""+diffDays/30;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

    public String datecheck(int year, int month, int day) {//생일 입력받아서 txt박스에 넣을 텍스트 반환해줌
        try {
            String start = year + "-" + month + "-" + day;
            Calendar current = Calendar.getInstance();
            String end = current.get(Calendar.YEAR) + "-" + current.get(Calendar.MONTH) + "-" + current.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = formatter.parse(start);
            Date endDate = formatter.parse(end);

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff = endDate.getTime() - beginDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);//생후 일수 나옴
            String ye= ""+diffDays;

            return ye;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

}
