package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

public class Splash extends Activity {

	private MediaPlayer flashSong;
	protected SIApplication app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		flashSong = MediaPlayer.create(Splash.this, R.raw.beep8);
		flashSong.start();
		
		new LoadViewTask().execute();
	}

	// To use the AsyncTask, it must be subclassed
	private class LoadViewTask extends AsyncTask<Void, Void, Void> {			

		

		@Override
		protected Void doInBackground(Void... params) {
			// Get the current thread's token
			synchronized (this) {
				
				app = (SIApplication)getApplication();	
				// Putting thread to sleep for FPS increase
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		flashSong.release();
		finish();
	}

}
