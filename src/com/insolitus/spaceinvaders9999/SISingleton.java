package com.insolitus.spaceinvaders9999;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SISingleton {

	public MediaPlayer loopSong, menuSong;
	private final static int MAX_VOLUME = 100;
	private final static float volume = (float) (1 - (Math.log(MAX_VOLUME - 60) / Math.log(MAX_VOLUME)));
	private static SISingleton instance;
	public Typeface font;
	public SoundPool sp;
	public int shotSound = 0;
	public final float height, width, shotMaxRange, emptySpace;
	public final Bitmap playerShip, background, missile, enemyMissile, enemyShipOne, enemyShipTwo, enemyShipThree, enemyShipFour, instructions;
	public Paint textPaint = new Paint();
	private boolean startShooting = false;
	
	

	public boolean isStartShooting() {
		return startShooting;
	}

	public void setStartShooting(boolean startShooting) {
		this.startShooting = startShooting;
	}

	private int shotCount = 1, loopSongLenght, menuSongLenght;

	public int getShotCount() {
		return shotCount;
	}

	public void setShotCount(int shotCount) {
		this.shotCount = shotCount;
	}

	public static void initInstance(Context context, float h, float w, Bitmap plShip, Bitmap enemyOne, Bitmap enemyTwo, Bitmap enemyThree, Bitmap enemyFour, Bitmap pMissile, Bitmap eMissile,
			Bitmap backGround, Bitmap instruct) {
		if (instance == null) {
			// Create the instance
			instance = new SISingleton(context, h, w, plShip, enemyOne, enemyTwo, enemyThree, enemyFour, pMissile, eMissile, backGround, instruct);
		}
	}

	public static SISingleton getInstance() {
		// Return the instance
		return instance;
	}

	private SISingleton(Context context, float h, float w, Bitmap plShip, Bitmap enemyOne, Bitmap enemyTwo, Bitmap enemyThree, Bitmap enemyFour, Bitmap pMissile, Bitmap eMissile, Bitmap backGround,
			Bitmap instruction) {
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

		// Instructions resizing
		scale = instruction.getWidth() / width;
		newWidth = Math.round(instruction.getWidth() / scale);
		scale = instruction.getHeight() / height;
		newHeight = Math.round(instruction.getHeight() / scale);
		instructions = Bitmap.createScaledBitmap(instruction, newWidth, newHeight, true);

		// Players ship resizing
		scale = plShip.getHeight() / (height / 11);
		newWidth = Math.round(plShip.getWidth() / scale);
		newHeight = Math.round(plShip.getHeight() / scale);
		playerShip = Bitmap.createScaledBitmap(plShip, newWidth, newHeight, true);

		// Missile resizing
		scale = pMissile.getHeight() / (height / 35);
		newWidth = Math.round(pMissile.getWidth() / scale);
		newHeight = Math.round(pMissile.getHeight() / scale);
		missile = Bitmap.createScaledBitmap(pMissile, newWidth, newHeight, true);

		// Enemy missile resizing
		scale = eMissile.getHeight() / (height / 30);
		newWidth = Math.round(eMissile.getWidth() / scale);
		newHeight = Math.round(eMissile.getHeight() / scale);
		enemyMissile = Bitmap.createScaledBitmap(eMissile, newWidth, newHeight, true);

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

		// Enemy Four resizing
		scale = enemyFour.getWidth() / (width / 8);
		newWidth = Math.round(enemyFour.getWidth() / scale);
		newHeight = Math.round(enemyFour.getHeight() / scale);
		enemyShipFour = Bitmap.createScaledBitmap(enemyFour, newWidth, newHeight, true);

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

		font = Typeface.createFromAsset(context.getAssets(), "PrStart.ttf");
		textPaint.setTypeface(font);
		textPaint.setARGB(250, 200, 200, 200);
		textPaint.setTextSize(width/25);
		textPaint.setTextAlign(Align.CENTER);
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
