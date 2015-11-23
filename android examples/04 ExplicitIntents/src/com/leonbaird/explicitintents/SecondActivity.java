package com.leonbaird.explicitintents;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		TextView tv = (TextView) findViewById(R.id.second_dataHolder);
		tv.setText(getIntent().getExtras().getString(MainActivity.IntentDataA));
	}
	
}
