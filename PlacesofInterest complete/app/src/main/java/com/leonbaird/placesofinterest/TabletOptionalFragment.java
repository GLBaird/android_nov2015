package com.leonbaird.placesofinterest;

import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by leon on 06/06/15.
 */
public class TabletOptionalFragment extends Fragment {
    boolean displayingPlace = false;

    void hideViewWithAnimation() {
        displayingPlace = false;
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(this).commitAllowingStateLoss();
    }

    void showViewWithAnimation() {
        displayingPlace = true;
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).show(this).commitAllowingStateLoss();
    }

    void hideView() {
        getFragmentManager().beginTransaction().hide(this).commit();
    }
}
