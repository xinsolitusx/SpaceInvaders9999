package com.insolitus.spaceinvaders9999;

import android.graphics.Canvas;

public class Player {

	private float x, y, playerOffSet;
	private PlayerShot playerShot = new PlayerShot();
	private int life = 5, highscore = 0; 	
	
	
	public int getHighscore() {
		return highscore;
	}

	public void setHighscore() {
		if (playerShot.isHit()){
			highscore+=100;
			playerShot.setHit(false);
		}
	}

	public void setHits(int hits){
		 playerShot.setHits(hits);
	}
	
	public int getLife(){
		return this.life;
	}
	
	public void setLife() {
		this.life--;
	}

	public int getHits(){
		return this.playerShot.getHits();
	}

	public Player() {

		x = SISingleton.getInstance().width / 2 - SISingleton.getInstance().playerShip.getWidth() / 2;
		y = (float) (0.85 * SISingleton.getInstance().height - SISingleton.getInstance().playerShip.getHeight() / 2);
		playerOffSet = 0;
	}
	
	public void drawPlayer(Canvas canvas) {
		
		canvas.drawBitmap(SISingleton.getInstance().playerShip, x + playerOffSet, y, null);
	}
	
	public void drawShot(Canvas canvas) {
		playerShot.drawShot(canvas, x + playerOffSet, y);
	}
	
	public void detectShotTargetColl(Enemys[] enemysArray) {
		playerShot.detectShotTargetColl(enemysArray);
	}
	
	public void movePlayersShip(float touchPoint) {

		if (touchPoint < SISingleton.getInstance().width / 2) {
			playerOffSet -= 0.003 * SISingleton.getInstance().width;
			if (x + playerOffSet < 0) {
				playerOffSet += 0.003 * SISingleton.getInstance().width;
			}
		} else if (touchPoint >= SISingleton.getInstance().width / 2) {
			playerOffSet += 0.003 * SISingleton.getInstance().width;
			if (x + playerOffSet + SISingleton.getInstance().playerShip.getWidth() > SISingleton.getInstance().width) {
				playerOffSet -= 0.003 * SISingleton.getInstance().width;
			}
		}
	}
	
	public float getX(){
		
		return x + playerOffSet;		
	}	
	
}


