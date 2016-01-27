package com.nytimes.news.interfaces;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Erick Flores
 */
public interface NyTimesNewsService {

    /**
     * Method to get news from the NewYorkTimes
     * @param limit The maximum number of items per request (1-20)
     * @param offset The desired start index starting from 0
     */
    @GET("all.json?api-key=733afc6ddff242156ee645286cdb394b:2:74102822")
    Call<ResponseBody> getNews(@Query("limit") int limit, @Query("offset") long offset);

}
