package com.leonbaird.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragmentDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // get Fragment manager
        FragmentManager fm = getFragmentManager();
        Fragment current = fm.findFragmentById(R.id.fragment_container);

        if (current == null) {
            MainFragment main = new MainFragment();
            main.setRetainInstance(true);
            main.delegate = this;

            fm.beginTransaction()
                    .add(R.id.fragment_container, main, "main")
                    .addToBackStack("main")
                    .commit();
        } else {
            fm.beginTransaction()
                    .attach(current)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showSecondFragmentAsActivity() {
        Intent nextActivity = new Intent(this, SecondActivity.class);
        startActivity(nextActivity);
    }
}
