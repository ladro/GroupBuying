package com.example.JsonFarvorData;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class FarvorResultBean {
    private int resultCode;
    private String resultInfo;
    private FarvorInfoBean info;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public FarvorInfoBean getInfo() {
        return info;
    }

    public void setInfo(FarvorInfoBean info) {
        this.info = info;
    }
}
