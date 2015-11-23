package com.leonbaird.placesofinterest;

/**
 * This is an interface to allow the tabs (detail and map) to talk to ANY
 * activity that will be responsible for passing onto them the Place instance
 * they should be working with. The activity will differe between the mobile
 * and tablet versions of the app.
 */
public interface PlaceOfInterestDataStore {

    Place getSelectedPlace();
    void  setSelectedPlace(Place place);

}
