package com.example.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	EditText etURL;
	TextView tvResults;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etURL 	  = (EditText) findViewById(R.id.urlET);
		tvResults = (TextView) findViewById(R.id.resultTV);
		Button go = (Button)   findViewById(R.id.button1);
		
		go.setOnClickListener(this);
		
		tvResults.setMovementMethod(new ScrollingMovementMethod());
		
	}

	@Override
	public void onClick(View button) {
		
		String url = etURL.getText().toString();
		new DownloadTextOnThread().execute(url);		
	}
	
	class DownloadTextOnThread extends AsyncTask<String, Void, String> {

		private Exception exception;
		
		@Override
		protected String doInBackground(String... urls) {
			
			String downloadedText = "";
			
			try {
				// open URL Connection
				URL webURL = new URL( urls[0] );
				URLConnection connection = webURL.openConnection();
				
				// get buffered reader to download data at URL
				BufferedReader br = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
				
				// download...
				String line = "";
				while ( (line = br.readLine()) != null) {
					downloadedText += line; 
				}
				
				return downloadedText;
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
				this.exception = e;
			} catch (IOException e) {
				e.printStackTrace();
				this.exception = e;
			}
			return null;
		}
		
		protected void onPostExecute(String downloadedText) {
	        if (this.exception != null) {
	        		tvResults.setText("*** Network Error! ***\n" + this.exception.getLocalizedMessage());
	        } else {
	        		tvResults.setText(downloadedText);
	        }
	    }
	}

}
