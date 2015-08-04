package com.phongbm.image;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;

import com.phongbm.ahihi.R;

public class ImageControl extends AppCompatActivity {
    public static final String EXTRA_IMAGE = "ImageControl:image";

    public static void launch(Activity activity, View transitionView, String url) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, transitionView, EXTRA_IMAGE);
        Intent intent = new Intent(activity, ImageControl.class);
        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
        setContentView(R.layout.activity_image_control);

        ImageView image = (ImageView) findViewById(R.id.imgImage);
        ViewCompat.setTransitionName(image, EXTRA_IMAGE);
        supportPostponeEnterTransition();

        //ImageView imgImage = (ImageView) findViewById(R.id.imgImage);
        //imgImage.setImageURI(Uri.parse(getIntent().getStringExtra(EXTRA_IMAGE)));
    }


}