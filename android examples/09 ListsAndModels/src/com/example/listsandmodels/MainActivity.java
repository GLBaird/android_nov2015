package com.example.listsandmodels;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;

public class MainActivity extends ListActivity {
	
	ArrayList<PeopleModel> people;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		people = new ArrayList<PeopleModel>();
		
		PeopleModel person1 = new PeopleModel("Baird", "Leon");
		PeopleModel person2 = new PeopleModel("Smith", "Esther");
		PeopleModel person3 = new PeopleModel("Jones", "John");
		
		people.add(person1);
		people.add(person2);
		people.add(person3);

		PeopleAdapter pa = new PeopleAdapter(this, R.layout.list_cell, people);
		
		setListAdapter(pa);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
