package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

public class Splash extends Activity {

	private MediaPlayer splashSong;
	protected SIApplication app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		splashSong = MediaPlayer.create(Splash.this, R.raw.splash);
		splashSong.setVolume(0.2f, 0.2f);
		splashSong.start();
		
		new LoadViewTask().execute();
	}

	// To use the AsyncTask, it must be subclassed
	private class LoadViewTask extends AsyncTask<Void, Void, Void> {			

		

		@Override
		protected Void doInBackground(Void... params) {
			// Get the current thread's token
			synchronized (this) {
				
				
				// Putting thread to sleep for FPS increase
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				app = (SIApplication)getApplication();	
			}
			return null;
		}		

		// after executing the code in the thread
		@Override
		protected void onPostExecute(Void result) {
			Intent openMenuActivity = new Intent(Splash.this, GameMenu.class);
			startActivity(openMenuActivity);
			// close this activity
			finish();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		splashSong.release();
		finish();
	}

	
}
