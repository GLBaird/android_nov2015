package com.leonbaird.implicitintents;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_view);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		Uri imageURI = (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);
		
		imageView.setImageURI(imageURI);
	}
}
