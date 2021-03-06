package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

	private int backKeyPressed = 0;
	private float x = 0;
	private boolean moveThreadRunning = false, cancelMoveThread = false, newGame = true;

	private Player player = new Player();
	private Levels level = new Levels();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameView = new SpaceInvSurfaceView(this);
		gameView.setOnTouchListener(this);
		setContentView(gameView);
		SISingleton.getInstance().setEnemyMissileSpeed((float) (0.006 * SISingleton.getInstance().height));
		SISingleton.getInstance().setPlayerMissileSpeed((float) (0.005 * SISingleton.getInstance().height));
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

	private class SpaceInvSurfaceView extends SurfaceView implements Runnable {

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

				//Draw everything
				if (!newGame) {

					if (player.getLife() > 0) {
						// Drawing background
						canvas.drawBitmap(SISingleton.getInstance().background, 0, 0, null);

						// Drawing everything if level isn't finished
						if (!level.isFinnished()) {

							// Draw life and score
							canvas.drawText(player.getLife() + " ♥", SISingleton.getInstance().width - SISingleton.getInstance().width / 9, (float) (0.04 * SISingleton.getInstance().height),
									SISingleton.getInstance().textPaint);
							canvas.drawText(player.getHighscore() + "", (float) (SISingleton.getInstance().width / 8.5), (float) (0.04 * SISingleton.getInstance().height),
									SISingleton.getInstance().textPaint);

							// Players spaceship
							player.drawPlayer(canvas);							

							// Start shooting after enemy arrival finished
							if (SISingleton.getInstance().isStartShooting()) {
								if (player.getHits() < 15) {
									player.drawShot(canvas);
									player.detectShotTargetColl(level.getEnemysArray());
									player.setHighscore();
								}
							} else {
								canvas.drawText("GET READY !!!", SISingleton.getInstance().width / 2, (float) (0.45 * SISingleton.getInstance().height), SISingleton.getInstance().textPaint);
							}

							// Level drawing
							level.drawEnemys(canvas, player.getX());

							// Setting player life if hit
							if (level.getPlayerHit()) {
								level.setPlayerHit(false);
								player.setLife();
							}	

						// Adjust enemy, generate new level, etc. when player clears current wave
						} else {
							SISingleton.getInstance().incEnemyMissileSpeed(1.1f);
							player.setHits(0);
							level = new Levels();
						}
					
					// Game over
					} else {
						canvas.drawBitmap(SISingleton.getInstance().background, 0, 0, null);
						canvas.drawText("GAME OVER :[", SISingleton.getInstance().width / 2, (float) (0.3 * SISingleton.getInstance().height), SISingleton.getInstance().textPaint);
						canvas.drawText("PLEASE WAIT", SISingleton.getInstance().width / 2, (float) (0.5 * SISingleton.getInstance().height), SISingleton.getInstance().textPaint);
						playerDeath();
					}

				//Draw instructions before player touches screen
				} else {

					// Drawing background
					canvas.drawBitmap(SISingleton.getInstance().background, 0, 0, null);

					// Drawing instructions
					canvas.drawBitmap(SISingleton.getInstance().instructions, 0, 0, null);

					// Players spaceship
					player.drawPlayer(canvas);
				}

				ourHolder.unlockCanvasAndPost(canvas);
			}
		}

	}

	private void playerDeath() {

		Intent launchNextActivity;
		launchNextActivity = new Intent(SpaceInvSurface.this, GameOver.class);
		launchNextActivity.putExtra("HighScore", player.getHighscore());
		startActivity(launchNextActivity);
		finish();

	}

	@Override
	public void onBackPressed() {

		backKeyPressed++;
		if (backKeyPressed == 1) {

			gameView.pause();
			new AlertDialog.Builder(this).setMessage("Do you want to return to menu and lose your progress?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {

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
