package de.meinkraft;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.OpenGLException;

public class BlockTypePlant extends BlockType {

	public BlockTypePlant(boolean solid, boolean both) {
		super(solid, both);
	}

	@Override
	public int getVerticesCount(Blockcheck check) {
		if(check.all())
			return 0;
		else
			return 16;
	}

	@Override
	public void addVertices(float x, float y, float z, FloatBuffer buffer, Blockcheck check, BlockAO ao, int... textures) {
		if(textures.length != 1)
			throw new OpenGLException(getClass().getSimpleName() + " must have 1 texture");
		
		if(!check.all()) {
			buffer.put(new float[] {
					x + 0, y + 0, z + 0,		0, 1, textures[0], getAO(false, false, false),
					x + 1, y + 0, z + 1,		1, 1, textures[0], getAO(false, false, false),
					x + 1, y + 1.4142f, z + 1,	1, 0, textures[0], getAO(false, false, false),
					x + 0, y + 1.4142f, z + 0,	0, 0, textures[0], getAO(false, false, false),
							
					x + 1, y + 0, z + 1,		1, 1, textures[0], getAO(false, false, false),
					x + 0, y + 0, z + 0,		0, 1, textures[0], getAO(false, false, false),
					x + 0, y + 1.4142f, z + 0,	0, 0, textures[0], getAO(false, false, false),
					x + 1, y + 1.4142f, z + 1,	1, 0, textures[0], getAO(false, false, false),
					
					x + 0, y + 0, z + 1,		0, 1, textures[0], getAO(false, false, false),
					x + 1, y + 0, z + 0,		1, 1, textures[0], getAO(false, false, false),
					x + 1, y + 1.4142f, z + 0,	1, 0, textures[0], getAO(false, false, false),
					x + 0, y + 1.4142f, z + 1,	0, 0, textures[0], getAO(false, false, false),
					
					x + 1, y + 0, z + 0,		1, 1, textures[0], getAO(false, false, false),
					x + 0, y + 0, z + 1,		0, 1, textures[0], getAO(false, false, false),
					x + 0, y + 1.4142f, z + 1,	0, 0, textures[0], getAO(false, false, false),
					x + 1, y + 1.4142f, z + 0,	1, 0, textures[0], getAO(false, false, false)
			});
		}
	}

}
