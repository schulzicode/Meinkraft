package de.meinkraft;

public abstract class WorldGenerator {

	public static final WorldGenerator FLAT = new WorldGeneratorFlat();
	
	public abstract void generate(Chunk chunk);

}
