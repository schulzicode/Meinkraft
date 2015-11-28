package de.meinkraft;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import de.meinkraft.lib.VAO;

public class Chunk {
	
	public static final int SIZE_X = 16, SIZE_Y = 128, SIZE_Z = 16;
	
	private final Block[][][] blocks;
	private final short[][][] lights;
	
	private VAO vao;
	public FloatBuffer vertices;
	public IntBuffer indices;
	private int solidBlocks;
	
	private boolean doLoad;
	
	private final int x, z;
	private ChunkState state;
	
	public Chunk(int x, int z) {
		this.x = x;
		this.z = z;
		this.state = ChunkState.UNLOADED;
		
		blocks = new Block[SIZE_X][SIZE_Y][SIZE_Z];
		lights = new short[SIZE_X][SIZE_Y][SIZE_Z];
	}
	
	public void update() {
		if(doLoad)
			load();
	}
	
	public void render() {
		if(isLoaded())
			vao.render(solidBlocks, 0);
	}
	
	public void load() {
		if(vao == null)
			vao = new VAO(GL_TRIANGLES);
		
		vao.initVBO(vertices);
		vao.initIBO(indices);
		vao.initVAO("3 7 0", "3 7 3", "1 7 6");
		
		solidBlocks = indices.limit();
		doLoad = false;
		state = ChunkState.LOADED;
	}
	
	public void unload() {
		if(isLoaded())
			vao.delete();
		
		vao = null;
		vertices = null;
		indices = null;
		state = ChunkState.UNLOADED;
	}
	
	public void deleteBlocks() {
		for(int x = 0; x < Chunk.SIZE_X; x++)
			for(int y = 0; y < Chunk.SIZE_Y; y++)
				for(int z = 0; z < Chunk.SIZE_Z; z++) {
					blocks[x][y][z] = null;
				}
	}
	
	public boolean isGenerated() {
		return blocks[SIZE_X - 1][SIZE_Y - 1][SIZE_Z - 1] != null;
	}
	
	public boolean isLoaded() {
		return vao != null;
	}

	public int getSkyLight(int x, int y, int z) {
		return lights[x][y][z] >> 12;
	}
	
	public void setSkyLight(int x, int y, int z, int value) {
		if(value < 0) value = 0; else if(value > 15) value = 15;
		
		lights[x][y][z] = (short) (lights[x][y][z] & 0x0FFF | value << 12);
	}
	
	public int getVoxelLightR(int x, int y, int z) {
		return lights[x][y][z] & 0x0F00 >> 8;
	}
	
	public int getVoxelLightG(int x, int y, int z) {
		return lights[x][y][z] & 0x00F0 >> 4;
	}
	
	public int getVoxelLightB(int x, int y, int z) {
		return lights[x][y][z] & 0x000F >> 0;
	}
	
	public void setVoxelLight(int x, int y, int z, int r, int g, int b) {
		if(r < 0) r = 0; else if(r > 15) r = 15;
		if(g < 0) g = 0; else if(g > 15) g = 15;
		if(b < 0) b = 0; else if(b > 15) b = 15;
		
		lights[x][y][z] = (short) (lights[x][y][z] & 0xF000 | r << 8 | g << 4 | b << 0);
	}
	
	public Block getBlockAt(int x, int y, int z) {
		if(isGenerated() && x >= 0 && x < SIZE_X && y >= 0 && y < SIZE_Y && z >= 0 && z < SIZE_Z)
			return blocks[x][y][z];
		
		return Block.AIR; // temp
	}
	
	public void setBlockAt(int x, int y, int z, Block block) {
		if(x >= 0 && x < SIZE_X && y >= 0 && y < SIZE_Y && z >= 0 && z < SIZE_Z)
			blocks[x][y][z] = block;
	}
	
	public void doLoad() {
		doLoad = true;
	}

	public ChunkState getState() {
		return state;
	}

	public void setState(ChunkState state) {
		this.state = state;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}
	
}
