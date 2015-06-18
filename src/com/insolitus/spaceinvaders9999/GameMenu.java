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
            }
        });

	}

}
