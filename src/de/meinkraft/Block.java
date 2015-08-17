package de.meinkraft;

public abstract class Block {

	private final int id;
	
	public Block(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
