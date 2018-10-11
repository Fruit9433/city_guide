package com.example.mikt.myapplication.model;

import java.util.Date;
import java.util.UUID;

import weborb.service.MapToProperty;

public class Place {
    private UUID mId;
    @MapToProperty( property = "comcat" )
    private String mComCat;
    @MapToProperty( property = "cat" )
    private String mCat;
    @MapToProperty( property = "title" )
    private String mTitle;
    @MapToProperty( property = "phonenumber" )
    private String mPhoneNumber;
    @MapToProperty( property = "workinghours" )
    private String mWorkingHours;
    @MapToProperty( property = "address" )
    private String mAddress;
    @MapToProperty( property = "shortdescription" )
    private String mShortDescription;
    private String objectId;


    public Place() {
        this.mId = UUID.randomUUID();

    }
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        mTitle = title;
    }

    public String getWorkingHours() {
        return mWorkingHours;
    }

    public void setWorkingHours(String mWorkingHours) {
        this.mWorkingHours = mWorkingHours;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    public String getComCat() {
        return mComCat;
    }

    public void setComCat(String comCat) {
        mComCat = comCat;
    }

    public String getCat() {
        return mCat;
    }

    public void setCat(String cat) {
        mCat = cat;
    }

    public String getObjectId() {
        return objectId;
    }
}
