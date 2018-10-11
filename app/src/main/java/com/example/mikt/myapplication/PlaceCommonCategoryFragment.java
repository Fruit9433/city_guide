package com.example.mikt.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mikt.myapplication.model.CatLogo;
import com.example.mikt.myapplication.model.PlaceLab;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;
import java.util.List;

public class PlaceCommonCategoryFragment extends Fragment {

    private RecyclerView mPlaceRecyclerView;

    private PlaceAdapter mAdapter;

    private List<String> mComCats;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        PlaceLab.get(getActivity()).getData();

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_category_list, container,
                false);
        mPlaceRecyclerView = (RecyclerView) view
                .findViewById(R.id.place_list_category_recycler_view);
        mPlaceRecyclerView.setLayoutManager(new GridLayoutManager
                (getActivity(),2));
        updateUI();
        return view;
    }

    private void updateUI() {
        PlaceLab.get(getActivity()).init();
        mComCats = PlaceLab.get(getActivity()).getComCatList();
        mAdapter = new PlaceAdapter(mComCats);
        mPlaceRecyclerView.setAdapter(mAdapter);
    }

    @NonNull
    public static PlaceCommonCategoryFragment newInstance() {
        return new PlaceCommonCategoryFragment();
    }

    private class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTitle;
        String mComCat;
        ImageView mCatLogo;
        ProgressBar mProgressBar;

        PlaceHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitle = itemView.findViewById(R.id.title_comcategory);
            mCatLogo = itemView.findViewById(R.id.cat_logo);
            mProgressBar = itemView.findViewById(R.id.progress_bar);
        }

        void bindViewHolder(String comCat) {
            mComCat = comCat;
            mTitle.setText(mComCat);
            for (CatLogo c: PlaceLab.get(getActivity()).getCatLogos()) {
                if (c.getComCat().equals(mComCat)) {
                    Picasso.get()
                            .load(c.getLogoUrl())
                            .into(mCatLogo, new Callback() {
                                @Override
                                public void onSuccess() {
                                    mProgressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Exception e) {
                                    e.printStackTrace();
                                }
                            });
                }
            }
        }

        @Override
        public void onClick(View v) {
            ((NavigationHost)getActivity())
                    .navigateTo(PlaceCategoryFragment.newInstance(mComCat), true);
        }
    }

    private class PlaceAdapter extends RecyclerView.Adapter<PlaceHolder>{
        private List<String> mComCats;

        PlaceAdapter(List<String> comCats) {
            this.mComCats = comCats;
        }

        @NonNull
        @Override
        public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.place_category_list_item, parent, false);
            return new PlaceHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
            String comCat = this.mComCats.get(position);
            holder.bindViewHolder(comCat);
        }

        @Override
        public int getItemCount() {
            return this.mComCats.size();
        }
    }
}
