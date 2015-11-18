package com.leonbaird.scifiquiz;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    EditText nameTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        nameTextField = (EditText) findViewById(R.id.settings_edittext_playername);
        nameTextField.setText( GameController.getGameController(this).playerName );


        Button savebutton = (Button) findViewById(R.id.settings_button_save);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameController.getGameController(SettingsActivity.this).playerName = nameTextField.getText().toString();
                finish();
            }
        });

    }
}
