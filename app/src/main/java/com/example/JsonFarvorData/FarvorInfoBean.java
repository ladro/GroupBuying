package com.example.JsonFarvorData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class FarvorInfoBean {
    private ArrayList<FarvorMainKeyBean> mainKey;
    private ArrayList<FarvorIndustryKeyBean> industryKey;

    public ArrayList<FarvorMainKeyBean> getMainKey() {
        return mainKey;
    }

    public void setMainKey(ArrayList<FarvorMainKeyBean> mainKey) {
        this.mainKey = mainKey;
    }

    public ArrayList<FarvorIndustryKeyBean> getIndustryKey() {
        return industryKey;
    }

    public void setIndustryKey(ArrayList<FarvorIndustryKeyBean> industryKey) {
        this.industryKey = industryKey;
    }
}
