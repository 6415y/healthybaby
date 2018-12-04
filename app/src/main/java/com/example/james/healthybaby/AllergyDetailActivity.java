package com.example.james.healthybaby;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllergyDetailActivity extends AppCompatActivity {

    CustomImageView babyHead_iv;
    CustomImageView babyBody_iv;
    CustomImageView babyLeftHand_iv;
    CustomImageView babyRightHand_iv;
    CustomImageView babyLeftLeg_iv;
    CustomImageView babyRightLeg_iv;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<AllergyResult> allergyResultArrayList = new ArrayList<AllergyResult>();
    RecyclerAdapForAllergyDetail adapter;
    CardView cvTextview;
    CardView cvCustumImgView;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_detail);

        /*
         * activity를 구성하는 view들 초기화
         */
        babyHead_iv = (CustomImageView) findViewById(R.id.baby_head_iv_AllergyDetailActivity);
        babyBody_iv = (CustomImageView) findViewById(R.id.baby_body_iv_AllergyDetailActivity);
        babyLeftHand_iv = (CustomImageView) findViewById(R.id.baby_lefthand_iv_AllergyDetailActivity);
        babyRightHand_iv = (CustomImageView) findViewById(R.id.baby_righthand_iv_AllergyDetailActivity);
        babyLeftLeg_iv = (CustomImageView) findViewById(R.id.baby_leftleg_iv_AllergyDetailActivity);
        babyRightLeg_iv = (CustomImageView) findViewById(R.id.baby_rightleg_iv_AllergyDetailActivity);
        cvCustumImgView = (CardView) findViewById(R.id.customImgView_cv_AllergyDetailActivity);
        cvTextview = (CardView) findViewById(R.id.textview_cv_AllergyDetailActivity);

        cvTextview.setVisibility(View.GONE);
        cvCustumImgView.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_AllergyDetailActivity);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new RecyclerAdapForAllergyDetail(allergyResultArrayList);
        mRecyclerView.setAdapter(adapter);

        ItemClickSupport.addTo(mRecyclerView, R.id.recyclerView_AllergyDetailActivity).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                /*
                 * RecyclerView의 아이템들이 클릭될때마다 그려놨던 canvas들을 지움
                 */
                CanvasClear(babyHead_iv);
                CanvasClear(babyBody_iv);
                CanvasClear(babyLeftHand_iv);
                CanvasClear(babyRightHand_iv);
                CanvasClear(babyLeftLeg_iv);
                CanvasClear(babyRightLeg_iv);

                databaseReference.child("AllergyResult").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        AllergyResult result;
                        cvCustumImgView.setVisibility(View.VISIBLE);
                        cvTextview.setVisibility(View.VISIBLE);
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            result = snapshot.getValue(AllergyResult.class);

                            if (result.getOccurrenceDate().equals(adapter.allergyResultsList.get(position).getOccurrenceDate())) {
                                switch (result.getOccurrenceArea()) {
                                    case "얼굴":
                                    case "목":
                                        DrawCircleOnImgView(babyHead_iv, result);
                                        return;
                                    case "왼쪽가슴":
                                    case "오른쪽가슴":
                                    case "배":
                                    case "등":
                                        DrawCircleOnImgView(babyBody_iv, result);
                                        return;
                                    case "상박(왼팔)":
                                    case "하박(왼팔)":
                                        DrawCircleOnImgView(babyLeftHand_iv, result);
                                        return;
                                    case "상박(오른팔)":
                                    case "하박(오른팔)":
                                        DrawCircleOnImgView(babyRightHand_iv, result);
                                        return;
                                    case "종아리(오른쪽다리)":
                                    case "허벅지(오른쪽다리)":
                                        DrawCircleOnImgView(babyRightLeg_iv, result);
                                        return;
                                    case "종아리(왼쪽다리)":
                                    case "허벅지(왼쪽다리)":
                                        DrawCircleOnImgView(babyLeftLeg_iv, result);
                                        return;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        databaseReference.child("AllergyResult").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AllergyResult result;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(!snapshot.getKey().equals("FoodResult")) {
                        result = snapshot.getValue(AllergyResult.class);

                        if (result != null) {
                            allergyResultArrayList.add(result);
                        }
                    }
                }
                /*
                 * RecyclerView 갱신
                 */
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * DrawCircleOnImgView(CustomImageView imgVIew, AllergyResult result)
     * @param imgVIew
     * @param result
     *
     * 알러지의 강도에 따라 색상을 지정해주고
     * CustomImgView에 있는 canvas를 사용하여 원을 그림
     */
    public void DrawCircleOnImgView(CustomImageView imgVIew, AllergyResult result) {
        imgVIew.XVlaue = (float) result.getXValue();
        imgVIew.YValue = (float) result.getYValue();

        if (result.getAllergyIntensity().equals("상"))
            imgVIew.colorValue = Color.RED;
        else if (result.getAllergyIntensity().equals("중"))
            imgVIew.colorValue = Color.GREEN;
        else
            imgVIew.colorValue = Color.BLUE;

        imgVIew.invalidate();
        imgVIew.drawCircle = true;
    }

    /**
     * CanvasClear(CustomImageView img)
     * @param img
     *
     * 기존에 CustomImgView에 그려진 원 그림 초기화
     */
    public void CanvasClear(CustomImageView img) {
        img.invalidate();
        img.drawCircle = false;
    }
}
