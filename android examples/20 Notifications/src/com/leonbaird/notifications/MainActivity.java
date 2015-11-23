package com.leonbaird.notifications;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button noticeButton = (Button) findViewById(R.id.button1);
		noticeButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(android.R.drawable.stat_notify_more)
		       .setContentTitle("Important Notice!")
		       .setContentText("News from notifications are not very important. Please get back to all that work you on your phone.");
		
		// intent to open your app from a notification
		Intent appIntent = new Intent(this, MainActivity.class);
		
		// a back stack to close your app and return to home screen after notice has been read
		TaskStackBuilder stack = TaskStackBuilder.create(this);
		stack.addParentStack(MainActivity.class);
		stack.addNextIntent(appIntent);
		
		// create pending intent to post this notice
		PendingIntent pending = stack.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
		// add pending intent to builder
		builder.setContentIntent(pending);
		
		// add sound to notification
		Uri soundURI = Uri.parse("android.resources://com.leonbaird.notifications/"+R.raw.beep);
		builder.setSound(soundURI);
		
		// make notification
		Notification notice = builder.build();
		
		// Get notifications manager and send
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(0, notice);
	}

}
