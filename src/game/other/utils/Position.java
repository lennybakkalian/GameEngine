package game.other.utils;

public class Position {

	private float x, y;

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getXAsInt() {
		return (int) x;
	}

	public int getYAsInt() {
		return (int) y;
	}

}
