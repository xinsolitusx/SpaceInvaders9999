package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.util.DisplayMetrics;
import android.util.Log;

public class GFXSurface extends Activity implements OnTouchListener {

	MBBSurface ourSV;
	private float x, playersShipOffset;
	private Bitmap playerShip, enemyShip, background, missile;
	SoundPool sp;
	private int shotSound = 0;
	private boolean moveThreadRunning = false;
	private boolean cancelMoveThread = false;
	private int shotCount = 2;
	private float missileSpeed, height = 0, width = 0, shotMaxRange = 0;
	Shoot sH1 = new Shoot();
	Shoot sH2 = new Shoot();
	Shoot sH3 = new Shoot();
	Shoot sH4 = new Shoot();

	public void setShotCount(int x) {
		this.shotCount = x;
	}

	public int getShotCount() {
		return shotCount;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ourSV = new MBBSurface(this);
		ourSV.setOnTouchListener(this);
		setContentView(R.layout.loading);
		initialize();
		setContentView(ourSV);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void initialize() {

		// coordinates
		x = playersShipOffset = 0;

		// Images
		playerShip = BitmapFactory.decodeResource(getResources(), R.drawable.ss1);
		enemyShip = BitmapFactory.decodeResource(getResources(), R.drawable.boss1);
		missile = BitmapFactory.decodeResource(getResources(), R.drawable.missiletest);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);

		resizeImages();
		initSounds();

		shotMaxRange = (float) ((0.82 * height - playerShip.getHeight() / 2 - missile.getHeight()) - ((0.82 * height - playerShip.getHeight() / 2 - missile
				.getHeight()) / getShotCount()));

	}

	private void resizeImages() {

		float scaleH;
		int newWidth, newHeight;

		// Getting display size
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		height = metrics.heightPixels;
		width = metrics.widthPixels;
		Log.i("Dimensions", "width: " + width);
		missileSpeed = (float) (0.007 * height);

		// Background resizing
		scaleH = (float) background.getHeight() / (float) height;
		newWidth = Math.round(background.getWidth() / scaleH);
		newHeight = Math.round(background.getHeight() / scaleH);
		background = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);

		// Players ship resizing
		scaleH = (float) playerShip.getHeight() / (float) (height / 11);
		newWidth = Math.round(playerShip.getWidth() / scaleH);
		newHeight = Math.round(playerShip.getHeight() / scaleH);
		playerShip = Bitmap.createScaledBitmap(playerShip, newWidth, newHeight, true);

		// Missile resizing
		scaleH = (float) missile.getHeight() / (float) (height / 30);
		newWidth = Math.round(missile.getWidth() / scaleH);
		newHeight = Math.round(missile.getHeight() / scaleH);
		missile = Bitmap.createScaledBitmap(missile, newWidth, newHeight, true);

