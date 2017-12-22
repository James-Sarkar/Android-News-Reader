package com.example.nexus.newsreader;

import android.app.AlertDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nexus.newsreader.Adapter.ListSourceAdapter;
import com.example.nexus.newsreader.Common.Common;
import com.example.nexus.newsreader.Interface.NewsService;
import com.example.nexus.newsreader.Model.Website;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView websiteList;

    RecyclerView.LayoutManager layoutManager;

    NewsService mNewsService;

    ListSourceAdapter adapter;

    AlertDialog dialog;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Cache
        Paper.init(this);

        // Initialize News Service
        mNewsService = Common.getNewsService();

        // Initialize View
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSource(true);
            }
        });

        websiteList = (RecyclerView) findViewById(R.id.sources_list);
        websiteList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        websiteList.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebsiteSource(false);
    }

    private void loadWebsiteSource(boolean isRefreshed) {

        if (!isRefreshed) {
            String cache = Paper.book().read("cache");
            if (cache != null && !cache.isEmpty()) {
                Website website = new Gson().fromJson(cache, Website.class); //convert cache from json to website object
                adapter = new ListSourceAdapter(getBaseContext(), website);
                adapter.notifyDataSetChanged();
                websiteList.setAdapter(adapter);
            } else {
                dialog.show();

                mNewsService.getSources().enqueue(new Callback<Website>() {
                    @Override
                    public void onResponse(Call<Website> call, Response<Website> response) {
                        adapter = new ListSourceAdapter(getBaseContext(), response.body());
                        adapter.notifyDataSetChanged();
                        websiteList.setAdapter(adapter);

                        // Save to cache
                        Paper.book().write("cache", new Gson().toJson(response.body()));

                    }

                    @Override
                    public void onFailure(Call<Website> call, Throwable t) {

                    }
                });
            }
        } else {
            dialog.show();

            mNewsService.getSources().enqueue(new Callback<Website>() {
                @Override
                public void onResponse(Call<Website> call, Response<Website> response) {
                    adapter = new ListSourceAdapter(getBaseContext(), response.body());
                    adapter.notifyDataSetChanged();
                    websiteList.setAdapter(adapter);

                    // Save to cache
                    Paper.book().write("cache", new Gson().toJson(response.body()));

                    // Dismiss refresh progress
                    swipeRefreshLayout.setRefreshing(false);
                    dialog.dismiss();

                }

                @Override
                public void onFailure(Call<Website> call, Throwable t) {

                }
            });
        }
    }
}
