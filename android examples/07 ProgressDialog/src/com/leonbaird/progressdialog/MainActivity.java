package com.leonbaird.progressdialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button spinnerButton  = (Button) findViewById(R.id.spinnerButton);
		Button progressButton = (Button) findViewById(R.id.progressButton);
		Button normalButton   = (Button) findViewById(R.id.normalButton);
		
		spinnerButton.setOnClickListener(this);
		progressButton.setOnClickListener(this);
		normalButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		// Check if showing normal dialog
		if(v.getId() == R.id.normalButton) {
			showDialgBox();
			return;
		}
		
		// make progress dialog
		ProgressDialog pd = new ProgressDialog(this);
		//pd.setTitle("Progress Demo");
		pd.setMessage("Please wait...");
		pd.setCancelable(true);
		
		switch (v.getId()) {
		case R.id.spinnerButton:
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setIndeterminate(true);
			break;
		
		case R.id.progressButton:
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setIndeterminate(false);
			break;
		}
		
		pd.show();
		pd.setProgress(50);
	}

	private void showDialgBox() {
		Dialog d = new Dialog(this);
		d.setContentView(R.layout.dialog_main);
		d.setTitle("Demo Dialog Box");
		d.show();
	}
}
