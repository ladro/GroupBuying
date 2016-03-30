package com.example.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.GroupBuying.R;
import com.example.JsonCheapData.CheapAdvertisingKey;
import com.example.JsonCheapData.CheapHotkey;
import com.example.JsonCheapData.CheapInfoBean;
import com.example.JsonCheapData.ResultCheapBean;
import com.example.cache.JsonCache;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


public class CheapFragment extends Fragment {
    String httpUrl = "http://www.warmtel.com:8080/keyConfig";
    private AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
    private SliderLayout mSliderLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cheap,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSliderLayout = (SliderLayout) getView().findViewById(R.id.slider_image);


        ConnectivityManager cwjManager= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cwjManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()){
            setSliderImager();
        }
        else
        {
            Gson gson = new Gson();
            ResultCheapBean resultCheapBean = gson.fromJson(JsonCache.getInstance(getActivity()).getJsonCache("cheaper"), ResultCheapBean.class);
            Log.e("teg","22222222222222222222222222"+resultCheapBean.getResultCode());
            CheapInfoBean infos = resultCheapBean.getInfo();
            ArrayList<CheapAdvertisingKey> advertisingKey = infos.getAdvertisingKey();
            ArrayList<CheapHotkey> hotkey = infos.getHotkey();

            HashMap<String, String> http_url_maps = new HashMap();
            for (int j = 0; j < 3; j++) {
                http_url_maps.put(advertisingKey.get(j).getDescription(), advertisingKey.get(j).getPicUrl());
            }
            for (final String key : http_url_maps.keySet()) {
                String url = http_url_maps.get(key);
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView.description(key);

                textSliderView.image(url);
                textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView baseSliderView) {
                        Toast.makeText(getActivity(), key, Toast.LENGTH_SHORT).show();
                    }
                });
                mSliderLayout.addSlider(textSliderView);
            }
            mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
            mSliderLayout.setClickable(true);
        }

    }

    public void showConfig(String msg){
        Log.e("tag", msg);

    }
    public void setSliderImager(){
        mAsyncHttpClient.get(httpUrl, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Gson gson = new Gson();
                ResultCheapBean resultCheapBean = gson.fromJson(s, ResultCheapBean.class);
                JsonCache.getInstance(getActivity()).setJsonCache("cheaper", s);
                Log.e("tag","111111111111111111111111111111"+s);
                CheapInfoBean info = resultCheapBean.getInfo();
                ArrayList<CheapAdvertisingKey> advertisingKey = info.getAdvertisingKey();
                ArrayList<CheapHotkey> hotkey = info.getHotkey();

                HashMap<String, String> http_url_maps = new HashMap();
                for (int j = 0; j < 3; j++) {
                    http_url_maps.put(advertisingKey.get(j).getDescription(), advertisingKey.get(j).getPicUrl());
                }
                for (final String key : http_url_maps.keySet()) {
                    String url = http_url_maps.get(key);
                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    textSliderView.description(key);

                    textSliderView.image(url);
                    textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
                    textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView baseSliderView) {
                            Toast.makeText(getActivity(), key, Toast.LENGTH_SHORT).show();
                        }
                    });
                    mSliderLayout.addSlider(textSliderView);
                }
                mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                mSliderLayout.setClickable(true);

            }
        });
    }

}
