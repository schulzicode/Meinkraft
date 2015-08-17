package de.meinkraft.lib;

public class Vector4 {
	
	private float x, y, z, w;
	
	public Vector4() {
		this(0);
	}
	
	public Vector4(float v) {
		this(v, v, v, v);
	}
	
	public Vector4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public float dot(Vector4 v) {
		return x * v.getX() + y * v.getY() + z * v.getZ() + w * v.getW();
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}
	
	public Vector4 negate() {
		return mul(-1);
	}
	
	public Vector4 normalised() {
		return new Vector4(x / length(), y / length(), z / length(), w / length());
	}
	
	public Vector4 add(Vector4 v) {
		return new Vector4(x + v.getX(), y + v.getY(), z + v.getZ(), w + v.getW());
	}
	
	public Vector4 mul(float v) {
		return new Vector4(x * v, y * v, z * v, w * v);
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
	
	public float getW() {
		return w;
	}
	
	public void setW(float w) {
		this.w = w;
	}
	
	@Override
	public String toString() {
		return "vec4(" + x + ", " + y + ", " + z + ", " + w + ")";
	}
	
}
