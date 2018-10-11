package com.example.mikt.myapplication.model;

import android.content.Context;

import com.backendless.Backendless;
import com.example.mikt.myapplication.Defaults;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PlaceLab {
    public static PlaceLab sPlaceLab;
    private Context mContext;
    private List<Place> mPlaces;
    private List<CatLogo> mCatLogos;
    private List<String> mComCats;
    Future<List<Place>> future;
    Future<List<CatLogo>> future2;
    ExecutorService ex;

    public static PlaceLab get (Context context) {
        if (sPlaceLab == null) {
            sPlaceLab = new PlaceLab(context);
        }
        return sPlaceLab;
    }

    private PlaceLab (Context context) {
        mContext = context;
        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(mContext, Defaults.APPLICATION_ID, Defaults.API_KEY);
        Backendless.Data.mapTableToClass( "base", Place.class);
        Backendless.Data.mapTableToClass( "comcatlogos", CatLogo.class);
    }


    public void getData() {
        if (mPlaces == null) {
            ex  = Executors.newFixedThreadPool(2);
            future = ex.submit(() -> {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                return Backendless.Data.of(Place.class).find();
            });
            future2 = ex.submit(()->{
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                return Backendless.Data.of(CatLogo.class).find();
            });

        }
    }

    public void init() {
        try {
            mPlaces = future.get();
            mCatLogos = future2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<Place> getPlaces() {
        return mPlaces;
    }

    public List<String> getComCatList() {
        mComCats = new ArrayList<>();
        for (CatLogo cl: mCatLogos) {
            if (!mComCats.contains(cl.getComCat()))
                mComCats.add(cl.getComCat());
        }
        return mComCats;
    }

    public List<String> getCatsByComCat(String comCat) {
        List<String> mCats = new ArrayList<>();
        for (Place pl: mPlaces) {
            if (pl.getComCat().equals(comCat) && !mCats.contains(pl.getCat())) {
                mCats.add(pl.getCat());
            }
        }
        return mCats;
    }

    public List<Place> getPlacesByCat (String cat) {
        List<Place> mPlacesByCat = new ArrayList<>();
        for (Place pl: mPlaces) {
            if (pl.getCat().equals(cat)) {
                mPlacesByCat.add(pl);
            }
        }
        return mPlacesByCat;
    }

    public Place getPlaceById (String id) {
        for (Place pl: mPlaces) {
            if (pl.getObjectId().equals(id)) {
                return pl;
            }
        }
        return new Place();
    }

    public List<CatLogo> getCatLogos() {
        return mCatLogos;
    }
}
