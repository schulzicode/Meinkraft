package de.meinkraft;

import de.meinkraft.lib.Time;

public class World {

	private final ChunkManager chunkManager;
	private final Player player;
	
	private String name;
	private WorldGenerator worldGenerator;
	
	public World(String name, WorldGenerator worldGenerator) {
		this.name = name;
		this.worldGenerator = worldGenerator;
		
		chunkManager = new ChunkManager(this);
		player = new Player();
	}
	
	public void update() {
		chunkManager.update();
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
	
	public Player getPlayer() {
		return player;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public WorldGenerator getWorldGenerator() {
		return worldGenerator;
	}
	
	public void setWorldGenerator(WorldGenerator worldGenerator) {
		this.worldGenerator = worldGenerator;
	}
	
}
