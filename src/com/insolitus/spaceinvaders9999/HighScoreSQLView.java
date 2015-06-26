package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class HighScoreSQLView extends Activity{
	
	TextView t10, HS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscore);
		
		TextView t10 = (TextView) findViewById(R.id.T10);
		TextView HS = (TextView) findViewById(R.id.HS);
		t10.setTypeface(SISingleton.getInstance().font);
		HS.setTypeface(SISingleton.getInstance().font);
		t10.setTextColor(Color.argb(255, 200, 200, 200));
		HS.setTextColor(Color.argb(255, 200, 200, 200));
		t10.setText("TOP 10");
		HS.setText("HIGHSCORES");

		
		String data = null;
		boolean success = true;
		
		TextView highscore = (TextView) findViewById(R.id.highscore);
		highscore.setTypeface(SISingleton.getInstance().font);
		highscore.setTextColor(Color.argb(255, 200, 200, 200));		
		
		try {
			HighScore info = new HighScore(this);
			info.open();
			data = info.getData();
			info.close();
		} catch (Exception e) {
			success = false;
		}		
		
		if (success){
			highscore.setText(data);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent launchNextActivity;
		launchNextActivity = new Intent(HighScoreSQLView.this, GameMenu.class);
		launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(launchNextActivity);
		finish();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		SISingleton.getInstance().resumeMusic(3);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		SISingleton.getInstance().pauseMusic(3);
		super.onPause();
	}
}
