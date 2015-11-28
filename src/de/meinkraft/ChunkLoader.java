package de.meinkraft;

import java.util.ArrayDeque;

import org.lwjgl.BufferUtils;

public class ChunkLoader extends Thread {

	private ArrayDeque<Chunk> queue, queue2;
	
	private final ChunkManager chunkManager;
	
	public ChunkLoader(ChunkManager chunkManager) {
		this.chunkManager = chunkManager;
		
		queue = new ArrayDeque<Chunk>();
		queue2 = new ArrayDeque<Chunk>();
		start();
	}
	
	public synchronized void addChunk(Chunk chunk) {
		if(queue.contains(chunk))
			return;
		
		chunk.setState(ChunkState.LOADING);
		queue.add(chunk);
		notify();
	}
	
	@Override
	public void run() {
		while(isAlive()) {
			if(!queue.isEmpty()) {
				Chunk chunk = queue.peek();
				
				if(!chunkManager.isChunkInRange(chunk.getX(), chunk.getZ())) {
					if(chunkManager.hasChunkNeighboursLoading(chunk))
						continue;
					
					chunk.setState(ChunkState.UNLOADED);
					queue.remove();
				} else {
//					System.out.println(chunk.getX() + "," + chunk.getZ() + " generating");
					chunkManager.getWorld().getWorldGenerator().generate(chunk);
					
					if(!queue2.contains(chunk))
						queue2.add(queue.poll());
				}
			}
			
			if(!queue2.isEmpty()) {
				Chunk chunk = queue2.peek();
				
				if(!chunkManager.isChunkInRange(chunk.getX(), chunk.getZ())) {
					if(chunkManager.hasChunkNeighboursLoading(chunk))
						continue;
					
					chunk.setState(ChunkState.UNLOADED);
					queue2.remove();
					continue;
				}
				
//				if(chunk.isGenerated()) {
					if(!chunkManager.isChunkSurrounded(chunk))
						continue;
					
					System.out.println(chunk.getX() + "," + chunk.getZ() + " loading");
					int vcount = 0;
					for(int x = 0; x < Chunk.SIZE_X; x++)
						for(int y = 0; y < Chunk.SIZE_Y; y++)
							for(int z = 0; z < Chunk.SIZE_Z; z++) {
								Blockcheck bc = new Blockcheck();
								
								int wx = chunk.getX() * Chunk.SIZE_X + x;
								int wz = chunk.getZ() * Chunk.SIZE_Z + z;
								
								if(chunkManager.getBlockAt(wx + 1, y, wz).getType().isSolid() && chunkManager.getBlockAt(wx + 1, y, wz).getMaterial().isOpaque())
									bc.east = true;
								
								if(chunkManager.getBlockAt(wx - 1, y, wz).getType().isSolid() && chunkManager.getBlockAt(wx - 1, y, wz).getMaterial().isOpaque())
									bc.west = true;
								
								if(chunkManager.getBlockAt(wx, y + 1, wz).getType().isSolid() && chunkManager.getBlockAt(wx, y + 1, wz).getMaterial().isOpaque())
									bc.top = true;
								
								if(chunkManager.getBlockAt(wx, y - 1, wz).getType().isSolid() && chunkManager.getBlockAt(wx, y - 1, wz).getMaterial().isOpaque())
									bc.bottom = true;
								
								if(chunkManager.getBlockAt(wx, y, wz + 1).getType().isSolid() && chunkManager.getBlockAt(wx, y, wz + 1).getMaterial().isOpaque())
									bc.south = true;
								
								if(chunkManager.getBlockAt(wx, y, wz - 1).getType().isSolid() && chunkManager.getBlockAt(wx, y, wz - 1).getMaterial().isOpaque())
									bc.north = true;
								
								vcount += chunk.getBlockAt(x, y, z).getType().getVerticesCount(bc);
							}
					
					chunk.vertices = BufferUtils.createFloatBuffer(vcount * 7);
					for(int x = 0; x < Chunk.SIZE_X; x++)
						for(int y = 0; y < Chunk.SIZE_Y; y++)
							for(int z = 0; z < Chunk.SIZE_Z; z++) {
								Blockcheck bc = new Blockcheck();
								BlockAO ao = new BlockAO();
								
								int wx = chunk.getX() * Chunk.SIZE_X + x;
								int wz = chunk.getZ() * Chunk.SIZE_Z + z;
								
								if(chunkManager.getBlockAt(wx + 1, y, wz).getType().isSolid() && chunkManager.getBlockAt(wx + 1, y, wz).getMaterial().isOpaque())
									bc.east = true;
								
								if(chunkManager.getBlockAt(wx - 1, y, wz).getType().isSolid() && chunkManager.getBlockAt(wx - 1, y, wz).getMaterial().isOpaque())
									bc.west = true;
								
								if(chunkManager.getBlockAt(wx, y + 1, wz).getType().isSolid() && chunkManager.getBlockAt(wx, y + 1, wz).getMaterial().isOpaque())
									bc.top = true;
								
								if(chunkManager.getBlockAt(wx, y - 1, wz).getType().isSolid() && chunkManager.getBlockAt(wx, y - 1, wz).getMaterial().isOpaque())
									bc.bottom = true;
								
								if(chunkManager.getBlockAt(wx, y, wz + 1).getType().isSolid() && chunkManager.getBlockAt(wx, y, wz + 1).getMaterial().isOpaque())
									bc.south = true;
								
								if(chunkManager.getBlockAt(wx, y, wz - 1).getType().isSolid() && chunkManager.getBlockAt(wx, y, wz - 1).getMaterial().isOpaque())
									bc.north = true;
								
								if(!bc.all()) {
									if(chunkManager.getBlockAt(wx + 1, y + 1, wz).getType().isSolid() && chunkManager.getBlockAt(wx + 1, y + 1, wz).getMaterial().isOpaque())
										ao.eT = true;
									if(chunkManager.getBlockAt(wx + 1, y - 1, wz).getType().isSolid() && chunkManager.getBlockAt(wx + 1, y - 1, wz).getMaterial().isOpaque())
										ao.eB = true;
									if(chunkManager.getBlockAt(wx - 1, y + 1, wz).getType().isSolid() && chunkManager.getBlockAt(wx - 1, y + 1, wz).getMaterial().isOpaque())
										ao.wT = true;
									if(chunkManager.getBlockAt(wx - 1, y - 1, wz).getType().isSolid() && chunkManager.getBlockAt(wx - 1, y - 1, wz).getMaterial().isOpaque())
										ao.wB = true;
									if(chunkManager.getBlockAt(wx, y + 1, wz + 1).getType().isSolid() && chunkManager.getBlockAt(wx, y + 1, wz + 1).getMaterial().isOpaque())
										ao.sT = true;
									if(chunkManager.getBlockAt(wx, y - 1, wz + 1).getType().isSolid() && chunkManager.getBlockAt(wx, y - 1, wz + 1).getMaterial().isOpaque())
										ao.sB = true;
									if(chunkManager.getBlockAt(wx, y + 1, wz - 1).getType().isSolid() && chunkManager.getBlockAt(wx, y + 1, wz - 1).getMaterial().isOpaque())
										ao.nT = true;
									if(chunkManager.getBlockAt(wx, y - 1, wz - 1).getType().isSolid() && chunkManager.getBlockAt(wx, y - 1, wz - 1).getMaterial().isOpaque())
										ao.nB = true;
									
									if(chunkManager.getBlockAt(wx + 1, y + 1, wz + 1).getType().isSolid() && chunkManager.getBlockAt(wx + 1, y + 1, wz + 1).getMaterial().isOpaque())
										ao.cXYZ = true;
									if(chunkManager.getBlockAt(wx - 1, y + 1, wz + 1).getType().isSolid() && chunkManager.getBlockAt(wx - 1, y + 1, wz + 1).getMaterial().isOpaque())
										ao.c_XYZ = true;
									if(chunkManager.getBlockAt(wx + 1, y - 1, wz + 1).getType().isSolid() && chunkManager.getBlockAt(wx + 1, y - 1, wz + 1).getMaterial().isOpaque())
										ao.cX_YZ = true;
									if(chunkManager.getBlockAt(wx + 1, y + 1, wz - 1).getType().isSolid() && chunkManager.getBlockAt(wx + 1, y + 1, wz - 1).getMaterial().isOpaque())
										ao.cXY_Z = true;
									if(chunkManager.getBlockAt(wx - 1, y - 1, wz + 1).getType().isSolid() && chunkManager.getBlockAt(wx - 1, y - 1, wz + 1).getMaterial().isOpaque())
										ao.c_X_YZ = true;
									if(chunkManager.getBlockAt(wx + 1, y - 1, wz - 1).getType().isSolid() && chunkManager.getBlockAt(wx + 1, y - 1, wz - 1).getMaterial().isOpaque())
										ao.cX_Y_Z = true;
									if(chunkManager.getBlockAt(wx - 1, y + 1, wz - 1).getType().isSolid() && chunkManager.getBlockAt(wx - 1, y + 1, wz - 1).getMaterial().isOpaque())
										ao.c_XY_Z = true;
									if(chunkManager.getBlockAt(wx - 1, y - 1, wz - 1).getType().isSolid() && chunkManager.getBlockAt(wx - 1, y - 1, wz - 1).getMaterial().isOpaque())
										ao.c_X_Y_Z = true;
								}
								
								chunk.getBlockAt(x, y, z).getType().addVertices(wx, y, wz, chunk.vertices, bc, ao, chunk.getBlockAt(x, y, z).getTextures());
							}
					chunk.vertices.flip();
					
					chunk.indices = BufferUtils.createIntBuffer(chunk.vertices.limit() / 4 * 6);
					for(int i = 0, j = 0; i < chunk.indices.limit(); i += 6, j += 4) {
						chunk.indices.put(j + 0);
						chunk.indices.put(j + 1);
						chunk.indices.put(j + 2);
						chunk.indices.put(j + 0);
						chunk.indices.put(j + 2);
						chunk.indices.put(j + 3);
					}
					chunk.indices.flip();
					chunk.doLoad();
					
					queue2.remove();
//				}
				
				continue;
			}
			
			synchronized (this) {
				try {
					System.out.println("waiting");
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
	
}
