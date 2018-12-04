package com.example.james.healthybaby;

/**
 * Created by JOO on 2018-11-30.
 */

public class profile {
    public int age;
    public int year,month,day;
    public String name,sex;

    public profile(){}
    public profile(profile t){
        t.age = age;
        t.year = year;
        t.month = month;
        t.day = day;
        t.name = name;
        t.sex=sex;
    }

    public profile(int age, int year, int month, int day, String name, String sex) {
        this.age = age;
        this.year = year;
        this.month = month;
        this.day = day;
        this.name = name;
        this.sex=sex;
    }

    public int getAge() {
        return age;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getName() {
        return name;
    }
    public String getSex(){return sex;}
}
