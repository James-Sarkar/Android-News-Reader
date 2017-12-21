package com.example.nexus.newsreader.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nexus.newsreader.Common.Common;
import com.example.nexus.newsreader.Interface.IconBetterIdeaService;
import com.example.nexus.newsreader.Interface.ItemClickListener;
import com.example.nexus.newsreader.Model.IconBetterIdea;
import com.example.nexus.newsreader.Model.Website;
import com.example.nexus.newsreader.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James Sarkar.
 */

class ListSourceViewHolder extends RecyclerView.ViewHolder
implements View.OnClickListener {

    ItemClickListener itemClickListener;

    TextView sourceTitle;

    CircleImageView sourceImage;

    public ListSourceViewHolder(View itemView) {
        super(itemView);

        sourceImage = (CircleImageView) itemView.findViewById(R.id.source_image);
        sourceTitle = (TextView) itemView.findViewById(R.id.source_name);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder> {

    private Context context;

    private Website website;

    private IconBetterIdeaService mIconBetterIdeaService;

    public ListSourceAdapter(Context context, Website website) {
        this.context = context;
        this.website = website;

        mIconBetterIdeaService = Common.getIconService();
    }

    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout, parent, false);
        return new ListSourceViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, int position) {

        StringBuilder iconBetterAPI = new StringBuilder("https://icons.better-idea.org/allicons.json?url=");
        iconBetterAPI.append(website.getSources().get(position).getUrl());

        mIconBetterIdeaService.getIconUrl(iconBetterAPI.toString())
                .enqueue(new Callback<IconBetterIdea>() {
                    @Override
                    public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
                        if (response.body().getIcons().size() > 0) {
                            Picasso.with(context)
                                    .load(response.body().getIcons().get(0).getUrl())
                                    .into(holder.sourceImage);
                        }
                    }

                    @Override
                    public void onFailure(Call<IconBetterIdea> call, Throwable t) {

                    }
                });

        holder.sourceTitle.setText(website.getSources().get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return website.getSources().size();
    }
}
