package com.example.james.healthybaby;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfilechnActivity extends AppCompatActivity {
    int PICK_IMAGE_REQUEST = 1;
    CircleImageView babyface;
    EditText editname;
    TextView selboy, selgirl, editbirth;
    Button savebtn;
    String gender="남자";
    int biryear, birmonth, birdate;
    public Uri mImageUri;
    public StorageReference mStroageRef;
    public DatabaseReference mDatabaseRef;

    protected void onStart() {//첫 실행시 프로필 출력
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference profileput = database.getReference();
        profileput.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile getprofile;
                for (DataSnapshot message : dataSnapshot.getChildren()) {
                    if (message.getKey().equals("profile")) {
                        getprofile = message.getValue(profile.class);
                        if (getprofile != null) {
                            babyface = (CircleImageView) findViewById(R.id.babyface);
                            editname = (EditText) findViewById(R.id.editname);
                            editbirth = (TextView) findViewById(R.id.editbirth);
                            selboy = (TextView) findViewById(R.id.selboy);
                            selgirl = (TextView) findViewById(R.id.selgirl);
                            birdate = getprofile.getDay();
                            birmonth = getprofile.getMonth();
                            biryear = getprofile.getYear();
                            gender = getprofile.getSex();
                            editname.setText(getprofile.getName());//아기이름 수정
                            editbirth.setText(getprofile.getYear() + "년 " + (getprofile.getMonth()+1) + "월 " + getprofile.getDay() + "일");
                            if (gender.equals("남자")) {
                                selboy.setTextColor(Color.RED);
                            } else {
                                selgirl.setTextColor(Color.RED);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Uploadimage upload;
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    upload = post.getValue(Uploadimage.class);
                    if (upload != null) {
                        Picasso.get().load(upload.getmImageUrl()).into(babyface);
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
        setContentView(R.layout.activity_profilechn);
        babyface = (CircleImageView) findViewById(R.id.babyface);
        editname = (EditText) findViewById(R.id.editname);
        editbirth = (TextView) findViewById(R.id.editbirth);
        selboy = (TextView) findViewById(R.id.selboy);
        selgirl = (TextView) findViewById(R.id.selgirl);
        savebtn = (Button) findViewById(R.id.savebtn);
        babyface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        selboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selboy.setTextColor(Color.RED);
                selgirl.setTextColor(Color.GRAY);
                gender="남자";
            }
        });
        selgirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selboy.setTextColor(Color.GRAY);
                selgirl.setTextColor(Color.RED);
                gender="여자";
            }
        });

        editbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog agedialog = new DatePickerDialog(ProfilechnActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        biryear = year;
                        birmonth = month;
                        birdate = date;
                        editbirth.setText(biryear + "년 " + (birmonth+1) + "월 " + birdate + "일");
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                Calendar maxDate = Calendar.getInstance();
                Calendar current = Calendar.getInstance();
                maxDate.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH));
                agedialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                agedialog.show();
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference profile = db.getReference();
                profile putprofile = new profile(profileage(biryear, birmonth, birdate), biryear, birmonth, birdate, editname.getText().toString(), gender);//프로필 db에 저장
                profile.child("profile").setValue(putprofile);
                Toast.makeText(ProfilechnActivity.this,"저장되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            uploadFile();
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStroageRef.child(getFileExtension(mImageUri));
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
                            Toast.makeText(ProfilechnActivity.this, "업로드 완료", Toast.LENGTH_SHORT).show();
                            Uploadimage upload = new Uploadimage(taskSnapshot.getDownloadUrl().toString());
                            mDatabaseRef.child("upload").setValue(upload);
                            babyface.setImageURI(mImageUri);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {//실패시
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfilechnActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {//프로그래스바 표현
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double profress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //mProgressBar.
                        }
                    });
        } else {
            Toast.makeText(this, "파일선택안됨", Toast.LENGTH_SHORT).show();
        }
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
