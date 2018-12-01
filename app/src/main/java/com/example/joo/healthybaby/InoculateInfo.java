package com.example.joo.healthybaby;

/**
 * InoculateInfo class
 * @author lolhi
 *
 * firebase에 저장 및 불러오기를 사용할때 필요한 class
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
