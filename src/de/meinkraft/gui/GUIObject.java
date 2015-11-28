package de.meinkraft.gui;

public abstract class GUIObject {
	
	/*
	 * first 4 bits hold the orientation (where should the position set to? (relative to the object)) 0000xxxx
	 * last 4 bits hold the alignment (where should the position align to?) xxxx0000
	 */
	private byte orientation;
	
	private float x, y;
	private int width, height;
	
	public GUIObject(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void draw() {
		
	}
	
	public void setOrientationX(int x) {
		if(x < 0) x = 0; else if(x > 2) x = 2;
		
		orientation = (byte) ((orientation & 252) | (x & 3));
	}
	
	public int getOrientationX() {
		return (orientation & 3) >> 0;
	}
	
	public void setOrientationY(int y) {
		if(y < 0) y = 0; else if(y > 2) y = 2;
		
		orientation = (byte) (orientation & 243 | (y & 3) << 2);
	}
	
	public int getOrientationY() {
		return (orientation & 12) >> 2;
	}
	
	public void setAlignmentX(int x) {
		if(x < 0) x = 0; else if(x > 2) x = 2;
		
		orientation = (byte) (orientation & 207 | (x & 3) << 4);
	}
	
	public int getAlignmentX() {
		return (orientation & 48) >> 4;
	}
	
	public void setAlignmentY(int y) {
		if(y < 0) y = 0; else if(y > 2) y = 2;
		
		orientation = (byte) (orientation & 63 | (y & 3) << 6);
	}
	
	public int getAlignmentY() {
		return (orientation & 192) >> 6;
	}
	
	public byte getOrientation() {
		return orientation;
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
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
}
