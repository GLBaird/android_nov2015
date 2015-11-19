package com.example.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiverCustom extends BroadcastReceiver {
	
	public static final String CUSTOM_INTENT = "com.example.CUSTOM_INTENT";
	
	
	@Override
	public void onReceive(Context context, Intent arg1) {
		Log.d("Broadcast Receiver Custom", "Here we go, just like an event handler");
		
		Intent demoService = new Intent(context, DemoService.class);
		context.startService(demoService);
		
		Intent demoIntentService = new Intent(context, DemoIntentService.class);
		context.startService(demoIntentService);
	}

}
