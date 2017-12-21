package com.example.nexus.newsreader.Interface;

import com.example.nexus.newsreader.Model.Website;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by James Sarkar.
 */

public interface NewsService {

    @GET("v1/sources?language=en")
    Call<Website> getSources();
}
