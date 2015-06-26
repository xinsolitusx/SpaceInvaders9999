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
			Intent launchNextActivity;
        	launchNextActivity = new Intent(Splash.this, GameMenu.class);
        	launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);                  
        	launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        	startActivity(launchNextActivity);
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
