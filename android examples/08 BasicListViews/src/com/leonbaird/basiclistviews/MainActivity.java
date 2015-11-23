package com.leonbaird.basiclistviews;

import java.util.Locale;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1, 
				getResources().getStringArray(R.array.tv_shows)
		);
		
		setListAdapter(adapter);
	}
	
	@SuppressLint("DefaultLocale")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		String[] tvShows = getResources().getStringArray(R.array.tv_shows);
		String textForMessage = String.format(Locale.ENGLISH, "List Item#%d was from show - \n%s", position, tvShows[position]);
		Toast messsage = Toast.makeText(this, textForMessage, Toast.LENGTH_SHORT);
		messsage.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
