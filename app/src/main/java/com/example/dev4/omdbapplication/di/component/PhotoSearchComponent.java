package com.example.dev4.omdbapplication.di.component;

import android.app.Activity;

import com.example.dev4.omdbapplication.di.modules.ActivityModule;
import com.example.dev4.omdbapplication.di.modules.PhotoSearchModule;
import com.example.dev4.omdbapplication.di.scopes.ActivityScope;
import com.example.dev4.omdbapplication.view.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, PhotoSearchModule.class})
public interface PhotoSearchComponent {
    Activity activityContext();

    void inject(MainActivity activity);
}
