package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Splash extends Activity {

	
    
	private MediaPlayer flashSong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		flashSong = MediaPlayer.create(Splash.this, R.raw.beep8);
		flashSong.start();
		
		new LoadViewTask().execute();		
	}
		
	
	//To use the AsyncTask, it must be subclassed  
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>  
    {  
       
    	private ProgressBar pb_progressBar;  
    	
    	//Before running code in separate thread  
        @Override  
        protected void onPreExecute()  
        {  
        	 pb_progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);  
             //Sets the maximum value of the progress bar to 100  
             pb_progressBar.setMax(100);   
        }  
  
        //The code to be executed in a background thread.  
        @Override  
        protected Void doInBackground(Void... params)  
        {  
            /* This is just a code that delays the thread execution 4 times, 
             * during 850 milliseconds and updates the current progress. This 
             * is where the code that is going to be executed on a background 
             * thread must be placed. 
             */  
            try  
            {  
                //Get the current thread's token  
                synchronized (this)  
                {  
                    //Initialize an integer (that will act as a counter) to zero  
                    int counter = 0;  
                    //While the counter is smaller than four  
                    while(counter <= 5)  
                    {  
                        //Wait 850 milliseconds  
                        this.wait(400);  
                        //Increment the counter  
                        counter++;  
                        //Set the current progress.  
                        //This value is going to be passed to the onProgressUpdate() method.  
                        publishProgress(counter*20);  
                    }  
                }  
            }  
            catch (InterruptedException e)  
            {  
                e.printStackTrace();  
            }  
            return null;  
        }  
  
        //Update the progress  
        @Override  
        protected void onProgressUpdate(Integer... values)  
        {  
        	//Update the progress at the UI if progress value is smaller than 100  
            if(values[0] <= 100)             {  
                
                pb_progressBar.setProgress(values[0]);  
            }  
        }  
  
        //after executing the code in the thread  
        @Override  
        protected void onPostExecute(Void result)  
        {  
        	Intent openMenuActivity = new Intent(Splash.this, Menu.class);
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
