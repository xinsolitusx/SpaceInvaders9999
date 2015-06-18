package com.insolitus.spaceinvaders9999;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;

public class SISingleton {

	private static SISingleton instance;
	public SoundPool sp;
	public int shotSound = 0;
	public final float height, width, shotMaxRange, emptySpace;
	public final Bitmap playerShip, background, missile, enemyShip;

	private int shotCount = 1;

	public int getShotCount() {
		return shotCount;
	}

	public void setShotCount(int shotCount) {
		this.shotCount = shotCount;
	}

	public static void initInstance(Context c, float h, float w, Bitmap pShip, Bitmap eShip, Bitmap mi, Bitmap bg) {
		if (instance == null) {
			// Create the instance
			instance = new SISingleton(c, h, w, pShip, eShip, mi, bg);
		}
	}

	public static SISingleton getInstance() {
		// Return the instance
		return instance;
	}

	private SISingleton(Context context, float h, float w, Bitmap pShip, Bitmap eShip, Bitmap mi, Bitmap bg) {
		// Constructor hidden because this is a singleton

		// Getting display size
		height = h;
		width = w;
		float scaleH;
		int newWidth, newHeight;

		// Background resizing
		scaleH = bg.getHeight() / height;
		newWidth = Math.round(bg.getWidth() / scaleH);
		newHeight = Math.round(bg.getHeight() / scaleH);
		background = Bitmap.createScaledBitmap(bg, newWidth, newHeight, true);

		// Players ship resizing
		scaleH = pShip.getHeight() / (height / 11);
		newWidth = Math.round(pShip.getWidth() / scaleH);
		newHeight = Math.round(pShip.getHeight() / scaleH);
		playerShip = Bitmap.createScaledBitmap(pShip, newWidth, newHeight, true);

		// Missile resizing
		scaleH = mi.getHeight() / (height / 30);
		newWidth = Math.round(mi.getWidth() / scaleH);
		newHeight = Math.round(mi.getHeight() / scaleH);
		missile = Bitmap.createScaledBitmap(mi, newWidth, newHeight, true);

		// Enemy Ship resizing
		scaleH = eShip.getHeight() / (height / 18);
		newWidth = Math.round(eShip.getWidth() / scaleH);
		newHeight = Math.round(eShip.getHeight() / scaleH);
		enemyShip = Bitmap.createScaledBitmap(eShip, newWidth, newHeight, true);

		// Players missile max range
		shotMaxRange = (float) ((0.82 * height - playerShip.getHeight() / 2 - missile.getHeight()) - ((0.82 * height - playerShip.getHeight() / 2 - missile.getHeight()) / getShotCount()));

		// Space between enemy ships
		emptySpace = (float) ((0.8*width - 5*enemyShip.getWidth())/6);

		// Sounds
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		shotSound = sp.load(context, R.raw.beep8, 1);
	}

}
