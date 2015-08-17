package de.meinkraft.lib;

public class Vector3 {
	
	private float x, y, z;
	
	public Vector3() {
		this(0);
	}
	
	public Vector3(float v) {
		this(v, v, v);
	}
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float angle(Vector3 v) {
		return (float) Math.toDegrees(Math.acos(dot(v) / (length() * v.length())));
	}
	
	public Vector3 cross(Vector3 v) {
		return new Vector3(y * v.getZ() - z * v.getY(), z * v.getX() - x * v.getZ(), x * v.getY() - y * v.getX());
	}
	
	public float dot(Vector3 v) {
		return x * v.getX() + y * v.getY() + z * v.getZ();
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public Vector3 negate() {
		return mul(-1);
	}
	
	public Vector3 normalised() {
		return new Vector3(x / length(), y / length(), z / length());
	}
	
	public Vector3 add(Vector3 v) {
		return new Vector3(x + v.getX(), y + v.getY(), z + v.getZ());
	}
	
	public Vector3 mul(float v) {
		return new Vector3(x * v, y * v, z * v);
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
	
	public float getZ() {
		return z;
	}
	
	public void setZ(float z) {
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "vec3(" + x + ", " + y + ", " + z + ")";
	}
	
}
