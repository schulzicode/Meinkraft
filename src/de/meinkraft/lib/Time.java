package de.meinkraft.lib;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {
	
	private static float delta = 0;
	private static double lastTime = 0;
	
	public static void update() {
		delta = (float) (glfwGetTime() - lastTime);
		lastTime = glfwGetTime();
	}
	
	public static float getDelta() {
		return delta;
	}
	
}
