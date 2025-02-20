package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS 
	final int originalTileSize = 16; //16x16 pixels for a tile 
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; //The tile will be on (16*3)^2 pixels cause screens are bigger now 
	public final int maxScreenCol = 16; 
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	// 
	
	// WORLD SETTINGS 
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeigth = tileSize * maxWorldRow;
	
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionCheck cCheck = new CollisionCheck(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10]; 
	

	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void setupGame() {
		
		aSetter.setObject();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
//	public void run() {
//		
//		double drawInterval = 1000000000/FPS;
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		
//		while(gameThread != null) {
//			
//			/*long currentTime = System.nanoTime();
//			System.out.println("current Time"+currentTime);*/
//			
//			
//			
//			// 1. UPDATE : update information such as character positions
//			update();
//			
//			// 2 DRAW : draw the screen with the updater information
//			repaint();
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime(); // in nanoseconds
//				remainingTime = remainingTime/1000000;
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long)remainingTime);
//				
//				nextDrawTime += drawInterval;
//				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: "+ drawCount);
				drawCount = 0;
				timer = 0;
				
			}
		}
	}
	
	public void update() {
		
		player.update();
		
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;	
		
		
		// TILE
		tileM.draw(g2);
		
		// OBJECT
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2,this);
			}
		}
		
		// PLAYER
		player.draw(g2);
		
		g2.dispose();
	}
	

}
