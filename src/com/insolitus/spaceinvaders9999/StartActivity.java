package com.insolitus.spaceinvaders9999;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends Activity {
	
	int counter;
	Button add, sub;
	TextView display;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		counter = 0 ;
		add = (Button) findViewById(R.id.Button01);
		sub = (Button) findViewById(R.id.button1);
		display = (TextView) findViewById(R.id.textView1);
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				try {
					counter ++;
					display.setText("" + counter);
				} catch (Exception e) {
					display.setText("ERROR");
				}
				
			}
		});
		
		sub.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				try {
					counter --;
					display.setText("" + counter);
				} catch (Exception e) {
					display.setText("ERROR");
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
