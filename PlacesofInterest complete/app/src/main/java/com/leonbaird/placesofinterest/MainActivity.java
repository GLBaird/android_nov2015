package com.leonbaird.placesofinterest;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements PlaceOfInterestDataStore {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                < Configuration.SCREENLAYOUT_SIZE_LARGE) {

            // setup page fragments on phone and not tablet
            Fragment placeList = getFragmentManager().findFragmentById(R.id.main_fragment_placelist);

            if (placeList == null) {
                placeList = new PlaceListFragment();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_fragment_placelist, placeList, "PlaceListFragment")
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.main_menu_add:
                showAddPlaceActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAddPlaceActivity() {
        Intent addPlace = new Intent(this, AddPlaceActivity.class);
        startActivity(addPlace);
    }

    // handle model passing in tablet form

    private Place selectedPlace;

    @Override
    public Place getSelectedPlace() {
        return selectedPlace;
    }

    @Override
    public void setSelectedPlace(Place place) {
        selectedPlace = place;
    }
}
