package de.meinkraft;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.OpenGLException;

public class BlockTypeCube extends BlockType {
	
	public BlockTypeCube(boolean solid) {
		super(solid);
	}

	@Override
	public int getVerticesCount(Blockcheck check) {
		int count = 0;
		
		if(!check.north)
			count += 4;
		
		if(!check.east)
			count += 4;
		
		if(!check.south)
			count += 4;
		
		if(!check.west)
			count += 4;
		
		if(!check.top)
			count += 4;
		
		if(!check.bottom)
			count += 4;
		
		return count;
	}

	@Override
	public void addVertices(float x, float y, float z, FloatBuffer buffer, Blockcheck check, int...textures) {
		if(textures.length != 6)
			throw new OpenGLException("BlockTypeCube must have 6 textures");
		
		if(!check.north)
			buffer.put(new float[] {
					x + 0, y + 0, z + 0,	1, 1, textures[0],
					x + 0, y + 1, z + 0,	1, 0, textures[0],
					x + 1, y + 1, z + 0,	0, 0, textures[0],
					x + 1, y + 0, z + 0,	0, 1, textures[0]
			});
		
		if(!check.east)
			buffer.put(new float[] {
					x + 1, y + 0, z + 1,	0, 1, textures[1],
					x + 1, y + 0, z + 0,	1, 1, textures[1],
					x + 1, y + 1, z + 0,	1, 0, textures[1],
					x + 1, y + 1, z + 1,	0, 0, textures[1]
			});
		
		if(!check.south)
			buffer.put(new float[] {
					x + 0, y + 0, z + 1,	0, 1, textures[2],
					x + 1, y + 0, z + 1,	1, 1, textures[2],
					x + 1, y + 1, z + 1,	1, 0, textures[2],
					x + 0, y + 1, z + 1,	0, 0, textures[2]
			});
		
		if(!check.west)
			buffer.put(new float[] {
					x + 0, y + 0, z + 0,	0, 1, textures[3],
					x + 0, y + 0, z + 1,	1, 1, textures[3],
					x + 0, y + 1, z + 1,	1, 0, textures[3],
					x + 0, y + 1, z + 0,	0, 0, textures[3]
			});
		
		if(!check.top)
			buffer.put(new float[] {
					x + 0, y + 1, z + 1,	0, 1, textures[4],
					x + 1, y + 1, z + 1,	1, 1, textures[4],
					x + 1, y + 1, z + 0,	1, 0, textures[4],
					x + 0, y + 1, z + 0,	0, 0, textures[4]
			});
		
		if(!check.bottom)
			buffer.put(new float[] {
					x + 0, y + 0, z + 1,	0, 1, textures[5],
					x + 0, y + 0, z + 0,	0, 0, textures[5],
					x + 1, y + 0, z + 0,	1, 0, textures[5],
					x + 1, y + 0, z + 1,	1, 1, textures[5]
			});
	}
	
	
	
}
