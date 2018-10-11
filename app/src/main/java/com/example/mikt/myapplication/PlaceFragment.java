package com.example.mikt.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mikt.myapplication.model.Place;
import com.example.mikt.myapplication.model.PlaceLab;

import java.util.Map;

public class PlaceFragment extends Fragment {
    private static final String ARG_PLACE_ID = "crime_id";
    private Place mPlace;
    private TextView mTitleField;
    private TextView mPhoneNumberField;
    private TextView mShortDescriptionField;
    private TextView mAddress;
    private ImageButton mCallButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String placeId = (String) getArguments().getSerializable(ARG_PLACE_ID);
        PlaceLab.get(getActivity()).getData();
        mPlace = PlaceLab.get(getContext()).getPlaceById(placeId);
    }

    public static PlaceFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLACE_ID, id);
        PlaceFragment fragment = new PlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place, container, false);

        mTitleField = v.findViewById(R.id.place_title);
        mTitleField.setText(mPlace.getTitle());

        mPhoneNumberField = v.findViewById(R.id.place_phone_number);
        mPhoneNumberField.setText(mPlace.getPhoneNumber());

        mShortDescriptionField = v.findViewById(R.id.place_short_description);
        mShortDescriptionField.setText(mPlace.getShortDescription());

        mAddress = v.findViewById(R.id.place_address);
        mAddress.setText(mPlace.getAddress());

        mCallButton = v.findViewById(R.id.callButton);
        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+mPhoneNumberField.getText()));
                startActivity(intent);
            }
        });


        return v;
    }
}
