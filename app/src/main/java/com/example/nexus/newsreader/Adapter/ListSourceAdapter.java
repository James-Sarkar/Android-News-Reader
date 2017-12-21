package com.example.nexus.newsreader.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nexus.newsreader.Interface.ItemClickListener;
import com.example.nexus.newsreader.Model.Website;
import com.example.nexus.newsreader.R;

import de.hdodenhof.circleimageview.CircleImageView;

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

    public ListSourceAdapter(Context context, Website website) {
        this.context = context;
        this.website = website;
    }

    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout, parent, false);
        return new ListSourceViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ListSourceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return website.getSources().size();
    }
}
