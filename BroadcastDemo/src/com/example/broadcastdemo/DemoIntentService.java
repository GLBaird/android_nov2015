package com.example.broadcastdemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class DemoIntentService extends IntentService {

	public DemoIntentService() {
		super("DemoIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("IntentService", "Intent service is running");
	}

}
