package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class GFX extends Activity {

	MyBringBack ourView;
	WakeLock wL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		PowerManager pM = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wL = pM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "whatever");
		
		
		super.onCreate(savedInstanceState);
		wL.acquire();
		ourView = new MyBringBack(this);
		setContentView(ourView);
	}

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		wL.acquire();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		wL.release();
	}

	
}
