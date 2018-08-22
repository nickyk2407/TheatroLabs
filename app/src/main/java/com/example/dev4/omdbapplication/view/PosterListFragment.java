package com.example.dev4.omdbapplication.view;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dev4.omdbapplication.R;
import com.example.dev4.omdbapplication.adapter.PhotoViewAdapter;
import com.example.dev4.omdbapplication.apiutil.NetworkState;
import com.example.dev4.omdbapplication.constant.Constants;
import com.example.dev4.omdbapplication.search.Search;
import com.example.dev4.omdbapplication.viewinterfaces.IPosterOverviewNavigator;
import com.example.dev4.omdbapplication.viewmodel.PosterViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PosterListFragment extends Fragment implements IPosterOverviewNavigator{

    @BindView(R.id.progressbar)
    protected ProgressBar progressbar;

    @BindView(R.id.recyclerview_photos)
    protected RecyclerView rvPhotos;

    @BindView(R.id.swipeContainer)
    protected SwipeRefreshLayout mSwipeContainer;

    @BindView(R.id.tv_search_text)
    protected TextView tvSearchText;

    private Unbinder unbinder;

    private PhotoViewAdapter mPhotoAdapter;
    private PosterViewModel mPhotoViewModel;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    public String searchText;

    public static Fragment getInstance() {
        return new PosterListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flickr_photos_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
        init();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getTitleText());
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        TextView textView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setTextColor(Color.WHITE);
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Constants.API_DEFAULT_PAGE_KEY = 1;
                    Constants.SEARCH_TEXT = query;
                    searchText = query;
                    showProgressbar();
                    mPhotoViewModel.retry(query);
                    searchView.onActionViewCollapsed();
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getTitleText());
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void init() {
        hideProgressbar();
        initPhotoView();
    }

    private void showProgressbar() {
        progressbar.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar() {
        progressbar.setVisibility(View.GONE);
    }

    private void initPhotoView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPhotos.setLayoutManager(layoutManager);

        mPhotoViewModel = ViewModelProviders.of(this).get(PosterViewModel.class);
        mPhotoAdapter = new PhotoViewAdapter(getActivity(), mPhotoViewModel);
        mPhotoViewModel.setNavigator(this);
        mPhotoViewModel.getMovieList().observe(this, new Observer<PagedList<Search>>() {
            @Override
            public void onChanged(@Nullable PagedList<Search> movies) {
                mPhotoAdapter.submitList(movies);
            }
        });

        mPhotoViewModel.getNetworkStateLiveData().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                mPhotoAdapter.setNetworkState(networkState);
                if (networkState == NetworkState.LOADED) {
                    hideProgressbar();
                    mSwipeContainer.setRefreshing(false);
                    tvSearchText.setVisibility(View.GONE);
                    rvPhotos.setVisibility(View.VISIBLE);
                    searchView.setIconified(true);
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getTitleText());
                }
                if (networkState == NetworkState.FAILED) {
                    hideProgressbar();
                }
            }
        });

        rvPhotos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvPhotos.setAdapter(mPhotoAdapter);

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Constants.API_DEFAULT_PAGE_KEY = 1;
                if (searchText == null) {
                    mSwipeContainer.setRefreshing(false);
                }
                Constants.SEARCH_TEXT = searchText;
                mPhotoViewModel.retry(searchText);
            }
        });

    }

    private String getTitleText() {
        String titleText = Constants.SEARCH_TEXT.trim();
        return titleText.trim().length() > 0 ? Character.toUpperCase(titleText.charAt(0)) + titleText.substring(1) :
                getString(R.string.app_name);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void openPosterDetail(Search searchItem) {
        ((MainActivity)getActivity()).openPosterDetail(searchItem);
    }

}