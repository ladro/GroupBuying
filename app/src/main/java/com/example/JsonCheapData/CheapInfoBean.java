package com.example.JsonCheapData;


import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
public class CheapInfoBean {
   private ArrayList<CheapAdvertisingKey> advertisingKey;
   private ArrayList<CheapHotkey> hotkey;

    public ArrayList<CheapAdvertisingKey> getAdvertisingKey() {
        return advertisingKey;
    }

    public void setAdvertisingKey(ArrayList<CheapAdvertisingKey> advertisingKey) {
        this.advertisingKey = advertisingKey;
    }

    public ArrayList<CheapHotkey> getHotkey() {
        return hotkey;
    }

    public void setHotkey(ArrayList<CheapHotkey> hotkey) {
        this.hotkey = hotkey;
    }
}
