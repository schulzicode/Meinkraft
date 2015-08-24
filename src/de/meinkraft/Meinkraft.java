package de.meinkraft;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_G;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.io.IOException;

import de.meinkraft.lib.Camera;
import de.meinkraft.lib.Display;
import de.meinkraft.lib.Input;
import de.meinkraft.lib.Matrix4;
import de.meinkraft.lib.Shader;
import de.meinkraft.lib.Texture;
import de.meinkraft.lib.Transform;
import de.meinkraft.lib.Utils;

public class Meinkraft {
	
	private Camera camera;
	private int textures;
	private Shader shader;
	
	private Chunk chunk;
	private ChunkLoader chunkLoader;
	
	public Meinkraft() {
		camera = new Camera(new Matrix4().initPerspective(70f, (float) Display.getWidth() / (float) Display.getHeight(), 0.01f, 1000f));
		try {
			textures = Texture.loadTexture2DArray(Utils.getResourceAsStream("/terrain.png"), 64, 64, GL_NEAREST, GL_LINEAR, GL_CLAMP_TO_EDGE);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			shader = Shader.loadShader("/shader.vs", "/shader.fs", null);
			shader.addUniform("p");
			shader.addUniform("v");
			shader.addUniform("m");
			
			shader.bind();
			shader.setUniform("p", camera.getProjectionMatrix());
			shader.setUniform("m", new Transform().getTransformationMatrix());
			Shader.unbind();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		chunk = new Chunk(0, 0);
		chunkLoader = new ChunkLoader();
	}
	
	public void input() {
		camera.input();
		
		if(Input.getKeyDown(GLFW_KEY_G))
			chunkLoader.addChunk(chunk);
	}
	
	public void update() {
		
	}
	
	public void render() {
		shader.bind();
		shader.setUniform("v", camera.getViewMatrix());
		Texture.bindTexture2DArray(textures);
		
		chunk.render();
		
		Shader.unbind();
	}
	
}
