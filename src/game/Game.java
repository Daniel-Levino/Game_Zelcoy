package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import entities.*;
import graficos.Spritesheet;
import graficos.UI;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener{
    
    public static JFrame frame;
    private Thread thread;
    public static boolean isRunning = true;
    private final int SCALE = 2;
    public static final int WIDTH = 480;
    public static final int HEIGHT = 280;
    public static String fps = "FPS: 60";
    
    private BufferedImage image;
    private static int currentLevel = 1, ultimateLevel = 3;
    
    //public static List<Entity> entities;
    public static List<Enemy> enemies;
    public static List<Collect> itens;
    public static List<ArrowShooting> shoots;
    
    public static Spritesheet spritesheet;
    
    public static World world;
    
    public static Player player;
    
    private UI ui;
    public static CheckPoint checkPoint;
    
    public static String gameState = "NORMAL";
    
    private boolean restartMessage = true;
    private int restartMessageTime = 0;
    
    public Game(){
    	addKeyListener(this);
    	addMouseListener(this);
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        initFrame();
        
        ui= new UI();
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        
        Game.initGame(currentLevel);
        
    }
    
    public static void initGame(int level) {
    	Game.enemies = new ArrayList<Enemy>();
    	Game.itens = new ArrayList<Collect>();
    	Game.shoots = new ArrayList<ArrowShooting>();
    	Game.spritesheet = new Spritesheet("/Spritesheets.png");
    	Game.player = new Player(0, 0, World.TILE_SIZE, World.TILE_SIZE, Game.spritesheet.getSprite(160, 0, World.TILE_SIZE, World.TILE_SIZE));
    	Game.world = new World("/map_"+level+".png");
    	Game.isRunning = true;
    	
    }
    
    public void gameOverRestart() {
    	if (gameState.equals("GAME OVER")) {
			gameState = "NORMAL";
			Game.initGame(currentLevel);
		}
    }
    
    public void gamePause() {
    	if(gameState.equals("NORMAL")) {
			gameState = "PAUSE";
		}else if (gameState.equals("PAUSE")) {
			gameState = "NORMAL";
		}
    }
    
    public final void initFrame(){
        frame = new JFrame("Game #1");
        frame.add(this); 
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop(){
        isRunning = false;
        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }    
    
    public void tick(){
    	if (gameState.equals("NORMAL")) {
	    	for (int i=0; i< enemies.size(); i++){Enemy e = enemies.get(i);e.tick();}
	    	//for (Entity e : enemies){e.tick();} // usando for-each
	    	
	    	for (int i=0; i< shoots.size(); i++){ArrowShooting e = shoots.get(i);e.tick();}
	    	//for (ArrowShooting e : shoots){e.tick();} // usando for-each
	    	
	    	player.tick();
	    	
	    	if (Entity.isColliding(player, checkPoint) && enemies.size() == 0) {
	    		currentLevel++ ;
	    		if(currentLevel>ultimateLevel)currentLevel=1;
	    		Game.initGame(currentLevel);
	    	}
    	}else if (gameState.equals("GAME OVER")) {
    		this.restartMessageTime++;
    		
    		if (restartMessageTime >= 60) {
    			restartMessageTime = 0;
    			if (restartMessage) restartMessage = false; else restartMessage=true;
    		}
    	}
    }
    
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0));
        g.fillRect(0, 0, this.WIDTH, this.HEIGHT);
        
        // ====== Área de Renderizacao do jogo =========
        
        world.render(g);
        
        for (int i=0; i< itens.size(); i++){Entity e = itens.get(i);e.render(g);}
        //for(Entity e : itens) {e.render(g);}
        
        for (int i=0; i< enemies.size(); i++){Entity e = enemies.get(i);e.render(g);}
        //sfor(Entity e : enemies){e.render(g);}
        
        
        checkPoint.render(g);
        
        player.render(g);
        
        for (ArrowShooting e : shoots){e.render(g);} // usando for-each
    	
        ui.render(g);
        
        
        // ==============================

        if (!gameState.equals("NORMAL")) {
        	Graphics2D g2 = (Graphics2D) g;
        	g2.setColor(new Color(0,0,0,200));
        	g2.fillRect(0, 0, WIDTH, HEIGHT);
        	
        	if (gameState.equals("GAME OVER")) {
	        	g2.setFont(new Font("Arial", 1, 20));
	        	g2.setColor(Color.WHITE);
	        	g2.drawString(gameState, WIDTH/2-60, HEIGHT/2-20);
	        	g2.drawLine(182, 125, 300, 125);
	        	g2.drawOval(WIDTH/2, HEIGHT/2, 5, 5);
	        	
	        	if (restartMessage) {
		        	g2.setFont(new Font("Arial", 1, 14));
		        	g2.setColor(Color.WHITE);
		        	g2.drawString("Pressione <ENTER> para continuar", WIDTH/2-124, HEIGHT/2+30);
		        	g2.drawLine(115, 175, 360, 175);
	        	}
	        	
        	} else if (gameState.equals("PAUSE")) {
	        	g2.setFont(new Font("Arial", 1, 20));
	        	g2.setColor(Color.WHITE);
	        	g2.drawString(gameState, WIDTH/2-30, HEIGHT/2-20);
	        	g2.drawLine(210, 125, 275, 125);
	        	g2.drawOval(WIDTH/2, HEIGHT/2, 5, 5);
        	}
        }
        
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        bs.show();
    }
    
    public void run(){
        
    	long lastTime = System.nanoTime();
	    double amountOfTicks = 60.0;
	    double ns = 1000000000/amountOfTicks;
	    double delta = 0;
	    int frames = 0;
	    double timer = System.currentTimeMillis();
	    requestFocus();
	    while(isRunning){
	        long now = System.nanoTime();
	        delta += (now - lastTime)/ns;
	        lastTime = now;
	        if(delta >= 1){
	            tick();
	            render();
	            frames++;
	            delta--;
	        }
	        
	        if(System.currentTimeMillis() - timer >= 1000){
	        	this.fps = "FPS: "+frames;
	            frames = 0;
	            timer = System.currentTimeMillis();
	            
	        }
	    }
	    stop();
    }

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.setR(true);
			
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.setL(true);
			
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.setU(true);
			
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.setD(true);
			
		}
		
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			player.setShoot(true);
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
		
		if (e.getKeyCode() == KeyEvent.VK_P) {
			gamePause();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			gameOverRestart();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.setR(!true);
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.setL(!true);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.setU(!true);
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.setD(!true);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		player.setShoot(true);
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void nextLevel() {
		this.currentLevel++;
	}
	
	public static int getCurrentLevel() {
		return currentLevel;
	}
}
	

