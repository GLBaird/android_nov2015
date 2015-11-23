package com.leonbaird.placesofinterest;


import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFragment extends TabletOptionalFragment {

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) <
                Configuration.SCREENLAYOUT_SIZE_LARGE) {
            // running on mobile, load view
            transferModelToView();
        } else if (!displayingPlace){
            // running on tablet, no place to display - hide interface
            hideView();
        }

    }

    @Override
    void showViewWithAnimation() {
        transferModelToView();
        super.showViewWithAnimation();
    }

    private void transferModelToView() {
        // get place from activity
        PlaceOfInterestDataStore parent = (PlaceOfInterestDataStore) getActivity();
        Place toDisplay = parent.getSelectedPlace();

        // get outlets
        TextView title  = (TextView) getView().findViewById(R.id.detail_text_name);
        TextView date   = (TextView) getView().findViewById(R.id.detail_text_date);
        TextView desc   = (TextView) getView().findViewById(R.id.detail_text_description);
        ImageView image = (ImageView) getView().findViewById(R.id.detail_imageview);

        // make description scrollable
        desc.setMovementMethod(new ScrollingMovementMethod());

        // transfer model to views
        title.setText(toDisplay.placeName);
        date.setText(toDisplay.getFormattedDateLong(getActivity()));
        desc.setText(toDisplay.placeDescription);

        // show image if exists
        if (toDisplay.imagePath != null && !toDisplay.imagePath.isEmpty()) {
            Uri imageURI = Uri.parse(toDisplay.imagePath);
            image.setImageURI(imageURI);
        }
    }

}
