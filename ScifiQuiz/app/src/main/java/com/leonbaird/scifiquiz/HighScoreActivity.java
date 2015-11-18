package com.leonbaird.scifiquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        ListView listView = (ListView) findViewById(R.id.highscores_listview);

        // update the listview using array adapter
        ArrayAdapter<HighScore> adapter = new ArrayAdapter<HighScore>(
                this,
                android.R.layout.simple_list_item_1,
                GameController.getGameController(this).getHighScores()
        );

        listView.setAdapter(adapter);
    }

}
