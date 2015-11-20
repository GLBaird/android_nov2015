package com.leonbaird.placesofinterest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataController {

    // values for singleton
    private static DataController theDataController;
    private Context appContext;

    // DB Values
    private static final String DATABASE_NAME = "places_of_interest";

    // DB Update Listener
    private DataControllerUpdateListener listener;

    public void setUpdateListener(DataControllerUpdateListener listener) {
        this.listener = listener;
    }

    private void updateListener() {
        if (listener != null) {
            listener.dataControllerHasUpdated();
        }
    }


    private DataController(Context c) {
        appContext = c.getApplicationContext();
        setupDataBase();
    }

    public static DataController getDataController(Context context) {
        if (theDataController == null) {
            theDataController = new DataController(context);
        }

        return  theDataController;
    }

    // DB Methods
    private void setupDataBase() {
        SQLiteDatabase db = openDB();
        String query = "CREATE TABLE IF NOT EXISTS places (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "placename TEXT," +
            "datevisited TEXT," +
            "description TEXT," +
            "imagepath TEXT," +
            "geolat TEXT," +
            "geolong TEXT" +
        ")";

        db.execSQL(query);
        db.close();
    }

    private SQLiteDatabase openDB() {
        return appContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    // Public DB Methods

    public void addPlace(Place newPlace) {
        SQLiteDatabase db = openDB();
        db.insert("places", null, newPlace.getDBValues());
        db.close();
        updateListener();
    }

    public Place getPlaceWithID(String id) {
        SQLiteDatabase db = openDB();
        String query = "SELECT * FROM places WHERE id = ?";
        String[] data = {id};
        Cursor result = db.rawQuery(query, data);

        Place found = null;

        if (result.getCount() > 0) {
            result.moveToFirst();
            found = unpackCursor(result);
        }

        db.close();

        return found;
    }

    private Place unpackCursor(Cursor cursor) {
        Date placeDate;
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        try {
            placeDate = df.parse( cursor.getString( cursor.getColumnIndex("datevisited") ) );
        } catch (ParseException e) {
            placeDate = new Date();
        }

        return new Place(
                cursor.getString( cursor.getColumnIndex("id") ),
                cursor.getString( cursor.getColumnIndex("placename") ),
                cursor.getString( cursor.getColumnIndex("description") ),
                cursor.getString( cursor.getColumnIndex("imagepath") ),
                cursor.getDouble( cursor.getColumnIndex("geolat") ),
                cursor.getDouble( cursor.getColumnIndex("geolong") ),
                placeDate
        );
    }

    public List<Place> getAllPlaces() {
        SQLiteDatabase db = openDB();
        String query = "SELECT * FROM places ORDER BY placename ASC, datevisited DESC";
        Cursor results = db.rawQuery(query, null);

        ArrayList<Place> places = new ArrayList<>();

        if (results.moveToFirst()) {
            do {
                places.add( unpackCursor(results) );
            } while (results.moveToNext());
        }

        db.close();

        return places;
    }

    public void deletePlaceWithID(String id) {
        Place placeToDelete = getPlaceWithID(id);
        if (placeToDelete != null && placeToDelete.imagePath != null && !placeToDelete.imagePath.isEmpty()) {
            File imageFile = new File(placeToDelete.imagePath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }

        SQLiteDatabase db = openDB();
        String query = "DELETE FROM places WHERE ID='"+id+"'";
        db.execSQL(query);
        db.close();
        updateListener();
    }


}

// Interface to listening for updates

interface DataControllerUpdateListener {
    public void dataControllerHasUpdated();
}
