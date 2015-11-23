package com.example.singletonfordata;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		
		ListView list = (ListView) findViewById(R.id.listView);
		list.setAdapter( DataManager.get(this).getArrayAdapter(this) );
	}
	
}
