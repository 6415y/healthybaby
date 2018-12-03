package com.example.joo.healthybaby;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class AllergyActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    ImageView babyHead_iv;
    ImageView babyBody_iv;
    ImageView babyLeftHand_iv;
    ImageView babyRightHand_iv;
    ImageView babyLeftLeg_iv;
    ImageView babyRightLeg_iv;

    TextView occurrenceArea_tv;
    TextView occurrenceDate_tv;
    Spinner occurrenceDate_sp;

    Spinner allergyIntensity_sp;

    EditText[] foodIngredients_et = new EditText[10];

    Button[] foodIngredients_btn = new Button[9];
    Button save_btn;
    Button watch_btn;

    double XValue;
    double YValue;

    private String[] occurrenceArea_str;
    private int position;
    private String time;
    private String intensity;
    private int foodIngredientsPosition;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy);

        /*
         * activity를 구성하는 view들 초기화
         */
        babyHead_iv = (ImageView) findViewById(R.id.baby_head_iv_AllergyActivity);
        babyBody_iv = (ImageView) findViewById(R.id.baby_body_iv_AllergyActivity);
        babyLeftHand_iv = (ImageView) findViewById(R.id.baby_lefthand_iv_AllergyActivity);
        babyRightHand_iv = (ImageView) findViewById(R.id.baby_righthand_iv_AllergyActivity);
        babyLeftLeg_iv = (ImageView) findViewById(R.id.baby_leftleg_iv_AllergyActivity);
        babyRightLeg_iv = (ImageView) findViewById(R.id.baby_rightleg_iv_AllergyActivity);

        occurrenceArea_tv = (TextView) findViewById(R.id.occurrenceArea_tv_AllergyActivity);
        occurrenceDate_tv = (TextView) findViewById(R.id.occurrenceDate_tv_AllergyActivity);
        occurrenceDate_sp = (Spinner) findViewById(R.id.occurrenceDate_sp_AllergyActivity);

        allergyIntensity_sp = (Spinner) findViewById(R.id.allergyIntensity_sp_AllergyActivity);

        foodIngredients_et[0] = (EditText) findViewById(R.id.foodIngredients1_et_AllergyActivity);
        foodIngredients_et[1] = (EditText) findViewById(R.id.foodIngredients2_et_AllergyActivity);
        foodIngredients_et[2] = (EditText) findViewById(R.id.foodIngredients3_et_AllergyActivity);
        foodIngredients_et[3] = (EditText) findViewById(R.id.foodIngredients4_et_AllergyActivity);
        foodIngredients_et[4] = (EditText) findViewById(R.id.foodIngredients5_et_AllergyActivity);
        foodIngredients_et[5] = (EditText) findViewById(R.id.foodIngredients6_et_AllergyActivity);
        foodIngredients_et[6] = (EditText) findViewById(R.id.foodIngredients7_et_AllergyActivity);
        foodIngredients_et[7] = (EditText) findViewById(R.id.foodIngredients8_et_AllergyActivity);
        foodIngredients_et[8] = (EditText) findViewById(R.id.foodIngredients9_et_AllergyActivity);
        foodIngredients_et[9] = (EditText) findViewById(R.id.foodIngredients10_et_AllergyActivity);

        foodIngredients_btn[0] = (Button) findViewById(R.id.foodIngredients1_btn_AllergyActivity);
        foodIngredients_btn[1] = (Button) findViewById(R.id.foodIngredients2_btn_AllergyActivity);
        foodIngredients_btn[2] = (Button) findViewById(R.id.foodIngredients3_btn_AllergyActivity);
        foodIngredients_btn[3] = (Button) findViewById(R.id.foodIngredients4_btn_AllergyActivity);
        foodIngredients_btn[4] = (Button) findViewById(R.id.foodIngredients5_btn_AllergyActivity);
        foodIngredients_btn[5] = (Button) findViewById(R.id.foodIngredients6_btn_AllergyActivity);
        foodIngredients_btn[6] = (Button) findViewById(R.id.foodIngredients7_btn_AllergyActivity);
        foodIngredients_btn[7] = (Button) findViewById(R.id.foodIngredients8_btn_AllergyActivity);
        foodIngredients_btn[8] = (Button) findViewById(R.id.foodIngredients9_btn_AllergyActivity);

        allergyIntensity_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                intensity = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        occurrenceDate_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save_btn = (Button) findViewById(R.id.save_btn_AllergyActivity);
        watch_btn = (Button) findViewById(R.id.watch_btn_AllergyActivity);

        /*
         * 재료를 입력받는 editText및 버튼 GONE 설정(edit : 1~9, button : 1~8까지)
         * 및 ClickListener 등록
         */
        for (int i = 1; i < foodIngredients_et.length; i++)
            foodIngredients_et[i].setVisibility(View.GONE);

        for (int i = 1; i < foodIngredients_btn.length; i++) {
            foodIngredients_btn[i].setVisibility(View.GONE);
            foodIngredients_btn[i].setOnClickListener(this);
        }
        foodIngredients_btn[0].setOnClickListener(this);

        /*
         * imageview touchlistner 등록
         * 및 textview, button clickListener 등록
         */
        babyHead_iv.setOnTouchListener(this);
        babyBody_iv.setOnTouchListener(this);
        babyLeftHand_iv.setOnTouchListener(this);
        babyRightHand_iv.setOnTouchListener(this);
        babyLeftLeg_iv.setOnTouchListener(this);
        babyRightLeg_iv.setOnTouchListener(this);

        occurrenceDate_tv.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        watch_btn.setOnClickListener(this);

    }


    /**
     * ShowChoiceDialog()
     *
     * 아기 이미지에서 특정 부위를 선택했을때 뜨는 dialog
     * 상세 부위를 선택하게 하고 occurrenceAreat_tv에 저장
     */
    public void ShowChoiceDialog() {
        AlertDialog.Builder dig = new AlertDialog.Builder(AllergyActivity.this);
        dig.setTitle("부위 선택");
        dig.setSingleChoiceItems(occurrenceArea_str, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                position = i;
            }
        });

        dig.setPositiveButton("선택", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                occurrenceArea_tv.setText(occurrenceArea_str[position]);
            }
        });
        dig.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.occurrenceDate_tv_AllergyActivity:
                DatePickerDialog dig = new DatePickerDialog(AllergyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dayStr;
                        if (day < 10)
                            dayStr = "0" + day;
                        else
                            dayStr = Integer.toString(day);

                        occurrenceDate_tv.setText(year + "-" + (month + 1) + "-" + dayStr);

                    }
                }, 2018, 11, 4);
                dig.show();
                break;

            case R.id.save_btn_AllergyActivity:
                List<String> foodIngerdientList = new ArrayList<String>();
                List<String> foodList = new ArrayList<String>();

                if(occurrenceArea_tv.getText().toString().equals("") || occurrenceDate_tv.getText().toString().equals("")
                        || foodIngredients_et[0].getText().toString().equals("")){
                    AlertDialog.Builder digbuild = new AlertDialog.Builder(AllergyActivity.this);
                    digbuild.setTitle("에러");
                    digbuild.setMessage("필수 정보(발생부위, 발생일자, 재료)가 입력되지 않았습니다. 다시한번 확인해 주세요.");
                    digbuild.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    digbuild.show();
                    break;
                }

                for (int i = 0; i < foodIngredientsPosition; i++)
                    if(!foodIngredients_et[i].getText().toString().equals("")) {
                        foodIngerdientList.add(foodIngredients_et[i].getText().toString() + ", ");
                        foodList.add(foodIngredients_et[i].getText().toString());
                    }
                if(!foodIngredients_et[foodIngredientsPosition].getText().toString().equals("")) {
                    foodIngerdientList.add(foodIngredients_et[foodIngredientsPosition].getText().toString());
                    foodList.add(foodIngredients_et[foodIngredientsPosition].getText().toString());
                }

                FoodResult foodResult = new FoodResult(foodList);
                databaseReference.child("AllergyResult").child("FoodResult").setValue(foodResult);

                AllergyResult result = new AllergyResult(occurrenceArea_tv.getText().toString(), occurrenceDate_tv.getText().toString() + " " + time, intensity, foodIngerdientList, XValue, YValue);
                databaseReference.child("AllergyResult").child(result.getOccurrenceDate()).setValue(result);
                Toast.makeText(AllergyActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                InitializeWiget();
                break;

            case R.id.watch_btn_AllergyActivity:
                Intent intent = new Intent(AllergyActivity.this, AllergyDetailActivity.class);
                startActivity(intent);
                break;

            case R.id.foodIngredients1_btn_AllergyActivity:
                WidgetSetInvisible(1);
                break;

            case R.id.foodIngredients2_btn_AllergyActivity:
                WidgetSetInvisible(2);
                break;

            case R.id.foodIngredients3_btn_AllergyActivity:
                WidgetSetInvisible(3);
                break;

            case R.id.foodIngredients4_btn_AllergyActivity:
                WidgetSetInvisible(4);
                break;

            case R.id.foodIngredients5_btn_AllergyActivity:
                WidgetSetInvisible(5);
                break;

            case R.id.foodIngredients6_btn_AllergyActivity:
                WidgetSetInvisible(6);
                break;

            case R.id.foodIngredients7_btn_AllergyActivity:
                WidgetSetInvisible(7);
                break;

            case R.id.foodIngredients8_btn_AllergyActivity:
                WidgetSetInvisible(8);
                break;

            case R.id.foodIngredients9_btn_AllergyActivity:
                foodIngredientsPosition = 9;
                foodIngredients_btn[8].setVisibility(View.GONE);
                foodIngredients_et[9].setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * WidgetSetInvisible(int i)
     * @param i
     *
     * 활성화된 edittext의 position을 저장
     * 포지션에 해당하는 edittext, button 활성화(VISIBLE)
     * 지난 포지션에 해당하는 button 비활성화(GONE)
     */
    public void WidgetSetInvisible(int i) {
        foodIngredientsPosition = i;
        foodIngredients_btn[i].setVisibility(View.VISIBLE);
        foodIngredients_btn[i - 1].setVisibility(View.GONE);
        foodIngredients_et[i].setVisibility(View.VISIBLE);
    }

    /**
     * InitializeWiget()
     *
     * 저장버튼을 눌렀을때 기존에 입력되있던 text값들 초기화
     */
    public void InitializeWiget() {
        foodIngredients_et[0].setVisibility(View.VISIBLE);
        foodIngredients_btn[0].setVisibility(View.VISIBLE);
        foodIngredients_et[0].setText("");
        for (int i = 1; i < foodIngredients_et.length - 1; i++) {
            foodIngredients_et[i].setText("");
            foodIngredients_btn[i].setVisibility(View.GONE);
            foodIngredients_et[i].setVisibility(View.GONE);
        }
        foodIngredients_et[9].setVisibility(View.GONE);

        occurrenceArea_tv.setText("");
        occurrenceDate_tv.setText("");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            XValue = motionEvent.getX();
            YValue = motionEvent.getY();

            /*
             * ImageView를 가져와 bitmap으로 변환
             * 변환된 bitmap의 pixel정보를 가져와 색상이 255,255,255일때
             * Toast message를 띄우고 return
             */
            ImageView img = (ImageView)view;
            BitmapDrawable drawable = (BitmapDrawable)img.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            int argbValue = bitmap.getPixel((int)XValue,(int)YValue);
            int r = Color.red(argbValue);
            int g = Color.green(argbValue);
            int b = Color.blue(argbValue);

            if(r == 0 && g== 0 && b== 0){
                Toast.makeText(AllergyActivity.this,"알러지 발생부분을 정확히 선택해주세요.",Toast.LENGTH_SHORT).show();
                return false;
            }

            switch (view.getId()) {
                case R.id.baby_head_iv_AllergyActivity:
                    occurrenceArea_str = new String[]{"얼굴", "목"};
                    break;

                case R.id.baby_body_iv_AllergyActivity:
                    occurrenceArea_str = new String[]{"왼쪽가슴", "오른쪽가슴", "배", "등"};
                    break;

                case R.id.baby_lefthand_iv_AllergyActivity:
                    occurrenceArea_str = new String[]{"상박(왼팔)", "하박(왼팔)"};
                    break;

                case R.id.baby_righthand_iv_AllergyActivity:
                    occurrenceArea_str = new String[]{"상박(오른팔)", "하박(오른팔)"};
                    break;

                case R.id.baby_leftleg_iv_AllergyActivity:
                    occurrenceArea_str = new String[]{"허벅지(왼쪽다리)", "종아리(왼쪽다리)"};
                    break;

                case R.id.baby_rightleg_iv_AllergyActivity:
                    occurrenceArea_str = new String[]{"허벅지(오른쪽다리)", "종아리(오른쪽다리)"};
                    break;
            }
            ShowChoiceDialog();
        }
        return false;
    }
}
