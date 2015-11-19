package com.leonbaird.scifiquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    List<Question> questions;
    int currentQuestion = 0, score = 0;

    // outlets
    private TextView questionNumber;
    private TextView questionText;
    private Button answer1, answer2, answer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(BUNDLE_SCORE);
            currentQuestion = savedInstanceState.getInt(BUNDLE_QUESTION);
        }

        // setup outlets
        questionNumber = (TextView) findViewById(R.id.game_text_question_number);
        questionText = (TextView) findViewById(R.id.game_text_question);
        answer1 = (Button) findViewById(R.id.game_button_answer1);
        answer2 = (Button) findViewById(R.id.game_button_answer2);
        answer3 = (Button) findViewById(R.id.game_button_answer3);

        // setup event listeners
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);

        questions = GameController.getGameController(this).getQuestions();
        showNextQuestion();
    }

    // Bundle Keys
    private static final String BUNDLE_SCORE = "score";
    private static final String BUNDLE_QUESTION = "question";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(BUNDLE_SCORE, score);
        outState.putInt(BUNDLE_QUESTION, currentQuestion);
    }

    private void showNextQuestion() {
        if (currentQuestion < questions.size()) {
            Question q = questions.get(currentQuestion);
            String qn = "Question "+(currentQuestion+1);
            questionNumber.setText(qn);
            questionText.setText(q.questionText);
            answer1.setText(q.answer1);
            answer2.setText(q.answer2);
            answer3.setText(q.answer3);
        } else {
            showResults();
        }
    }

    private void showResults() {
        Intent resultIntent = new Intent(this, ResultsActivity.class);
        resultIntent.putExtra(ResultsActivity.INTENT_KEY_SCORE, score);
        startActivity(resultIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            showSettings();
            return  true;
        } else if (item.getItemId() == R.id.action_quit) {
            // check we can quite game
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit Game");
            builder.setMessage("Are you sure?");
            builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNeutralButton("Cancel", null);
            builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSettings() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    @Override
    public void onClick(View v) {
        int buttonIndex = Integer.parseInt( (String) v.getTag()) ;
        Question q = questions.get(currentQuestion++);
        if (q.isCorrectAnswer(buttonIndex)) {
            score += 10;
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        } else {
            score -= 5;
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }

        showNextQuestion();
    }
}
