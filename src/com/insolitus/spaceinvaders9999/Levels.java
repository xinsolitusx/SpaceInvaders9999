package com.insolitus.spaceinvaders9999;

import java.util.Random;

import android.graphics.Canvas;

public class Levels {

	private Random r = new Random();

	private boolean finnished = false, playerHit = false;
	private int xMulti = 0, enemyType, enemyShotOne, enemyShotTwo, enemyShotThree, numberOfEnemysFiring;
	private float yStartMulti = 0.1f, yFinalMulti = 0.3f;
	private Enemys[] enemysArray = new Enemys[15];
	private int[] enemysTemp = new int[15];

	public boolean isFinnished() {
		return finnished;
	}

	public Levels() {		
		setEnemys();
		numberOfEnemyFiring();
		SISingleton.getInstance().setStartShooting(false);
	}

	public Enemys[] getEnemysArray() {
		return this.enemysArray;
	}

	public void setPlayerHit(boolean playerHit) {
		this.playerHit = playerHit;
	}

	public boolean getPlayerHit() {
		return playerHit;
	}

	private void setEnemys() {

		for (int i = 0; i < enemysArray.length; i++) {
			enemyType = r.nextInt(4) + 1;
			enemysArray[i] = new Enemys();
			enemysArray[i].setIndex(i);
			enemysArray[i].setLocation((float) (0.09 * SISingleton.getInstance().width) + xMulti * SISingleton.getInstance().emptySpace + xMulti * SISingleton.getInstance().enemyShipOne.getWidth(), 0
					- yStartMulti * SISingleton.getInstance().height, yFinalMulti * SISingleton.getInstance().height, enemyType);

			xMulti++;
			if (xMulti > 4) {
				xMulti = 0;
				yStartMulti += 0.1f;
				yFinalMulti -= 0.1f;
			}
			enemysTemp[i] = i;
		}
	}

	public void drawEnemys(Canvas canvas, float x) {

		for (Enemys a : enemysArray) {
			if (a.isKilled() != true) {
				a.drawEnemy(canvas);
			}
		}

		if (SISingleton.getInstance().isStartShooting() && finnished == false) {
			switch (numberOfEnemysFiring) {

			case 1:

				enemysArray[enemyShotOne].drawShot(canvas);
				if (enemysArray[enemyShotOne].detectShotCollision(x)) {
					playerHit = true;
				}

				if (enemysArray[enemyShotOne].getMissileStartLoc()) {
					numberOfEnemyFiring();
					enemysArray[enemyShotOne].setMissileRestart(true);
				}

				break;
			case 2:
				enemysArray[enemyShotOne].drawShot(canvas);
				enemysArray[enemyShotTwo].drawShot(canvas);
				if (enemysArray[enemyShotOne].detectShotCollision(x) || enemysArray[enemyShotTwo].detectShotCollision(x)) {
					playerHit = true;
				}

				if (enemysArray[enemyShotOne].getMissileStartLoc() && enemysArray[enemyShotTwo].getMissileStartLoc()) {
					numberOfEnemyFiring();
					enemysArray[enemyShotOne].setMissileRestart(true);
					enemysArray[enemyShotTwo].setMissileRestart(true);
				}

				break;
			case 3:
				enemysArray[enemyShotOne].drawShot(canvas);
				enemysArray[enemyShotTwo].drawShot(canvas);
				enemysArray[enemyShotThree].drawShot(canvas);
				if (enemysArray[enemyShotOne].detectShotCollision(x) || enemysArray[enemyShotTwo].detectShotCollision(x) || enemysArray[enemyShotThree].detectShotCollision(x)) {
					playerHit = true;
				}

				if (enemysArray[enemyShotOne].getMissileStartLoc() && enemysArray[enemyShotTwo].getMissileStartLoc() && enemysArray[enemyShotThree].getMissileStartLoc()) {
					numberOfEnemyFiring();
					enemysArray[enemyShotOne].setMissileRestart(true);
					enemysArray[enemyShotTwo].setMissileRestart(true);
					enemysArray[enemyShotThree].setMissileRestart(true);
				}

				break;
			}
		}
	}

	private void numberOfEnemyFiring() {

		int temp1 = 0;
		for (Enemys a : enemysArray) {
			if (a.isKilled() == false) {
				temp1++;
			}
		}
		if (temp1 > 0) {
			if (temp1 != enemysArray.length) {
				enemysTemp = new int[temp1];
				int temp2 = 0;
				for (int i = 0; i < enemysArray.length; i++) {
					if (enemysArray[i].isKilled() == false) {
						enemysTemp[temp2] = enemysArray[i].getIndex();
						temp2++;
					}
				}
			}

			if (enemysTemp.length > 2) {
				numberOfEnemysFiring = r.nextInt(3) + 1;
			} else {
				numberOfEnemysFiring = r.nextInt(enemysTemp.length) + 1;
			}

			switch (numberOfEnemysFiring) {

			case 1:
				enemyShotOne = enemysTemp[r.nextInt(enemysTemp.length)];
				break;
			case 2:
				enemyShotOne = enemysTemp[r.nextInt(enemysTemp.length)];
				enemyShotTwo = enemysTemp[r.nextInt(enemysTemp.length)];
				while (enemyShotOne == enemyShotTwo) {
					enemyShotTwo = enemysTemp[r.nextInt(enemysTemp.length)];
				}
				break;
			case 3:
				enemyShotOne = enemysTemp[r.nextInt(enemysTemp.length)];
				enemyShotTwo = enemysTemp[r.nextInt(enemysTemp.length)];
				while (enemyShotOne == enemyShotTwo) {
					enemyShotTwo = enemysTemp[r.nextInt(enemysTemp.length)];
				}
				enemyShotThree = enemysTemp[r.nextInt(enemysTemp.length)];
				while ((enemyShotOne == enemyShotThree) || (enemyShotTwo == enemyShotThree)) {
					enemyShotThree = enemysTemp[r.nextInt(enemysTemp.length)];
				}
				break;
			}

		} else {
			finnished = true;
		}
	}
	

}
