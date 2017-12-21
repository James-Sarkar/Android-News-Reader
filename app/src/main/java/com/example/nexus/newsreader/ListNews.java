package com.example.nexus.newsreader;

import android.app.AlertDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.nexus.newsreader.Adapter.ListNewsAdapter;
import com.example.nexus.newsreader.Common.Common;
import com.example.nexus.newsreader.Interface.NewsService;
import com.example.nexus.newsreader.Model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNews extends AppCompatActivity {

    KenBurnsView kenBurnsView;

    DiagonalLayout diagonalLayout;

    AlertDialog dialog;

    NewsService mNewsService;

    TextView topAuthor, topTitle;

    SwipeRefreshLayout swipeRefreshLayout;

    String source = "", sortBy = "", webHotUrl = "";

    ListNewsAdapter listNewsAdapter;

    RecyclerView listNews;

    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        mNewsService = Common.getNewsService();

        dialog = new SpotsDialog(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source, true);
            }
        });

        diagonalLayout = (DiagonalLayout) findViewById(R.id.diagonal_layout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        kenBurnsView = (KenBurnsView) findViewById(R.id.top_image);
        topAuthor = (TextView) findViewById(R.id.top_author);
        topTitle = (TextView) findViewById(R.id.top_title);

        if (getIntent() != null) {
            source = getIntent().getStringExtra("source");
            sortBy = getIntent().getStringExtra("sortBy");

            if (!source.isEmpty() && !sortBy.isEmpty()) {
                loadNews(source, false);
            }
        }
    }

    private void loadNews(String source, boolean isRefreshed) {

        if (!isRefreshed) {
            dialog.show();
            mNewsService.getNewestArticles(Common.getApiUrl(source, sortBy, Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {

                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });
        }
    }
}
