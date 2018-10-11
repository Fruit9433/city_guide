package com.example.mikt.myapplication;

import android.support.v4.app.Fragment;


public class PlaceCommonCategoryActivity extends AppFragmentActivity {


    @Override
    public Fragment createFragment() {
        return PlaceCommonCategoryFragment.newInstance();
    }
}
