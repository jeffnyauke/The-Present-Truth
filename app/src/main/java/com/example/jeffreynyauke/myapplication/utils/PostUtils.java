package com.example.jeffreynyauke.myapplication.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;

import com.example.jeffreynyauke.myapplication.models.Post;

/**
 * Created by Jeffrey Nyauke on 2/2/2017.
 */

public class PostUtils {
    public void navigateToGameDetails(Post post, View viewToshare) {

       /* Intent detailsActivityIntent = new Intent(activityContext, DetailsActivity.class);
        detailsActivityIntent.putExtra(DetailsActivity.GAME_EXTRA, Parcels.wrap(game));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) activityContext, viewToshare, "sharedImage");

        ActivityCompat.startActivity((Activity) activityContext, detailsActivityIntent, options.toBundle());*/
    }

}
