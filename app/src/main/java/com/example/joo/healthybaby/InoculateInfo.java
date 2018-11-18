package com.example.joo.healthybaby;

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
