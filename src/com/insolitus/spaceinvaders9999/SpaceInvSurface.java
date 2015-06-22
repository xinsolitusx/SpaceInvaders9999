package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
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

	private int backKeyPressed = 0;
	private float x = 0;
	private boolean moveThreadRunning = false, cancelMoveThread = false, newGame = true;

	private Paint textPaint = new Paint();
	private Player player = new Player();
	private Levels level = new Levels();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameView = new SpaceInvSurfaceView(this);
		gameView.setOnTouchListener(this);
		setContentView(gameView);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

			gameView.resume();
			newGame = false;
			backKeyPressed = 0;

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
		new Thread() {

			@Override
			public void run() {
				try {

					moveThreadRunning = true;
					while (!cancelMoveThread) {

						handler.post(new Runnable() {
							@Override
							public void run() {
								player.movePlayersShip(x);
							}
						});

						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							throw new RuntimeException("Could not wait between move repeats", e);
						}

					}
				} finally {
					moveThreadRunning = false;
					cancelMoveThread = false;
				}
			}
		}.start();
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

				if (!newGame) {
					// Drawing background
					canvas.drawBitmap(SISingleton.getInstance().background, 0, 0, null);

					// Level drawing
					level.drawEnemys(canvas);

					// Players spaceship
					player.drawPlayer(canvas);
					if (!level.startShooting()) {
						player.drawShot(canvas);
					} else {
						canvas.drawText("GET READY !!!", SISingleton.getInstance().width / 2, (float) (0.45 * SISingleton.getInstance().height), SISingleton.getInstance().textPaint);
					}
				} else {
					
					// Drawing background
					canvas.drawBitmap(SISingleton.getInstance().background, 0, 0, null);
					
					canvas.drawBitmap(SISingleton.getInstance().instructions, 0, 0, null);
					
					// Players spaceship
					player.drawPlayer(canvas);
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
	public void onBackPressed() {

		backKeyPressed++;
		if (backKeyPressed == 1) {
			gameView.pause();
			AlertDialog alertbox = new AlertDialog.Builder(this).setMessage("Do you want to return to menu and lose your progress?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				// do something when the button is clicked
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent openMenuActivity = new Intent(SpaceInvSurface.this, GameMenu.class);
					startActivity(openMenuActivity);
					gameView.resume();
					finish();
				}
			}).setNegativeButton("No", new DialogInterface.OnClickListener() {

				// do something when the button is clicked
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					backKeyPressed = 0;
					gameView.resume();
				}
			}).show();
		} else {
			backKeyPressed = 0;
			gameView.resume();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		gameView.resume();
		super.onPause();
		SISingleton.getInstance().pauseMusic(2);
		gameView.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SISingleton.getInstance().resumeMusic(2);
		gameView.resume();
		backKeyPressed = 0;
	}
}
