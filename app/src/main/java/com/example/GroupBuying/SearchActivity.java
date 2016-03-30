package com.example.GroupBuying;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent mintent=getIntent();
        if (Intent.ACTION_SEARCH.equals(SearchManager.QUERY)){
            String query=mintent.getStringExtra(SearchManager.QUERY);
            Log.e("************","SearchActivity:查询query");

        }
    }
}
