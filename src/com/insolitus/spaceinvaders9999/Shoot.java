package com.insolitus.spaceinvaders9999;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;

public class Shoot {	
	
	private boolean missileStartLoc = true;
	private float x = 0, y = 0, missileLocation = 0;
	
	public void drawShot(Canvas canvas, float weight, float height, float shipHeight, float shipOffset, float missileSpeed, Bitmap image, SoundPool sP, int shotSound){
		
		
		//Players shoot
		if (!missileStartLoc){						
			y = (float) (0.82*height-shipHeight/2 - image.getHeight());
		}
		else{
			x = weight/2 + shipOffset - image.getWidth()/2;
			y = (float) (0.82*height - shipHeight/2 - image.getHeight());
			if (shotSound != 0){
				sP.play(shotSound, 0.2f, 0.2f, 0, 0, 1);
			}			
			missileStartLoc = false;
		}
			
		if (y - missileLocation >= 0){
			canvas.drawBitmap(image, x, y - missileLocation, null);	
			missileLocation += missileSpeed;
		}
		else {
			missileLocation = 0;
			missileStartLoc = true;
			
		}
		
	}

	public float getY() {
		return y-missileLocation;
	}	

}
