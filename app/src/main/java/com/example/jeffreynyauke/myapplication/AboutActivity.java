package com.example.jeffreynyauke.myapplication;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.Items;
import me.drakeet.support.about.AbsAboutActivity;
import me.drakeet.support.about.Card;
import me.drakeet.support.about.Category;
import me.drakeet.support.about.Contributor;
import me.drakeet.support.about.License;
import me.drakeet.support.about.Line;

/**
 * Created by Jeffrey Nyauke on 1/31/2017.
 */

public class AboutActivity extends AbsAboutActivity {

    @SuppressLint("SetTextI18n") @Override
    protected void onCreateHeader(ImageView icon, TextView slogan, TextView version) {
        setHeaderContentColor(getResources().getColor(R.color.textColorPrimary));
        setNavigationIcon(R.drawable.ic_close_black_24dp);
        icon.setImageResource(R.mipmap.ic_launcher);
        slogan.setText("The Present Truth");
        version.setText("v" + BuildConfig.VERSION_NAME);
    }

    @Override protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category("About"));
        items.add(new Card(getString(R.string.card_content), "Donate"));

        items.add(new Line());

        items.add(new Category("Developers"));
        items.add(new Contributor(R.drawable.ic_close_black_24dp, "Jeffrey Nyauke", "Developer & designer"));
        items.add(new Contributor(R.drawable.ic_close_black_24dp, "Vic Preston", "Developer"));

        items.add(new Line());

        items.add(new Category("Open Source Licenses"));
        items.add(new License("MultiType", "drakeet", License.APACHE_2,
                "https://github.com/drakeet/MultiType"));
        items.add(new License("about-page", "drakeet", License.APACHE_2,
                "https://github.com/drakeet/about-page"));
    }

    @Override protected void onActionClick(View action) {

    }
}
