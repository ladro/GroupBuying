package com.example.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.GroupBuying.R;


public class PocketFragment extends Fragment {
    private ImageView image_pocket;
    public static PocketFragment newInstance() {
        PocketFragment fragment = new PocketFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pocket,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        image_pocket = (ImageView) getView().findViewById(R.id.pocket_logo);
    }
}
