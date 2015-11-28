package de.meinkraft;

import java.util.ArrayDeque;
import static org.lwjgl.opengl.GL11.*;

public class ChunkManager {

	// the radius in which chunks get generated
	public static final byte RADIUS = 4;
	
	private Chunk[][] chunks;
	
	private final World world;
	private final ChunkLoader chunkLoader;
	private final ArrayDeque<Chunk> queue;
	
	public ChunkManager(World world) {
		this.world = world;
		chunkLoader = new ChunkLoader(this);
		queue = new ArrayDeque<Chunk>();
		
		chunks = new Chunk[2 * RADIUS + 1][2 * RADIUS + 1];
	}
	
	public void update() {
		int px = getPlayerChunkPositionX();
		int pz = getPlayerChunkPositionZ();
		
		for(int x = 0; x < chunks.length; x++)
			for(int z = 0; z < chunks.length; z++) {
				Chunk chunk = chunks[x][z];
				
				if(chunk == null) {
					cs:for(int lx = -RADIUS; lx <= RADIUS; lx++)
						for(int lz = -RADIUS; lz <= RADIUS; lz++) {
							if(getChunkAt(px + lx, pz + lz) == null) {
								chunks[x][z] = new Chunk(px + lx, pz + lz);
								chunkLoader.addChunk(chunks[x][z]);
								break cs;
							}
						}
				} else {
					if(isChunkInRange(chunk.getX(), chunk.getZ()))
							chunk.update();
					else {
						if(chunk.getState() == ChunkState.LOADING || hasChunkNeighboursLoading(chunk)) {
							if(!queue.contains(chunk))
								queue.add(chunk);
						} else {
							System.out.println("FIRST_HAS_NEIGHBOURS_LOADING: " + hasChunkNeighboursLoading(chunk) + " " + chunk.getX() + "," + chunk.getZ());
							chunk.unload();
						}
						
						chunks[x][z] = null;
						
						if(z > 0)
							z--;
						else {
							if(x > 0) {
								x--;
								z = chunks.length - 1;
							}
						}
					}
				}
			}
		
		if(!queue.isEmpty()) {
			Chunk chunk = queue.poll();
			
			if(chunk.getState() != ChunkState.LOADING && !hasChunkNeighboursLoading(chunk)) {
				System.out.println("SECOND_HAS_NEIGHBOURS_LOADING: " + hasChunkNeighboursLoading(chunk) + " " + chunk.getX() + "," + chunk.getZ());
				chunk.unload();
			}
			else
				queue.add(chunk);
		}
	}
	
	public void render() {
		for(int x = 0; x < chunks.length; x++)
			for(int z = 0; z < chunks.length; z++) {
				Chunk chunk = chunks[x][z];
				
				if(chunk == null || !chunk.isLoaded())
					continue;
				
//				Vector3 playerPos = new Vector3(world.getPlayer().pos.getX(), world.getPlayer().pos.getY(), world.getPlayer().pos.getZ());
//				Vector3 chunkDir1 = new Vector3(chunk.getX() * Chunk.SIZE_X, 0, chunk.getZ() * Chunk.SIZE_Z).add(playerPos.negate()).normalised();
//				Vector3 chunkDir2 = new Vector3(chunk.getX() * Chunk.SIZE_X + 16, 0, chunk.getZ() * Chunk.SIZE_Z).add(playerPos.negate()).normalised();
//				Vector3 chunkDir3 = new Vector3(chunk.getX() * Chunk.SIZE_X + 16, 0, chunk.getZ() * Chunk.SIZE_Z + 16).add(playerPos.negate()).normalised();
//				Vector3 chunkDir4 = new Vector3(chunk.getX() * Chunk.SIZE_X, 0, chunk.getZ() * Chunk.SIZE_Z + 16).add(playerPos.negate()).normalised();
//				
//				if(chunkDir1.dot(world.getPlayer().dir) > 0 && chunkDir2.dot(world.getPlayer().dir) > 0 && chunkDir3.dot(world.getPlayer().dir) > 0 && chunkDir4.dot(world.getPlayer().dir) > 0) {
					chunk.render();
//					count++;
//				}
			}
		
		// debug
		glBegin(GL_LINE_STRIP);
		{
			for(int x = 0; x < chunks.length; x++)
				for(int z = 0; z < chunks.length; z++) {
					Chunk chunk = chunks[x][z];
					
					if(chunk == null)
						continue;
					
					if(chunk.isGenerated()) {
						glVertex3f(chunk.getX() * Chunk.SIZE_X, 65, chunk.getZ() * Chunk.SIZE_Z);
						glVertex3f(chunk.getX() * Chunk.SIZE_X + Chunk.SIZE_X, 65, chunk.getZ() * Chunk.SIZE_Z);
						glVertex3f(chunk.getX() * Chunk.SIZE_X + Chunk.SIZE_X, 65, chunk.getZ() * Chunk.SIZE_Z + Chunk.SIZE_Z);
						glVertex3f(chunk.getX() * Chunk.SIZE_X, 65, chunk.getZ() * Chunk.SIZE_Z + Chunk.SIZE_Z);
					}
				}
		}
		glEnd();
	}
	
	public void regenerate() {
		for(int xx = 0; xx < chunks.length; xx++)
			for(int zz = 0; zz < chunks.length; zz++) {
				if(chunks[xx][zz] == null)
					continue;
				
				chunks[xx][zz].deleteBlocks();
				chunkLoader.addChunk(chunks[xx][zz]);
			}
	}
	
	public int getPlayerChunkPositionX() {
		return (int) Math.floor(world.getPlayer().pos.getX() / Chunk.SIZE_X);
	}
	
