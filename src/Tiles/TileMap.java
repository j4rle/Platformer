package Tiles;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class TileMap {

	//Offsets
	private int x;
	private int y;

	private int tileSize;
	private int[][] map;
	private int mapWidth; //width of the map as read from the mapfile
	private int mapHeight; //height of the map as read from the mapfile

	public TileMap(String s, int tileSize){

		this.tileSize = tileSize;

		try{
			BufferedReader br = new BufferedReader(new FileReader(s));

			mapWidth = Integer.parseInt(br.readLine()); //reads first line
			mapHeight = Integer.parseInt(br.readLine()); //reads second line
			map = new int[mapHeight][mapWidth]; //map represented as array

			String delimiter = " "; //separator for information in our file

			//Reading the rest of the map file:
			for (int row = 0; row < mapHeight; row++) { //loop over rows (number of lines in map.txt)
				String line = br.readLine();
				String[] tokens = line.split(delimiter); //Splits the read line with the delimiter
				for (int col = 0; col < mapWidth; col++) { //loop over columns
					map[row][col] = Integer.parseInt(tokens[col]); //inserts the parsed int array into the correct column
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getColTile(int x){
		return x / tileSize;
	}
	public int getRowTile(int y){
		return y / tileSize;
	}

	public int getTile(int row, int col){
		return map[row][col]; //return value of map at this position
	}

	public int getTileSize(){
		return tileSize;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void update(){

	}

	public void draw(Graphics2D g){
		//iterate over rows and columns
		for (int row = 0; row < mapHeight; row++) {
			for (int col = 0; col < mapWidth; col++) {

				int rc = map[row][col]; //currentpos

				if(rc == 0){
					g.setColor(Color.BLACK);
				}
				if(rc ==1){
					g.setColor(Color.WHITE);
				}

				g.fillRect(x + col * tileSize, y + row * tileSize, tileSize,tileSize);
			}
		}
	}

}
