package de.meinkraft;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.OpenGLException;

public class BlockTypeCube extends BlockType {
	
	public BlockTypeCube(boolean solid, boolean both) {
		super(solid, both);
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
	public void addVertices(float x, float y, float z, FloatBuffer buffer, Blockcheck check, BlockAO ao, int...textures) {
		if(textures.length != 6)
			throw new OpenGLException(getClass().getSimpleName() + " must have 6 textures");
		
		if(!check.north)
			buffer.put(new float[] {
					x + 0, y + 0, z + 0,	1, 1, textures[0], getAO(ao.nB, ao.wB, ao.c_X_Y_Z),
					x + 0, y + 1, z + 0,	1, 0, textures[0], getAO(ao.nT, ao.wT, ao.c_XY_Z),
					x + 1, y + 1, z + 0,	0, 0, textures[0], getAO(ao.nT, ao.eT, ao.cXY_Z),
					x + 1, y + 0, z + 0,	0, 1, textures[0], getAO(ao.nB, ao.eB, ao.cX_Y_Z)
			});
		
		if(!check.east)
			buffer.put(new float[] {
					x + 1, y + 0, z + 1,	0, 1, textures[1], getAO(ao.eB, ao.sB, ao.cX_YZ),
					x + 1, y + 0, z + 0,	1, 1, textures[1], getAO(ao.eB, ao.nB, ao.cX_Y_Z),
					x + 1, y + 1, z + 0,	1, 0, textures[1], getAO(ao.eT, ao.nT, ao.cXY_Z),
					x + 1, y + 1, z + 1,	0, 0, textures[1], getAO(ao.eT, ao.sT, ao.cXYZ)
			});
		
		if(!check.south)
			buffer.put(new float[] {
					x + 0, y + 0, z + 1,	0, 1, textures[2], getAO(ao.sB, ao.wB, ao.c_X_YZ),
					x + 1, y + 0, z + 1,	1, 1, textures[2], getAO(ao.sB, ao.eB, ao.cX_YZ),
					x + 1, y + 1, z + 1,	1, 0, textures[2], getAO(ao.sT, ao.eT, ao.cXYZ),
					x + 0, y + 1, z + 1,	0, 0, textures[2], getAO(ao.sT, ao.wT, ao.c_XYZ)
			});
		
		if(!check.west)
			buffer.put(new float[] {
					x + 0, y + 0, z + 0,	0, 1, textures[3], getAO(ao.wB, ao.nB, ao.c_X_Y_Z),
					x + 0, y + 0, z + 1,	1, 1, textures[3], getAO(ao.wB, ao.sB, ao.c_X_YZ),
					x + 0, y + 1, z + 1,	1, 0, textures[3], getAO(ao.wT, ao.sT, ao.c_XYZ),
					x + 0, y + 1, z + 0,	0, 0, textures[3], getAO(ao.wT, ao.nT, ao.c_XY_Z)
			});
		
		if(!check.top)
			buffer.put(new float[] {
					x + 0, y + 1, z + 1,	0, 1, textures[4], getAO(ao.wT, ao.sT, ao.c_XYZ),
					x + 1, y + 1, z + 1,	1, 1, textures[4], getAO(ao.sT, ao.eT, ao.cXYZ),
					x + 1, y + 1, z + 0,	1, 0, textures[4], getAO(ao.eT, ao.nT, ao.cXY_Z),
					x + 0, y + 1, z + 0,	0, 0, textures[4], getAO(ao.nT, ao.wT, ao.c_XY_Z)
			});
		
		if(!check.bottom)
			buffer.put(new float[] {
					x + 0, y + 0, z + 1,	0, 1, textures[5], getAO(ao.sB, ao.wB, ao.c_X_YZ),
					x + 0, y + 0, z + 0,	0, 0, textures[5], getAO(ao.wB, ao.nB, ao.c_X_Y_Z),
					x + 1, y + 0, z + 0,	1, 0, textures[5], getAO(ao.nB, ao.eB, ao.cX_Y_Z),
					x + 1, y + 0, z + 1,	1, 1, textures[5], getAO(ao.eB, ao.sB, ao.cX_YZ)
			});
	}
	
}
