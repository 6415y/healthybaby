package com.example.james.healthybaby;

import java.io.Serializable;

/**
 * VaccinInfoDetail class
 * @author 장용주
 *
 * VaccinDetailActivity에서 백신의 간략한 정보를 가지는 class
 * intent로 VaccinInfoActivity로 넘기기 위해 Serializable implements
 */
public class VaccinInfoDetail implements Serializable {
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
