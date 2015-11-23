package com.leonbaird.drawingandmedia;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;


public class MainActivity extends ActionBarActivity {

    VideoView videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get container
        LinearLayout container = (LinearLayout) findViewById(R.id.main_container);

        // create video player
        videoPlayer = new VideoView(this);
        videoPlayer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        container.addView(videoPlayer);

        // setup video player
        videoPlayer.setMediaController( new MediaController(this) );
        videoPlayer.setVideoPath("android.resource://com.leonbaird.drawingandmedia/raw/video");

        // make play
        videoPlayer.requestFocus();
        videoPlayer.start();

        // play audio
        MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
        mp.start();


        // apply animation
        Animation dropSpinIn = AnimationUtils.loadAnimation(this, R.anim.drop_in);
        videoPlayer.startAnimation(dropSpinIn);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if  (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
