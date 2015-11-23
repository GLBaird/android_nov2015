package com.leonbaird.placesofinterest;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class PlaceListFragment extends Fragment implements DataControllerInterface {

    // outlets
    private ListView placeList;
    private int selectedIndex = -1;
    private PlaceListAdapter adapter;

    // image cache
    private LruCache<String, Bitmap> imageCache;

    public PlaceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_place_list, container, false);

        // setup listview
        placeList = (ListView) v.findViewById(R.id.placelist_listview);

        // setup LruCache
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        imageCache = new LruCache<>(cacheSize);

        refreshData();

        // become delegate of DataController
        DataController.getController(getActivity()).delegate = this;

        return v;
    }


    private void refreshData() {
        List<Place> places = DataController.getController(getActivity()).getAllPlaces();
        adapter = new PlaceListAdapter(getActivity(), R.layout.cell_place, places);
        placeList.setAdapter(adapter);

        selectedIndex = -1;
    }


    // DataController delegate methods

    @Override
    public void dataControllerHasUpdated() {

        refreshData();

        // hide views on tablet
        if (onTablet()) {
            TabletOptionalFragment[] frags = getFragmentsForUpdate();

            for (TabletOptionalFragment frag : frags) {
                frag.hideViewWithAnimation();
            }
        }
    }


    // **** PlaceListAdapter ****

    private class PlaceListAdapter extends ArrayAdapter<Place> {


        public PlaceListAdapter(Context context, int resource, List<Place> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // Inflate UI manually for cell
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View row = li.inflate(R.layout.cell_place, parent, false);

            // get outlets from cell
            TextView  placeName      = (TextView)    row.findViewById(R.id.cell_placename);
            TextView  placeDate      = (TextView)    row.findViewById(R.id.cell_date);
            ImageView placeImage     = (ImageView)   row.findViewById(R.id.cell_image);
            ImageButton deleteButton = (ImageButton) row.findViewById(R.id.cell_delete);

            final Place info = getItem(position);

            // transfer data
            placeName.setText(info.placeName);
            placeDate.setText(info.getFormattedDateShort(getContext()));

            // check for image data
            if (info.imagePath != null && !info.imagePath.isEmpty()) {
                Bitmap loadedImage = imageCache.get(info.databaseID);
                if (loadedImage == null) {
                    // load from disk
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    loadedImage = BitmapFactory.decodeFile(info.imagePath, options);
                    imageCache.put(info.databaseID, loadedImage);
                }

                // place data in image view
                placeImage.setImageBitmap(loadedImage);
            }


            // make delete button work
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Remove place?");
                    builder.setMessage("You cannot undo this!");
                    builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataController.getController(getActivity()).deletePlaceWithID(info.databaseID);
                        }
                    });
                    builder.setNeutralButton("Cancel", null);
                    builder.create().show();
                }
            });

            // handle cell being clicked
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedIndex = position;

                    // reset list cells back to unselected
                    clearTableSelection();

                    // mark clicked cell as selected
                    row.setBackgroundColor(Color.rgb(200, 210, 220));

                    // decide what to do?????
                    Place selectedRecord = adapter.getItem(position);

                    if ( !onTablet() ) {
                        // running app on a phone
                        // open the Activity for the tabbed Detail and Map view
                        Intent tabbedActivity = new Intent(getActivity(), DetailMapTabsActivity.class);
                        // tell activity the ID of the record to display
                        tabbedActivity.putExtra(DetailMapTabsActivity.INTENT_DATABASEID, selectedRecord.databaseID);
                        startActivity(tabbedActivity);
                    } else {
                        // running app on a tablet
                        // trigger update on the Detail and Map Fragments

                        PlaceOfInterestDataStore ds = (PlaceOfInterestDataStore) getActivity();
                        ds.setSelectedPlace(selectedRecord);

                        TabletOptionalFragment[] frags = getFragmentsForUpdate();

                        for (TabletOptionalFragment frag : frags) {
                            frag.showViewWithAnimation();
                        }

                    }
                }
            });


            return row;
        }
    }

    private void clearTableSelection() {
        if (placeList.getAdapter() != null && placeList.getCount() > 0) {
            for (int i = 0; i < placeList.getCount(); i++) {
                View v = placeList.getChildAt(i);
                if (v != null) {
                    v.setBackgroundColor(Color.parseColor("#EFEFEF"));
                }
            }
        }
    }


    private TabletOptionalFragment[] getFragmentsForUpdate() {
        TabletOptionalFragment[] values = {
                (TabletOptionalFragment) getFragmentManager().findFragmentById(R.id.frag_detail),
                (TabletOptionalFragment) getFragmentManager().findFragmentById(R.id.frag_map)
        };

        return values;
    }

    private boolean onTablet() {
        return ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                    >= Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

}






