package com.insolitus.spaceinvaders9999;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.SparseArray;

@SuppressLint("UseSparseArrays")
public class SISingleton {

	private static int SOUNDPOOLSND_BUTTTON = 0;
	private static int SOUNDPOOLSND_PLAYER_SHOT = 1;
	private static int SOUNDPOOLSND_ENEMY_SHOT = 2;
	private static int SOUNDPOOLSND_PLAYER_HIT = 3;
	private static int SOUNDPOOLSND_ENEMY_HIT = 4;
	private static int SOUNDPOOLSND_NEW_LIFE = 5;
	private static int SOUNDPOOLSND_PLAYER_DESTROYED = 6;	
	public final SparseArray<Integer> mSoundPoolMap = new SparseArray<Integer>();
	public SoundPool sp;
	public MediaPlayer loopSong, menuSong, gameOverSong;			
	private int loopSongLenght, menuSongLenght, gameOverSongLenght;

	public final float height, width, emptySpace;
	public final Bitmap playerShip, background, missile, enemyMissile, enemyShipOne, enemyShipTwo, enemyShipThree, enemyShipFour, instructions;
	
	public Typeface font;
	public Paint textPaint = new Paint();
	
	private static SISingleton instance;
	private boolean startShooting = false;
	private float enemyMissileSpeed, playerMissileSpeed;

	public float getEnemyMissileSpeed() {
		return enemyMissileSpeed;
	}

	public float getPlayerMissileSpeed() {
		return playerMissileSpeed;
	}

	public void incEnemyMissileSpeed(float speedInc) {
		this.enemyMissileSpeed *= speedInc;
	}

	public void setEnemyMissileSpeed(float speed) {
		this.enemyMissileSpeed = speed;
	}

	public void setPlayerMissileSpeed(float playerMissileSpeed) {
		this.playerMissileSpeed = playerMissileSpeed;
	}

	public void incPlayerMissileSpeed(float speedInc) {
		this.playerMissileSpeed *= speedInc;
	}

	public boolean isStartShooting() {
		return startShooting;
	}

	public void setStartShooting(boolean startShooting) {
		this.startShooting = startShooting;
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

		// Space between enemy ships
		emptySpace = (float) ((0.82 * width - 5 * enemyShipOne.getWidth()) / 4);

		// Enemy missile speed
		enemyMissileSpeed = (float) (0.005 * height);

		// Player missile speed
		playerMissileSpeed = (float) (0.005 * height);

		// Sounds
		sp = new SoundPool(7, AudioManager.STREAM_MUSIC, 0);
		mSoundPoolMap.put(SOUNDPOOLSND_BUTTTON, sp.load(context, R.raw.klik1, 1));
		mSoundPoolMap.put(SOUNDPOOLSND_PLAYER_SHOT, sp.load(context, R.raw.mo1, 1));
		mSoundPoolMap.put(SOUNDPOOLSND_ENEMY_SHOT, sp.load(context, R.raw.shoot, 1));
		mSoundPoolMap.put(SOUNDPOOLSND_PLAYER_HIT, sp.load(context, R.raw.explode, 1));
		mSoundPoolMap.put(SOUNDPOOLSND_ENEMY_HIT, sp.load(context, R.raw.alienhit, 1));
		mSoundPoolMap.put(SOUNDPOOLSND_NEW_LIFE, sp.load(context, R.raw.splash, 1));
		mSoundPoolMap.put(SOUNDPOOLSND_PLAYER_DESTROYED, sp.load(context, R.raw.saucerhit, 1));
		
		loopSong = MediaPlayer.create(context, R.raw.loopsong);
		loopSong.setLooping(true);
		loopSong.setVolume(0.5f, 0.5f);
		menuSong = MediaPlayer.create(context, R.raw.menusong);
		menuSong.setLooping(true);
		menuSong.setVolume(0.4f, 0.4f);
		gameOverSong = MediaPlayer.create(context, R.raw.gameoversong);
		gameOverSong.setLooping(true);
		gameOverSong.setVolume(1f, 1f);

		font = Typeface.createFromAsset(context.getAssets(), "PrStart.ttf");
		textPaint.setTypeface(font);
		textPaint.setARGB(250, 200, 200, 200);
		textPaint.setTextSize(width / 25);
		textPaint.setTextAlign(Align.CENTER);
	}

	public void pauseMusic(int x) {

		switch (x) {
		case 1:
			if (menuSong.isPlaying()) {
				menuSong.pause();
				menuSongLenght = menuSong.getCurrentPosition();
			}
			break;
		case 2:
			if (loopSong.isPlaying()) {
				loopSong.pause();
				loopSongLenght = loopSong.getCurrentPosition();
			}
			break;
		case 3:
			if (gameOverSong.isPlaying()) {
				gameOverSong.pause();
				gameOverSongLenght = menuSong.getCurrentPosition();
			}
			break;
		}		
	}

	public void resumeMusic(int x) {
		
		switch (x) {
		case 1:
			if (menuSong.isPlaying() == false) {
				menuSong.seekTo(menuSongLenght);
				menuSong.start();
			}
			break;
		case 2:
			if (loopSong.isPlaying() == false) {
				loopSong.seekTo(loopSongLenght);
				loopSong.start();
			}
			break;
		case 3:
			if (gameOverSong.isPlaying() == false) {
				gameOverSong.seekTo(gameOverSongLenght);
				gameOverSong.start();
			}
			break;
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
