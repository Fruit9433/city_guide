package com.example.mikt.myapplication;

import android.support.v4.app.Fragment;

public interface NavigationHost {
    void navigateTo(Fragment fragment, boolean addToBackstack);


}
