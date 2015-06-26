package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOver extends Activity {

	Button startNewGame;
	Button highScore;
	Button mainMenu;
	TextView gameover, highscorename, highscore, hsNote;
	EditText hsName;
	private int score;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);

		Bundle extras = getIntent().getExtras();
		score = extras.getInt("HighScore");

		TextView gameover = (TextView) findViewById(R.id.gameover);
		TextView highscorename = (TextView) findViewById(R.id.highscorename);
		TextView highscore = (TextView) findViewById(R.id.highscore);
		TextView hsNote = (TextView) findViewById(R.id.hsNote);
		final EditText hsName = (EditText) findViewById(R.id.hsName);

		gameover.setTypeface(SISingleton.getInstance().font);
		highscorename.setTypeface(SISingleton.getInstance().font);
		highscore.setTypeface(SISingleton.getInstance().font);
		hsNote.setTypeface(SISingleton.getInstance().font);
		hsName.setTypeface(SISingleton.getInstance().font);

		gameover.setTextColor(Color.argb(255, 200, 200, 200));
		highscorename.setTextColor(Color.argb(255, 200, 200, 200));
		highscore.setTextColor(Color.argb(255, 200, 200, 200));
		hsNote.setTextColor(Color.argb(255, 200, 200, 200));
		hsName.setTextColor(Color.argb(255, 200, 200, 200));

		gameover.setText("GAME OVER");
		highscorename.setText("YOUR SCORE");
		highscore.setText(score + "");
		hsNote.setText("Enter name to save your score");

		Button startNewGame = (Button) findViewById(R.id.startNewGamebtn);
		startNewGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(0), 0.5f, 0.5f, 1, 0, 1f);
				name = hsName.getText().toString();
				saveToDatabase();
				Intent launchNextActivity;
				launchNextActivity = new Intent(GameOver.this, SpaceInvSurface.class);
				startActivity(launchNextActivity);
				finish();
			}
		});
		Button highScore = (Button) findViewById(R.id.highScorebtn);
		highScore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(0), 0.5f, 0.5f, 1, 0, 1f);
				name = hsName.getText().toString();
				saveToDatabase();
				Intent launchNextActivity;
				launchNextActivity = new Intent(GameOver.this, HighScoreSQLView.class);
				launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(launchNextActivity);
				finish();
			}
		});
		Button mainMenu = (Button) findViewById(R.id.mainMenubtn);
		mainMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(0), 0.5f, 0.5f, 1, 0, 1f);
				name = hsName.getText().toString();
				saveToDatabase();
				Intent launchNextActivity;
				launchNextActivity = new Intent(GameOver.this, GameMenu.class);
				launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(launchNextActivity);
				finish();
			}
		});

	}

	private void saveToDatabase() {
		
		if (!name.matches("")) {
			HighScore entry = new HighScore(GameOver.this);
			entry.open();
			entry.createEntry(name, score);
			entry.close();
		}
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
