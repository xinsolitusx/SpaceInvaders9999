package com.insolitus.spaceinvaders9999;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Enemys {

	private boolean startLocation, left, right;
	private float x, startY, finalY, offSetX, offSetY;
	Bitmap enemyType;

	public void setLocation(float xPos, float yStartPos, float yFinalPos, int eType) {

		this.x = xPos;
		this.startY = yStartPos;
		this.finalY = yFinalPos;
		offSetX = 0;
		offSetY = 0;
		startLocation = true;
		left = false;
		right = false;
		setEnemyType(eType);
	}

	private void setEnemyType(int x) {

		switch (x) {

		case 1:
			enemyType = SISingleton.getInstance().enemyShipOne;
			break;
		case 2:
			enemyType = SISingleton.getInstance().enemyShipTwo;
			break;
		case 3:
			enemyType = SISingleton.getInstance().enemyShipThree;
			break;
		}
	}

	public boolean startAllocationFinnished() {

		return this.startLocation;
	}

	public void drawEnemy(Canvas canvas) {

		if (startLocation) {
			offSetY += 1;
			if ((startY + offSetY) < finalY) {
				canvas.drawBitmap(enemyType, x, startY + offSetY, null);
			} else {
				startLocation = false;
				left = true;
				canvas.drawBitmap(enemyType, x, finalY, null);
			}

		} else {
			canvas.drawBitmap(enemyType, x + offSetX, finalY, null);
		}

		if (left) {
			if ((float) (0.09 * SISingleton.getInstance().width) + offSetX > 0) {
				offSetX -= 1;
			} else {
				left = false;
				right = true;
			}
		}

		if (right) {
			if ((float) (0.09 * SISingleton.getInstance().width) + 4 * SISingleton.getInstance().emptySpace + 5 * SISingleton.getInstance().enemyShipOne.getWidth() + offSetX < SISingleton
					.getInstance().width) {
				offSetX += 1;
			} else {
				left = true;
				right = false;
			}
		}
	}

}
