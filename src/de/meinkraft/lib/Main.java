package de.meinkraft.lib;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALContext;

import de.meinkraft.Meinkraft;

public class Main {
	
	public static void main(String[] args) {
		Display.create(1280, 720, "Meinkraft git");
		ALContext alc = ALContext.create();
//		alDistanceModel
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glClearColor(1, 1, 1, 1);
		
		Meinkraft mk = new Meinkraft();
		
		while(glfwWindowShouldClose(Display.getWindow()) == 0) {
			Time.update();
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			mk.input();
			Input.update();
			glfwPollEvents();
			mk.update();
			
			mk.render();
			
			glfwSwapBuffers(Display.getWindow());
		}
		
		Input.destroy();
		Display.destory();
		AL.destroy(alc);
		
		System.exit(0);
	}
	
}
