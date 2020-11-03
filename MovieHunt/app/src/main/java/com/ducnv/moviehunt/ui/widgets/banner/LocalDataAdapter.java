package com.ducnv.moviehunt.ui.widgets.banner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.ducnv.moviehunt.R;

public class LocalDataAdapter extends RecyclerView.Adapter<LocalDataAdapter.ViewHolder> {
    private int[] images = {R.drawable.image_banner1, R.drawable.image_banner2, R.drawable.image_banner3,
            R.drawable.image_banner4, R.drawable.image_banner5};

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
//
        }
    }
}
