package com.example.listsandmodels;


import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PeopleAdapter extends ArrayAdapter<PeopleModel> {
	
	
	public PeopleAdapter(Context context, int resource,
			List<PeopleModel> people) {
		super(context, resource, people);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// build custom view
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.list_cell, parent, false);
		
		TextView tvSurname  = (TextView) row.findViewById(R.id.surname);
		TextView tvForename = (TextView) row.findViewById(R.id.forename);
		
		PeopleModel pm = getItem(position);
		
		tvSurname.setText(pm.getSurname());
		tvForename.setText(pm.getForename());
		
		return row;
	}

}
