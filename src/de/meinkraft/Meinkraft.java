package de.meinkraft;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.io.IOException;
import java.util.Locale;

import de.meinkraft.gui.GUI;
import de.meinkraft.gui.GUIFont;
import de.meinkraft.gui.GUILabel;
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
	
	private GUILabel guiText;
	
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
		
		guiText = new GUILabel(0.5f, 0.05f, 200, 50);
		guiText.setOrientationX(1);
		guiText.setOrientationY(0);
		
		world = new World("World", new WorldGeneratorFlat());
	}
	
	public void input() {
		camera.input();
		
		if(Input.getKeyDown(GLFW_KEY_F)) {
			shader.bind();
			shader.setUniform("enableFog", fog ? 0 : 1);
			fog = !fog;
			Shader.unbind();
		}
		
		if(Input.getKeyDown(GLFW_KEY_P)) {
			try {
				StructureLoader.loadStructure(world, (int) Math.floor(-camera.getTransform().getTranslation().getX()), 64, (int) Math.floor(-camera.getTransform().getTranslation().getZ()), Utils.getResourceAsStream("/structure_house.txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// for debug
		if(Input.getKeyDown(GLFW_KEY_R)) {
			world.setWorldGenerator(new WorldGeneratorNormal());
			world.getChunkManager().regenerate();
		}
	}
	
	public void update() {
		// temp
		world.getPlayer().pos = camera.getTransform().getTranslation().negate();
		world.getPlayer().dir = camera.getDirection().negate();
		world.update();
		
		Display.setTitle("FPS " + String.format(Locale.ENGLISH, "%.2f", 1.0f / Time.getDelta()) + " cx:" + world.getChunkManager().getPlayerChunkPositionX() + " cz:" + world.getChunkManager().getPlayerChunkPositionZ());
		if(Display.wasResized()) {
			camera.getProjectionMatrix().initPerspective(70f, (float) Display.getWidth() / (float) Display.getHeight(), 0.01f, 1000f);
			GUI.ORTHO.initOrthographic(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
			
			shader.bind();
			shader.setUniform("p", camera.getProjectionMatrix());
			
			GUI.SHADER.bind();
			GUI.SHADER.setUniform("p", GUI.ORTHO);
			GUIFont.SHADER.bind();
			GUIFont.SHADER.setUniform("p", GUI.ORTHO);
			Shader.unbind();
			
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
	}
	
	public void render() {
		shader.bind();
		shader.setUniform("v", camera.getViewMatrix());
		Texture.bindTexture2DArray(textures);
		world.render();
		
		glCullFace(GL_FRONT);
		guiText.draw();
		GUILabel.font.drawString("BlA", 0, 0);
		glCullFace(GL_BACK);
		
		Shader.unbind();
	}
	
}
