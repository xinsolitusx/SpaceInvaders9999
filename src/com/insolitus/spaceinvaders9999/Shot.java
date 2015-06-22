package com.insolitus.spaceinvaders9999;

import android.graphics.Canvas;

public class Shot {

	protected boolean missileStartLoc = true;
	private boolean missileRestart = true;
	protected float x, y, missileOffset = 0, missileSpeed = (float) (0.006 * SISingleton.getInstance().height);

	public void setMissileRestart(boolean restart) {
		this.missileRestart = restart;
	}

	public boolean isMissileStartLoc() {
		return missileStartLoc;
	}

	public void drawShot(Canvas canvas, float shipX, float shipY) {

		// Players shoot
		if (missileStartLoc && missileRestart) {

			x = shipX + SISingleton.getInstance().enemyShipOne.getWidth() / 2 - SISingleton.getInstance().enemyMissile.getWidth() / 2;
			y = shipY + SISingleton.getInstance().enemyMissile.getHeight();
			if (SISingleton.getInstance().shotSound != 0) {
				SISingleton.getInstance().sp.play(SISingleton.getInstance().shotSound, 0.2f, 0.2f, 0, 0, 1);
			}
			missileStartLoc = false;
			missileRestart = false;
		}

		if (!missileStartLoc) {
			if (y + missileOffset <= SISingleton.getInstance().height) {
				canvas.drawBitmap(SISingleton.getInstance().enemyMissile, x, y + missileOffset, null);
				missileOffset += missileSpeed;
			} else {
				missileOffset = 0;
				missileStartLoc = true;
			}
		}
	}

}
