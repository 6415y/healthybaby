package com.example.james.healthybaby;

/**
 * InoculateInfo.java
 * @author 장용주
 *
 * firebase에 예방접종 내역을 저장 및 불러오기를 사용할때 필요한 class
 */
public class InoculateInfo {
    private String vaccinName;
    private String inoculateDate;

    public InoculateInfo(){}

    public InoculateInfo(String vaccinName, String inoculateDate) {
        this.vaccinName = vaccinName;
        this.inoculateDate = inoculateDate;
    }

    public String getVaccinName() {
        return vaccinName;
    }

    public String getInoculateDate() {
        return inoculateDate;
    }
}
