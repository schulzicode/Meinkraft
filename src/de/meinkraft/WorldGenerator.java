package de.meinkraft;

import java.util.Random;

public abstract class WorldGenerator {
	
	private final Random random = new Random(2015);
	
	public abstract void generate(Chunk chunk);
	
	public Random getRandom() {
		return random;
	}

}
