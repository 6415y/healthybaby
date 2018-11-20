package com.example.joo.healthybaby;

import java.io.Serializable;

/*
 *  public class VaccinInfo implements Serializable{ ......
 *
 *  백신의 간략한 정보를 가지는 class
 */

public class VaccinInfo implements Serializable{
    private String vaccinName;
    private String vaccinInfo;
    private int[] inoculateDate;

    public VaccinInfo() {}

    public VaccinInfo(String vaccinName, String vaccinInfo, int[] inoculateDate) {
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

    public int[] getInoculateDate() { return inoculateDate; }
}
