package de.meinkraft;

public class World {

	private final ChunkManager chunkManager;
	
	private final String name;
	
	public World(String name) {
		this.name = name;
		
		chunkManager = new ChunkManager(this);
	}
	
	public void update(int px, int pz) {
		chunkManager.update(px, pz);
	}
	
	public void render() {
		chunkManager.render();
	}
	
	public Block getBlockAt(int x, int y, int z) {
		return chunkManager.getBlockAt(x, y, z);
	}
	
	public void setBlockAt(int x, int y, int z, Block block) {
		chunkManager.setBlockAt(x, y, z, block);
	}
	
	public ChunkManager getChunkManager() {
		return chunkManager;
	}
	
	public String getName() {
		return name;
	}
	
}
