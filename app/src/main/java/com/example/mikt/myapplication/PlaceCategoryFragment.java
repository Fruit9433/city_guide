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

import com.example.mikt.myapplication.model.PlaceLab;

import java.util.List;

public class PlaceCategoryFragment extends Fragment {
    private static final String ARG_PLACE_ID = "crime_id";
    private RecyclerView mPlaceRecyclerView;
    private String comCat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comCat = (String) getArguments().getSerializable(ARG_PLACE_ID);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_list, container,
                false);
        mPlaceRecyclerView = (RecyclerView) view
                .findViewById(R.id.place_category_recycler_view);
        mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public static PlaceCategoryFragment newInstance(String comCat) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLACE_ID, comCat);
        PlaceCategoryFragment fragment = new PlaceCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void updateUI() {
        List<String> cats = PlaceLab.get(getContext()).getCatsByComCat(comCat);
        PlaceAdapter adapter = new PlaceAdapter(cats);
        mPlaceRecyclerView.setAdapter(adapter);
    }

    private class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTitle;
        String mCat;

        public PlaceHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitle = itemView.findViewById(R.id.title_category);
        }

        public void bindViewHolder(String cat) {
            mCat = cat;
            mTitle.setText(cat);
        }

        @Override
        public void onClick(View v) {
            ((NavigationHost)getActivity())
                    .navigateTo(PlaceListFragment.newInstance(mCat), true);
        }
    }

    private class PlaceAdapter extends RecyclerView.Adapter<PlaceHolder>{

        private List<String> mCats;

        PlaceAdapter(List<String> cats) {
            this.mCats = cats;
        }

        @NonNull
        @Override
        public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.category_list_item, parent, false);
            return new PlaceHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
            String cat = this.mCats.get(position);
            holder.bindViewHolder(cat);
        }

        @Override
        public int getItemCount() {
            return this.mCats.size();
        }
    }

}
