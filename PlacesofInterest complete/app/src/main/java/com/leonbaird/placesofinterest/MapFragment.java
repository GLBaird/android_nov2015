package com.leonbaird.placesofinterest;


import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends TabletOptionalFragment implements View.OnClickListener {

    // properties
    private LatLng coordinates;
    private com.google.android.gms.maps.MapFragment mapView = null;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        // setup outlets
        Button standardButton  = (Button) getView().findViewById(R.id.map_button_standard);
        Button satelliteButton = (Button) getView().findViewById(R.id.map_button_satellite);
        Button hybridButton    = (Button) getView().findViewById(R.id.map_button_hybrid);

        standardButton.setOnClickListener(this);
        satelliteButton.setOnClickListener(this);
        hybridButton.setOnClickListener(this);

        // setup the map fragment, if needed
        mapView = (com.google.android.gms.maps.MapFragment)getFragmentManager().findFragmentById(R.id.map);
        MapsInitializer.initialize(getActivity());

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                < Configuration.SCREENLAYOUT_SIZE_LARGE) {
            updateMap();
        } else {
            // hide for tablet version
            hideView();
        }

    }

    @Override
    void showViewWithAnimation() {
        updateMap();
        super.showViewWithAnimation();
    }

    private void updateMap() {
        // get place from activity
        PlaceOfInterestDataStore parent = (PlaceOfInterestDataStore) getActivity();
        Place placeForMap = parent.getSelectedPlace();

        // update UI
        TextView titleTV = (TextView) getView().findViewById(R.id.map_title);
        titleTV.setText("Map of "+placeForMap.placeName);

        // setup coordinates
        coordinates = new LatLng(placeForMap.geoLat, placeForMap.geoLong);
        if (mapView == null) {
            mapView = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map);
        }

        GoogleMap map = mapView.getMap();

        if (map != null) {
            // add a marker to the map
            map.addMarker(
                    new MarkerOptions()
                            .position(coordinates)
                            .title(placeForMap.placeName)
                            .snippet(placeForMap.placeDescription)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
            );

            // animate to its position
            map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        } else {
            Toast.makeText(getActivity(), "Map has failed to load!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (mapView != null) {
            GoogleMap map = mapView.getMap();
            if (map != null) {
                switch (v.getId()) {
                    case R.id.map_button_standard:
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case R.id.map_button_satellite:
                        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case R.id.map_button_hybrid:
                        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                }
            }
        }
    }
}
