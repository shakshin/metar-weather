package com.shakshin.metar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootReceiver extends BroadcastReceiver {
	public void onReceive(Context context, Intent intent) {
		SharedPreferences settings = context.getSharedPreferences("com.shakshin.metar", 0);
		if (settings.getBoolean("autostart", false)) {
			context.startService(new Intent(context, UpdateService.class));
		}		
	}

}
