package com.example.dev4.omdbapplication.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dev4.omdbapplication.datamodels.PosterDetailService;
import com.example.dev4.omdbapplication.search.PosterDetail;

public class PosterDetailViewModel extends ViewModel {
    public static String POSTER_IMDB_ID = "POSTER_IMDB_ID";
    private final LiveData<PosterDetail> posterDetailObservable;
    private String mImdbId;

    public PosterDetailViewModel() {
        posterDetailObservable = PosterDetailService.getInstance().getPosterDetail(POSTER_IMDB_ID);

    }

    public LiveData<PosterDetail> getPosterDetailObservable() {
        return posterDetailObservable;
    }
}
