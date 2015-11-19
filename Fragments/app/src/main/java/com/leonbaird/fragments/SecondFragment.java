package com.leonbaird.fragments;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SecondFragment extends Fragment {


    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_second, container, false);

        Button button = (Button) v.findViewById(R.id.second_frag_button);
        button.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hi from second fragment", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
