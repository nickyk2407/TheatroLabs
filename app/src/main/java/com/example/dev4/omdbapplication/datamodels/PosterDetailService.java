package com.example.dev4.omdbapplication.datamodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.dev4.omdbapplication.GlowApplication;
import com.example.dev4.omdbapplication.constant.Constants;
import com.example.dev4.omdbapplication.search.PosterDetail;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PosterDetailService {
    private final IPhotoSearchServiceApi posterService;
    private static PosterDetailService posterDetailService;
    @Inject
    Retrofit retrofit;

    public PosterDetailService() {
        GlowApplication.getApp().getRemoteClientComponent().inject(this);
        posterService = retrofit.create(IPhotoSearchServiceApi.class);
    }


    public synchronized static PosterDetailService getInstance() {
        if (posterDetailService == null) {
            if (posterDetailService == null) {
                posterDetailService = new PosterDetailService();
            }
        }
        return posterDetailService;
    }

    public LiveData<PosterDetail> getPosterDetail(String id) {
        final MutableLiveData<PosterDetail> data = new MutableLiveData<>();
        Observer<PosterDetail> observer = new Observer<PosterDetail>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(PosterDetail response) {
                data.setValue(response);
            }

            @Override
            public void onError(Throwable e) {
                data.setValue(null);

            }

            @Override
            public void onComplete() {

            }
        };
        posterService.getPosterDetail(Constants.API_KEY, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return data;
    }
}
