package com.example.dev4.omdbapplication.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.dev4.omdbapplication.apiutil.NetworkState;
import com.example.dev4.omdbapplication.constant.Constants;
import com.example.dev4.omdbapplication.datamodels.IPhotoSearchServiceApi;
import com.example.dev4.omdbapplication.datamodels.PhotoWebService;
import com.example.dev4.omdbapplication.datasource.PhotoDataSource;
import com.example.dev4.omdbapplication.datasource.PhotoDataSourceFactory;
import com.example.dev4.omdbapplication.search.Search;
import com.example.dev4.omdbapplication.viewinterfaces.IPosterOverviewNavigator;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PosterViewModel extends ViewModel {
    private final ExecutorService executor;
    private final MutableLiveData<PhotoDataSource> mDataSource;
    private final LiveData<PagedList<Search>> mMovieList;
    private final LiveData<NetworkState> networkStateLiveData;
    private WeakReference<IPosterOverviewNavigator> mNavigator;

    public PosterViewModel() {
        executor = Executors.newFixedThreadPool(5);
        IPhotoSearchServiceApi webService = new PhotoWebService().createService(IPhotoSearchServiceApi.class);
        PhotoDataSourceFactory factory = new PhotoDataSourceFactory(executor, webService);
        mDataSource = factory.getMutableLiveData();
        Constants.API_DEFAULT_PAGE_KEY = 1;

        networkStateLiveData = Transformations.switchMap(factory.getMutableLiveData(), new Function<PhotoDataSource, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(PhotoDataSource source) {
                return source.getNetworkState();
            }
        });

        PagedList.Config pageConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20).build();

        mMovieList = (new LivePagedListBuilder<Integer, Search>(factory, pageConfig))
                .setBackgroundThreadExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Search>> getMovieList() {
        return mMovieList;
    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }

    public void retry(String query) {
        mDataSource.getValue().invalidate();
    }

    public void openPosterDetail(Search item) {
        if (mNavigator != null && mNavigator.get() != null){
            ((IPosterOverviewNavigator) mNavigator.get()).openPosterDetail(item);
        }
    }

    public void setNavigator(IPosterOverviewNavigator navigator) {
        mNavigator = new WeakReference<>(navigator);
    }
}
