package de.meinkraft;

public class ChunkManager {

	// the squared radius in which chunks get generated
	public static final byte RADIUS = 6;
	
	private Chunk[][] chunks;
	
	private final World world;
	private final ChunkLoader chunkLoader;
	
	public ChunkManager(World world) {
		this.world = world;
		chunkLoader = new ChunkLoader(this);
		
		chunks = new Chunk[2 * RADIUS + 1][2 * RADIUS + 1];
	}
	
	public void update(int px, int pz) {
		for(int x = 0; x < chunks.length; x++)
			for(int z = 0; z < chunks.length; z++) {
				Chunk chunk = chunks[x][z];
				
				if(chunk == null) {
					cs:for(int lx = -RADIUS; lx <= RADIUS; lx++)
						for(int lz = -RADIUS; lz <= RADIUS; lz++) {
							if(!existsChunkAt(px + lx, pz + lz)) {
								chunks[x][z] = new Chunk(px + lx, pz + lz);
								chunkLoader.addChunk(chunks[x][z]);
								break cs;
							}
						}
				} else {
					if(Math.abs(px - chunk.getX()) > RADIUS || Math.abs(pz - chunk.getZ()) > RADIUS) {
						chunk.unload();
						chunks[x][z] = null;
						
						if(z > 0)
							z--;
						else {
							x--;
							z = chunks.length - 1;
						}
					} else
						chunk.update();
				}
			}
	}
	
	public void render() {
		for(int x = 0; x < chunks.length; x++)
			for(int z = 0; z < chunks.length; z++) {
				if(chunks[x][z] == null || !chunks[x][z].isLoaded())
					continue;
				
				chunks[x][z].render();
			}
	}
	
	private boolean existsChunkAt(int x, int z) {
		for(int xx = 0; xx < chunks.length; xx++)
			for(int zz = 0; zz < chunks.length; zz++) {
				if(chunks[xx][zz] == null)
					continue;
				
				if(chunks[xx][zz].getX() == x && chunks[xx][zz].getZ() == z)
					return true;
			}
		
		return false;
	}
	
	public Block getBlockAt(int x, int y, int z) {
		int chunkX = (int) Math.floor((double) x / Chunk.SIZE_X);
		int chunkZ = (int) Math.floor((double) z / Chunk.SIZE_Z);
		
		for(int xx = 0; xx < chunks.length; xx++)
			for(int zz = 0; zz < chunks.length; zz++) {
				if(chunks[xx][zz] == null)
					continue;
				
				if(chunks[xx][zz].getX() == chunkX && chunks[xx][zz].getZ() == chunkZ)
					return chunks[xx][zz].getBlockAt(x - chunkX * Chunk.SIZE_X, y, z - chunkZ * Chunk.SIZE_Z);
			}
		
		return Block.AIR;
	}
	
	public void setBlockAt(int x, int y, int z, Block block) {
		int chunkX = (int) Math.floor(x / Chunk.SIZE_X);
		int chunkZ = (int) Math.floor(z / Chunk.SIZE_Z);
		
		for(int xx = 0; xx < chunks.length; xx++)
			for(int zz = 0; zz < chunks.length; zz++) {
				if(chunks[xx][zz] == null)
					continue;
				
				if(chunks[xx][zz].getX() == chunkX && chunks[xx][zz].getZ() == chunkZ) {
					chunks[xx][zz].setBlockAt(x - chunkX * Chunk.SIZE_X, y, z - chunkZ * Chunk.SIZE_Z, block);
					// make the chunk rebuild in the next update
				}
			}
	}
	
	public World getWorld() {
		return world;
	}
	
}
