package com.leonbaird.placesofinterest;

import android.content.ContentValues;
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

    // the singleton
    private static DataController _dataController;
    private Context appContext;

    // for the database
    private static final String DATABASE_NAME = "places_of_interest";

    private DataController(Context c) {
        appContext = c.getApplicationContext();

        // setup database
        SQLiteDatabase db = openDatabase();
        String query =  "CREATE TABLE IF NOT EXISTS places ("+
                        "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "placename TEXT,"+
                        "datevisited TEXT,"+
                        "placedescription TEXT,"+
                        "imagepath TEXT,"+
                        "geolat TEXT,"+
                        "geolong TEXT)";
        db.execSQL(query);
        db.close();
    }

    private SQLiteDatabase openDatabase() {
        return appContext.openOrCreateDatabase(DATABASE_NAME, appContext.MODE_PRIVATE, null);
    }

    public static DataController getController(Context context) {
        if (_dataController == null) {
            _dataController = new DataController(context);
        }

        return _dataController;
    }

    public void addPlace(Place newPlace) {
        SQLiteDatabase db = openDatabase();
        ContentValues vals = new ContentValues();
        vals.put("placename",        newPlace.placeName);
        vals.put("datevisited",      newPlace.dateVisited.toString());
        vals.put("placedescription", newPlace.placeDescription);
        vals.put("imagepath",        newPlace.imagePath);
        vals.put("geolat",           String.valueOf(newPlace.geoLat) );
        vals.put("geolong",          String.valueOf(newPlace.geoLong) );
        db.insert("places", null, vals);
        db.close();

        callDelegate();
    }

    public Place getPlaceWithID(String id) {
        SQLiteDatabase db = openDatabase();
        String query = "SELECT * FROM places WHERE id = ?";
        String[] data = {id};
        Cursor result = db.rawQuery(query, data);

        Place found = null;

        if (result.getCount() > 0) {
            result.moveToFirst();
            found = parseQueryResult(result);
        }

        db.close();

        return found;
    }

    public List<Place> getAllPlaces() {
        SQLiteDatabase db = openDatabase();
        String query = "SELECT * FROM places ORDER BY datevisited ASC, placename ASC";
        Cursor results = db.rawQuery(query, null);

        ArrayList<Place> places = new ArrayList<>();

        if (results.moveToFirst()) {
            do {
                places.add( parseQueryResult(results) );
            } while (results.moveToNext());
        }

        db.close();

        return places;
    }

    private Place parseQueryResult(Cursor result) {
        Date placeDate;
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        try {
            placeDate = df.parse( result.getString( result.getColumnIndex("datevisited") ) );
        } catch (ParseException e) {
            placeDate = new Date();
        }

        return new Place(
                result.getString( result.getColumnIndex("id") ),
                result.getString( result.getColumnIndex("placename") ),
                result.getString( result.getColumnIndex("placedescription") ),
                result.getString( result.getColumnIndex("imagepath") ),
                result.getDouble( result.getColumnIndex("geolat") ),
                result.getDouble( result.getColumnIndex("geolong") ),
                placeDate
        );
    }

    public void deletePlaceWithID(String id) {
        Place toDelete = getPlaceWithID(id);
        if (toDelete != null && toDelete.imagePath != null && !toDelete.imagePath.isEmpty()) {
            File imageFile = new File(toDelete.imagePath);
            if (imageFile != null && imageFile.exists()) {
                imageFile.delete();
            }
        }

        SQLiteDatabase db = openDatabase();
        String query = "DELETE FROM places WHERE id="+id;
        db.execSQL(query);
        db.close();

        callDelegate();
    }

    // DELEGATION

    public DataControllerInterface delegate;

    private void callDelegate() {
        if (delegate != null) {
            delegate.dataControllerHasUpdated();
        }
    }

}


// Delegate Interface

interface DataControllerInterface {
    public void dataControllerHasUpdated();
}
