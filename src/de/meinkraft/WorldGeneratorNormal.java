package de.meinkraft;

public class WorldGeneratorNormal extends WorldGenerator {
	
	private OpenSimplexNoise osn0, osn1, osn2;
	
	public WorldGeneratorNormal() {
		osn0 = new OpenSimplexNoise(64, 0.1, 315123531234l);
		osn1 = new OpenSimplexNoise(30, 0.2, 315123531234l);
		osn2 = new OpenSimplexNoise(30, 0.2, 837538751238l);
	}

	@Override
	public void generate(Chunk chunk) {
		for(int x = 0; x < Chunk.SIZE_X; x++)
			for(int z = 0; z < Chunk.SIZE_Z; z++) {
				double v = osn0.getNoise((chunk.getX() * Chunk.SIZE_X + x), (chunk.getZ() * Chunk.SIZE_Z + z)) * 0.5 + 0.5;
				int h = (int) (Chunk.SIZE_Y * v);
				
				for(int y = 0; y < Chunk.SIZE_Y; y++) {
					if(y < h - 3)
						chunk.setBlockAt(x, y, z, Block.STONE);
					else if(y < h)
						chunk.setBlockAt(x, y, z, Block.DIRT);
					else if(y == h)
						chunk.setBlockAt(x, y, z, Block.GRASS);
					else
						chunk.setBlockAt(x, y, z, Block.AIR);
				}
			}
		
		for(int x = 0; x < Chunk.SIZE_X; x++)
			for(int z = 0; z < Chunk.SIZE_Z; z++) {
				for(int y = 0; y < Chunk.SIZE_Y; y++) {
					double v = osn1.getNoise((chunk.getX() * Chunk.SIZE_X + x), y / 1.5, (chunk.getZ() * Chunk.SIZE_Z + z)) * 0.5 + 0.5;
					double v2 = osn2.getNoise((chunk.getX() * Chunk.SIZE_X + x), y / 1.5, (chunk.getZ() * Chunk.SIZE_Z + z)) * 0.5 + 0.5;
					
					if(v > MIN && v < MAX && v2 > MIN && v2 < MAX)
						chunk.setBlockAt(x, y, z, Block.AIR);
//					else
//						chunk.setBlockAt(x, y, z, Block.AIR);
					
//					if(v > 0.50 && v < 0.51 && v2 > 0.50 && v2 < 0.51)
//						chunk.setBlockAt(x, y, z, Block.STONE);
//					else
//						chunk.setBlockAt(x, y, z, Block.AIR);
				}
			}
	}
	
	private final static double MIN = 0.52, MAX = 0.56;
	
//	@Override
//	public void generate(Chunk chunk) {
//		for(int x = 0; x < Chunk.SIZE_X; x++)
//			for(int z = 0; z < Chunk.SIZE_Z; z++) {
//				double v = osn.getNoise((chunk.getX() * Chunk.SIZE_X + x), (chunk.getZ() * Chunk.SIZE_Z + z)) * 0.5 + 0.5;
//				int h = (int) (Chunk.SIZE_Y * v);
//				
//				for(int y = 0; y < Chunk.SIZE_Y; y++) {
//					if(y < h - 3)
//						chunk.setBlockAt(x, y, z, Block.STONE);
//					else if(y < h)
//						chunk.setBlockAt(x, y, z, Block.DIRT);
//					else if(y == h)
//						chunk.setBlockAt(x, y, z, Block.GRASS);
//					else
//						chunk.setBlockAt(x, y, z, Block.AIR);
//				}
//			}
//	}

}
