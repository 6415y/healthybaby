package com.example.joo.healthybaby;

/**
 * Created by JOO on 2018-12-01.
 */

public class Uploadimage {
    private String mImageUrl;

    public Uploadimage(){

    }

    public  Uploadimage(String ImageUrl){

        mImageUrl=ImageUrl;
    }
    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
