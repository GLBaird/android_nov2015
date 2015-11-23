package com.leonbaird.basicbuttons;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private static final String TAG = "Main Activity";
	
	private TextView resultsTextView;
	
	private Button button1;
	private Button button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// get pointers to the views from resources
		resultsTextView = (TextView)findViewById(R.id.results_textview);
		
		button1 = (Button)findViewById(R.id.button_1);
		button2 = (Button)findViewById(R.id.button_2);
		
		// add on click listeners for buttons
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.d(TAG, "Button 1 Clicked");		
				
				// change text in results text view
				resultsTextView.setText("Button 1 Clicked");
			}
		});	
		
		button2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.d(TAG, "Button 2 Clicked");
				
				// change text in results text view
				resultsTextView.setText("Button 2 Clicked");
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
