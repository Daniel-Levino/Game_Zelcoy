package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.*;
import game.Game;

public class World {

	private static Tile[] tiles;
	public static int WIDTH, HEIGHT, SIZE;
	public static final int TILE_SIZE = 40;
	

	public World(String local) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(local));

			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			SIZE = WIDTH * HEIGHT;

			int[] pixels = new int[SIZE];

			tiles = new Tile[SIZE];

			map.getRGB(0, 0, this.WIDTH, this.HEIGHT, pixels, 0, this.WIDTH);

			for (int i = 0; i < this.WIDTH; i++) {
				for (int j = 0; j < this.HEIGHT; j++) {
					
					int index = i + (j * this.WIDTH);
					int pixelAtual = pixels[index];
					
					tiles[index] = new FloorTile(i * TILE_SIZE, j * TILE_SIZE, Tile.TILE_FLOOR);
					
					if (pixelAtual == 0xFFFFFFFF) { // WHITE
						
						// PIXEL WHITE = WALL
						tiles[index] = new WallTile(i * TILE_SIZE, j * TILE_SIZE, Tile.TILE_WALL);
						
					} else if (pixelAtual == 0xFFFF00FF) { // PURPLE
						
						// PIXEL PURPLE = PLAYER
						Game.player.setX(i * TILE_SIZE);
						Game.player.setY(j * TILE_SIZE);
						Game.player.setMask(8, 0, TILE_SIZE-16, TILE_SIZE);
						
					} else if (pixelAtual == 0xFFFF0000) { // RED
						
						// PIXEL RED = ENEMY
						Enemy en = new Enemy(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE/2, TILE_SIZE/2, Entity.ENEMY);
						//Game.entities.add(en);
						Game.enemies.add(en);
						
					} else if (pixelAtual == 0xFF00FF00) { // GREEN
						
						// PIXEL GREEN = BOW
						Game.itens.add(new Bow(i*TILE_SIZE,j*TILE_SIZE,TILE_SIZE/2,TILE_SIZE/2, Entity.BOW));
						
					} else if (pixelAtual == 0xFFFFFF00) { // YELLOW
						
						// PIXEL YELLOW = LIFEPACK
						Game.itens.add(new Lifepack(i*TILE_SIZE,j*TILE_SIZE, TILE_SIZE/2, TILE_SIZE/2, Entity.LIFEPACK));
						
					} else if (pixelAtual == 0xFF0000FF) { // BLUE
						
						// PIXEL BLUE = ARROW
						Game.itens.add(new Arrow(i*TILE_SIZE,j*TILE_SIZE, TILE_SIZE/2, TILE_SIZE/2, Entity.ARROW));
						
					} else {
						
						// FLOOR
						
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext){
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE-1) / TILE_SIZE;
		int y2 = (yNext + TILE_SIZE-1) / TILE_SIZE;
		
		return !(tiles[x1+(y1*World.WIDTH)] instanceof WallTile ||
				 tiles[x1+(y2*World.WIDTH)] instanceof WallTile ||
				 tiles[x2+(y1*World.WIDTH)] instanceof WallTile ||
				 tiles[x2+(y2*World.WIDTH)] instanceof WallTile);
	}
	public static boolean isFree(int xNext, int yNext, int height){
		int x1 = (xNext / TILE_SIZE);
		int y1 = (yNext / TILE_SIZE);
		
		int x2 = (xNext + height-1) / TILE_SIZE;
		int y2 = (yNext + height-1) / TILE_SIZE;
		
		return !(tiles[x1+(y1*World.WIDTH)] instanceof WallTile ||
				 tiles[x1+(y2*World.WIDTH)] instanceof WallTile ||
				 tiles[x2+(y1*World.WIDTH)] instanceof WallTile ||
				 tiles[x2+(y2*World.WIDTH)] instanceof WallTile);
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x / TILE_SIZE;
		int ystart = Camera.y / TILE_SIZE;
		
		int xfinal = xstart+(Game.WIDTH/TILE_SIZE);
		int yfinal = ystart+(Game.HEIGHT/TILE_SIZE);
		
		
		for (int i = xstart; i <= xfinal; i++) {
			for (int j = ystart; j <= yfinal; j++) {
				if (i < 0 || j < 0 || i >= WIDTH || j >= HEIGHT) continue;
				Tile tile = tiles[i + (j * this.WIDTH)];
				tile.render(g);
			}
		}
	}

}
