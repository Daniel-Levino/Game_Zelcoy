package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.Game;
import graficos.UI;
import world.Camera;
import world.World;

public class Enemy extends Entity {
	
	private double speed; //= 10;
	
	private int frames = 0, maxFrames = 10, index, maxIndex=3;
	
	private BufferedImage[] sprites;
	
	private int dano = 5;
	
	private int level = 3;
	
	private int sec=0;
	
	public int life = 5, maxLife = 5;
	
	
	
	public Enemy(int x, int y, int w, int h, BufferedImage sprite) {
		super(x, y, w, h, null);
		sprites = new BufferedImage[4];
		sprites[0] = Game.spritesheet.getSprite(320, 40, 20, 20);
		sprites[1] = Game.spritesheet.getSprite(340, 40, 20, 20);
		sprites[2] = Game.spritesheet.getSprite(320, 60, 20, 20);
		sprites[3] = Game.spritesheet.getSprite(340, 60, 20, 20);
	}
	
	public void tick() {
		if (!isCollidingWithPlayer()) {
			speed = 1;
			int rand = (int)(Math.random()*100)+1;
			if (rand<80) {
				if(this.getX() < Game.player.getX() && 
						World.isFree((int)(x + speed), this.getY(), 20, 20) && 
						!isCollidingWithEachOther((int)(x + speed), this.getY())) {
					x += speed;
				} else if(this.getX() > Game.player.getX() && 
						World.isFree((int)(x - speed), this.getY(), 20, 20) && 
						!isCollidingWithEachOther((int)(x - speed), this.getY())) {
					x-=speed;
				}//else
				if(this.getY() < (int)(Game.player.getY()+18) && 
						World.isFree(this.getX(), (int)(y + speed), 20, 20) &&
						!isCollidingWithEachOther(this.getX(), (int)(y + speed))) {
					y+=speed;
				} else if(this.getY() > Game.player.getY() && 
						World.isFree(this.getX(), (int)(y - speed), 20, 20) &&
						!isCollidingWithEachOther(this.getX(), (int)(y - speed))) {
					y-=speed;
				}
			}
		}else {
			sec++;
			if (sec == 45) {
				int rand = (int)(Math.random()*dano);
				Game.player.damage(rand);
				sec = 0;
				
			}
		}
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
		
		for (int i=0; i<Game.shoots.size();i++) {
			ArrowShooting a = Game.shoots.get(i);
			if (Entity.isColliding(this, a)) {
				life--;
				if(!(a instanceof ArrowSuperShooting)) Game.shoots.remove(a);
				
				UI.renderEnemyDamage(this);
				
			}
		}
		
		if (life<=0) {
			Game.enemies.remove(this);
			return;
		}
		
	}
	
	public boolean isCollidingWithPlayer() {
		Rectangle currentEnemy =new Rectangle(this.getX(), this.getY(), World.TILE_SIZE/2, World.TILE_SIZE/2);
		Rectangle player = new Rectangle(Game.player.getX()+8, Game.player.getY(), World.TILE_SIZE-16, World.TILE_SIZE);
		
		return player.intersects(currentEnemy);
	}
	
	public boolean isCollidingWithEachOther(int xNext, int yNext) {
		Rectangle currentEnemy = new Rectangle(xNext, yNext, World.TILE_SIZE/2, World.TILE_SIZE/2);
		for (Enemy e : Game.enemies){
			
			if (e == this) continue;
			
			Rectangle targetEnemy = new Rectangle(e.getX(),e.getY(),World.TILE_SIZE/2,World.TILE_SIZE/2);
			
			if(currentEnemy.intersects(targetEnemy)) return true;
		}
		
		return false;
	}
	
	public void render (Graphics g) {
		//super.render(g);
		g.drawImage(sprites[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		
	}

}
