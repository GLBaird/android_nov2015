package com.example.frameanimation;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ImageView iv = (ImageView) findViewById(R.id.imageView1);
		iv.setBackgroundResource(R.animator.animation);
		iv.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AnimationDrawable animation = (AnimationDrawable) iv.getBackground();
				animation.start();
			}
		} );
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
