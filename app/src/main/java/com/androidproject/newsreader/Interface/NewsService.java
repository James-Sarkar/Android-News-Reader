package com.androidproject.newsreader.Interface;

import com.androidproject.newsreader.Model.News;
import com.androidproject.newsreader.Model.Website;

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
