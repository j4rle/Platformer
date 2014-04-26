package Player;

import Game.GamePanel;
import Tiles.TileMap;

import java.awt.*;

public class Player1 extends Player{

	public Player1(TileMap tm) {
		tileMap = tm;

		width = 20;
		height = 20;

		moveSpeed = 0.5;
		maxSpeed = 3.1;
		maxFallingSpeed = 6;
		stopSpeed = 0.40;
		jumpStart = -6.0;
		gravity = 0.20;

	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setLeft(boolean b){
		left = b;
	}

	public void setRight(boolean b){
		right = b;
	}

	public void setJumping(boolean b){
		if(!falling){
			jumping = true;
		}
	}

	public void calculateCorners(double x, double y){ //rectangular hit-detection
		int leftTile = tileMap.getColTile((int) x - width / 2);
		int rightTile = tileMap.getColTile(((int) (x + width / 2)) - 1);
		int topTile = tileMap.getRowTile((int)(y - height / 2));
		int bottomTile = tileMap.getRowTile((int)(y + height / 2) - 1);
		topLeft = tileMap.getTile(topTile, leftTile) == 0;
		topRight = tileMap.getTile(topTile,rightTile) == 0;
		bottomLeft = tileMap.getTile(bottomTile, leftTile) == 0;
		bottomRight = tileMap.getTile(bottomTile, rightTile) == 0;

	}

	public void update(){
	//calculate next position
		if(left){//accelerates/moves left
			dx -= moveSpeed;
			if(dx < -maxSpeed){
				dx = -maxSpeed;
			}
		} //accelerates/moves right
		else if(right){
			dx += moveSpeed;
			if(dx > maxSpeed){
				dx = maxSpeed;
			}
		}
		else { //slows down to a stop
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			}
			else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}

		if(jumping){ //start of jump
			dy = jumpStart;
			falling = true;
			jumping = false;
		}

		if(falling){ //mid jump or if falling down
			dy += gravity;
			if(dy > maxFallingSpeed){
				dy = maxFallingSpeed;
			}
		}
		else{ //standing still
			dy = 0;
		}

		//collision detection

		int currentColumn = tileMap.getColTile((int)x);
		int currentRow = tileMap.getRowTile((int)y);

		double nextX = x + dx;
		double nextY = y + dy;

		double tempX = x;
		double tempY = y;

		calculateCorners(x, nextY); //calculate for next y
		if(dy < 0){ //accelerating upwards
			if(topLeft || topRight){
				dy = 0;
				tempY = currentRow * tileMap.getTileSize() + height / 2;

			}
			else{ //no collision, can continue
				tempY += dy;
			}
		}
		if(dy > 0){ //accelerating towards ground
			if(bottomLeft || bottomRight){
				dy = 0;
				falling = false;
				tempY = (currentRow + 1) * tileMap.getTileSize() - height / 2;
			}
			else{ //no collision
				tempY += dy;
			}
		}

		calculateCorners(nextX, y); //calculate for x
		if(dx < 0){ //to the left
			if(topLeft || bottomLeft){
				dx = 0;
				tempX = currentColumn * tileMap.getTileSize() + width / 2;

			}
			else{ //no collision
				tempX += dx;
			}
		}
		if(dx > 0){ //to the right
			if(topRight || bottomRight){
				dx = 0;
				tempX = (currentColumn + 1) * tileMap.getTileSize() - width / 2;
			}
			else{ //no collision
				tempX += dx;
			}
		}

		if(!falling){
			calculateCorners(x, y + 1);
			if(!bottomLeft && !bottomRight){
				falling = true;
			}
		}
		x = tempX;
		y = tempY;

		//moves the camera
		tileMap.setX((int)(GamePanel.WIDTH/2 - x));
		tileMap.setY((int)(GamePanel.HEIGHT/2 - y));

	}

	public void draw(Graphics2D g){

		int tx = tileMap.getX();
		int ty = tileMap.getY();

		g.setColor(Color.BLUE);
		g.fillRect((int)(tx + x - width / 2),(int)(ty + y - height/2),width, height);

	}

}
