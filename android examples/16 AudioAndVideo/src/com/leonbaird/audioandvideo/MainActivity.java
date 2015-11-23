package com.leonbaird.audioandvideo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button audioButton = (Button) findViewById(R.id.soundButton);
		audioButton.setOnClickListener(this);
		
		setupVideo();
		
	}
	
	@Override
	public void onClick(View button) {
		// PLAY AUDIO
		MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
		mp.start();
	}
	
	private void setupVideo() {
		VideoView player = (VideoView) findViewById(R.id.videoView);
		player.setVideoPath("android.resource://com.leonbaird.audioandvideo/raw/render");
		player.setMediaController( new MediaController(this) );
		// player.requestFocus();
		// player.start();
	}

}
