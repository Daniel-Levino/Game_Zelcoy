package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import world.Camera;
import world.World;

public class Enemy extends Entity {
	
	private double speed; //= 10;
	
	public Enemy(int x, int y, int w, int h, BufferedImage sprite) {
		super(x, y, w, h, sprite);
		
	}
	
	public void tick() {
		speed = 1;
		int rand = (int)(Math.random()*100)+1;
		if (rand<80) {
			if(this.getX() < Game.player.getX() && 
					World.isFree((int)(x + speed), this.getY()) && 
					!isColliding((int)(x + speed), this.getY())) {
				x += speed;
			} else if(this.getX() > Game.player.getX() && 
					World.isFree((int)(x - speed), this.getY()) && 
					!isColliding((int)(x - speed), this.getY())) {
				x-=speed;
			}else if(this.getY() < Game.player.getY() && 
					World.isFree(this.getX(), (int)(y + speed)) &&
					!isColliding(this.getX(), (int)(y + speed))) {
				y+=speed;
			} else if(this.getY() > Game.player.getY() && 
					World.isFree(this.getX(), (int)(y - speed)) &&
					!isColliding(this.getX(), (int)(y - speed))) {
				y-=speed;
			}
		}
	}
	
	public boolean isColliding(int xNext, int yNext) {
		Rectangle currentEnemy = new Rectangle(xNext+8, yNext, World.TILE_SIZE-16, World.TILE_SIZE);
		for (Enemy e : Game.enemies){
			
			if (e == this) continue;
			
			Rectangle targetEnemy = new Rectangle(e.getX()+8,e.getY(),World.TILE_SIZE-16,World.TILE_SIZE);
			
			if(currentEnemy.intersects(targetEnemy)) return true;
		}
		
		return false;
	}
	
	public void render (Graphics g) {
		super.render(g);
		// Mascara de colisão
		g.setColor(Color.RED);
		g.drawRect(x+8-Camera.x, y-Camera.y, World.TILE_SIZE-16, World.TILE_SIZE);
	}

}
