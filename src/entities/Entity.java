package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {
	
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
		g.drawImage(this.sprite, this.x, this.y, null);
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
