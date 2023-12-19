package com.papitomarket.service.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SBStartsAtBoot extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	
		Intent serviceIntent = new Intent(context, Updates.class);
        context.startService(serviceIntent);

	}

}
