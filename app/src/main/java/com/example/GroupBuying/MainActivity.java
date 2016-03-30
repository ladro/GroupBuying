package com.example.GroupBuying;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;

import com.example.Fragment.CheapFragment;
import com.example.Fragment.FarvorFragment;
import com.example.Fragment.MoreFragment;
import com.example.Fragment.NearFragment;
import com.example.Fragment.PocketFragment;

public class MainActivity extends FragmentActivity {
    private CheapFragment cheapFragment;
    private FarvorFragment farvorFragment;
    private MoreFragment moreFragment;
    private NearFragment nearFragment;
    private PocketFragment pocketFragment;
    private RadioGroup myTabRg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public void initView() {
        nearFragment = new NearFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, nearFragment).commit();
        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rbNear:
                        nearFragment = new NearFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                        nearFragment).commit();
                        break;
                    case R.id.rbCheap:
                            cheapFragment= new CheapFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, cheapFragment)
                                .commit();
                        break;
                    case R.id.rbFarvor:
                        farvorFragment = new FarvorFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, farvorFragment)
                                .commit();
                        break;
                    case R.id.rbPocket:
                        pocketFragment = new PocketFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, pocketFragment)
                                .commit();
                        break;
                    case R.id.rbMore:
                        moreFragment = new MoreFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, moreFragment)
                                .commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
