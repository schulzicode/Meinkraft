package de.meinkraft;

import java.nio.FloatBuffer;

public class BlockTypeAir extends BlockType {

	public BlockTypeAir(boolean solid) {
		super(solid);
	}

	@Override
	public int getVerticesCount(Blockcheck check) {
		return 0;
	}

	@Override
	public void addVertices(float x, float y, float z, FloatBuffer buffer, Blockcheck check, int... textures) {
	}

}
