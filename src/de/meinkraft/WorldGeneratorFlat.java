package de.meinkraft;

public class WorldGeneratorFlat extends WorldGenerator {
	
	@Override
	public void generate(Chunk chunk) {
		for(int x = 0; x < Chunk.SIZE_X; x++)
			for(int y = 0; y < Chunk.SIZE_Y; y++)
				for(int z = 0; z < Chunk.SIZE_Z; z++) {
					if(y > 63) {
						if(y == 64) {
							if(getRandom().nextInt(5) == 0)
								chunk.setBlockAt(x, y, z, Block.TALL_GRASS);
							else if(getRandom().nextInt(50) == 0)
								chunk.setBlockAt(x, y, z, Block.ROSE);
							else
								chunk.setBlockAt(x, y, z, Block.AIR);
						} else
							chunk.setBlockAt(x, y, z, Block.AIR);
					}
					else {
						if(y == 63)
							chunk.setBlockAt(x, y, z, Block.GRASS);
						else if(y >= 60)
							chunk.setBlockAt(x, y, z, Block.DIRT);
						else
							chunk.setBlockAt(x, y, z, Block.STONE);
					}
				}
	}
	
}
