package com.example.nexus.newsreader.Interface;

import com.example.nexus.newsreader.Model.News;
import com.example.nexus.newsreader.Model.Website;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by James Sarkar.
 */

public interface NewsService {

    @GET("v1/sources?language=en")
    Call<Website> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);
}
