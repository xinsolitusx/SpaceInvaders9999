package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity {

	MediaPlayer flashSong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		flashSong = MediaPlayer.create(Splash.this, R.raw.beep8);
		flashSong.start();
		Thread timer = new Thread() {

			@Override
			public void run() {

				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openStartActivity = new Intent(Splash.this, Menu.class);
					startActivity(openStartActivity);
				}
			}
		};

		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		flashSong.release();
		finish();
	}

}
