package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class SpaceInvSurface extends Activity implements OnTouchListener {

	SpaceInvSurfaceView gameView;
	private float x, playersShipOffset, missileSpeed, offset = 0;
	private boolean moveThreadRunning = false, cancelMoveThread = false, left = true, right = false;

	private Shoot sH1 = new Shoot();
	private Shoot sH2 = new Shoot();
	private Shoot sH3 = new Shoot();
	private Shoot sH4 = new Shoot();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameView = new SpaceInvSurfaceView(this);
		gameView.setOnTouchListener(this);
		initialize();
		setContentView(gameView);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void initialize() {

		// Coordinates, etc.
		x = playersShipOffset = 0;
		SISingleton.getInstance().setShotCount(1);
		missileSpeed = (float) (0.007 * SISingleton.getInstance().height);
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
				x = event.getX(event.findPointerIndex(event.getPointerId(event.getActionIndex())));
			} else {
				x = event.getX();
			}
			if (!moveThreadRunning) {
				startMoveThread();
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
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
				synchronized (gameView) {
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
								Thread.sleep(10);
							} catch (InterruptedException e) {
								throw new RuntimeException("Could not wait between move repeats", e);
							}

						}
					} finally {
						//moveThreadRunning = false;
						cancelMoveThread = false;
					}
				}
			}
		};
		try {
			move.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		move.start();
	}

	private void movePlayersShip() {

		if (x < SISingleton.getInstance().width / 2) {
			playersShipOffset -= 0.0025 * SISingleton.getInstance().width;
			if (SISingleton.getInstance().width / 2 - SISingleton.getInstance().playerShip.getWidth() / 2 + playersShipOffset < 0) {
				playersShipOffset += 0.0025 * SISingleton.getInstance().width;
			}
		} else if (x >= SISingleton.getInstance().width / 2) {
			playersShipOffset += 0.0025 * SISingleton.getInstance().width;
			if (SISingleton.getInstance().width / 2 + SISingleton.getInstance().playerShip.getWidth() / 2 + playersShipOffset > SISingleton.getInstance().width) {
				playersShipOffset -= 0.0025 * SISingleton.getInstance().width;
			}
		}
	}

	public class SpaceInvSurfaceView extends SurfaceView implements Runnable {

		SurfaceHolder ourHolder;
		Thread ourThread = null;
		boolean isRunning = false;

		public SpaceInvSurfaceView(Context context) {
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

				// Drawing background
				canvas.drawBitmap(SISingleton.getInstance().background, 0, 0, null);

				// Players spaceship position
				canvas.drawBitmap(SISingleton.getInstance().playerShip, SISingleton.getInstance().width / 2 - SISingleton.getInstance().playerShip.getWidth() / 2 + playersShipOffset,
						(float) (0.82 * SISingleton.getInstance().height - SISingleton.getInstance().playerShip.getHeight() / 2), null);

				// Players shots
				sH1.drawShot(canvas, playersShipOffset, missileSpeed);
				if (SISingleton.getInstance().getShotCount() >= 2) {

					if ((sH1.getY() < SISingleton.getInstance().shotMaxRange) || (sH2.getY() != 0)) {

						// Log.i("SHooting", "SECOND_SHOT: " + sH2.getY());
						sH2.drawShot(canvas, playersShipOffset, missileSpeed);
					}
				}
				if (SISingleton.getInstance().getShotCount() >= 3) {

					if (((sH2.getY() != 0) && (sH2.getY() < SISingleton.getInstance().shotMaxRange)) || (sH3.getY() != 0)) {

						// Log.i("SHooting", "THIRD_SHOT: " + sH3.getY() );
						sH3.drawShot(canvas, playersShipOffset, missileSpeed);
					}
				}
				if (SISingleton.getInstance().getShotCount() == 4) {

					if (((sH3.getY() != 0) && (sH3.getY() < SISingleton.getInstance().shotMaxRange)) || (sH4.getY() != 0)) {

						// Log.i("SHooting", "FORTH_SHOT: " + sH4.getY() );
						sH4.drawShot(canvas, playersShipOffset, missileSpeed);
					}
				}

				canvas.drawBitmap(SISingleton.getInstance().enemyShip, (float) (0.1 * SISingleton.getInstance().width) + offset, (float) (0.1 * SISingleton.getInstance().height), null);
				canvas.drawBitmap(SISingleton.getInstance().enemyShip,
						(float) (0.1 * SISingleton.getInstance().width) + SISingleton.getInstance().emptySpace + SISingleton.getInstance().enemyShip.getWidth() + offset,
						(float) (0.1 * SISingleton.getInstance().height), null);
				canvas.drawBitmap(SISingleton.getInstance().enemyShip, (float) (0.1 * SISingleton.getInstance().width) + 2 * SISingleton.getInstance().emptySpace + 2
						* SISingleton.getInstance().enemyShip.getWidth() + offset, (float) (0.1 * SISingleton.getInstance().height), null);
				canvas.drawBitmap(SISingleton.getInstance().enemyShip, (float) (0.1 * SISingleton.getInstance().width) + 3 * SISingleton.getInstance().emptySpace + 3
						* SISingleton.getInstance().enemyShip.getWidth() + offset, (float) (0.1 * SISingleton.getInstance().height), null);
				canvas.drawBitmap(SISingleton.getInstance().enemyShip, (float) (0.1 * SISingleton.getInstance().width) + 4 * SISingleton.getInstance().emptySpace + 4
						* SISingleton.getInstance().enemyShip.getWidth() + offset, (float) (0.1 * SISingleton.getInstance().height), null);

				if (left) {
					if ((float) (0.1 * SISingleton.getInstance().width) + offset > 0) {
						offset -= 1;
					} else {
						left = false;
						right = true;
					}
				}

				if (right) {
					if ((float) (0.1 * SISingleton.getInstance().width) + 4 * SISingleton.getInstance().emptySpace + 5 * SISingleton.getInstance().enemyShip.getWidth() + offset < SISingleton
							.getInstance().width) {
						offset += 1;
					} else {
						left = true;
						right = false;
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		gameView.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		gameView.resume();
	}

}