		// Enemy Ship resizing
		scaleH = (float) enemyShip.getHeight() / (float) (height / 30);
		newWidth = Math.round(enemyShip.getWidth() / scaleH);
		newHeight = Math.round(enemyShip.getHeight() / scaleH);
		enemyShip = Bitmap.createScaledBitmap(enemyShip, newWidth, newHeight, true);
	}

	private void initSounds() {

		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		shotSound = sp.load(this, R.raw.beep8, 1);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSV.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ourSV.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		// Putting thread to sleep for FPS increase
		try {
			Thread.sleep((long) 16.666666666666667);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get masked (not specific to a pointer) action
		int maskedAction = event.getActionMasked();

		// Gathering coordinates
		switch (maskedAction) {

		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:

			if (event.getPointerCount() > 1) {
				// Log.i("Repeat", "MULTI: " + event.getActionIndex());
				x = event.getX(event.findPointerIndex(event.getPointerId(event.getActionIndex())));
			} else {
				x = event.getX();
			}
			if (!moveThreadRunning) {
				// Log.i("Repeat", "ACTION_NEW_POINTER_DOWN_STARTING: " + x);
				startMoveThread();
			}

			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:

			// Log.i("Repeat", "ACTION_UP " + moveThreadRunning);
			cancelMoveThread = true;
			moveThreadRunning = false;
			break;
		}
		return true;
	}

	private void startMoveThread() {

		final Handler handler = new Handler();
		Thread move = new Thread() {

			@Override
			public void run() {
				try {

					moveThreadRunning = true;
					while (!cancelMoveThread) {

						handler.post(new Runnable() {
							@Override
							public void run() {
								movePlayersShip();
							}
						});

						try {
							Thread.sleep(17);
						} catch (InterruptedException e) {
							throw new RuntimeException("Could not wait between move repeats", e);
						}

					}
				} finally {
					moveThreadRunning = false;
					cancelMoveThread = false;
				}
			}
		};
		synchronized (move) {
			move.interrupt();
		}
		move.start();
	}

	private void movePlayersShip() {

		if (x < width / 2) {
			playersShipOffset -= 0.0065 * width;
			if (width / 2 - playerShip.getWidth() / 2 + playersShipOffset < 0) {
				playersShipOffset += 0.0065 * width;
			}
		} else if (x >= width / 2) {
			playersShipOffset += 0.0065 * width;
			if (width / 2 + playerShip.getWidth() / 2 + playersShipOffset > width) {
				playersShipOffset -= 0.0065 * width;
			}
		}
	}

	public class MBBSurface extends SurfaceView implements Runnable {

		SurfaceHolder ourHolder;
		Thread ourThread = null;
		boolean isRunning = false;

		public MBBSurface(Context context) {
			super(context);
			ourHolder = getHolder();
		}

		public void pause() {

			isRunning = false;
			while (true) {
				try {
					ourThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			ourThread = null;
		}

		public void resume() {

			isRunning = true;
			ourThread = new Thread(this);
			ourThread.start();
		}

		@Override
		public void run() {

			while (isRunning) {
				if (!ourHolder.getSurface().isValid()) {
					continue;
				}

				Canvas canvas = ourHolder.lockCanvas();

				canvas.drawBitmap(background, 0, 0, null);

				// Players spaceship position
				canvas.drawBitmap(playerShip, width / 2 - playerShip.getWidth() / 2 + playersShipOffset,
						(float) (0.82 * height - playerShip.getHeight() / 2), null);

				// Log.i("SHooting", "SHOT_COUNT: " + getShotCount());
				// Players shoot
				sH1.drawShot(canvas, width, height, playerShip.getHeight(), playersShipOffset, missileSpeed, missile, sp, shotSound);
				if (getShotCount() >= 2) {

					if ((sH1.getY() < shotMaxRange) || (sH2.getY() != 0)) {

						// Log.i("SHooting", "SECOND_SHOT: " + sH2.getY());
						sH2.drawShot(canvas, width, height, playerShip.getHeight(), playersShipOffset, missileSpeed, missile, sp, shotSound);
					}
				}
				if (getShotCount() >= 3) {

					if (((sH2.getY() != 0) && (sH2.getY() < shotMaxRange)) || (sH3.getY() != 0)) {

						// Log.i("SHooting", "THIRD_SHOT: " + sH3.getY() );
						sH3.drawShot(canvas, width, height, playerShip.getHeight(), playersShipOffset, missileSpeed, missile, sp, shotSound);
					}
				}
				if (getShotCount() == 4) {

					if (((sH3.getY() != 0) && (sH3.getY() < shotMaxRange)) || (sH4.getY() != 0)) {

						// Log.i("SHooting", "FORTH_SHOT: " + sH4.getY() );
						sH4.drawShot(canvas, width, height, playerShip.getHeight(), playersShipOffset, missileSpeed, missile, sp, shotSound);
					}
				}

				/*
				 * if (!missileStartLoc){ holdPlayersY = (float)
				 * (0.85*height-playerShip.getHeight()/2) - missile.getHeight();
				 * } else{ holdPlayersX = width/2 + playersShipOffset -
				 * missile.getWidth()/2; holdPlayersY = (float)
				 * (0.85*height-playerShip.getHeight()/2) - missile.getHeight();
				 * missileStartLoc = false; }
				 * 
				 * if (holdPlayersY - missileLocation >= 0){
				 * canvas.drawBitmap(missile,holdPlayersX, holdPlayersY -
				 * missileLocation, null); missileLocation += missileSpeed; }
				 * else { missileLocation = 0; missileStartLoc = true;
				 * 
				 * }
				 */

				/*
				 * drawMissile(canvas, holdPlayersX, holdPlayersY);
				 * 
				 * drawMissile(canvas, holdPlayersX, holdPlayersY);
				 * 
				 * drawMissile(canvas, holdPlayersX, holdPlayersY);
				 */

				/*
				 * if (x != 0 && y != 0) {
				 * 
				 * 
				 * canvas.drawBitmap(playerShip, x - playerShip.getWidth() / 2,
				 * y - playerShip.getHeight() / 2, null); } if
				 * (playersShipOffset != 0 && sY != 0) {
				 * 
				 * canvas.drawBitmap(enemyShip, playersShipOffset -
				 * enemyShip.getWidth() / 2, sY - enemyShip.getHeight() / 2,
				 * null); } if (fX != 0 && fY != 0) {
				 * 
				 * canvas.drawBitmap(missile, fX - (missile.getWidth() / 2), fY
				 * - (playerShip.getHeight() / 2) - missile.getHeight() - aY,
				 * null); canvas.drawBitmap(playerShip, fX -
				 * playerShip.getWidth() / 2, fY - playerShip.getHeight() / 2,
				 * null); if (fX >= playersShipOffset - enemyShip.getWidth() / 2
				 * && fX <= playersShipOffset + enemyShip.getWidth() / 2){ if
				 * (fY - (playerShip.getHeight() / 2) - missile.getHeight() - aY
				 * <= sY + enemyShip.getHeight() / 2 ){ if (explosion != 0){
				 * sp.play(explosion, 1, 1, 0, 0, 1); } dX = dY = aX = aY =
				 * scaledX = scaledY = fX = fY = playersShipOffset = sY = 0; } }
				 * 
				 * } aY += scaledY;
				 */

				ourHolder.unlockCanvasAndPost(canvas);
			}
		}

	}

}
