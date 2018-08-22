package com.example.dev4.omdbapplication.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.dev4.omdbapplication.apiutil.NetworkState;
import com.example.dev4.omdbapplication.constant.Constants;
import com.example.dev4.omdbapplication.datamodels.IPhotoSearchServiceApi;
import com.example.dev4.omdbapplication.search.Search;
import com.example.dev4.omdbapplication.search.SearchResponse;

import java.util.concurrent.ExecutorService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.dev4.omdbapplication.constant.Constants.API_DEFAULT_PAGE_KEY;
import static com.example.dev4.omdbapplication.constant.Constants.SEARCH_TEXT;

public class PhotoDataSource extends PageKeyedDataSource<Integer, Search> {
    private final IPhotoSearchServiceApi mWebService;
    private MutableLiveData<NetworkState> initialLoading;
    private MutableLiveData<NetworkState> networkState;

    public PhotoDataSource(ExecutorService executor, IPhotoSearchServiceApi webService) {
        this.mWebService = webService;
        this.initialLoading = new MutableLiveData<>();
        this.networkState = new MutableLiveData<>();
    }

    public MutableLiveData<NetworkState> getInitialLoading() {
        return initialLoading;
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Search> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        Observer<SearchResponse> observer = new Observer<SearchResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(SearchResponse response) {
                if (response != null && response.getSearch() != null && !response.getSearch().isEmpty()) {
                    initialLoading.postValue(NetworkState.LOADING);
                    networkState.postValue(NetworkState.LOADED);
                    API_DEFAULT_PAGE_KEY++;
                    callback.onResult(response.getSearch(), null, API_DEFAULT_PAGE_KEY);
                } else {
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.getError()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.getError()));
                }
            }

            @Override
            public void onError(Throwable e) {
                String errorMessage = e.getMessage();
                initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));

            }

            @Override
            public void onComplete() {

            }
        };
        if (Constants.SEARCH_TEXT != null && !SEARCH_TEXT.isEmpty()) {
            mWebService.search(Constants.API_KEY, Constants.SEARCH_TEXT, API_DEFAULT_PAGE_KEY)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Search> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Search> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        Observer<SearchResponse> observer = new Observer<SearchResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(SearchResponse response) {
                if (response != null && response.getSearch() != null && !response.getSearch().isEmpty()) {
                    initialLoading.postValue(NetworkState.LOADING);
                    networkState.postValue(NetworkState.LOADED);
                    API_DEFAULT_PAGE_KEY = (params.key + 1);
                    callback.onResult(response.getSearch(), API_DEFAULT_PAGE_KEY);
                } else {
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.getError()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.getError()));
                }
            }

            @Override
            public void onError(Throwable e) {
                String errorMessage = e.getMessage();
                initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }

            @Override
            public void onComplete() {

            }
        };

        if (Constants.SEARCH_TEXT != null && !SEARCH_TEXT.isEmpty()) {
            mWebService.search(Constants.API_KEY, Constants.SEARCH_TEXT, API_DEFAULT_PAGE_KEY)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }

    @Override
    public void addInvalidatedCallback(@NonNull InvalidatedCallback onInvalidatedCallback) {
        super.addInvalidatedCallback(onInvalidatedCallback);
    }
}
