package com.example.dev4.omdbapplication.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.dev4.omdbapplication.GlowApplication;
import com.example.dev4.omdbapplication.R;
import com.example.dev4.omdbapplication.constant.Constants;
import com.example.dev4.omdbapplication.di.component.DaggerPhotoSearchComponent;
import com.example.dev4.omdbapplication.di.component.PhotoSearchComponent;
import com.example.dev4.omdbapplication.di.modules.ActivityModule;
import com.example.dev4.omdbapplication.search.Search;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    public static final String POSTER_IMDB_ID = "POSTER_IMDB_ID";
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

   /* @BindView(R.id.titleTextView)
    protected TextView mHeaderText;*/

    private Unbinder unbinder;
    private PhotoSearchComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component().inject(this);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Constants.SEARCH_TEXT = "";
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_layout, new PosterListFragment())
                    .commit();
        }
    }

    public PhotoSearchComponent component() {
        if (component == null) {
            component = DaggerPhotoSearchComponent.builder()
                    .applicationComponent(((GlowApplication) getApplication()).getApplicationComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return component;
    }

    private void setToolbarTitle() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        //toolbar.setTitle(getString(R.string.app_name));
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    public void openPosterDetail(Search searchItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(POSTER_IMDB_ID, searchItem);
        PosterDetailFragment fragment = PosterDetailFragment.getInstance(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout, fragment)
                .addToBackStack(null)
                .commit();
    }
}
