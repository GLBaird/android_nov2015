package com.leonbaird.scifiquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String INTENT_KEY_SCORE = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        GameController gc = GameController.getGameController(this);
        int score = getIntent().getIntExtra(INTENT_KEY_SCORE, 0);
        int totalQuestions = gc.getQuestions().size();
        String playerName = gc.playerName;

        if (savedInstanceState == null) {
            gc.addHighScore(playerName, score);
            gc.saveHighScores();
        }

        String message = playerName+" has scored "+score+" / "+totalQuestions*10;

        TextView resultsTV = (TextView) findViewById(R.id.results_text_score);
        resultsTV.setText(message);

        Button menuButton = (Button) findViewById(R.id.results_button_menu);
        Button scoreButton = (Button) findViewById(R.id.results_button_scores);

        menuButton.setOnClickListener(this);
        scoreButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.results_button_scores) {
            Intent hsIntent = new Intent(this, HighScoreActivity.class);
            startActivity(hsIntent);
        }

        finish();
    }
}
