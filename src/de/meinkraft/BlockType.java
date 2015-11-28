package de.meinkraft;

import java.nio.FloatBuffer;

public abstract class BlockType {
	
	public static final BlockType AIR = new BlockTypeAir(false, false);
	public static final BlockType CUBE = new BlockTypeCube(true, false);
	public static final BlockType PLANT = new BlockTypePlant(false, true);
	
	private final boolean solid, doubleSided;
	
	public BlockType(boolean solid, boolean doubleSided) {
		this.solid = solid;
		this.doubleSided = doubleSided;
	}
	
	public abstract int getVerticesCount(Blockcheck check);
	public abstract void addVertices(float x, float y, float z, FloatBuffer buffer, Blockcheck check, BlockAO ao, int...textures);
	
	public float getAO(boolean side1, boolean side2, boolean corner) {
		if(side1 && side2)
			return 0;
		
		return 3 - (side1 ? 1 : 0 + (side2 ? 1 : 0) + (corner ? 1 : 0));
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean isDoubleSided() {
		return doubleSided;
	}
	
}
