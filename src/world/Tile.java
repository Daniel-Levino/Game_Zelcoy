package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;

public class Tile {
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 40, 40);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(80, 80, 40, 40);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x, y, null);
	}
}
