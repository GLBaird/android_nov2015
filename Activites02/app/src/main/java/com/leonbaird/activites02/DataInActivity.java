package com.leonbaird.activites02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DataInActivity extends AppCompatActivity {

    // constants
    public static final String INTENT_KEY_TEXT = "text";

    private EditText textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_in);

        // show back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String importedValue = getIntent().getStringExtra(INTENT_KEY_TEXT);

        textField = (EditText) findViewById(R.id.datain_edittext);

        if (importedValue != null) {
            textField.setText(importedValue);
        }

        // setup save button
        Button saveButton = (Button) findViewById(R.id.datain_button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(MainActivity.INTENT_KEY_DATA, textField.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
