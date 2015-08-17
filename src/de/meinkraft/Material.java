package de.meinkraft;

public class Material {
	
	public static final Material AIR = new Material().setOpaque(false);
	
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
