package com.example.james.healthybaby;

import java.io.Serializable;

/**
 * VaccinInfo.java
 * @author 장용주
 *
 * VaccinActivity에서 백신의 간략한 정보를 가지는 class
 * intent로 VaccinDetailActivity로 넘기기 위해 Serializable implements
 */

public class VaccinInfo implements Serializable {
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
