package com.example.dev4.omdbapplication.datamodels;

import com.example.dev4.omdbapplication.GlowApplication;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class PhotoWebService {
    @Inject
    Retrofit retrofit;

    public PhotoWebService() {
        GlowApplication.getApp().getRemoteClientComponent().inject(this);
    }

    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
