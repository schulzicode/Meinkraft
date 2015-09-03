package de.meinkraft;

public class Blockcheck {
	
	public boolean north, east, south, west, top, bottom;
	
	public boolean all() {
		return north && east && south && west && top && bottom;
	}
	
}
