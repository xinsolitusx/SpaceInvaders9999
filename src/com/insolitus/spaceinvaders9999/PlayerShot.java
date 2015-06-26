package com.insolitus.spaceinvaders9999;

import android.graphics.Canvas;

public class PlayerShot extends Shot {

	private int enemyIndex = 0, hits = 0;
	private boolean hit = false;

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	@Override
	public void drawShot(Canvas canvas, float shipX, float shipY) {
		if (hits < 15) {
			if (missileStartLoc) {

				x = shipX + SISingleton.getInstance().playerShip.getWidth() / 2 - SISingleton.getInstance().missile.getWidth() / 2;
				y = shipY - SISingleton.getInstance().missile.getHeight();
				SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(1), 0.2f, 0.2f, 1, 0, 1f);
				missileStartLoc = false;
				enemyIndex = 0;
			}

			if (y - missileOffset >= 0) {
				canvas.drawBitmap(SISingleton.getInstance().missile, x, y - missileOffset, null);
				missileOffset += SISingleton.getInstance().getPlayerMissileSpeed();
			} else {
				missileOffset = 0;
				missileStartLoc = true;
			}
		}
	}

	public void detectShotTargetColl(Enemys[] enemysArray) {

		while (!missileStartLoc && (enemyIndex < enemysArray.length)) {
			if (enemysArray[enemyIndex].isKilled() == false) {
				// Missile passed playerShip Y axis
				if ((y - missileOffset) < enemysArray[enemyIndex].currentYLoc() + SISingleton.getInstance().enemyShipOne.getHeight()) {
					if ((y - missileOffset) > (enemysArray[enemyIndex].currentYLoc() + 0.25 * SISingleton.getInstance().enemyShipOne.getHeight())) {
						// Missile between playerShip X axis
						if ((x + 0.75 * SISingleton.getInstance().missile.getWidth()) <= (enemysArray[enemyIndex].currentXLoc() + SISingleton.getInstance().enemyShipOne.getWidth())) {
							if ((x + 0.25 * SISingleton.getInstance().missile.getWidth()) >= enemysArray[enemyIndex].currentXLoc()) {
								hits++;
								missileOffset = 0;
								missileStartLoc = true;
								hit = true;
								SISingleton.getInstance().sp.play(SISingleton.getInstance().mSoundPoolMap.get(3), 0.2f, 0.2f, 1, 0, 1f);
								enemysArray[enemyIndex].setKilled(true);
							}
						}
					}
				}
			}
			enemyIndex++;
		}
		if (!missileStartLoc && (enemyIndex == enemysArray.length)) {
			enemyIndex = 0;
		}
	}
}
