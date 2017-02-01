package com.example.jeffreynyauke.myapplication.providers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeffreynyauke.myapplication.R;
import com.example.jeffreynyauke.myapplication.models.Post_Author;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Jeffrey Nyauke on 2/1/2017.
 */

public class Post_AuthorProvider extends ItemViewProvider<Post_Author, Post_AuthorProvider.TextHolder> {

    static class TextHolder extends RecyclerView.ViewHolder {
        @NonNull
        private final TextView text;

        TextHolder(@NonNull View itemView) {
            super(itemView);
            this.text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    @NonNull @Override
    protected TextHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_post_news, parent, false);
        return new TextHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull TextHolder holder, @NonNull Post_Author postNews) {
        holder.text.setText("hello: " + postNews.getText());
        Log.d("demo", "position: " + getPosition(holder));
        Log.d("demo", "adapter: " + getAdapter().toString());
    }
}