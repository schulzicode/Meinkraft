package de.meinkraft;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.io.IOException;
import java.util.Locale;

import de.meinkraft.lib.Camera;
import de.meinkraft.lib.Display;
import de.meinkraft.lib.Input;
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
	
	private boolean fog;
	
	private World world;
	
	public Meinkraft() {
		camera = new Camera(new Matrix4().initPerspective(70f, (float) Display.getWidth() / (float) Display.getHeight(), 0.01f, 1000f));
		try {
			textures = Texture.loadTexture2DArray(Utils.getResourceAsStream("/terrain.png"), 64, 64, GL_NEAREST, GL_NEAREST, GL_CLAMP_TO_EDGE);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			shader = Shader.loadShader("/shader.vs", "/shader.fs", null);
			shader.addUniform("p");
			shader.addUniform("v");
			shader.addUniform("m");
			shader.addUniform("enableFog");
			
			shader.bind();
			shader.setUniform("p", camera.getProjectionMatrix());
			shader.setUniform("m", new Transform().getTransformationMatrix());
			shader.setUniform("enableFog", 0);
			Shader.unbind();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		world = new World("World");
	}
	
	public void input() {
		camera.input();
		
		if(Input.getKeyDown(GLFW_KEY_F)) {
			shader.bind();
			shader.setUniform("enableFog", fog ? 0 : 1);
			fog = !fog;
			Shader.unbind();
		}
	}
	
	public void update() {
		// temp
		world.update((int) Math.floor(-camera.getTransform().getTranslation().getX() / Chunk.SIZE_X), (int) Math.floor(-camera.getTransform().getTranslation().getZ() / Chunk.SIZE_Z));
		
		Display.setTitle("FPS " + String.format(Locale.ENGLISH, "%.2f", 1.0f / Time.getDelta()));
		if(Display.wasResized()) {
			camera.getProjectionMatrix().initPerspective(70f, (float) Display.getWidth() / (float) Display.getHeight(), 0.01f, 1000f);
			shader.bind();
			shader.setUniform("p", camera.getProjectionMatrix());
			Shader.unbind();
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
