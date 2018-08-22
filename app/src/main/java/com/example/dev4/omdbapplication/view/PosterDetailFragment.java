package com.example.dev4.omdbapplication.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev4.omdbapplication.R;
import com.example.dev4.omdbapplication.search.PosterDetail;
import com.example.dev4.omdbapplication.search.Search;
import com.example.dev4.omdbapplication.viewmodel.PosterDetailViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.dev4.omdbapplication.view.MainActivity.POSTER_IMDB_ID;

public class PosterDetailFragment extends Fragment {
    private Unbinder unbinder;
    private Search mSearchItem;
    private PosterDetailViewModel mPosterDetailViewModel;

    @BindView(R.id.poster_image)
    protected ImageView ivPoster;

    @BindView(R.id.poster_title)
    protected TextView tvTitle;

    @BindView(R.id.poster_rating)
    protected TextView tvRating;

    @BindView(R.id.progressbar)
    protected ProgressBar progressbar;

    public static PosterDetailFragment getInstance(Bundle bundle) {
        PosterDetailFragment fragment = new PosterDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poster_detail_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        if (mSearchItem != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mSearchItem.getTitle());
    }

    private void init() {
        if (getArguments() != null && getArguments().containsKey(POSTER_IMDB_ID) && getArguments().getParcelable(POSTER_IMDB_ID) instanceof Search) {
            mSearchItem = (Search) getArguments().getParcelable(POSTER_IMDB_ID);
        }
        PosterDetailViewModel.POSTER_IMDB_ID = mSearchItem.getImdbID();
        mPosterDetailViewModel = ViewModelProviders.of(this).get(PosterDetailViewModel.class);
        mPosterDetailViewModel.getPosterDetailObservable().observe(this, new Observer<PosterDetail>() {
            @Override
            public void onChanged(@Nullable PosterDetail posterDetail) {
                progressbar.setVisibility(View.GONE);
                Picasso picasso = Picasso.with(getActivity());
                if (posterDetail != null) {
                    picasso.load(posterDetail.getPoster()).into(ivPoster);
                    tvTitle.setText(getString(R.string.title) + posterDetail.getTitle() + " \n \n" +
                            getString(R.string.actors) + posterDetail.getActors() + " \n \n " +
                            getString(R.string.genre) + " " + posterDetail.getGenre() + "\n");
                    tvRating.setText(getText(R.string.rating) + posterDetail.getImdbRating());
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
