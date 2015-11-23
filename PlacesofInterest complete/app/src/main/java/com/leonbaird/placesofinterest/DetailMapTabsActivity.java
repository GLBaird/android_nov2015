package com.leonbaird.placesofinterest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class DetailMapTabsActivity extends AppCompatActivity implements View.OnClickListener, PlaceOfInterestDataStore {

    // Intent keys
    public static final String INTENT_DATABASEID = "database_id";

    // Bundle keys
    public static final String BUNDLE_CURRENT_TAG = "current_tag";

    // selected place for display
    private Place selectedRecord;

    // Fragments
    private Fragment detailFragment;
    private Fragment mapFragment;
    private Fragment currentFragment;
    private String   currentFragmentTag;

    // outlets
    private Button detailTab;
    private Button mapTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map_tabs);

        // get selected place from intent
        selectedRecord = DataController.getController(this).getPlaceWithID(
                getIntent().getStringExtra(INTENT_DATABASEID)
        );

        // show back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // setup outlets
        detailTab = (Button) findViewById(R.id.tabs_detailtab);
        mapTab    = (Button) findViewById(R.id.tabs_maptab);

        detailTab.setOnClickListener(this);
        mapTab.setOnClickListener(this);

        // load from bundle
        if (savedInstanceState != null) {
            currentFragmentTag = savedInstanceState.getString(BUNDLE_CURRENT_TAG);
        } else {
            currentFragmentTag = (String) detailTab.getTag();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // get fragments
        detailFragment = getFragmentManager().findFragmentByTag((String) detailTab.getTag());
        mapFragment    = getFragmentManager().findFragmentByTag((String) mapTab.getTag());

        // if fragment does not exist, this is the first load, setup both fragments
        // we are adding both as when detaching or replacing, crashes due to
        // map fragment being attached by user interface inflation - instead will hide / show fragments
        if (detailFragment == null || mapFragment == null) {
            detailFragment = new DetailFragment();
            mapFragment    = new MapFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.tabs_fragment, detailFragment, (String) detailTab.getTag())
                    .add(R.id.tabs_fragment, mapFragment, (String) mapTab.getTag())
                    .hide(currentFragmentTag.equals(detailTab.getTag()) ? mapFragment : detailFragment)
                    .commit();
            currentFragment = currentFragmentTag.equals(detailTab.getTag()) ? detailFragment : mapFragment;
        }

        // set the selected tab
        detailTab.setBackgroundColor(
                currentFragmentTag.equals(detailTab.getTag()) ?
                        getResources().getColor(R.color.PlacesTabSelected) :
                        getResources().getColor(R.color.PlacesTabNormal)
        );
        mapTab.setBackgroundColor(
                currentFragmentTag.equals(detailTab.getTag()) ?
                        getResources().getColor(R.color.PlacesTabNormal) :
                        getResources().getColor(R.color.PlacesTabSelected)
        );

    }

    @Override
    protected void onPause() {
        super.onPause();
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                < Configuration.SCREENLAYOUT_SIZE_LARGE) {
            // if on phone, unload tabs from screen and remove instances
            // this is because of the map being loaded in interface file
            // would not be needed if fragment was loaded in code
            getFragmentManager()
                    .beginTransaction()
                    .remove(mapFragment)
                    .remove(detailFragment)
                    .commit();
            detailFragment = null;
            mapFragment = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(BUNDLE_CURRENT_TAG, currentFragmentTag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_map_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_delete:
                deletePlace(selectedRecord.databaseID);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tabs_detailtab && !currentFragmentTag.equals(detailTab.getTag())) {
            Fragment newFrag = getFragmentManager().findFragmentByTag((String) detailTab.getTag());
            if (newFrag == null) {
                newFrag = new DetailFragment();
            }
            changeTab(newFrag, detailTab);
            detailTab.setBackgroundColor(getResources().getColor(R.color.PlacesTabSelected));
            mapTab.setBackgroundColor(getResources().getColor(R.color.PlacesTabNormal));
        } else if (v.getId() == R.id.tabs_maptab && !currentFragmentTag.equals(mapTab.getTag())){
            Fragment newFrag = getFragmentManager().findFragmentByTag((String) mapTab.getTag());
            if (newFrag == null) {
                newFrag = new MapFragment();
            }
            changeTab(newFrag, mapTab);
            detailTab.setBackgroundColor(getResources().getColor(R.color.PlacesTabNormal));
            mapTab.setBackgroundColor(getResources().getColor(R.color.PlacesTabSelected));
        }

    }

    private void changeTab(Fragment newFrag, Button tab) {
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .hide(currentFragment)
                .show(newFrag)
                .commit();
        currentFragment = newFrag;
        currentFragmentTag = (String) tab.getTag();
    }

    private void deletePlace(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove place?");
        builder.setMessage("You cannot undo this!");
        builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataController.getController(DetailMapTabsActivity.this).deletePlaceWithID(id);
                finish();
            }
        });
        builder.setNeutralButton("Cancel", null);
        builder.create().show();
    }

    // Data-store Interface method for passing selected place to fragment

    @Override
    public Place getSelectedPlace() {
        return selectedRecord;
    }

    @Override
    public void setSelectedPlace(Place place) {
        // method not needed as place is passed from list fragment
    }
}
