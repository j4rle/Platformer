package Game;

import Player.Player1;
import Tiles.TileMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;

	private Thread thread;
	private boolean running;

	private BufferedImage image;
	private Graphics2D g;

	private int FPS = 60;
	private int targetTime = 1000/FPS;

	private TileMap tileMap;
	private Player1 player1;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}


	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
	}

	public void run() {

		init();

		long startTime;
		long elapsed;
		long waitTime;

		while (running) {

			startTime = System.nanoTime();

			update();
			render();
			draw();

			elapsed = System.nanoTime() - startTime;
			waitTime = targetTime - (elapsed/1000000);
			if(waitTime<0)waitTime=5;

			try {
				Thread.sleep(waitTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public void init(){

		running = true;

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		tileMap = new TileMap("testmap.txt", 32);
		player1 = new Player1(tileMap);
		player1.setX(50);
		player1.setY(50);
	}

	private void update(){
		tileMap.update();
		player1.update();
	}

	private void render(){
		tileMap.draw(g);
		player1.draw(g);
	}

	private void draw(){
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	public void keyTyped(KeyEvent key){

	}
	public void keyPressed(KeyEvent key){
		int code = key.getKeyCode();

		if(code == KeyEvent.VK_A){
			player1.setLeft(true);
		}
		if(code == KeyEvent.VK_D){
			player1.setRight(true);
		}
		if(code == KeyEvent.VK_SPACE){
			player1.setJumping(true);
		}
	}
	public void keyReleased(KeyEvent key){
		int code = key.getKeyCode();

		if(code == KeyEvent.VK_A){
			player1.setLeft(false);
		}
		if(code == KeyEvent.VK_D){
			player1.setRight(false);
		}
	}


}
