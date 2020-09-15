package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import entities.Collect;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import graficos.Spritesheet;
import graficos.UI;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener{
    
    public static JFrame frame;
    private Thread thread;
    public static boolean isRunning = true;
    private final int SCALE = 2;
    public static final int WIDTH = 480;
    public static final int HEIGHT = 280;
    private String fps = "FPS: 60";
    
    private BufferedImage image;
    
    //public static List<Entity> entities;
    public static List<Enemy> enemies;
    public static List<Collect> itens;
    
    public static Spritesheet spritesheet;
    
    public static World world;
    
    public static Player player;
    
    private UI ui;
    
    //public static Game game;
    
    public Game(){
    	addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        initFrame();
        
        ui= new UI();
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        //entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        itens = new ArrayList<Collect>();
        spritesheet = new Spritesheet("/Spritesheets.png");
        player = new Player(0, 0, World.TILE_SIZE, World.TILE_SIZE,spritesheet.getSprite(160, 0, World.TILE_SIZE, World.TILE_SIZE));
        world = new World("/map.png");
        //entities.add(player);
        
        
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
        isRunning = true;
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
    
    public static void restartGame() {
    	Game.enemies = new ArrayList<Enemy>();
    	Game.itens = new ArrayList<Collect>();
    	Game.spritesheet = new Spritesheet("/Spritesheets.png");
    	Game.player = new Player(0, 0, World.TILE_SIZE, World.TILE_SIZE, Game.spritesheet.getSprite(160, 0, World.TILE_SIZE, World.TILE_SIZE));
    	Game.world = new World("/map.png");
    	Game.isRunning = true;
    }
    
    public void tick(){
    	//for (int i=0; i< enemies.size(); i++){Enemy e = enemies.get(i);e.tick();}
    	for (Entity e : enemies){e.tick();} // usando for-each
    	player.tick();
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
        
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString(this.fps, this.WIDTH-40, this.HEIGHT-2);
        
        // ====== Área de Renderizacao do jogo =========
        
        world.render(g);
        
        //for (int i=0; i< entities.size(); i++){Entity e = entities.get(i);e.render(g);}
        for(Entity e : itens) {e.render(g);}
        for(Entity e : enemies){e.render(g);}
        
        player.render(g);
        
        ui.render(g);
        
        // ==============================
        
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(1);
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
}
	

