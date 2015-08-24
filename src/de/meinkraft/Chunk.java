package de.meinkraft;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import de.meinkraft.lib.VAO;

public class Chunk {
	
	public static final int SIZE_X = 16, SIZE_Y = 256, SIZE_Z = 16;
	
	private final Block[][][] blocks;
	private VAO vao;
	private int count;
	
	private final int x, z;
	
	public Chunk(int x, int z) {
		this.x = x;
		this.z = z;
		
		blocks = new Block[SIZE_X][SIZE_Y][SIZE_Z];
		vao = new VAO(GL_TRIANGLES);
	}
	
	public void render() {
		if(isLoaded())
			vao.render(count, 0);
	}
	
	public void load() {
		if(vao == null)
			vao = new VAO(GL_TRIANGLES);
		
		int verticesCount = 0;
		for(int x = 0; x < Chunk.SIZE_X; x++)
			for(int y = 0; y < Chunk.SIZE_Y; y++)
				for(int z = 0; z < Chunk.SIZE_Z; z++) {
					Blockcheck bc = new Blockcheck();
					
					if(getBlockAt(x + 1, y, z).getType().isSolid() && getBlockAt(x + 1, y, z).getMaterial().isOpaque())
						bc.east = true;
					
					if(getBlockAt(x - 1, y, z).getType().isSolid() && getBlockAt(x - 1, y, z).getMaterial().isOpaque())
						bc.west = true;
					
					if(getBlockAt(x, y + 1, z).getType().isSolid() && getBlockAt(x, y + 1, z).getMaterial().isOpaque())
						bc.top = true;
					
					if(getBlockAt(x, y - 1, z).getType().isSolid() && getBlockAt(x, y - 1, z).getMaterial().isOpaque())
						bc.bottom = true;
					
					if(getBlockAt(x, y, z + 1).getType().isSolid() && getBlockAt(x, y, z + 1).getMaterial().isOpaque())
						bc.south = true;
					
					if(getBlockAt(x, y, z - 1).getType().isSolid() && getBlockAt(x, y, z - 1).getMaterial().isOpaque())
						bc.north = true;
					
					verticesCount += getBlockAt(x, y, z).getType().getVerticesCount(bc);
				}
		
		FloatBuffer vertices = BufferUtils.createFloatBuffer(verticesCount * 6);
		for(int x = 0; x < Chunk.SIZE_X; x++)
			for(int y = 0; y < Chunk.SIZE_Y; y++)
				for(int z = 0; z < Chunk.SIZE_Z; z++) {
					Blockcheck bc = new Blockcheck();
					
					if(getBlockAt(x + 1, y, z).getType().isSolid() && getBlockAt(x + 1, y, z).getMaterial().isOpaque())
						bc.east = true;
					
					if(getBlockAt(x - 1, y, z).getType().isSolid() && getBlockAt(x - 1, y, z).getMaterial().isOpaque())
						bc.west = true;
					
					if(getBlockAt(x, y + 1, z).getType().isSolid() && getBlockAt(x, y + 1, z).getMaterial().isOpaque())
						bc.top = true;
					
					if(getBlockAt(x, y - 1, z).getType().isSolid() && getBlockAt(x, y - 1, z).getMaterial().isOpaque())
						bc.bottom = true;
					
					if(getBlockAt(x, y, z + 1).getType().isSolid() && getBlockAt(x, y, z + 1).getMaterial().isOpaque())
						bc.south = true;
					
					if(getBlockAt(x, y, z - 1).getType().isSolid() && getBlockAt(x, y, z - 1).getMaterial().isOpaque())
						bc.north = true;
					
					getBlockAt(x, y, z).getType().addVertices(getX() * Chunk.SIZE_X + x, y, getZ() * Chunk.SIZE_Z + z, vertices, bc, 1,1,1,1,1,1);
				}
		vertices.flip();
		
		IntBuffer indices = BufferUtils.createIntBuffer(vertices.limit() / 4 * 6);
		for(int i = 0; i < indices.limit(); i += 6) {
			indices.put(i + 0);
			indices.put(i + 1);
			indices.put(i + 2);
			indices.put(i + 0);
			indices.put(i + 2);
			indices.put(i + 3);
		}
		indices.flip();
		
		count = indices.limit();
		vao.initVBO(vertices);
		vao.initIBO(indices);
		vao.initVAO("3 6 0", "3 6 3");
	}
	
	public void unload() {
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
	
	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}
	
}
