package de.meinkraft;

public class Material {
	
	public static final Material AIR = new Material();
	public static final Material STONE = new Material().setOpaque(true);
	public static final Material GROUND = new Material().setOpaque(true);
	
	private boolean opaque;
	
	public Material() {
		
	}
	
	public boolean isOpaque() {
		return opaque;
	}
	
	public Material setOpaque(boolean opaque) {
		this.opaque = opaque;
		return this;
	}
	
}
