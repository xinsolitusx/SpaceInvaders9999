package com.insolitus.spaceinvaders9999;

import java.util.Random;

import android.graphics.Canvas;
import android.util.Log;

public class Levels {

	private Random r = new Random();

	private boolean finnished;
	private int xMulti, enemyType, enemyShotOne, enemyShotTwo, enemyShotThree, numberOfEnemysFiring, playerHit;
	private float yStartMulti, yFinalMulti;
	private Enemys[] enemysArray = new Enemys[15];

	public Levels() {

		xMulti = 0;
		yStartMulti = 0.1f;
		yFinalMulti = 0.3f;
		finnished = false;
		numberOfEnemyFiring();
		setEnemys();
	}	
	
	public int getPlayerHit() {
		return playerHit;
	}

	public boolean startShooting() {

		return enemysArray[0].startAllocationFinnished();
	}

	private void setEnemys() {

		for (int i = 0; i < 15; i++) {
			enemyType = r.nextInt(4) + 1;
			enemysArray[i] = new Enemys();
			enemysArray[i].setLocation((float) (0.09 * SISingleton.getInstance().width) + xMulti * SISingleton.getInstance().emptySpace + xMulti * SISingleton.getInstance().enemyShipOne.getWidth(), 0
					- yStartMulti * SISingleton.getInstance().height, yFinalMulti * SISingleton.getInstance().height, enemyType);

			xMulti++;
			if (xMulti > 4) {
				xMulti = 0;
				yStartMulti += 0.1f;
				yFinalMulti -= 0.1f;
			}
		}
	}

	public void drawEnemys(Canvas canvas, float x) {

		for (Enemys a : enemysArray) {
			a.drawEnemy(canvas);
		}

		if (!startShooting()) {

			switch (numberOfEnemysFiring) {

			case 1:
				if (!enemysArray[enemyShotOne].detectShotCollision(x)) {
					enemysArray[enemyShotOne].drawShot(canvas);
				} else {
					playerHit++;
				}
				
				if (enemysArray[enemyShotOne].getMissileStartLoc()) {
					numberOfEnemyFiring();
					enemysArray[enemyShotOne].setMissileRestart(true);
				}
				break;
			case 2:
				if ((!enemysArray[enemyShotOne].detectShotCollision(x)) && (!enemysArray[enemyShotTwo].detectShotCollision(x))) {
					enemysArray[enemyShotOne].drawShot(canvas);
					enemysArray[enemyShotTwo].drawShot(canvas);
				} 
				if (enemysArray[enemyShotOne].detectShotCollision(x) || enemysArray[enemyShotTwo].detectShotCollision(x)) {
					Log.i("DVASUTA", "POGODATKx2");
					playerHit++;
				}
				
				if (enemysArray[enemyShotOne].getMissileStartLoc() && enemysArray[enemyShotTwo].getMissileStartLoc()) {
					numberOfEnemyFiring();
					enemysArray[enemyShotOne].setMissileRestart(true);
					enemysArray[enemyShotTwo].setMissileRestart(true);
				}
				break;
			case 3:
				if ((!enemysArray[enemyShotOne].detectShotCollision(x)) && (!enemysArray[enemyShotTwo].detectShotCollision(x)) && (!enemysArray[enemyShotThree].detectShotCollision(x))) {
					enemysArray[enemyShotOne].drawShot(canvas);
					enemysArray[enemyShotTwo].drawShot(canvas);
					enemysArray[enemyShotThree].drawShot(canvas);
				} 
				if (enemysArray[enemyShotOne].detectShotCollision(x) || enemysArray[enemyShotTwo].detectShotCollision(x) ||  enemysArray[enemyShotThree].detectShotCollision(x)) {
					playerHit++;
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

		numberOfEnemysFiring = r.nextInt(3) + 1;
		switch (numberOfEnemysFiring) {

		case 1:
			enemyShotOne = r.nextInt(15);
			break;
		case 2:
			enemyShotOne = r.nextInt(15);
			enemyShotTwo = r.nextInt(15);
			while (enemyShotOne == enemyShotTwo) {
				enemyShotTwo = r.nextInt(15);
			}
			break;
		case 3:
			enemyShotOne = r.nextInt(15);
			enemyShotTwo = r.nextInt(15);
			while (enemyShotOne == enemyShotTwo) {
				enemyShotTwo = r.nextInt(15);
			}
			enemyShotThree = r.nextInt(15);
			while ((enemyShotOne == enemyShotThree) || (enemyShotTwo == enemyShotThree)) {
				enemyShotThree = r.nextInt(15);
			}
			break;
		}

	}

}
