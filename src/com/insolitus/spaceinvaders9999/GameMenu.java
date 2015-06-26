package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameMenu extends Activity {

	Button startNewGame;
	Button highScore;
	Button exitGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);		
		Button startNewGame = (Button) findViewById(R.id.startNewGamebtn);
		startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(0), 0.5f, 0.5f, 1, 0, 1f); 
            	Intent launchNextActivity;
            	launchNextActivity = new Intent(GameMenu.this, SpaceInvSurface.class);
            	launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); 
            	startActivity(launchNextActivity);
            	finish();
            }
        });
		Button highScore = (Button) findViewById(R.id.highScorebtn);
		highScore.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(0), 0.5f, 0.5f, 1, 0, 1f); 
            	Intent launchNextActivity;
            	launchNextActivity = new Intent(GameMenu.this, HighScoreSQLView.class);
            	launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            	startActivity(launchNextActivity);
            	finish();
            }
        });
		Button exitGame = (Button) findViewById(R.id.exitGamebtn);
		exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {    
            	SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(0), 0.5f, 0.5f, 1, 0, 1f); 
				finish();				
            }
        });

	}		

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		SISingleton.getInstance().resumeMusic(1);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		SISingleton.getInstance().pauseMusic(1);
		super.onPause();
	}
}
