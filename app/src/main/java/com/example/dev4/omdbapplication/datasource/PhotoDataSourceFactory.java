package com.example.dev4.omdbapplication.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.util.Log;

import com.example.dev4.omdbapplication.datamodels.IPhotoSearchServiceApi;

import java.util.concurrent.ExecutorService;

public class PhotoDataSourceFactory extends DataSource.Factory {
    private static final String TAG = PhotoDataSourceFactory.class.getName();
    private final ExecutorService executor;
    private final IPhotoSearchServiceApi webService;
    private final MutableLiveData<PhotoDataSource> mutableLiveData;
    private PhotoDataSource mPhotoDataSource;

    public PhotoDataSourceFactory(ExecutorService executor, IPhotoSearchServiceApi webService) {

        this.executor = executor;
        this.webService = webService;
        mutableLiveData = new MutableLiveData<>();

    }

    @Override
    public DataSource create() {
        Log.d(TAG, "create: ");
        mPhotoDataSource = new PhotoDataSource(executor, webService);
        mutableLiveData.postValue(mPhotoDataSource);
        return mPhotoDataSource;
    }

    public MutableLiveData<PhotoDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
