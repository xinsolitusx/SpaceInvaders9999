package com.insolitus.spaceinvaders9999;

import android.graphics.Canvas;
import android.util.Log;

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
				if (SISingleton.getInstance().shotSound != 0) {
					SISingleton.getInstance().sp.play(SISingleton.getInstance().shotSound, 0.2f, 0.2f, 0, 0, 1);
				}
				missileStartLoc = false;
				enemyIndex = 0;
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

	public void detectShotTargetColl(Enemys[] enemysArray) {

		while (!missileStartLoc && enemyIndex < enemysArray.length) {
			if (enemysArray[enemyIndex].isKilled() == false) {
				// Missile passed playerShip Y axis
				if ((y - missileOffset) <= enemysArray[enemyIndex].currentYLoc() + SISingleton.getInstance().enemyShipOne.getHeight()) {
					if (y - missileOffset < enemysArray[enemyIndex].currentYLoc()) {
						// Missile between playerShip X axis
						if (((x + SISingleton.getInstance().missile.getWidth() <= (enemysArray[enemyIndex].currentXLoc() + SISingleton.getInstance().enemyShipOne.getWidth())) && (x > enemysArray[enemyIndex]
								.currentXLoc()))) {
							Log.i("POGODATK", "Brod:" + enemyIndex);
							hits++;
							missileOffset = 0;
							missileStartLoc = true;
							hit = true;
							enemysArray[enemyIndex].setKilled(true);
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
