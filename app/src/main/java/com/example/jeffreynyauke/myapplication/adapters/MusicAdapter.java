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
import com.example.jeffreynyauke.myapplication.app.Config;
import com.example.jeffreynyauke.myapplication.models.Music;
import com.example.jeffreynyauke.myapplication.models.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jeffrey Nyauke on 11/7/2015.
 */
public class MusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Music> postList;
    private Activity activity;

    public MusicAdapter(Activity activity, List<Music> mpostList) {
        postList=mpostList;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return postList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View viewMusic = inflater.inflate(R.layout.item_music, viewGroup, false);
        viewHolder= new ViewHolderMusic(viewMusic);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {


                ViewHolderMusic viewHolderMusic=(ViewHolderMusic) viewHolder;
                configureMusicView(viewHolderMusic,position);



    }

    private void configureMusicView(ViewHolderMusic viewHolderMusic, int position) {
        Music post=postList.get(position);
        viewHolderMusic.getTitleTextView().setText(post.getTitle());
        viewHolderMusic.getDescriptionTextView().setText(post.getText());
        Glide.with(activity).load(post.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolderMusic.getImageView());
    }



    @Override
    public int getItemCount() {
        return postList.size();
    }


    public void refillAdapter(Music newPost){

        /*add new message chat to list*/
        postList.add(newPost);

        /*refresh view*/
        notifyItemInserted(getItemCount()-1);
    }

    public void refillFirsTimeAdapter(List<Music> newPost){

        /*add new message chat to list*/
        postList.clear();
        postList.addAll(newPost);
        /*refresh view*/
        notifyItemInserted(getItemCount()-1);
    }

    public void cleanUp() {
        postList.clear();
    }


    /*==============ViewHolder===========*/

    /*ViewHolder for Music*/

    public class ViewHolderMusic extends RecyclerView.ViewHolder {

        @BindView(R.id.title) TextView title;
        @BindView(R.id.description) TextView description;
        @BindView(R.id.image) ImageView image;

        public ViewHolderMusic(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTitleTextView() {
            return title;
        }

        public void setTitleTextView(TextView textView) {
            title = textView;
        }

        public TextView getDescriptionTextView() {
            return description;
        }

        public void setDescriptionTextView(TextView textView) {
            description = textView;
        }

        public ImageView getImageView() {
            return image;
        }

        public void setImageView(ImageView imageView) {
            image = imageView;
        }
    }



}
