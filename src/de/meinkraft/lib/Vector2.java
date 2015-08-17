package de.meinkraft.lib;

public class Vector2 {
	
	private float x, y;
	
	public Vector2() {
		this(0);
	}
	
	public Vector2(float v) {
		this(v, v);
	}
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float angle(Vector2 v) {
		return (float) Math.toDegrees(Math.acos(dot(v) / (length() * v.length())));
	}
	
	public float dot(Vector2 v) {
		return x * v.getX() + y * v.getY();
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public Vector2 negate() {
		return mul(-1);
	}
	
	public Vector2 normalised() {
		return new Vector2(x / length(), y / length());
	}
	
	public Vector2 add(Vector2 v) {
		return new Vector2(x + v.getX(), y + v.getY());
	}
	
	public Vector2 mul(float v) {
		return new Vector2(x * v, y * v);
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "vec2(" + x + ", " + y + ")";
	}
	
}
