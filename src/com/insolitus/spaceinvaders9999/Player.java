package com.insolitus.spaceinvaders9999;

import android.graphics.Canvas;

public class Player {

	private float x, y, playerOffSet, missileSpeed = (float) (0.006 * SISingleton.getInstance().height);
	private PlayerShot playerShot = new PlayerShot();
	private int life = 5;
	
	public int getLife(){
		return this.life;
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
	
	public void movePlayersShip(float touchPoint) {

		if (touchPoint < SISingleton.getInstance().width / 2) {
			playerOffSet -= 0.0025 * SISingleton.getInstance().width;
			if (x + playerOffSet < 0) {
				playerOffSet += 0.0025 * SISingleton.getInstance().width;
			}
		} else if (touchPoint >= SISingleton.getInstance().width / 2) {
			playerOffSet += 0.0025 * SISingleton.getInstance().width;
			if (x + playerOffSet + SISingleton.getInstance().playerShip.getWidth() > SISingleton.getInstance().width) {
				playerOffSet -= 0.0025 * SISingleton.getInstance().width;
			}
		}
	}
	
	public float getX(){
		
		return x + playerOffSet;		
	}	
	
}


/*// Players shots
sH1.drawShot(canvas, playersShipOffset, missileSpeed);
if (SISingleton.getInstance().getShotCount() >= 2) {

	if ((sH1.getY() < SISingleton.getInstance().shotMaxRange) || (sH2.getY() != 0)) {

		// Log.i("SHooting", "SECOND_SHOT: " + sH2.getY());
		sH2.drawShot(canvas, playersShipOffset, missileSpeed);
	}
}
if (SISingleton.getInstance().getShotCount() >= 3) {

	if (((sH2.getY() != 0) && (sH2.getY() < SISingleton.getInstance().shotMaxRange)) || (sH3.getY() != 0)) {

		// Log.i("SHooting", "THIRD_SHOT: " + sH3.getY() );
		sH3.drawShot(canvas, playersShipOffset, missileSpeed);
	}
}
if (SISingleton.getInstance().getShotCount() == 4) {

	if (((sH3.getY() != 0) && (sH3.getY() < SISingleton.getInstance().shotMaxRange)) || (sH4.getY() != 0)) {

		// Log.i("SHooting", "FORTH_SHOT: " + sH4.getY() );
		sH4.drawShot(canvas, playersShipOffset, missileSpeed);
	}
}*/