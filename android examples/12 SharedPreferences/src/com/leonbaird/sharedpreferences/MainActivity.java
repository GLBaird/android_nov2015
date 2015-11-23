package com.leonbaird.sharedpreferences;

import android.os.Bundle;
import android.widget.EditText;
import android.app.Activity;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
	
	private EditText editText;
	private SharedPreferences settings;
	private static final String SETTINGS_NAME = "settings";
	private static final String KEY_NAME = "User Name";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		settings = getSharedPreferences(SETTINGS_NAME, 0);
		editText = (EditText) findViewById(R.id.userName);
		editText.setText( settings.getString(KEY_NAME, "") );
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		SharedPreferences.Editor edit = settings.edit();
		edit.putString(KEY_NAME, editText.getText().toString());
		edit.commit();
	}

}