	public int getPlayerChunkPositionZ() {
		return (int) Math.floor(world.getPlayer().pos.getZ() / Chunk.SIZE_Z);
	}
	
//	public boolean allChunksGenerated() {
//		for(int x = 0; x < chunks.length; x++)
//			for(int z = 0; z < chunks.length; z++) {
//				if(chunks[x][z] == null || !chunks[x][z].isGenerated())
//					return false;
//			}
//		
//		return true;
//	}
	
	public boolean isChunkInRange(int x, int z) {
		if(Math.abs((int) Math.floor(world.getPlayer().pos.getX() / Chunk.SIZE_X) - x) > RADIUS || Math.abs((int) Math.floor(world.getPlayer().pos.getZ() / Chunk.SIZE_Z) - z) > RADIUS)
			return false;
		else
			return true;
	}
	
	/**
	 * 
	 * @param chunk
	 * @return Returns true if the chunk is surrounded by generated chunks
	 */
	public boolean isChunkSurrounded(Chunk chunk) {
		if(isChunkInRange(chunk.getX() - 1, chunk.getZ())) {
			if(getChunkAt(chunk.getX() - 1, chunk.getZ()) == null || !getChunkAt(chunk.getX() - 1, chunk.getZ()).isGenerated())
				return false;
		}
		
		if(isChunkInRange(chunk.getX() + 1, chunk.getZ())) {
			if(getChunkAt(chunk.getX() + 1, chunk.getZ()) == null || !getChunkAt(chunk.getX() + 1, chunk.getZ()).isGenerated())
				return false;
		}
		
		if(isChunkInRange(chunk.getX(), chunk.getZ() - 1)) {
			if(getChunkAt(chunk.getX(), chunk.getZ() - 1) == null || !getChunkAt(chunk.getX(), chunk.getZ() - 1).isGenerated())
				return false;
		}
		
		if(isChunkInRange(chunk.getX(), chunk.getZ() + 1)) {
			if(getChunkAt(chunk.getX(), chunk.getZ() + 1) == null || !getChunkAt(chunk.getX(), chunk.getZ() + 1).isGenerated())
				return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param chunk
	 * @return Returns true if the chunk has a neighbour, which is in a loading state
	 */
	public boolean hasChunkNeighboursLoading(Chunk chunk) {
		if(getChunkAt(chunk.getX() - 1, chunk.getZ()) != null && !getChunkAt(chunk.getX() - 1, chunk.getZ()).isGenerated())
			return true;
	
		if(getChunkAt(chunk.getX() + 1, chunk.getZ()) != null && !getChunkAt(chunk.getX() + 1, chunk.getZ()).isGenerated())
			return true;
	
		if(getChunkAt(chunk.getX(), chunk.getZ() - 1) != null && !getChunkAt(chunk.getX(), chunk.getZ() - 1).isGenerated())
			return true;
	
		if(getChunkAt(chunk.getX(), chunk.getZ() + 1) != null && !getChunkAt(chunk.getX(), chunk.getZ() + 1).isGenerated())
			return true;
		
		return false;
	}
	
	public Chunk getChunkAt(int x, int z) {
		for(int xx = 0; xx < chunks.length; xx++)
			for(int zz = 0; zz < chunks.length; zz++) {
				if(chunks[xx][zz] == null)
					continue;
				
				if(chunks[xx][zz].getX() == x && chunks[xx][zz].getZ() == z)
					return chunks[xx][zz];
			}
		
		if(!queue.isEmpty()) {
			Object[] chunks = queue.toArray();
			for(Object obj : chunks) {
				if(obj instanceof Chunk) {
					Chunk chunk = (Chunk) obj;
					
					if(chunk.getX() == x && chunk.getZ() == z)
						return chunk;
				}
			}
		}
		
		return null;
	}
	
	public Block getBlockAt(int x, int y, int z) {
		int chunkX = (int) Math.floor((float) x / Chunk.SIZE_X);
		int chunkZ = (int) Math.floor((float) z / Chunk.SIZE_Z);
		
		for(int xx = 0; xx < chunks.length; xx++)
			for(int zz = 0; zz < chunks.length; zz++) {
				if(chunks[xx][zz] == null)
					continue;
				
				if(chunks[xx][zz].getX() == chunkX && chunks[xx][zz].getZ() == chunkZ)
					return chunks[xx][zz].getBlockAt(x - chunkX * Chunk.SIZE_X, y, z - chunkZ * Chunk.SIZE_Z);
			}
		
		if(!queue.isEmpty()) {
			Object[] chunks = queue.toArray();
			for(Object obj : chunks) {
				if(obj instanceof Chunk) {
					Chunk chunk = (Chunk) obj;
					
					if(chunk.getX() == chunkX && chunk.getZ() == chunkZ)
						return chunk.getBlockAt(x - chunkX * Chunk.SIZE_X, y, z - chunkZ * Chunk.SIZE_Z);
				}
			}
		}
		
		return Block.AIR;
	}
	
	public void setBlockAt(int x, int y, int z, Block block) {
		int chunkX = (int) Math.floor((float) x / Chunk.SIZE_X);
		int chunkZ = (int) Math.floor((float) z / Chunk.SIZE_Z);
		
		for(int xx = 0; xx < chunks.length; xx++)
			for(int zz = 0; zz < chunks.length; zz++) {
				if(chunks[xx][zz] == null)
					continue;
				
				if(chunks[xx][zz].getX() == chunkX && chunks[xx][zz].getZ() == chunkZ) {
					chunks[xx][zz].setBlockAt(x - chunkX * Chunk.SIZE_X, y, z - chunkZ * Chunk.SIZE_Z, block);
					
					chunkLoader.addChunk(chunks[xx][zz]);
				}
			}
	}
	
	public World getWorld() {
		return world;
	}
	
	public Chunk[][] getChunks() {
		return chunks;
	}
	
}
