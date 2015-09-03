package de.meinkraft;

import java.util.ArrayDeque;

import org.lwjgl.BufferUtils;

public class ChunkLoader extends Thread {

	private ArrayDeque<Chunk> queue;
	
	private final ChunkManager chunkManager;
	
	public ChunkLoader(ChunkManager chunkManager) {
		this.chunkManager = chunkManager;
		
		queue = new ArrayDeque<Chunk>();
		start();
	}
	
	public synchronized void addChunk(Chunk chunk) {
		queue.add(chunk);
		notify();
	}
	
	@Override
	public void run() {
		while(isAlive()) {
			if(!queue.isEmpty()) {
				System.out.println("loading");
				if(queue.peek().isGenerated()) {
					Chunk chunk = queue.poll();
					
					int count = 0;
					for(int x = 0; x < Chunk.SIZE_X; x++)
						for(int y = 0; y < Chunk.SIZE_Y; y++)
							for(int z = 0; z < Chunk.SIZE_Z; z++) {
								Blockcheck bc = new Blockcheck();
								
								if(chunk.getBlockAt(x + 1, y, z).getType().isSolid() && chunk.getBlockAt(x + 1, y, z).getMaterial().isOpaque())
									bc.east = true;
								
								if(chunk.getBlockAt(x - 1, y, z).getType().isSolid() && chunk.getBlockAt(x - 1, y, z).getMaterial().isOpaque())
									bc.west = true;
								
								if(chunk.getBlockAt(x, y + 1, z).getType().isSolid() && chunk.getBlockAt(x, y + 1, z).getMaterial().isOpaque())
									bc.top = true;
								
								if(chunk.getBlockAt(x, y - 1, z).getType().isSolid() && chunk.getBlockAt(x, y - 1, z).getMaterial().isOpaque())
									bc.bottom = true;
								
								if(chunk.getBlockAt(x, y, z + 1).getType().isSolid() && chunk.getBlockAt(x, y, z + 1).getMaterial().isOpaque())
									bc.south = true;
								
								if(chunk.getBlockAt(x, y, z - 1).getType().isSolid() && chunk.getBlockAt(x, y, z - 1).getMaterial().isOpaque())
									bc.north = true;
								
								count += chunk.getBlockAt(x, y, z).getType().getVerticesCount(bc);
							}
					
					chunk.vertices = BufferUtils.createFloatBuffer(count * 6);
					for(int x = 0; x < Chunk.SIZE_X; x++)
						for(int y = 0; y < Chunk.SIZE_Y; y++)
							for(int z = 0; z < Chunk.SIZE_Z; z++) {
								Blockcheck bc = new Blockcheck();
								
								if(chunk.getBlockAt(x + 1, y, z).getType().isSolid() && chunk.getBlockAt(x + 1, y, z).getMaterial().isOpaque())
									bc.east = true;
								
								if(chunk.getBlockAt(x - 1, y, z).getType().isSolid() && chunk.getBlockAt(x - 1, y, z).getMaterial().isOpaque())
									bc.west = true;
								
								if(chunk.getBlockAt(x, y + 1, z).getType().isSolid() && chunk.getBlockAt(x, y + 1, z).getMaterial().isOpaque())
									bc.top = true;
								
								if(chunk.getBlockAt(x, y - 1, z).getType().isSolid() && chunk.getBlockAt(x, y - 1, z).getMaterial().isOpaque())
									bc.bottom = true;
								
								if(chunk.getBlockAt(x, y, z + 1).getType().isSolid() && chunk.getBlockAt(x, y, z + 1).getMaterial().isOpaque())
									bc.south = true;
								
								if(chunk.getBlockAt(x, y, z - 1).getType().isSolid() && chunk.getBlockAt(x, y, z - 1).getMaterial().isOpaque())
									bc.north = true;
								
								chunk.getBlockAt(x, y, z).getType().addVertices(chunk.getX() * Chunk.SIZE_X + x, y, chunk.getZ() * Chunk.SIZE_Z + z, chunk.vertices, bc, chunk.getBlockAt(x, y, z).getTextures());
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
				} else
					WorldGeneratorFlat.FLAT.generate(queue.peek());
			} else {
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
	
}
