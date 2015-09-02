package de.meinkraft;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.io.IOException;
import java.util.Locale;

import de.meinkraft.lib.Camera;
import de.meinkraft.lib.Display;
import de.meinkraft.lib.Matrix4;
import de.meinkraft.lib.Shader;
import de.meinkraft.lib.Texture;
import de.meinkraft.lib.Time;
import de.meinkraft.lib.Transform;
import de.meinkraft.lib.Utils;

public class Meinkraft {
	
	private Camera camera;
	private int textures;
	private Shader shader;
	
	private World world;
	
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
		
		world = new World("World");
	}
	
	public void input() {
		camera.input();
	}
	
	public void update() {
		// temp
		world.update((int) Math.floor(-camera.getTransform().getTranslation().getX() / Chunk.SIZE_X), (int) Math.floor(-camera.getTransform().getTranslation().getZ() / Chunk.SIZE_Z));
		
		Display.setTitle("FPS " + String.format(Locale.ENGLISH, "%.2f", 1.0f / Time.getDelta()));
		if(Display.wasResized()) {
			camera.getProjectionMatrix().initPerspective(70f, (float) Display.getWidth() / (float) Display.getHeight(), 0.01f, 1000f);
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
	}
	
	public void render() {
		shader.bind();
		shader.setUniform("v", camera.getViewMatrix());
		Texture.bindTexture2DArray(textures);
		world.render();
		Shader.unbind();
	}
	
}
