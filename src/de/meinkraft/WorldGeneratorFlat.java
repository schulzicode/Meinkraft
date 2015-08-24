package de.meinkraft;

public class WorldGeneratorFlat extends WorldGenerator {
	
	@Override
	public void generate(Chunk chunk) {
		for(int x = 0; x < Chunk.SIZE_X; x++)
			for(int y = 0; y < Chunk.SIZE_Y; y++)
				for(int z = 0; z < Chunk.SIZE_Z; z++) {
					chunk.setBlockAt(x, y, z, Block.DIRT);
				}
	}
	
}
