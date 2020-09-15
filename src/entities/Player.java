package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Game;
import graficos.Spritesheet;
import world.Camera;
import world.World;

public class Player extends Entity {
	
	private boolean d,u,r,l;
	
	private int rDirect = 0, lDirect = 1;
	private int direct = rDirect;
	private int speed = 2;
	
	private int frames = 0, maxFrames = 5, index, maxIndex=3;
	private boolean moved;
	
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	private int level = 1;
	private double life = 100;
	private double maxLife = 100;
	private double damage = 0;
	
	private int arrows=0;
	
	private int mms = 0;
	
	public Player(int x, int y, int w, int h, BufferedImage sprite ) {
		super(x, y, h, w, sprite);
		
		rightPlayer = new BufferedImage[5];
		leftPlayer = new BufferedImage[5];
		
		for (int i = 0; i < 4; i++ ) {
			rightPlayer[i] = Game.spritesheet.getSprite(160+(i*World.TILE_SIZE), 0, World.TILE_SIZE, World.TILE_SIZE);
			leftPlayer[i] = Game.spritesheet.getSprite(160+(i*World.TILE_SIZE), 40, World.TILE_SIZE, World.TILE_SIZE);
		}
		rightPlayer[4] = Game.spritesheet.getSprite(160, 80, World.TILE_SIZE, World.TILE_SIZE);
		leftPlayer[4] = Game.spritesheet.getSprite(280, 120, World.TILE_SIZE, World.TILE_SIZE);
	}
	
	public void tick() {
		moved = false;
		
		if (d && World.isFree(this.getX(), (int)this.getY()+speed)) { // DOWN
			this.setY(getY() + speed);
			moved = true;
		}
		if (u && World.isFree(this.getX(), this.getY()-speed)) { // UP
			this.setY(getY() - speed);
			moved = true;
		}
		if (r && World.isFree((this.getX()+speed), this.getY())) { // RIGTH
			direct = rDirect;
			this.setX(getX() + speed);
			moved = true;
		}
		if (l && World.isFree(this.getX()-speed, this.getY())) { //LEFT
			direct = lDirect;
			this.setX(getX() - speed);
			moved = true;
		}
		
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		} else {index = (direct == rDirect)?0:3;}
		
		checkCollisionCollect();
		
		Camera.x = Camera.clamp((this.getX() - (Game.WIDTH/2)), 0, World.WIDTH*40 - Game.WIDTH );
		Camera.y = Camera.clamp((this.getY() - (Game.HEIGHT/2)), 0, World.HEIGHT*40 - Game.HEIGHT );
		
		if (damage != 0) {
			mms++;
			if (mms>5) {
				damage = 0;
				mms = 0;
			}
		}
		
		if (life<=0) Game.restartGame();
		
	}
	
	public void damage(int d) {
		this.damage = d;
		this.life -= damage;
		
	}
	
	public void checkCollisionCollect() {
		for (int i = 0; i < Game.itens.size(); i++) {
			Entity atual = Game.itens.get(i);
			if (atual instanceof Lifepack) {
				if (Entity.isColliding(this, atual) && (life<maxLife)) {
					this.life+=10;
					if (this.life>this.maxLife)this.life=this.maxLife;
					System.out.println("pegou life "+i);
					Game.itens.remove(atual);
					return;
				}
			}else if(atual instanceof Arrow) {
				if (Entity.isColliding(this, atual)) {
					this.arrows+=12;
					System.out.println("pegou arrow "+i);
					Game.itens.remove(atual);
					return;
				}
			}
		}
	}
	
		
	
	public void render(Graphics g) {
		//super.render(g);
		
		if (direct == rDirect) {
			if(damage == 0) {
				g.drawImage(rightPlayer[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
			}else {
				g.drawImage(rightPlayer[4], this.getX()-Camera.x, this.getY()-Camera.y, null);
				g.setColor(Color.YELLOW);
				g.setFont(new Font("Arial", Font.PLAIN, 10));
				g.drawString("-"+damage, this.getX()+(World.TILE_SIZE/2)-Camera.x-10, this.getY()-10-Camera.y);
			}
		}else if (direct == lDirect) {
			if(damage == 0) {
				g.drawImage(leftPlayer[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
			}else {
				g.drawImage(leftPlayer[4], this.getX()-Camera.x, this.getY()-Camera.y, null);
				g.setColor(Color.YELLOW);
				g.setFont(new Font("Arial", Font.PLAIN, 10));
				g.drawString("-"+damage, this.getX()+(World.TILE_SIZE/2)-Camera.x-10, this.getY()-10-Camera.y);
			}
		}
		
		
	}
	
	
	// ======================== Getters and Setters ====================>
	
	public boolean isU() {
		return this.u;
	}

	public void setU(boolean u) {
		this.u = u;
	}

	public boolean isD() {
		return this.d;
	}

	public void setD(boolean d) {
		this.d = d;
	}

	public boolean isL() {
		return this.l;
	}

	public void setL(boolean l) {
		this.l = l;
	}

	public boolean isR() {
		return this.r;
	}

	public void setR(boolean r) {
		this.r = r;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int newSpeed) {
		this.speed = newSpeed;
	}
	
	public double getLife() {
		return this.life;
	}
	
	public void setLife(double life) {
		this.life = life;
	}
	
	public void modLife(int life) {
		this.setLife(this.getLife()+life);
	}

	public double getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}
	
	public int getArrows() {
		return this.arrows;
	}
	
}
