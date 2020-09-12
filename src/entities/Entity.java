package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
import world.Camera;

public class Entity {
	
	public static BufferedImage ENEMY = Game.spritesheet.getSprite(160, 80, 40, 40);
	public static BufferedImage LIFEPACK = Game.spritesheet.getSprite(340, 20, 20, 20);
	public static BufferedImage ARROW = Game.spritesheet.getSprite(340, 0, 20, 20);
	public static BufferedImage BOW = Game.spritesheet.getSprite(320, 0, 20, 20);
	
	private int x,y,w,h;
	private BufferedImage sprite;
	
	public Entity(int x, int y, int w, int h, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.sprite = sprite;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(this.sprite, this.getX() - Camera.x, this.getY()-Camera.y, null);
	}
	
	
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public int getW() {return this.w;}
	public int getH() {return this.h;}
	
	public void setX(int x) {this.x=x;}
	public void setY(int y) {this.y=y;}
	public void setW(int w) {this.w=w;}
	public void setH(int h) {this.h=h;}
}
