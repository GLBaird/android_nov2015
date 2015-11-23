package com.example.singletonfordata;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

public class DataManager {
	
	public static final String DATA_MANAGER_TAG = "Data Manager";
	
	private static DataManager sDataManager;
	private Context mAppContext;
	private ArrayList<String> mNames;
	
	private static final String DATAFILE = "names.txt";
	
	private DataManager(Context appContext) {
		mAppContext = appContext;
		mNames = new ArrayList<String>();
		
		if(loadData()) {
			Log.i(DATA_MANAGER_TAG, "Data loaded and parsed");
		} else {
			Log.i(DATA_MANAGER_TAG, "No data store to load");
		}
	}
	
	public static DataManager get(Context c) {
		if(sDataManager == null) {
			sDataManager = new DataManager(c.getApplicationContext());
		}
		return sDataManager;
	}
	
	public void addName(String newName) {
		mNames.add(newName);
	}
	
	public ArrayAdapter<String> getArrayAdapter(Context context) {
		return new ArrayAdapter<String>(
				context, 
				android.R.layout.simple_list_item_1, 
				mNames);
	}
	
	public Boolean saveData() {
		
		String names = "";
		for (int i = 0; i < mNames.size(); i++) {
			names += mNames.get(i) + (i+1 < mNames.size() ? ";" : "");
		}
		
		try {
			FileOutputStream fos = mAppContext.openFileOutput(DATAFILE, Context.MODE_PRIVATE);
			fos.write(names.getBytes());
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	private Boolean loadData() {
			
		String loadedData = "";
		
		try {
			FileInputStream fis = mAppContext.openFileInput(DATAFILE);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);

			String beingRead = br.readLine();
			while( beingRead != null) {
				loadedData += beingRead;
				beingRead = br.readLine();
			}
			
			isr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		mNames = new ArrayList<String>( Arrays.asList(loadedData.split(";")) );
		
		return true;
	}

}
