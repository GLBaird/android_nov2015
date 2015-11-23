package com.leonbaird.menus;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button contextButton = (Button) findViewById(R.id.button1);
		registerForContextMenu(contextButton);
		
		// Example of Alert View
		Button exitButton = (Button) findViewById(R.id.quit);
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Are you sure you want to quit the app?");
				builder.setCancelable(false);
				
				// add buttons with event listeners
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.this.finish();
					}
				});
				
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				
				// make and show dialog
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}

	// Creates Options and Contextual Menus
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.main_context, menu);
	}
	
	// handle Menu Clicks
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		TextView tv = (TextView) findViewById(R.id.infoText);
		switch (item.getItemId()) {
			case R.id.main_option1:
				tv.setText("Option Button #1 Clicked");
				break;
				
			case R.id.main_option2:
				tv.setText("Option Button #2 Clicked");
				break;
				
			case R.id.main_option3:
				tv.setText("Option Button #3 Clicked");
				break;
	
			default:
				tv.setText("Unknown Button Clicked");
				break;
		}
		
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		TextView tv = (TextView) findViewById(R.id.infoText);
		switch (item.getItemId()) {
			case R.id.context_option1:
				tv.setText("Contextual Button #1 Clicked");
				break;
				
			case R.id.context_option2:
				tv.setText("Contextul Button #2 Clicked");
				break;
				
			case R.id.context_option3:
				tv.setText("Contextual Button #3 Clicked");
				break;
				
			default:
				tv.setText("Unknown Button Clicked");
				break;
		}
		
		return super.onContextItemSelected(item);
	}

}
