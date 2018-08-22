package com.example.dev4.omdbapplication.search;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.util.DiffUtil;

public class Search implements Parcelable {
    private String Title;
    private String Year;
    private String imdbID;
    private String Type;
    private String Poster;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }


    public static final DiffUtil.ItemCallback<Search> DIFF_CALL = new DiffUtil.ItemCallback<Search>() {
        @Override
        public boolean areItemsTheSame(Search oldItem, Search newItem) {
            return oldItem.imdbID == newItem.imdbID;
        }

        @Override
        public boolean areContentsTheSame(Search oldItem, Search newItem) {
            return oldItem.imdbID == newItem.imdbID;
        }
    };


    public Search(Parcel in) {
        this.Title = in.readString();
        this.Year = in.readString();
        this.imdbID = in.readString();
        this.Type = in.readString();
        this.Poster = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.Title);
        parcel.writeString(this.Year);
        parcel.writeString(this.imdbID);
        parcel.writeString(this.Type);
        parcel.writeString(this.Poster);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
}
