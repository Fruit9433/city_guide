package com.example.mikt.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mikt.myapplication.model.Place;
import com.example.mikt.myapplication.model.PlaceLab;

import java.util.List;


public class PlaceListFragment extends Fragment {
    private static final String ARG_CAT = "category";
    private RecyclerView mPlaceRecyclerView;
    private PlaceAdapter mAdapter;
    private String mCat;
    private List<Place> mPlaces;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCat = (String) getArguments().getSerializable(ARG_CAT);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_place_list, container,
                false);

        mPlaceRecyclerView = (RecyclerView) view.findViewById(R.id.place_recycler_view);
        mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        PlaceLab.get(getActivity()).getData();
        mPlaces = PlaceLab.get(getContext()).getPlacesByCat(mCat);
        mAdapter = new PlaceAdapter(mPlaces);
        mPlaceRecyclerView.setAdapter(mAdapter);
    }

    public static PlaceListFragment newInstance(String comCat) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CAT, comCat);
        PlaceListFragment fragment = new PlaceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTitle;
        TextView mPhoneNumber;
        TextView mAddress;

        public PlaceHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitle = itemView.findViewById(R.id.place_title);
            mPhoneNumber = itemView.findViewById(R.id.place_phone_number);
            mAddress = itemView.findViewById(R.id.place_address);
        }

        public void bindViewHolder(Place place) {
            mTitle.setText(place.getTitle());
            mPhoneNumber.setText(place.getPhoneNumber());
            mAddress.setText(place.getAddress());
        }

        @Override
        public void onClick(View v) {
            ((NavigationHost)getActivity())
                    .navigateTo(PlacePagerFragment.newInstance(mCat), true);
        }
    }

    private class PlaceAdapter extends RecyclerView.Adapter<PlaceHolder>{
        private List<Place> mPlaces;


        public PlaceAdapter(List<Place> places) {
            mPlaces = places;
        }

        @NonNull
        @Override
        public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.place_list_item, parent, false);
            return new PlaceHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
            Place place = mPlaces.get(position);
            holder.bindViewHolder(place);
        }

        @Override
        public int getItemCount() {
            return mPlaces.size();
        }
    }

}
