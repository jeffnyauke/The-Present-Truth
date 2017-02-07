package com.example.jeffreynyauke.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.jeffreynyauke.myapplication.app.Config;
import com.example.jeffreynyauke.myapplication.models.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostActivity extends AppCompatActivity {

    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.text) EditText text;
    @BindView(R.id.author) EditText author;
    @BindView(R.id.image) EditText image;
    @BindView(R.id.url) EditText url;
    @BindView(R.id.type) EditText type;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance().getReference(Config.FIREBASE_POSTS);
    }
    @OnClick(R.id.button_send)
    public void sendPost(Button button) {

        if(!title.getText().toString().isEmpty()){

            Post send = new Post();
            send.setTitle(title.getText().toString().trim());
            send.setAuthor(author.getText().toString().trim());
            send.setText(text.getText().toString().trim());
            send.setImage(image.getText().toString().trim());
            send.setUrl(url.getText().toString().trim());
            send.setType(Integer.valueOf(type.getText().toString().trim()));

            database.push().setValue(send);

            // Clear text
            title.setText("");
            author.setText("");
            text.setText("");
            image.setText("");
            url.setText("");
            type.setText("");

        }

    }
}
