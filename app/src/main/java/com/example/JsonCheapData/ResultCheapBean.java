package com.example.JsonCheapData;




/**
 * Created by Administrator on 2016/2/25 0025.
 */
public class ResultCheapBean {
    private int resultCode;
    private String resultInfo;
    private CheapInfoBean info;

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

    public CheapInfoBean getInfo() {
        return info;
    }

    public void setInfo(CheapInfoBean info) {
        this.info = info;
    }
}
