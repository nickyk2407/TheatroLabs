package com.example.dev4.omdbapplication.di.component;

import android.content.Context;

import com.example.dev4.omdbapplication.MyApplication;
import com.example.dev4.omdbapplication.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MyApplication app);

    void inject(Context context);
}
