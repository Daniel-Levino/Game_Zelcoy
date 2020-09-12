package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.*;
import game.Game;

public class World {

	private Tile[] tiles;
	private static int WIDTH, HEIGHT, SIZE;

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
					
					tiles[index] = new FloorTile(i * 40, j * 40, Tile.TILE_FLOOR);
					
					if (pixelAtual == 0xFFFFFFFF) { // WHITE
						
						tiles[index] = new FloorTile(i * 40, j * 40, Tile.TILE_WALL);
						
					} else if (pixelAtual == 0xFFFF00FF) { // PURPLE
						
						// PIXEL PURPLE = PLAYER
						Game.player.setX(i*40);
						Game.player.setY(j*40);
						
					} else if (pixelAtual == 0xFF00FFFF) { // CIAN
						
						// PIXEL CIANO = ENEMY
						Game.entities.add(new Enemy(i*40,j*40,40,40, Entity.ENEMY));
						
					} else if (pixelAtual == 0xFF00FF00) { // GREEN
						
						// PIXEL GREEN = BOW
						Game.entities.add(new Bow(i*40,j*40,40,40, Entity.BOW));
						
					} else if (pixelAtual == 0xFFFFFF00) { // YELLOW
						
						// PIXEL YELLOW = LIFEPACK
						Game.entities.add(new Lifepack(i*40,j*40,40,40, Entity.LIFEPACK));
						
					} else if (pixelAtual == 0xFF0000FF) { // BLUE
						
						// PIXEL BLUE = ARROW
						Game.entities.add(new Arrow(i*40,j*40,40,40, Entity.ARROW));
						
					} else {
						
						// FLOOR
						
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < this.WIDTH; i++) {
			for (int j = 0; j < this.HEIGHT; j++) {
				Tile tile = tiles[i + (j * this.WIDTH)];
				tile.render(g);
			}
		}
	}

}
