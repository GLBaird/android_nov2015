package com.leonbaird.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment implements View.OnClickListener {

    private String userName = "Bob";

    public String information = "";

    public MainFragmentDelegate delegate;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("main_fragment", "Loading View "+userName);

        userName = "Leon";

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        TextView tv = (TextView) v.findViewById(R.id.main_frag_text);
        tv.setText(information);

        Button button1 = (Button) v.findViewById(R.id.main_frag_button1);
        Button button2 = (Button) v.findViewById(R.id.main_frag_button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View button) {
        if (button.getId() == R.id.main_frag_button1) {
            FragmentManager fm = getFragmentManager();
            Fragment second = new SecondFragment();

            fm.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment_container, second, "second")
                    .addToBackStack("second")
                    .commit();
        } else {

            if (delegate != null) {
                delegate.showSecondFragmentAsActivity();
            }

        }
    }
}

interface MainFragmentDelegate {

    void showSecondFragmentAsActivity();

}
