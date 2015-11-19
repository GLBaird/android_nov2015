package com.leonbaird.placesofinterest;

import android.content.ContentValues;
import android.content.Context;
import android.text.format.DateFormat;

import java.util.Date;

public class Place {

    public String placeName;
    public Date   dateVisited;
    public String placeDescription;
    public String imagePath;
    public double geoLat;
    public double geoLong;

    public String databaseID;

    public Place() {
        dateVisited = new Date();
    }

    public Place(String id, String placeName, String placeDescription, String imagePath, double geoLat, double geoLong, Date dateVisited) {
        this.databaseID = id;
        this.placeName = placeName;
        this.placeDescription = placeDescription;
        this.imagePath = imagePath;
        this.geoLat = geoLat;
        this.geoLong = geoLong;
        this.dateVisited = dateVisited;
    }

    public String getShortFormattedDate(Context c) {
        return DateFormat.getDateFormat(c).format(dateVisited);
    }

    public String getLongFormattedDate(Context c) {
        return DateFormat.getLongDateFormat(c).format(dateVisited);
    }

    public ContentValues getDBValues() {
        ContentValues cv = new ContentValues();
        cv.put("placename", placeName);
        cv.put("description", placeDescription);
        cv.put("datevisited", dateVisited.toString());
        cv.put("imagepath", imagePath);
        cv.put("geolat", String.valueOf( geoLat ));
        cv.put("geolong", String.valueOf( geoLong ));

        return cv;
    }

}
