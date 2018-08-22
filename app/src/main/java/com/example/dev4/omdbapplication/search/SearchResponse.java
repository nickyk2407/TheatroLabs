
package com.example.dev4.omdbapplication.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("Search")
    @Expose
    private List<Search> Search;

    private boolean Response;
    private String Error;

    public String getError() {
        return Error;
    }

    public boolean isResponse() {
        return Response;
    }

    public List<Search> getSearch() {
        return Search;
    }
}
