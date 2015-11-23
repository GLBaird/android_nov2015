package com.leonbaird.explicitintents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

public class ThirdActivity extends Activity {
	
	private EditText mDataB;
	public static final String ThirdActivityData = "ThirdActivityData";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		
		mDataB = (EditText) findViewById(R.id.third_textDataB);
		mDataB.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_UP) {
					if(keyCode == KeyEvent.KEYCODE_ENTER) {
						return false;
					}
					
					String valueToSend = ThirdActivity.this.mDataB.getText().toString();
					Intent data = new Intent();
					data.putExtra(ThirdActivity.ThirdActivityData, valueToSend);
					setResult(RESULT_OK, data);
				}
				return false;
			}
		});
		
		Button closeButton = (Button) findViewById(R.id.third_closeActivity);
		closeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String valueToSend = ThirdActivity.this.mDataB.getText().toString();
				Intent data = new Intent();
				data.putExtra(ThirdActivity.ThirdActivityData, valueToSend);
				setResult(RESULT_OK, data);
				ThirdActivity.this.finish();
			}
		});
	}
}
