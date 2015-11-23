package com.leonbaird.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment implements OnClickListener{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("FRAGMENT", "New MAIN Fragment Created");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		
		Button clickButton = (Button) view.findViewById(R.id.button1);
		clickButton.setOnClickListener(this);
				
		return view;
	}

	@Override
	public void onClick(View v) {
		TextView tv = (TextView) getView().findViewById(R.id.textView);
		tv.setText("This fragment works!");
	}
	
}
