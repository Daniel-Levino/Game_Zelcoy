package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Game;
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
	
	
	
	public Player(int x, int y, int w, int h, BufferedImage sprite ) {
		super(x, y, h, w, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		
		for (int i = 0; i < 4; i++ ) {
			rightPlayer[i] = Game.spritesheet.getSprite(160+(i*40), 0, 40, 40);
			leftPlayer[i] = Game.spritesheet.getSprite(160+(i*40), 40, 40, 40);
		}
	}
	
	public void tick() {
		moved = false;
		if (d) {
			moved = true;
			this.setY(getY() + speed);
		}
		if (u) {
			moved = true;
			this.setY(getY() - speed);
		}
		if (r) {
			moved = true;
			direct = rDirect;
			this.setX(getX() + speed);
		}
		if (l) {
			moved = true;
			direct = lDirect;
			this.setX(getX() - speed);
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
		}
		
		Camera.x = Camera.clamp((this.getX() - (Game.WIDTH/2)), 0, World.WIDTH*40 - Game.WIDTH );
		Camera.y = Camera.clamp((this.getY() - (Game.HEIGHT/2)), 0, World.HEIGHT*40 - Game.HEIGHT );
		
	}
	
	public void render(Graphics g) {
		if (direct == rDirect) {
			g.drawImage(rightPlayer[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
		}else if (direct == lDirect) {
			g.drawImage(leftPlayer[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
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
}
