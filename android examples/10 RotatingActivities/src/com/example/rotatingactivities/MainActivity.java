package com.example.rotatingactivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity {
	
	private int mValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState != null) {
			mValue = savedInstanceState.getInt("count");
		}
		
		final TextView tv = (TextView) findViewById(R.id.valueText);
		tv.setText(String.valueOf(mValue));
		
		Button addButton = (Button) findViewById(R.id.button);
		addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mValue++;
				tv.setText(String.valueOf(mValue));
			}
		});
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("count", mValue);
	}

}
