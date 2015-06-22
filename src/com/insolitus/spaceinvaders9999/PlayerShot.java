package com.insolitus.spaceinvaders9999;

import android.graphics.Canvas;

public class PlayerShot extends Shot {

	@Override
	public void drawShot(Canvas canvas, float shipX, float shipY) {
		// TODO Auto-generated method stub
		if (missileStartLoc) {

			x = shipX + SISingleton.getInstance().playerShip.getWidth() / 2 - SISingleton.getInstance().missile.getWidth() / 2;
			y = shipY - SISingleton.getInstance().missile.getHeight();
			if (SISingleton.getInstance().shotSound != 0) {
				SISingleton.getInstance().sp.play(SISingleton.getInstance().shotSound, 0.2f, 0.2f, 0, 0, 1);
			}
			missileStartLoc = false;
		}

		if (y - missileOffset >= 0) {
			canvas.drawBitmap(SISingleton.getInstance().missile, x, y - missileOffset, null);
			missileOffset += missileSpeed;
		} else {
			missileOffset = 0;
			missileStartLoc = true;
		}
	}

}
