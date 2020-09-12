package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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
					if (pixelAtual == 0xFF000000) {
						
						// PIXEL PRETO = CHÃO
						tiles[index] = new FloorTile(i * 40, j * 40, Tile.TILE_FLOOR);
						System.out.println("chão");
						
					} else if (pixelAtual == 0xFFFFFFFF) {
						
						// PIXEL BRANCO = PAREDE
						tiles[index] = new FloorTile(i * 40, j * 40, Tile.TILE_WALL);
						System.out.println("Parede");
					} else if (pixelAtual == 0xFF00FFFF) {
						
						// PIXEL BRANCO = PAREDE
						tiles[index] = new FloorTile(i * 40, j * 40, Tile.TILE_FLOOR);
						System.out.println("player");
					} else {
						
						// chão
						tiles[index] = new FloorTile(i * 40, j * 40, Tile.TILE_FLOOR);
						System.out.println("chão");
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
