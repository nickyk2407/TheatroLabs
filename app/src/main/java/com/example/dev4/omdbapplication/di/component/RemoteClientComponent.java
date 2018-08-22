package com.example.dev4.omdbapplication.di.component;

import com.example.dev4.omdbapplication.datamodels.PhotoWebService;
import com.example.dev4.omdbapplication.datamodels.PosterDetailService;
import com.example.dev4.omdbapplication.di.modules.RemoteClientModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RemoteClientModule.class)
public interface RemoteClientComponent {
    void inject(PhotoWebService api);
    void inject(PosterDetailService api);
}
