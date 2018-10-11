package com.example.mikt.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mikt.myapplication.model.Place;
import com.example.mikt.myapplication.model.PlaceLab;

import java.util.List;

public class PlacePagerFragment extends Fragment {
    private static final String EXTRA_PLACE_CAT =
            "place_id";

    private ViewPager mViewPager;
    private List<Place> mPlaces;

    public static PlacePagerFragment newInstance(String Cat) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PLACE_CAT, Cat);
        PlacePagerFragment fragment = new PlacePagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_place_pager,container, false);
        mViewPager = v.findViewById(R.id.activity_place_pager_view_pager);
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        String cat = (String) getArguments().getSerializable(EXTRA_PLACE_CAT);
        mPlaces = PlaceLab.get(getActivity()).getPlacesByCat(cat);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return PlaceFragment.newInstance(mPlaces.get(position).getObjectId());
            }

            @Override
            public int getCount() {
                return mPlaces.size();
            }
        });
        return v;
    }
}
