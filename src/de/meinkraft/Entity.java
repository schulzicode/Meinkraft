package de.meinkraft;

import de.meinkraft.lib.Vector3;

public abstract class Entity {

	private Vector3 pos;
	
	public Entity() {
		pos = new Vector3();
	}
	
	public Vector3 getPos() {
		return pos;
	}
	
	public void setPos(Vector3 pos) {
		this.pos = pos;
	}
	
}
