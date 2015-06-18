package com.insolitus.spaceinvaders9999;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

public class SIApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize the singletons so their instances
		// are bound to the application process.
		initSingletons();
	}

	protected void initSingletons() {

		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		final float height = metrics.heightPixels;
		final float width = metrics.widthPixels;
		final Bitmap playerShip = BitmapFactory.decodeResource(getResources(), R.drawable.ss1);
		final Bitmap enemyShip = BitmapFactory.decodeResource(getResources(), R.drawable.boss1);
		final Bitmap missile = BitmapFactory.decodeResource(getResources(), R.drawable.missiletest);
		final Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);

		// Initialize the instance of MySingleton
		SISingleton.initInstance(this, height, width, playerShip, enemyShip, missile, background);
	}
}
