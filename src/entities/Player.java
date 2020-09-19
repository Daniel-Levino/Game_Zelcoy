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
	private int superShootTimer=0;
	
	private boolean hasBow, shoot, superShoot, superShootActived;
	
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
		
		// ========== PLAYER MOVIMENTS ==========>
		if (d && World.isFree(this.getX()+8, (int)this.getY()+speed,World.TILE_SIZE-16,World.TILE_SIZE)) { // DOWN
			this.setY(getY() + speed);
			moved = true;
		}
		if (u && World.isFree(this.getX()+8, this.getY()-speed,World.TILE_SIZE-16,World.TILE_SIZE)) { // UP
			this.setY(getY() - speed);
			moved = true;
		}
		if (r && World.isFree((this.getX()+8+speed), this.getY(),World.TILE_SIZE-16,World.TILE_SIZE)) { // RIGTH
			direct = rDirect;
			this.setX(getX() + speed);
			moved = true;
		}
		if (l && World.isFree(this.getX()+8-speed, this.getY(),World.TILE_SIZE-16,World.TILE_SIZE)) { //LEFT
			direct = lDirect;
			this.setX(getX() - speed);
			moved = true;
		}
		
		// ========== SPRITES ANIMATION =========>
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
		
		// ========== CHECK ITEMS COLLISION ==========>
		checkCollisionCollect();
		
		// ========== CLAMP ADJUSTMENT ==========>
		Camera.x = Camera.clamp((this.getX() - (Game.WIDTH/2)), 0, World.WIDTH*40 - Game.WIDTH );
		Camera.y = Camera.clamp((this.getY() - (Game.HEIGHT/2)), 0, World.HEIGHT*40 - Game.HEIGHT );
		
		// ========== WHEN DAMAGE ==========>
		if (damage != 0) {
			mms++; 
			if (mms>20) {
				damage = 0;
				mms = 0;
			}
		}
		
		// ========== WHEN SHOOTING ==========>
		if (shoot && hasBow && (arrows>0)) {
			Sound.SHOOT.play();
			if (direct == rDirect) {
				int dx = 1;
				ArrowShooting shooting = new ArrowShooting(this.getX()+20, this.getY()+25, 20, 5, Entity.R_ARROW_SHOOTING, dx, 0);
				Game.shoots.add(shooting);
			}else {
				int dx = -1;
				ArrowShooting shooting = new ArrowShooting(this.getX(), this.getY()+25, 20, 5, Entity.L_ARROW_SHOOTING, dx, 0);
				Game.shoots.add(shooting);
			}
			
			arrows--;
			
		}
		
		prepareSuperShoot();
		
		if (superShootActived && hasBow && (arrows>0)) {
			Sound.SUPER_SHOOT.play();
			if (direct == rDirect) {
				int dx = 1;
				ArrowSuperShooting superShooting = new ArrowSuperShooting(this.getX()+20, this.getY()+25, 20, 5, Entity.R_SUPER_ARROW_SHOOTING, dx, 0);
				superShooting.setMask(0, -20, 20, 40);
				Game.shoots.add(superShooting);
			}else {
				int dx = -1;
				ArrowSuperShooting superShooting = new ArrowSuperShooting(this.getX(), this.getY()+25, 20, 5, Entity.L_ARROW_SHOOTING, dx, 0);
				Game.shoots.add(superShooting);
			}
			
			arrows-=10;
			superShootTimer = 0;
			superShootActived = false;
			
		}
		
		// ========== LIFE FINISH ===========>
		if (life<=0) {
			life = 0;
			Game.gameState = "GAME OVER"; // Game.initGame(Game.getCurrentLevel());
		}
		
		
		shoot = false;
	// ================================ TICK END ============================>
	}
	
	public void damage(int d) {
		this.damage = d;
		this.life -= damage;
		Sound.PLAYER_DAMAGE.play();;
		
	}
	
	public void checkCollisionCollect() {
		for (Entity atual : Game.itens) {	
			if (atual instanceof Lifepack) {
				if (Entity.isColliding(this, atual) && (life<maxLife)) {
					this.life+=10;
					if (this.life>this.maxLife)this.life=this.maxLife;
					Game.itens.remove(atual);
					return;
				}
			}else if(atual instanceof Arrow && hasBow) {
				if (Entity.isColliding(this, atual)) {
					this.arrows+=120;
					Game.itens.remove(atual);
					return;
				}
			}else if(atual instanceof Bow) {
				if (Entity.isColliding(this, atual)) {
					hasBow = true;
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
				if (hasBow) g.drawImage(Entity.R_HAS_BOW, getX()+18-Camera.x, getY()+18-Camera.y, null);
			}else {
				g.drawImage(rightPlayer[4], this.getX()-Camera.x, this.getY()-Camera.y, null);
				g.setColor(Color.YELLOW);
				g.setFont(new Font("Arial", Font.PLAIN, 10));
				g.drawString("-"+damage, this.getX()+(World.TILE_SIZE/2)-Camera.x-10, this.getY()-10-Camera.y);
			}
		}else if (direct == lDirect) {
			if(damage == 0) {
				g.drawImage(leftPlayer[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
				if (hasBow) g.drawImage(Entity.L_HAS_BOW, getX()+14-Camera.x, getY()+18-Camera.y, null);
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
	public void setShoot(boolean arg) {
		this.shoot = arg;
	}
	
	public void setSuperShoot(boolean arg) {
		this.superShoot = arg;
	}
	
	public void prepareSuperShoot() {
		if(superShoot) {
			superShootTimer++;
			if (superShootTimer>300) {
				superShootActived = true;
			}
		}else {
			superShootTimer = 0;
		}
	}
	
}
