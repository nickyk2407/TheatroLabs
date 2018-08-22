package com.example.dev4.omdbapplication.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dev4.omdbapplication.R;
import com.example.dev4.omdbapplication.apiutil.NetworkState;
import com.example.dev4.omdbapplication.search.Search;
import com.example.dev4.omdbapplication.viewmodel.PosterViewModel;
import com.squareup.picasso.Picasso;

public class PhotoViewAdapter extends PagedListAdapter<Search, RecyclerView.ViewHolder> {
    private final PosterViewModel viewModel;
    private NetworkState mNetworkState;

    public PhotoViewAdapter(Context context, PosterViewModel viewModel) {
        super(Search.DIFF_CALL);
        this.viewModel = viewModel;
    }

    @Override
    public PhotoViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photos_item_list, parent, false);
        return new ViewHolder(v);
    }


    public void setNetworkState(NetworkState networkState) {
        NetworkState prevState = networkState;
        boolean wasLoading = isLoadingData();
        mNetworkState = networkState;
        boolean willLoad = isLoadingData();
        if (wasLoading != willLoad) {
            if (wasLoading) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        }
    }

    public boolean isLoadingData() {
        return (mNetworkState != null && mNetworkState != NetworkState.LOADED);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Search movie = getItem(position);
        viewHolder.bind(movie);
    }

    public void clearAll() {
        final int size = getItemCount();
        if (size > 0) {
            notifyItemRangeRemoved(0, getItemCount());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context mContext;
        private final ImageView mImage;
        private final TextView mTitle;

        public ViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mImage = view.findViewById(R.id.display_pic);
            mTitle = view.findViewById(R.id.title);
            view.setOnClickListener(this);
        }

        public void bind(Search movie) {
            Picasso picasso = Picasso.with(mContext);
            picasso.load(movie.getPoster()).into(mImage);
            mTitle.setText(movie.getTitle().trim());
        }

        @Override
        public void onClick(View v) {
            viewModel.openPosterDetail(getItem(getAdapterPosition()));
        }
    }
}
