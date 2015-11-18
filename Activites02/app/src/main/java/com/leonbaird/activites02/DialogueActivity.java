package com.leonbaird.activites02;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DialogueActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue);

        Button okButton = (Button) findViewById(R.id.dialogue_button_ok);
        okButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
