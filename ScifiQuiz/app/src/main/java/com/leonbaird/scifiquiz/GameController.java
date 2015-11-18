package com.leonbaird.scifiquiz;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GameController {

    // tag for logging
    private static final String LOG_TAG = "game_controller";

    // Context for accessing file system
    private Context appContext;

    // properties
    public String playerName = "Player 1";
    private ArrayList<HighScore> highScores;
    private List<Question> questions;

    // instance for singleton
    private static GameController theGameController;

    // Getter for creating singleton
    public static GameController getGameController(Context context) {
        if (theGameController == null) {
            theGameController = new GameController(context);
        }

        return theGameController;
    }

    private GameController(Context c) {
        appContext = c.getApplicationContext();
        loadQuestions();
        loadHighScores();
    }

    private void loadQuestions() {
        try {
            // Get assets
            AssetManager assets = appContext.getAssets();
            InputSource source = new InputSource( assets.open("questions.xml") );

            // create XML DOM and parse file
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDoc = db.parse(source);

            // convert XML into Question Models
            ArrayList<Question> parsedQuestions = new ArrayList<>();
            NodeList questions = xmlDoc.getElementsByTagName("question");

            for (int i = 0; i < questions.getLength(); i++) {
                Element q = (Element) questions.item(i);

                Question newQuestion = new Question(
                    q.getElementsByTagName("questionText").item(0).getFirstChild().getNodeValue(),
                    q.getElementsByTagName("answer1Text").item(0).getFirstChild().getNodeValue(),
                    q.getElementsByTagName("answer2Text").item(0).getFirstChild().getNodeValue(),
                    q.getElementsByTagName("answer3Text").item(0).getFirstChild().getNodeValue(),
                    Integer.parseInt( q.getElementsByTagName("correctAnswer").item(0).getFirstChild().getNodeValue() )
                );

                parsedQuestions.add(newQuestion);
            }

            // store results in property
            this.questions = parsedQuestions;
            Log.d(LOG_TAG, "Questions loaded and parsed");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Game Controller failed to load and parse XML\n" + e.getLocalizedMessage());
            this.questions = new ArrayList<>();
            Toast.makeText(appContext, "App Error!\nFailed to load questions.\nReinstall App!", Toast.LENGTH_LONG).show();
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }


    private final String dataFilename = "scores.json";

    private void loadHighScores() {
        try {
            String loadedData = "";

            FileInputStream   fis = appContext.openFileInput(dataFilename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader    br  = new BufferedReader(isr);

            String readString = br.readLine();
            while (readString != null) {
                loadedData += readString;
                readString = br.readLine();
            }

            isr.close();

            JSONArray parsedJSON = new JSONArray(loadedData);
            highScores = new ArrayList<>();

            for (int i = 0; i < parsedJSON.length(); i++) {
                JSONObject score = parsedJSON.getJSONObject(i);
                addHighScore(score.getString("name"), score.getInt("score"));
            }

            Log.d(LOG_TAG, "High scores loaded");
        } catch (Exception e) {
            Log.d(LOG_TAG, "Could not load high scores");
            highScores = new ArrayList<>();
        }
    }


    public void saveHighScores() {
        JSONArray scores = new JSONArray();

        for (HighScore hs : highScores) {
            try {
                JSONObject score = new JSONObject();
                score.put("name", hs.playerName);
                score.put("score", hs.score);

                scores.put(score);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String jsonEncodedData = scores.toString();

        // write to disk
        try {
            FileOutputStream fos = appContext.openFileOutput(dataFilename, Context.MODE_PRIVATE);
            fos.write( jsonEncodedData.getBytes() );
            fos.close();
            Log.d(LOG_TAG, "High scores have been saved");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Failed to save high scores");
        }
    }

    public void addHighScore(HighScore newScore) {
        highScores.add(newScore);
    }

    public void addHighScore(String playerName, int score) {
        HighScore newScore = new HighScore(playerName, score);
        addHighScore(newScore);
    }

    public ArrayList<HighScore> getHighScores() {
        return highScores;
    }
}
