package com.insolitus.spaceinvaders9999;

import android.graphics.Canvas;


public class Shoot {	
	
	private boolean missileStartLoc = true;
	private float x = 0, y = 0, missileLocation = 0;
	
	public void drawShot(Canvas canvas, float shipOffset, float missileSpeed){
		
		
		//Players shoot
		if (missileStartLoc){			
			
			x = SISingleton.getInstance().width/2 + shipOffset - SISingleton.getInstance().missile.getWidth()/2;
			y = (float) (0.82*SISingleton.getInstance().height - SISingleton.getInstance().playerShip.getHeight()/2 - SISingleton.getInstance().missile.getHeight());
			if (SISingleton.getInstance().shotSound != 0){
				SISingleton.getInstance().sp.play(SISingleton.getInstance().shotSound, 0.2f, 0.2f, 0, 0, 1);
			}			
			missileStartLoc = false;
		}
			
		if (y - missileLocation >= 0){
			canvas.drawBitmap(SISingleton.getInstance().missile, x, y - missileLocation, null);	
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
