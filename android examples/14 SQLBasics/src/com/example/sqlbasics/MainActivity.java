package com.example.sqlbasics;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// code only samples:
		
		// SQLiteDatabase db = openOrCreateDatabase(name, mode, factory);
		SQLiteDatabase db = openOrCreateDatabase("DEMO", MODE_PRIVATE, null);
		String tableQuery = "CREATE TABLE IF NOT EXISTS demo_table "+
						    "(id INTEGER PRIMARY KEY AUTOINCREMENT, "+
						    	"name TEXT, "+
						    "age  INTEGER);";
		db.execSQL(tableQuery);
		
		String newRecord  = "INSERT INTO demo_table (name, age) VALUES ('Leon', '37');";
		db.execSQL(newRecord);
		
		// get info
		Cursor c = db.rawQuery("SELECT * FROM demo_table", null);
		c.moveToFirst();
		
		String name = c.getString( c.getColumnIndex("name") );		
		
		Log.d("SQL", "Name: "+name);
		
		db.close();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
