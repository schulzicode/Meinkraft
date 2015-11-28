package de.meinkraft.gui;

import java.io.IOException;

import de.meinkraft.lib.Display;
import de.meinkraft.lib.Matrix4;
import de.meinkraft.lib.Shader;
import de.meinkraft.lib.Transform;

public class GUI {

	public static final Matrix4 ORTHO = new Matrix4().initOrthographic(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
	public static Shader SHADER;
	static {
		try {
			SHADER = Shader.loadShader("/gui.vs", "/gui.fs", null);
			SHADER.addUniform("p");
			SHADER.addUniform("v");
			SHADER.addUniform("m");
			
			SHADER.bind();
			SHADER.setUniform("p", ORTHO);
			SHADER.setUniform("v", new Transform().getTransformationMatrix());
			SHADER.setUniform("m", new Transform().getTransformationMatrix());
			
			Shader.unbind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
