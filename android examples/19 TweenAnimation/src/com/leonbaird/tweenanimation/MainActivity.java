package com.leonbaird.tweenanimation;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button aniButton = (Button) findViewById(R.id.animationButton);
		aniButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.animation_button);
				aniButton.startAnimation(fadeIn);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
