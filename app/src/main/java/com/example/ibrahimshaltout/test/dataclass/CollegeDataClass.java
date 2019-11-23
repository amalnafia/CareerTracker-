package com.example.ibrahimshaltout.test.dataclass;

import java.util.ArrayList;

public class CollegeDataClass {
    String collegeFieldOverview;
    String CollegeFieldName;
    ArrayList <String> CollegeDepList;

    public String getCollegeFieldOverview() {
        return collegeFieldOverview;
    }

    public void setCollegeFieldOverview(String collegeFieldOverview) {
        this.collegeFieldOverview = collegeFieldOverview;
    }

    public String getCollegeFieldName() {
        return CollegeFieldName;
    }

    public void setCollegeFieldName(String collegeFieldName) {
        CollegeFieldName = collegeFieldName;
    }

    public ArrayList<String> getCollegeDepList() {
        return CollegeDepList;
    }

    public void setCollegeDepList(ArrayList<String> collegeDepList) {
        CollegeDepList = collegeDepList;
    }
}
