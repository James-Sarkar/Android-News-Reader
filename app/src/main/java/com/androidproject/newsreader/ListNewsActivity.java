package com.androidproject.newsreader;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.androidproject.newsreader.Adapter.ListNewsAdapter;
import com.androidproject.newsreader.Common.Common;
import com.androidproject.newsreader.Interface.NewsService;
import com.androidproject.newsreader.Model.Article;
import com.androidproject.newsreader.Model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNewsActivity extends AppCompatActivity {

    private KenBurnsView kenBurnsView;

    private DiagonalLayout diagonalLayout;

    private AlertDialog dialog;

    private NewsService mNewsService;

    private TextView topAuthor, topTitle;

    private SwipeRefreshLayout swipeRefreshLayout;

    private String source = "", webHotUrl = "";

    private ListNewsAdapter listNewsAdapter;

    private RecyclerView listNews;

    private RecyclerView.LayoutManager layoutManager;

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
                Intent articleDetail = new Intent(getBaseContext(), NewsArticleDetailsActivity.class);
                articleDetail.putExtra("webURL", webHotUrl);
                startActivity(articleDetail);
            }
        });

        kenBurnsView = (KenBurnsView) findViewById(R.id.top_image);
        topAuthor = (TextView) findViewById(R.id.top_author);
        topTitle = (TextView) findViewById(R.id.top_title);

        listNews = (RecyclerView) findViewById(R.id.list_news);
        listNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listNews.setLayoutManager(layoutManager);

        if (getIntent() != null) {
            source = getIntent().getStringExtra("source");

            if ((source != null)) {
                loadNews(source, false);
            }
        }
    }

    private void loadNews(String source, boolean isRefreshed) {
        dialog.show();

        mNewsService.getNewestArticles(Common.getApiUrl(source, Common.API_KEY))
                .enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        dialog.dismiss();

                        Picasso.with(getBaseContext())
                                .load(response.body().getArticles().get(0).getUrlToImage())
                                .into(kenBurnsView);

                        topTitle.setText(response.body().getArticles().get(0).getTitle());
                        topAuthor.setText(response.body().getArticles().get(0).getAuthor());
                        webHotUrl = response.body().getArticles().get(0).getUrl();

                        // Load all articles
                        List<Article> remainingArticles = response.body().getArticles();
                        remainingArticles.remove(0);

                        listNewsAdapter = new ListNewsAdapter(remainingArticles, getBaseContext());
                        listNewsAdapter.notifyDataSetChanged();

                        listNews.setAdapter(listNewsAdapter);
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {

                    }
                });

        if (isRefreshed) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
