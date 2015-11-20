package com.leonbaird.placesofinterest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class DetailMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.tabs_detailmap);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tabs_content);

        tabHost.addTab(tabHost.newTabSpec("details").setIndicator("Details"), DetailsFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("map").setIndicator("Map"), MapFragment.class, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DetailsFragment df = (DetailsFragment) getSupportFragmentManager().findFragmentByTag("details");
        MapFragment     mf = (MapFragment) getSupportFragmentManager().findFragmentByTag("map");

        String id = getIntent().getStringExtra("id");
        Place detail = DataController.getDataController(this).getPlaceWithID(id);

        if (df != null) {
            df.updateForPlace(detail);
        } else {
            Log.e("Tabs", "Detail Fragment did not load!");
        }

        if (mf != null) {
            mf.updateForPlace(detail);
        } else {
            Log.e("Tabs", "Map Fragment did not load!");
        }
    }
}
