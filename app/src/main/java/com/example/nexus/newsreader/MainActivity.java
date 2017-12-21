package com.example.nexus.newsreader;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.nexus.newsreader.Adapter.ListSourceAdapter;
import com.example.nexus.newsreader.Interface.NewsService;

public class MainActivity extends AppCompatActivity {

    RecyclerView websiteList;

    RecyclerView.LayoutManager layoutManager;

    NewsService mNewsService;

    ListSourceAdapter adapter;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
