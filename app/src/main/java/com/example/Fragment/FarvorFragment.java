package com.example.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.GroupBuying.R;
import com.example.JsonFarvorData.FarvorInfoBean;
import com.example.JsonFarvorData.FarvorMainKeyBean;
import com.example.JsonFarvorData.FarvorResultBean;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


public class FarvorFragment extends Fragment {
    String HttpUrl = "http://www.warmtel.com:8080/maininit";
    private SliderLayout msliderLayout;
    private AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
    private ImageView image_farvor,image_icon_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favour,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        image_farvor = (ImageView) getView().findViewById(R.id.farvor_logo);
        image_icon_title = (ImageView) getView().findViewById(R.id.farvor_icon_title_img);
        msliderLayout = (SliderLayout) getView().findViewById(R.id.farvor_slider_image);
        setSliderImager();
    }
    public void setSliderImager(){
        mAsyncHttpClient.get(HttpUrl, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Gson gson = new Gson();
                FarvorResultBean farvorResultBean = gson.fromJson(s, FarvorResultBean.class);
                Log.e("tag","========="+farvorResultBean.getInfo().getMainKey().get(1).getBigpicUrl());
                FarvorInfoBean info = farvorResultBean.getInfo();
                ArrayList<FarvorMainKeyBean> mainKey = info.getMainKey();

                HashMap<String,String> http_url_maps =new HashMap();
                for(int j = 0;j < 12;j++){
                    http_url_maps.put(mainKey.get(j).getDescription(),mainKey.get(j).getBigpicUrl());
                }
                for(final String key:http_url_maps.keySet()){
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
                    msliderLayout.addSlider(textSliderView);
                }
                msliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                msliderLayout.setClickable(true);
            }
        });

    }
}
