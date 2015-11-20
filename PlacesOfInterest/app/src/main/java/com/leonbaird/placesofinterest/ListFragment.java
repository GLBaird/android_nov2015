package com.leonbaird.placesofinterest;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class ListFragment extends Fragment implements DataControllerUpdateListener {

    // outlets
    private ListView placeList;

    // image cache
    private LruCache<String, Bitmap> imageCache;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_list, container, false);


        placeList = (ListView) v.findViewById(R.id.list_places);

        // setup LRU Cache
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        imageCache = new LruCache<>(cacheSize);

        DataController.getDataController(getActivity()).setUpdateListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        List<Place> places = DataController.getDataController(getActivity()).getAllPlaces();
        PlaceListAdatper adapter = new PlaceListAdatper(getActivity(), R.layout.cell_place, places);
        placeList.setAdapter(adapter);
    }

    @Override
    public void dataControllerHasUpdated() {
        refreshData();

        // TODO: hide details and map UI on tablet
    }

    // Array Adapter for List View

    private class PlaceListAdatper extends ArrayAdapter<Place> {

        public PlaceListAdatper(Context context, int resource, List<Place> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View row = li.inflate(R.layout.cell_place, parent, false);

            // get cell outlets
            TextView  placeName  = (TextView) row.findViewById(R.id.cell_placename);
            TextView  placeDate  = (TextView) row.findViewById(R.id.cell_datevisited);
            ImageView placeImage = (ImageView) row.findViewById(R.id.cell_image);

            final Place info = getItem(position);

            // update UI
            placeName.setText(info.placeName);
            placeDate.setText(info.getShortFormattedDate(getContext()));

            if (info.imagePath != null && !info.imagePath.isEmpty()) {
                Bitmap loadedImage = imageCache.get(info.imagePath);
                if (loadedImage == null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    loadedImage = BitmapFactory.decodeFile(info.imagePath, options);
                    imageCache.put(info.imagePath, loadedImage);
                }

                placeImage.setImageBitmap(loadedImage);
            }

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTableSelection();

                    row.setBackgroundColor(Color.rgb(200, 210, 220));

                    if( (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                            < Configuration.SCREENLAYOUT_SIZE_LARGE) {

                        Intent tabs = new Intent(getActivity(), DetailMapActivity.class);
                        tabs.putExtra("id", info.databaseID);
                        startActivity(tabs);

                    } else {
                        // TODO: send to fragments
                    }
                }
            });

            return row;
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
    }
}
