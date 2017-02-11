package com.example.jeffreynyauke.myapplication.adapters;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jeffreynyauke.myapplication.R;
import com.example.jeffreynyauke.myapplication.models.Music;
import com.example.jeffreynyauke.myapplication.models.Video;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jeffrey Nyauke on 11/7/2015.
 */
public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Video> videoList;
    private Activity activity;

    public VideoAdapter(Activity activity, List<Video> videoList) {
        this.videoList=videoList;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return videoList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View viewVideo = inflater.inflate(R.layout.item_video, viewGroup, false);
        viewHolder= new ViewHolderVideo(viewVideo);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderVideo viewHolderVideo=(ViewHolderVideo) viewHolder;
        configureVideoView(viewHolderVideo,position);

    }

    private void configureVideoView(ViewHolderVideo viewHolderVideo, int position) {
        Video video=videoList.get(position);
        viewHolderVideo.getTitleTextView().setText(video.getTitle());
        viewHolderVideo.getTimestampTextView().setText(video.getTimeStamp()+", "+video.getSize());
        Glide.with(activity).load(R.drawable.about_page_button_dark)
                .thumbnail(0.2f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolderVideo.getImageView());
    }



    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public void refillAdapter(Video newVideo){

        /*add new message chat to list*/
        videoList.add(newVideo);

        /*refresh view*/
        notifyItemInserted(getItemCount()-1);
    }

    public void refillFirsTimeAdapter(List<Video> newVideo){

        /*add new message chat to list*/
        videoList.clear();
        videoList.addAll(newVideo);
        /*refresh view*/
        notifyItemInserted(getItemCount()-1);
    }

    public void cleanUp() {
        videoList.clear();
    }


    /*==============ViewHolder===========*/

    /*ViewHolder for Video*/

    public class ViewHolderVideo extends RecyclerView.ViewHolder {

        @BindView(R.id.title) TextView title;
        @BindView(R.id.timestamp) TextView timestamp;
        @BindView(R.id.image) ImageView image;

        public ViewHolderVideo(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTitleTextView() {
            return title;
        }

        public void setTitleTextView(TextView textView) {
            title = textView;
        }

        public TextView getTimestampTextView() {
            return timestamp;
        }

        public void setTimestampTextView(TextView textView) {
            timestamp = textView;
        }

        public ImageView getImageView() {
            return image;
        }

        public void setImageView(TextView textView) {
            timestamp = textView;
        }

    }



}
