package com.insolitus.spaceinvaders9999;

import android.graphics.Canvas;

public class Enemys {

	private boolean startLocation = true;
	private float x, y, offSet;

	public void setLocation(float xPos) {

		this.x = xPos;
		offSet = SISingleton.getInstance().height - SISingleton.getInstance().enemyShip.getHeight() ;
	}

	public void drawEnemy(Canvas canvas) {

		// Players shoot
		if (!startLocation) {
			y = (float) (0.05 * SISingleton.getInstance().height);
		} else {
			x = (float) (0.05 * SISingleton.getInstance().height);
			y = (float) (0.05 * SISingleton.getInstance().height);
			if (SISingleton.getInstance().shotSound != 0) {
				SISingleton.getInstance().sp.play(SISingleton.getInstance().shotSound, 0.2f, 0.2f, 0, 0, 1);
			}
			startLocation = false;
		}		
	}

	
}
