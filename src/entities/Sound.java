package entities;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	
	private AudioClip clip;
	
	public static final Sound BG_MUSIC = new Sound("/copycat.wav");
	public static final Sound SHOOT = new Sound("/shoot.wav");
	public static final Sound SUPER_SHOOT = new Sound("/Super_Shoot.wav");
	public static final Sound PLAYER_DAMAGE = new Sound("/Player_Damage.wav");
	public static final Sound NEXT_LEVEL = new Sound("/Next_Level.wav");
	
	public Sound(String name) {
		try {
		clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch (Throwable e){
			
		}
		
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}catch (Throwable e){
			
		}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		}catch (Throwable e){
			
		}
	}
	public void stop() {
		try {
			new Thread() {
				public void run() {
					clip.stop();
				}
			}.start();
		}catch (Throwable e){
			
		}
	}
	
}
