package com.example.singletonfordata;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private EditText mSurname;
	private EditText mForename;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSurname  = (EditText) findViewById(R.id.main_surname);
		mForename = (EditText) findViewById(R.id.main_forename);
		
		Button saveButton = (Button) findViewById(R.id.main_button_save);
		Button viewButton = (Button) findViewById(R.id.main_button_view);
		
		saveButton.setOnClickListener(this);
		viewButton.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View button) {
		switch(button.getId()) {
		case R.id.main_button_save:
			saveData();
			break;
			
		case R.id.main_button_view:
			viewNames();
			break;
		}
	}
	
	private void saveData() {
		String newName = mForename.getText().toString() + " " +
				         mSurname.getText().toString();
		DataManager.get(this).addName(newName);
		
		// reset UI
		mForename.setText("");
		mSurname.setText("");
	}
	
	private void viewNames() {
		startActivity( new Intent(this, ViewActivity.class) );
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if(!DataManager.get(this).saveData()) {
			Toast toast = Toast.makeText(this, "File Error Saving Data!", Toast.LENGTH_SHORT);
			toast.show();
		} else {
			Log.i("Main Activity", "Data Saved");
		}
	}

}
