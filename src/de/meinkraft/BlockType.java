package de.meinkraft;

import java.nio.FloatBuffer;

public abstract class BlockType {
	
	public static final BlockType AIR = new BlockTypeAir(false);
	public static final BlockType CUBE = new BlockTypeCube(true);
	public static final BlockType PLANT = new BlockTypePlant(false);
	
	private final boolean solid;
	
	public BlockType(boolean solid) {
		this.solid = solid;
	}
	
	public abstract int getVerticesCount(Blockcheck check);
	public abstract void addVertices(float x, float y, float z, FloatBuffer buffer, Blockcheck check, int...textures);
	
	public boolean isSolid() {
		return solid;
	}
	
}
