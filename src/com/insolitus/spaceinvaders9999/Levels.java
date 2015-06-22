package com.insolitus.spaceinvaders9999;

import java.util.Random;

import android.graphics.Canvas;

public class Levels {

	private Random r = new Random();

	private boolean finnished;
	private int xMulti, enemyType, enemyShotOne, enemyShotTwo, enemyShotThree, numberOfEnemysFiring;
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
	
	public void drawEnemys(Canvas canvas) {

		for (Enemys a : enemysArray) {
			a.drawEnemy(canvas);
		}

		if (!startShooting()) {			

			switch (numberOfEnemysFiring) {

			case 1:
				enemysArray[enemyShotOne].drawShot(canvas);
				if (enemysArray[enemyShotOne].missileFinnished()) {
					numberOfEnemyFiring();
					enemysArray[enemyShotOne].setMissileFinnished(true);
				}				
				break;
			case 2:
				enemysArray[enemyShotOne].drawShot(canvas);
				enemysArray[enemyShotTwo].drawShot(canvas);
				if (enemysArray[enemyShotOne].missileFinnished() && enemysArray[enemyShotTwo].missileFinnished()) {
					numberOfEnemyFiring();
					enemysArray[enemyShotOne].setMissileFinnished(true);
					enemysArray[enemyShotTwo].setMissileFinnished(true);
				}	
				break;
			case 3:
				enemysArray[enemyShotOne].drawShot(canvas);
				enemysArray[enemyShotTwo].drawShot(canvas);
				enemysArray[enemyShotThree].drawShot(canvas);
				if (enemysArray[enemyShotOne].missileFinnished() && enemysArray[enemyShotTwo].missileFinnished() && enemysArray[enemyShotThree].missileFinnished()) {					
					numberOfEnemyFiring();
					enemysArray[enemyShotOne].setMissileFinnished(true);
					enemysArray[enemyShotTwo].setMissileFinnished(true);					
					enemysArray[enemyShotThree].setMissileFinnished(true);
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
