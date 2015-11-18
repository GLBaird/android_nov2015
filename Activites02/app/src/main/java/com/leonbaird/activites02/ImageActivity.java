package com.leonbaird.activites02;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // get image from intent
        ImageView iv = (ImageView) findViewById(R.id.image_view);
        Uri imageURI = (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);
        iv.setImageURI(imageURI);

    }
}
