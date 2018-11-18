package com.example.joo.healthybaby;

import java.io.Serializable;

public class VaccinInfoDetail implements Serializable{
    private String vaccinName;
    private String vaccinInfo;
    private int inoculateDate;

    public VaccinInfoDetail(String vaccinName, String vaccinInfo, int inoculateDate) {
        this.vaccinName = vaccinName;
        this.vaccinInfo = vaccinInfo;
        this.inoculateDate = inoculateDate;
    }

    public String getVaccinName() {
        return vaccinName;
    }

    public String getVaccinInfo() {
        return vaccinInfo;
    }

    public int getInoculateDate() {
        return inoculateDate;
    }
}