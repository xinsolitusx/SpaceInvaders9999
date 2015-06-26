package com.insolitus.spaceinvaders9999;

import android.graphics.Canvas;

public class Shot {

	protected boolean missileStartLoc = true;
	private boolean missileRestart = true;
	protected float x, y, missileOffset = 0;
	private float playerY = (float) (0.85 * SISingleton.getInstance().height - SISingleton.getInstance().playerShip.getHeight() / 2);

	public void setMissileRestart(boolean restart) {
		this.missileRestart = restart;
	}

	public boolean getMissileStartLoc() {
		return missileStartLoc;
	}

	public void drawShot(Canvas canvas, float shipX, float shipY) {

		// Players shoot
		if (missileStartLoc && missileRestart) {

			x = shipX + SISingleton.getInstance().enemyShipOne.getWidth() / 2 - SISingleton.getInstance().enemyMissile.getWidth() / 2;
			y = shipY + SISingleton.getInstance().enemyMissile.getHeight();
			SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(2), 0.2f, 0.2f, 1, 0, 1f);
			missileStartLoc = false;
			missileRestart = false;
		}

		if (!missileStartLoc) {
			if (y + missileOffset <= SISingleton.getInstance().height) {
				canvas.drawBitmap(SISingleton.getInstance().enemyMissile, x, y + missileOffset, null);
				missileOffset += SISingleton.getInstance().getEnemyMissileSpeed();
			} else {
				missileOffset = 0;
				missileStartLoc = true;
			}
		}

	}

	public boolean detectShotTargetColl(float playerX) {

		boolean hold = false;

		if ((y + missileOffset + SISingleton.getInstance().enemyMissile.getHeight()) > playerY) {
			if ((y + missileOffset + SISingleton.getInstance().enemyMissile.getHeight()) < (playerY + 0.35 * SISingleton.getInstance().playerShip.getHeight())) {

				if (((x + 0.75 * SISingleton.getInstance().enemyMissile.getWidth() <= (playerX + 0.65 * SISingleton.getInstance().playerShip.getWidth())) && (x + 0.25
						* SISingleton.getInstance().enemyMissile.getWidth() >= playerX + 0.35 * SISingleton.getInstance().playerShip.getWidth()))) {

					missileOffset = 0;
					missileStartLoc = true;
					SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(4), 0.2f, 0.2f, 1, 0, 1f);
					return true;
				}
			} else if ((y + missileOffset + SISingleton.getInstance().enemyMissile.getHeight()) < (playerY + 0.65 * SISingleton.getInstance().playerShip.getHeight())) {

				if (((x + 0.75 * SISingleton.getInstance().enemyMissile.getWidth() <= (playerX + SISingleton.getInstance().playerShip.getWidth())) && (x + 0.25
						* SISingleton.getInstance().enemyMissile.getWidth() >= playerX))) {
					
					missileOffset = 0;
					missileStartLoc = true;
					SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(4), 0.2f, 0.2f, 1, 0, 1f);
					return true;
				}
			}
		}
		return hold;
	}
}
