package com.example.dev4.omdbapplication.di.component;

import android.content.Context;

import com.example.dev4.omdbapplication.GlowApplication;
import com.example.dev4.omdbapplication.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(GlowApplication app);

    void inject(Context context);
}
