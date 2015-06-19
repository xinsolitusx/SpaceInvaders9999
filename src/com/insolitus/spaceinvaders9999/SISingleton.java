package com.insolitus.spaceinvaders9999;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SISingleton {

	public MediaPlayer loopSong, menuSong;
	private final static int MAX_VOLUME = 100;
	private final static float volume = (float) (1 - (Math.log(MAX_VOLUME - 60) / Math.log(MAX_VOLUME)));
	private static SISingleton instance;
	public SoundPool sp;
	public int shotSound = 0;
	public final float height, width, shotMaxRange, emptySpace;
	public final Bitmap playerShip, background, missile, enemyShipBoss, enemyShipOne, enemyShipTwo, enemyShipThree;

	private int shotCount = 1, loopSongLenght, menuSongLenght;

	public int getShotCount() {
		return shotCount;
	}

	public void setShotCount(int shotCount) {
		this.shotCount = shotCount;
	}

	public static void initInstance(Context context, float h, float w, Bitmap plShip, Bitmap enemyBoss, Bitmap enemyOne, Bitmap enemyTwo, Bitmap enemyThree, Bitmap mi, Bitmap backGround) {
		if (instance == null) {
			// Create the instance
			instance = new SISingleton(context, h, w, plShip, enemyBoss, enemyOne, enemyTwo, enemyThree, mi, backGround);
		}
	}

	public static SISingleton getInstance() {
		// Return the instance
		return instance;
	}

	private SISingleton(Context context, float h, float w, Bitmap plShip, Bitmap enemyBoss, Bitmap enemyOne, Bitmap enemyTwo, Bitmap enemyThree, Bitmap mi, Bitmap backGround) {
		// Constructor hidden because this is a singleton

		// Getting display size
		height = h;
		width = w;
		float scale;
		int newWidth, newHeight;

		// Background resizing
		scale = backGround.getHeight() / height;
		newWidth = Math.round(backGround.getWidth() / scale);
		newHeight = Math.round(backGround.getHeight() / scale);
		background = Bitmap.createScaledBitmap(backGround, newWidth, newHeight, true);

		// Players ship resizing
		scale = plShip.getHeight() / (height / 11);
		newWidth = Math.round(plShip.getWidth() / scale);
		newHeight = Math.round(plShip.getHeight() / scale);
		playerShip = Bitmap.createScaledBitmap(plShip, newWidth, newHeight, true);

		// Missile resizing
		scale = mi.getHeight() / (height / 30);
		newWidth = Math.round(mi.getWidth() / scale);
		newHeight = Math.round(mi.getHeight() / scale);
		missile = Bitmap.createScaledBitmap(mi, newWidth, newHeight, true);

		// Enemy Boss resizing
		scale = enemyBoss.getHeight() / (height / 8);
		newWidth = Math.round(enemyBoss.getWidth() / scale);
		newHeight = Math.round(enemyBoss.getHeight() / scale);
		enemyShipBoss = Bitmap.createScaledBitmap(enemyBoss, newWidth, newHeight, true);

		// Enemy One resizing
		scale = enemyOne.getWidth() / (width / 8);
		newWidth = Math.round(enemyOne.getWidth() / scale);
		newHeight = Math.round(enemyOne.getHeight() / scale);
		enemyShipOne = Bitmap.createScaledBitmap(enemyOne, newWidth, newHeight, true);

		// Enemy Two resizing
		scale = enemyTwo.getWidth() / (width / 8);
		newWidth = Math.round(enemyTwo.getWidth() / scale);
		newHeight = Math.round(enemyTwo.getHeight() / scale);
		enemyShipTwo = Bitmap.createScaledBitmap(enemyTwo, newWidth, newHeight, true);

		// Enemy Three resizing
		scale = enemyThree.getWidth() / (width / 8);
		newWidth = Math.round(enemyThree.getWidth() / scale);
		newHeight = Math.round(enemyThree.getHeight() / scale);
		enemyShipThree = Bitmap.createScaledBitmap(enemyThree, newWidth, newHeight, true);

		// Players missile max range
		shotMaxRange = (float) ((0.82 * height - playerShip.getHeight() / 2 - missile.getHeight()) - ((0.82 * height - playerShip.getHeight() / 2 - missile.getHeight()) / getShotCount()));

		// Space between enemy ships
		emptySpace = (float) ((0.82 * width - 5 * enemyShipOne.getWidth()) / 4);

		// Sounds
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		shotSound = sp.load(context, R.raw.splash, 1);

		loopSong = MediaPlayer.create(context, R.raw.loopsong);
		loopSong.setLooping(true);
		loopSong.setVolume(volume, volume);
		menuSong = MediaPlayer.create(context, R.raw.menusong);
		menuSong.setLooping(true);
		menuSong.setVolume(0.5f, 0.5f);
	}

	public void pauseMusic(int x) {
		if (x == 2) {
			if (loopSong.isPlaying()) {
				loopSong.pause();
				loopSongLenght = loopSong.getCurrentPosition();
			}
		} else {
			if (menuSong.isPlaying()) {
				menuSong.pause();
				menuSongLenght = menuSong.getCurrentPosition();
			}
		}
	}

	public void resumeMusic(int x) {
		if (x == 2) {
			if (loopSong.isPlaying() == false) {
				loopSong.seekTo(loopSongLenght);
				loopSong.start();
			}
		} else {
			if (menuSong.isPlaying() == false) {
				menuSong.seekTo(menuSongLenght);
				menuSong.start();
			}
		}
	}

	public void stopMusic(MediaPlayer player) {
		if (player != null) {
			try {
				player.stop();
				player.release();
			} finally {
				player = null;
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		stopMusic(loopSong);
		stopMusic(menuSong);
	}

}
