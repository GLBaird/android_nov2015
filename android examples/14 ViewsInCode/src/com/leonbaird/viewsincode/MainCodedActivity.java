package com.leonbaird.viewsincode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainCodedActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// create layout Params for main view and others
		LinearLayout.LayoutParams lpFull   = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.MATCH_PARENT
		);
		LinearLayout.LayoutParams lpNormal = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT
		);
		
		// create linear layout
		LinearLayout mainContainer = new LinearLayout(this);
		mainContainer.setLayoutParams(lpFull);
		mainContainer.setOrientation(LinearLayout.VERTICAL);
		mainContainer.setPadding(20, 20, 20, 20);
		
		// make text
		TextView title = new TextView(this);
		title.setLayoutParams(lpNormal);
		title.setText("Welcome to the world of code!");
		title.setTextSize(30);
		title.setTextColor(Color.parseColor("#661000"));
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		
		// layout params for view:
		LinearLayout.LayoutParams lpForIfno = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 2);
		lpForIfno.setMargins(0, 15, 0, 30);
		
		// make view
		View line = new View(this);
		line.setLayoutParams(lpForIfno);
		line.setBackgroundColor(Color.BLACK);
		
		// make an image view
		ImageView scorpio = new ImageView(this);
		scorpio.setLayoutParams(lpFull);
		scorpio.setImageResource(R.drawable.scorpio);
		scorpio.setScaleType(ImageView.ScaleType.FIT_CENTER);
		scorpio.setBackgroundColor(Color.BLACK);
		scorpio.setPadding(10, 10, 10, 10);
		
		// add content to container
		mainContainer.addView(title);
		mainContainer.addView(line);
		mainContainer.addView(scorpio);
		
		// load into screen
		setContentView(mainContainer);
	}
	
}
