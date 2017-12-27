package com.androidproject.newsreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidproject.newsreader.Common.ISO8601DateParser;
import com.androidproject.newsreader.Interface.ItemClickListener;
import com.androidproject.newsreader.Model.Article;
import com.androidproject.newsreader.NewsArticleDetails;
import com.androidproject.newsreader.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by James Sarkar.
 */

class ListNewsViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private ItemClickListener itemClickListener;

    TextView articleTitle;

    RelativeTimeTextView articleTime;

    CircleImageView articleImage;

    ListNewsViewHolder(View itemView) {
        super(itemView);

        articleTitle = (TextView) itemView.findViewById(R.id.article_title);
        articleTime = (RelativeTimeTextView) itemView.findViewById(R.id.article_time);
        articleImage = (CircleImageView) itemView.findViewById(R.id.article_image);

        itemView.setOnClickListener(this);
    }

    void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder> {

    private List<Article> articleList;

    private Context context;

    public ListNewsAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public ListNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.news_layout, parent, false);

        return new ListNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListNewsViewHolder holder, int position) {
        Picasso.with(context)
                .load(articleList.get(position).getUrlToImage())
                .into(holder.articleImage);

        if (articleList.get(position).getTitle().length() > 65) {
            holder.articleTitle.setText(articleList.get(position).getTitle().substring(0, 65) + "...");
        } else {
            holder.articleTitle.setText(articleList.get(position).getTitle());
        }

        Date date = null;

        try {
            String dateInISO8601 = articleList.get(position).getPublishedAt();
            date = ISO8601DateParser.parse(dateInISO8601);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            holder.articleTime.setReferenceTime(date.getTime());
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent articleDetail = new Intent(context, NewsArticleDetails.class);
                articleDetail.putExtra("webURL", articleList.get(position).getUrl());

                context.startActivity(articleDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
