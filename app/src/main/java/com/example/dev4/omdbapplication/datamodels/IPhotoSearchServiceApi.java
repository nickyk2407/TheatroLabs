package com.example.dev4.omdbapplication.datamodels;

import com.example.dev4.omdbapplication.search.PosterDetail;
import com.example.dev4.omdbapplication.search.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IPhotoSearchServiceApi {

    //http://www.omdbapi.com/?s=comedy&apikey=be6f8e26&type=movie

    String SEARCH_PARAM_NAME = "s";
    String INFO_PARAM_NAME = "i";
    String API_KEY_PARAM_NAME = "apikey";
    String PAGE_PARAM_NAME = "page";

    @GET("./")
    Observable<SearchResponse> search(
            @Query(API_KEY_PARAM_NAME) String apiKey,
            @Query(SEARCH_PARAM_NAME) String s,
            @Query(PAGE_PARAM_NAME) int page);

    @GET("./")
    Observable<PosterDetail> getPosterDetail(
            @Query(API_KEY_PARAM_NAME) String apiKey,
            @Query(INFO_PARAM_NAME) String i);
}
