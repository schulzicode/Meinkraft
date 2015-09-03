package de.meinkraft;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import de.meinkraft.lib.VAO;

public class Chunk {
	
	public static final int SIZE_X = 16, SIZE_Y = 256, SIZE_Z = 16;
	
	private final Block[][][] blocks;
	private VAO vao;
	public FloatBuffer vertices;
	public IntBuffer indices;
	private int solidBlocks;
	
	private boolean load;
	
	private final int x, z;
	
	public Chunk(int x, int z) {
		this.x = x;
		this.z = z;
		
		blocks = new Block[SIZE_X][SIZE_Y][SIZE_Z];
	}
	
	public void update() {
		if(load)
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
		vao.initVAO("3 6 0", "3 6 3");
		
		solidBlocks = indices.limit();
		vertices = null;
		indices = null;
		load = false;
	}
	
	public void unload() {
		if(isLoaded())
			vao.delete();
		
		vao = null;
	}
	
	public boolean isGenerated() {
		return blocks[0][0][0] != null;
	}
	
	public boolean isLoaded() {
		return vao != null;
	}

	public Block getBlockAt(int x, int y, int z) {
		if(x >= 0 && x < SIZE_X && y >= 0 && y < SIZE_Y && z >= 0 && z < SIZE_Z)
			return blocks[x][y][z];
		
		return Block.AIR; // temp
	}
	
	public void setBlockAt(int x, int y, int z, Block block) {
		if(x >= 0 && x < SIZE_X && y >= 0 && y < SIZE_Y && z >= 0 && z < SIZE_Z)
			blocks[x][y][z] = block;
	}
	
	public void doLoad() {
		load = true;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}
	
}
