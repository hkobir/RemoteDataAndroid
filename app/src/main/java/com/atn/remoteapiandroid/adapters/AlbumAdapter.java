package com.atn.remoteapiandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.atn.remoteapiandroid.R;
import com.atn.remoteapiandroid.models.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private List<Album> albumList;
    private Context context;

    public AlbumAdapter(Context context) {
        this.context = context;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;

    }

    @NonNull
    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.ViewHolder holder, int position) {
        holder.bind(albumList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView coverIV;
        CircleImageView profileIV;
        AppCompatTextView titleTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coverIV = itemView.findViewById(R.id.coverIV);
            profileIV = itemView.findViewById(R.id.circleImageView);
            titleTv = itemView.findViewById(R.id.titleTV);
        }

        public void bind(Album album) {
            titleTv.setText(album.getTitle());
            Picasso.get().load(album.getUrl()).into(coverIV);
            Picasso.get().load(album.getThumbnailUrl()).into(profileIV);

        }
    }
}
