package com.leonbaird.scifiquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // setup buttons
        Button gameButton  = (Button) findViewById(R.id.main_button_play);
        Button scoreButton = (Button) findViewById(R.id.main_button_highscores);

        gameButton.setOnClickListener(this);
        scoreButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            showSettings();
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSettings() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_button_play) {
            // start game
            Intent gameIntent = new Intent(this, GameActivity.class);
            startActivity(gameIntent);
        } else {
            // show scores
            Intent scoresIntent = new Intent(this, HighScoreActivity.class);
            startActivity(scoresIntent);
        }
    }
}
