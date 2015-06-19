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
		final Bitmap playerShip = BitmapFactory.decodeResource(getResources(), R.drawable.playerships_orange);
		final Bitmap enemyShipBoss = BitmapFactory.decodeResource(getResources(), R.drawable.boss4);
		final Bitmap enemyShip1 = BitmapFactory.decodeResource(getResources(), R.drawable.enemy5);
		final Bitmap enemyShip2 = BitmapFactory.decodeResource(getResources(), R.drawable.enemy2);
		final Bitmap enemyShip3 = BitmapFactory.decodeResource(getResources(), R.drawable.enemy8);
		final Bitmap missile = BitmapFactory.decodeResource(getResources(), R.drawable.missiletest);
		final Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.backgorund1);

		// Initialize the instance of MySingleton
		SISingleton.initInstance(this, height, width, playerShip, enemyShipBoss, enemyShip1, enemyShip2, enemyShip3, missile, background);
	}

}
