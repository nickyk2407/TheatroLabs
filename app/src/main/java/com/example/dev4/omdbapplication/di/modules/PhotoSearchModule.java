package com.example.dev4.omdbapplication.di.modules;

import com.example.dev4.omdbapplication.di.scopes.ActivityScope;
import com.example.dev4.omdbapplication.viewmodel.PosterViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotoSearchModule {
    @Provides
    @ActivityScope
    PosterViewModel providePhotoSearchViewModel(PosterViewModel viewModel) {
        return viewModel;
    }
}
