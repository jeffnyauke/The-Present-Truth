package com.example.jeffreynyauke.myapplication.adapters;



import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jeffreynyauke.myapplication.R;
import com.example.jeffreynyauke.myapplication.app.Config;
import com.example.jeffreynyauke.myapplication.models.Post;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jeffrey Nyauke on 11/7/2015.
 */
public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Post> postList;
    private Activity activity;

    public PostAdapter(Activity activity, List<Post> mpostList) {
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

        switch (viewType) {
            case Config.NEWS:
                View viewNews = inflater.inflate(R.layout.item_post_news, viewGroup, false);
                viewHolder= new ViewHolderNews(viewNews);
                break;
            case Config.ARTICLE:
                View viewArticle = inflater.inflate(R.layout.item_post_article, viewGroup, false);
                viewHolder=new ViewHolderArticle(viewArticle);
                break;
            case Config.AUTHOR:
                View viewAuthor = inflater.inflate(R.layout.item_post_author, viewGroup, false);
                viewHolder= new ViewHolderAuthor(viewAuthor);
                break;
            case Config.AD:
                View viewAd = inflater.inflate(R.layout.item_advertisement, viewGroup, false);
                viewHolder=new ViewHolderAd(viewAd);
                break;
            default:
                View viewDefault = inflater.inflate(R.layout.item_post_news, viewGroup, false);
                viewHolder= new ViewHolderNews(viewDefault);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()){
            case Config.NEWS:
                ViewHolderNews viewHolderNews=(ViewHolderNews)viewHolder;
                configureNewsView(viewHolderNews,position);
                break;
            case Config.ARTICLE:
                ViewHolderArticle viewHolderArticle=(ViewHolderArticle)viewHolder;
                configureArticleView(viewHolderArticle,position);
                break;
            case Config.AUTHOR:
                ViewHolderAuthor viewHolderAuthor=(ViewHolderAuthor)viewHolder;
                configureAuthorView(viewHolderAuthor,position);
                break;
            case Config.AD:
                ViewHolderAd viewHolderAd=(ViewHolderAd)viewHolder;
                configureAdView(viewHolderAd,position);
                break;
            default:
                ViewHolderNews viewHolderNews1=(ViewHolderNews)viewHolder;
                configureNewsView(viewHolderNews1,position);
                break;
        }


    }

    private void configureNewsView(ViewHolderNews viewHolderNews, int position) {
        Post post=postList.get(position);
        viewHolderNews.getTitleTextView().setText(post.getTitle());
        viewHolderNews.getTextTextView().setText(post.getText());
        viewHolderNews.getTimestampTextView().setText(post.getTimeStamp());
        Glide.with(activity).load(post.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolderNews.getImageView());
    }

    private void configureArticleView(ViewHolderArticle viewHolderArticle, int position) {
        Post post=postList.get(position);
        viewHolderArticle.getTitleTextView().setText(post.getTitle());
        viewHolderArticle.getTextTextView().setText(post.getText());
        viewHolderArticle.getTimestampTextView().setText(post.getTimeStamp());
        Glide.with(activity).load(post.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolderArticle.getImageView());
    }

    private void configureAuthorView(ViewHolderAuthor viewHolderAuthor, int position) {
        Post post=postList.get(position);
        viewHolderAuthor.getTitleTextView().setText(post.getTitle());
        viewHolderAuthor.getTextTextView().setText(post.getText());
        viewHolderAuthor.getTimestampTextView().setText(post.getTimeStamp());
        viewHolderAuthor.getAuthorTextView().setText(post.getAuthor());
        Log.e("Firebase image",post.getImage());
        Glide.with(activity).load(post.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolderAuthor.getImageView());
    }

    private void configureAdView(ViewHolderAd viewHolderAd, int position) {
        Post post=postList.get(position);
        viewHolderAd.getTitleTextView().setText(post.getTitle());
        viewHolderAd.getTextTextView().setText(post.getText());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    public void refillAdapter(Post newPost){

        /*add new message chat to list*/
        postList.add(newPost);

        /*refresh view*/
        notifyItemInserted(getItemCount()-1);
    }

    public void refillFirsTimeAdapter(List<Post> newPost){

        /*add new message chat to list*/
        postList.clear();
        postList.addAll(newPost);
        /*refresh view*/
        notifyItemInserted(getItemCount()-1);
    }

    public void cleanUp() {
        postList.clear();
    }

    public List<Post> getCurrentPosts() {
        return postList;
    }


    /*==============ViewHolder===========*/

    /*ViewHolder for News*/

    public class ViewHolderNews extends RecyclerView.ViewHolder {

        @BindView(R.id.title) TextView title;
        @BindView(R.id.text) TextView text;
        @BindView(R.id.timestamp) TextView timestamp;
        @BindView(R.id.image) ImageView image;

        public ViewHolderNews(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTitleTextView() {
            return title;
        }

        public void setTitleTextView(TextView textView) {
            title = textView;
        }

        public TextView getTextTextView() {
            return text;
        }

        public void setTextTextView(TextView textView) {
            text = textView;
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

        public void setImageView(ImageView imageView) {
            image = imageView;
        }
    }


    /*ViewHolder for Article*/
    public class ViewHolderArticle extends RecyclerView.ViewHolder {

        @BindView(R.id.title) TextView title;
        @BindView(R.id.text) TextView text;
        @BindView(R.id.timestamp) TextView timestamp;
        @BindView(R.id.image) ImageView image;

        public ViewHolderArticle(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTitleTextView() {
            return title;
        }

        public void setTitleTextView(TextView textView) {
            title = textView;
        }

        public TextView getTextTextView() {
            return text;
        }

        public void setTextTextView(TextView textView) {
            text = textView;
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

        public void setImageView(ImageView imageView) {
            image = imageView;
        }
    }

    /*ViewHolder for Author*/
    public class ViewHolderAuthor extends RecyclerView.ViewHolder {

        @BindView(R.id.title) TextView title;
        @BindView(R.id.text) TextView text;
        @BindView(R.id.timestamp) TextView timestamp;
        @BindView(R.id.author) TextView author;
        @BindView(R.id.image) ImageView image;

        public ViewHolderAuthor(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTitleTextView() {
            return title;
        }

        public void setTitleTextView(TextView textView) {
            title = textView;
        }

        public TextView getTextTextView() {
            return text;
        }

        public void setTextTextView(TextView textView) {
            text = textView;
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

        public void setImageView(ImageView imageView) {
            image = imageView;
        }

        public TextView getAuthorTextView() {
            return author;
        }

        public void setAuthorTextView(TextView textView) {
            author = textView;
        }
    }

    /*ViewHolder for Ad*/
    public class ViewHolderAd extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.text)
        TextView text;

        public ViewHolderAd(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTitleTextView() {
            return title;
        }

        public void setTitleTextView(TextView textView) {
            title = textView;
        }

        public TextView getTextTextView() {
            return text;
        }

        public void setTextTextView(TextView textView) {
            text = textView;
        }
    }
}
