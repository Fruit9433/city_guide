package com.example.mikt.myapplication.model;

import weborb.service.MapToProperty;

public class CatLogo {
    @MapToProperty( property = "cat" )
    private String mComCat;
    @MapToProperty( property = "logourl" )
    private String mLogoUrl;

    public String getComCat() {
        return mComCat;
    }

    public String getLogoUrl() {
        return mLogoUrl;
    }
}
