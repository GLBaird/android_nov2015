package com.leonbaird.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("MAIN ACTIVITY", "New Activity Created");
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment  = fm.findFragmentById(R.id.fragment_container);
		
		// reuse fragment or create new?
		if (fragment == null) {
			fragment = new MainFragment();
			
			// set to retain fragment
			fragment.setRetainInstance(true);
			
			// add to manager
			fm.beginTransaction()
				.add(R.id.fragment_container, fragment)
				.commit();
			
			Log.d("MAIN ACTIVITY", "New Fragment");
		}
		
	}

}
