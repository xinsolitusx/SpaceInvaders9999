package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameMenu extends Activity {

	Button startNewGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);		
		Button startNewGame = (Button) findViewById(R.id.startNewGamebtn);
		startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	Intent openMenuActivity = new Intent(GameMenu.this, SpaceInvSurface.class);
				startActivity(openMenuActivity);
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
