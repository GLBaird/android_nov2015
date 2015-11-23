package com.leonbaird.explicitintents;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {
	
	private EditText mDataA;
	
	public static final String IntentDataA = "IntentDataA";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button sendButton   = (Button) findViewById(R.id.main_button_send);
		Button returnButton = (Button) findViewById(R.id.main_button_return);
		
		mDataA = (EditText) findViewById(R.id.textDataA);
		
		sendButton.setOnClickListener(this);
		returnButton.setOnClickListener(this);
		
		// handle keyboard so return key hide keyboard
		mDataA.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
					return true;
				}
				return false;
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK) {
			
			String returnedValue = data.getExtras().getString(ThirdActivity.ThirdActivityData);
			mDataA.setText(returnedValue);
		}
		
	}

	@Override
	public void onClick(View button) {
		switch(button.getId()) {
			case R.id.main_button_send:
				openActivityTwo();
				break;
	
			case R.id.main_button_return:
				openActivityThree();
				break;
		}
	}
	
	private void openActivityTwo() {
		Intent activityTwoIntent = new Intent(this, SecondActivity.class);
		activityTwoIntent.putExtra(IntentDataA, mDataA.getText().toString());
		startActivity(activityTwoIntent);
	}
	
	private void openActivityThree() {
		Intent activityThreeIntent = new Intent(this, ThirdActivity.class);
		startActivityForResult(activityThreeIntent, 0);
	}
	
	

}
