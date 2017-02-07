package com.example.jeffreynyauke.myapplication.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeffreynyauke.myapplication.R;
import com.example.jeffreynyauke.myapplication.adapters.PostAdapter;
import com.example.jeffreynyauke.myapplication.app.Config;
import com.example.jeffreynyauke.myapplication.models.Post;
import com.example.jeffreynyauke.myapplication.models.Post_Ad;
import com.example.jeffreynyauke.myapplication.models.Post_Article;
import com.example.jeffreynyauke.myapplication.models.Post_Author;
import com.example.jeffreynyauke.myapplication.models.Post_News;
import com.example.jeffreynyauke.myapplication.providers.Post_AdProvider;
import com.example.jeffreynyauke.myapplication.providers.Post_ArticleProvider;
import com.example.jeffreynyauke.myapplication.providers.Post_AuthorProvider;
import com.example.jeffreynyauke.myapplication.providers.Post_NewsProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Jeffrey Nyauke on 1/31/2017.
 */

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    @BindView(R.id.fragment_recycler)
    RecyclerView recyclerView;
    private Unbinder unbinder;
    private PostAdapter postAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, view);

        // Set recyclerView and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(false);

        // Initialize adapter
        List<Post> emptyMessageChat=new ArrayList<Post>();
        postAdapter=new PostAdapter(getActivity(), emptyMessageChat);

        // Set adapter to recyclerView
        recyclerView.setAdapter(postAdapter);

        //Initialize firebase for these posts
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(Config.FIREBASE_POSTS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                Log.e("Firebase",dataSnapshot.toString());

                Post post = dataSnapshot.getValue(Post.class);
                postAdapter.refillAdapter(post);
                Log.e("Firebase","onChildAdded");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  view;

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * Saving current games loaded to restore them if the view lifecycle is restarted
     * @param outState with current games loaded parcel
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        List<Post> currentPosts = postAdapter.getCurrentPosts();

        //Parcelable currentPostsParcel = Parcels.wrap(currentGames);
        //outState.putParcelable(EXTRA_CURRENT_GAMES_LOADED, currentPostsParcel);
    }

    /**
     * Trying to restore games stored when lifecycle got stopped
     * @param savedInstanceState
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            /*Parcelable safeGamesLoadedParcel = savedInstanceState.getParcelable(EXTRA_CURRENT_GAMES_LOADED);
            List<Game> safeGamesLoaded = Parcels.unwrap(safeGamesLoadedParcel);
            gameListPresenter.updateViewWithSafeGames(safeGamesLoaded);*/
        }
    }


}