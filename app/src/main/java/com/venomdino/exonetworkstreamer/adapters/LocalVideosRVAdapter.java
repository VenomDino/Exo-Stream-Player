package com.venomdino.exonetworkstreamer.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.venomdino.exonetworkstreamer.R;
import com.venomdino.exonetworkstreamer.activities.PlayerActivity;
import com.venomdino.exonetworkstreamer.helpers.CustomMethods;
import com.venomdino.exonetworkstreamer.models.VideoInfoModel;

import java.util.List;

@UnstableApi
public class LocalVideosRVAdapter extends RecyclerView.Adapter<LocalVideosRVAdapter.MyCustomViewHolder> {

    private final Activity activity;
    private final List<VideoInfoModel> videos;

    public LocalVideosRVAdapter(Activity activity, List<VideoInfoModel> videos){
        this.activity = activity;
        this.videos = videos;
    }

    @NonNull
    @Override
    public MyCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.sample_local_videos_item, parent, false);
        return new MyCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCustomViewHolder holder, int position) {

        VideoInfoModel video = videos.get(holder.getBindingAdapterPosition());

        Glide.with(activity.getApplicationContext())
                .load(video.getVideoPath())
                .placeholder(R.drawable.sample_thumbnail)
                .into(holder.thumbnailView);

        holder.videoTitleTV.setText(video.getVideoTitle());
        holder.videoDurationTV.setText(CustomMethods.formatDuration(video.getVideoDuration()));
        holder.videoSizeTV.setText(CustomMethods.formatFileSize(video.getVideoSize()));
        holder.modifiedDateTV.setText(CustomMethods.formatModifiedDate(video.getModifiedDate()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, PlayerActivity.class);
            intent.putExtra("mediaFileUrlOrPath", video.getVideoPath());
            intent.putExtra("mediaFileName", video.getVideoTitle());
            intent.putExtra("isLocalFile", true);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class MyCustomViewHolder extends RecyclerView.ViewHolder{

        ImageView thumbnailView;
        TextView videoTitleTV, videoSizeTV, modifiedDateTV, videoDurationTV;

        public MyCustomViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnailView = itemView.findViewById(R.id.thumbnailView);
            videoTitleTV = itemView.findViewById(R.id.videoTitleTV);
            videoSizeTV = itemView.findViewById(R.id.videoSizeTV);
            modifiedDateTV = itemView.findViewById(R.id.modifiedDateTV);
            videoDurationTV = itemView.findViewById(R.id.videoDurationTV);
        }
    }
}
