package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import world.Camera;
import world.World;

public class Entity {
	
	public static BufferedImage ENEMY = Game.spritesheet.getSprite(160, 80, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage LIFEPACK = Game.spritesheet.getSprite(340, 20, World.TILE_SIZE/2, World.TILE_SIZE/2);
	public static BufferedImage ARROW = Game.spritesheet.getSprite(340, 0, World.TILE_SIZE/2, World.TILE_SIZE/2);
	public static BufferedImage BOW = Game.spritesheet.getSprite(320, 0, World.TILE_SIZE/2, World.TILE_SIZE/2);
	public static BufferedImage R_HAS_BOW = Game.spritesheet.getSprite(360, 0, 8,20);
	public static BufferedImage L_HAS_BOW = Game.spritesheet.getSprite(360, 20, 8,20);
	public static BufferedImage R_ARROW_SHOOTING = Game.spritesheet.getSprite(320, 20, 20,5);
	public static BufferedImage L_ARROW_SHOOTING = Game.spritesheet.getSprite(320, 25, 20,5);
	
	public int x, y;
	private int w, h;
	private BufferedImage sprite;
	
	private int maskX, maskY, maskW, maskH;
	
	public Entity(int x, int y, int w, int h, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.sprite = sprite;
		
		this.maskX = 0;
		this.maskY = 0;
		this.maskW = w;
		this.maskH = h;
		
	}
	public void setMask(int x, int y, int w, int h) {
		this.maskX = x;
		this.maskY = y;
		this.maskW = w;
		this.maskH = h;
		
	}
	
	public void tick() {
		
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.getMaskX(), e1.getY()+e1.getMaskY(),e1.getMaskW(),e1.getMaskH());
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.getMaskX(), e2.getY()+e2.getMaskY(),e2.getMaskW(),e2.getMaskH());
		
		return e1Mask.intersects(e2Mask);
	}
	
	
	
	public void render(Graphics g) {
		g.drawImage(this.sprite, this.getX() - Camera.x, this.getY()-Camera.y, null);
		g.setColor(Color.RED);
		g.drawRect(this.getX() + this.getMaskX() - Camera.x, this.getY()+this.getMaskY()-Camera.y,this.getMaskW(),this.getMaskH());
	}
	
	
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	//public int getW() {return this.w;}
	//public int getH() {return this.h;}
	
	public int getMaskX() {return this.maskX;}
	public int getMaskY() {return this.maskY;}
	public int getMaskW() {return this.maskW;}
	public int getMaskH() {return this.maskH;}
	
	public void setX(int x) {this.x=x;}
	public void setY(int y) {this.y=y;}
	//public void setW(int w) {this.w=w;}
	//public void setH(int h) {this.h=h;}
}
