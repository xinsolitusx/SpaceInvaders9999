package com.insolitus.spaceinvaders9999;

import java.util.Random;

import android.graphics.Canvas;

public class Levels {

	private Random r = new Random();
	
	private boolean finnished;
	private int xMulti, enemyType;
	private float yStartMulti, yFinalMulti;
	private Enemys[] enemysArray = new Enemys[15];

	public Levels() {
		 
		xMulti = 0;
		yStartMulti = 0.1f;
		yFinalMulti = 0.3f;
		finnished = false;
		setEnemys();
	}
	
	public boolean startShooting(){
		
		return enemysArray[0].startAllocationFinnished();
	}

	private void setEnemys() {

		for (int i = 0; i < 15; i++) {
			enemyType = r.nextInt(3) + 1;
			enemysArray[i] = new Enemys();
			enemysArray[i].setLocation((float) (0.09 * SISingleton.getInstance().width) + xMulti * SISingleton.getInstance().emptySpace + xMulti * SISingleton.getInstance().enemyShipOne.getWidth(),
					0 - yStartMulti * SISingleton.getInstance().height, 
					yFinalMulti * SISingleton.getInstance().height,
					enemyType);

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
	}

}
