package world;

public class Camera {
	public static int x, y;
	public static int clamp(int atual, int min, int max) {
		atual = (atual < min) ? min : atual;
		atual = (atual > max) ? max : atual;
		
		return atual;
	}
}
