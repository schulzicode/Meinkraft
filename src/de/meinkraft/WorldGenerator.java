package de.meinkraft;

import java.util.Random;

public abstract class WorldGenerator {

	public static final WorldGenerator FLAT = new WorldGeneratorFlat();
	
	private final Random random = new Random(2015);
	
	public abstract void generate(Chunk chunk);
	
	public Random getRandom() {
		return random;
	}

}
