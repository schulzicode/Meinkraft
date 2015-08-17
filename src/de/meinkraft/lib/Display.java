package de.meinkraft.lib;

import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GLContext;

public class Display {
	
	private static int WIDTH, HEIGHT;
	private static long WINDOW;
	
	private static GLFWErrorCallback errorCallback;
	private static GLFWWindowSizeCallback windowSizeCallback;
	
	private static boolean resized;
	
	public static void create(int w, int h, String t) {
		WIDTH = w;
		HEIGHT = h;
		
		glfwSetErrorCallback(errorCallback = Callbacks.errorCallbackPrint(System.err));
		
		if(glfwInit() == 0)
			throw new RuntimeException("Unable to initialize GLFW");
		
		glfwWindowHint(GLFW_RESIZABLE, 1);
		
		WINDOW = glfwCreateWindow(w, h, t, 0, 0);
		
		glfwMakeContextCurrent(WINDOW);
		GLContext.createFromCurrent();
		
		glfwSwapInterval(1);
		setupCallbacks();
	}
	
	public static void destory() {
		glfwDestroyWindow(WINDOW);
		windowSizeCallback.release();
		glfwTerminate();
        errorCallback.release();
	}
	
	private static void setupCallbacks() {
		glfwSetWindowSizeCallback(Display.WINDOW, windowSizeCallback = new GLFWWindowSizeCallback() {
			
			@Override
			public void invoke(long window, int width, int height) {
				WIDTH = width;
				HEIGHT = height;
				
				resized = true;
			}
			
		});
	}
	
	public static int getWidth() {
		return WIDTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}
	
	public static long getWindow() {
		return WINDOW;
	}
	
	public static boolean wasResized() {
		if(resized) {
			resized = false;
			return true;
		} else
			return false;
	}
	
	public static void setTitle(String title) {
		glfwSetWindowTitle(WINDOW, title);
	}
	
}
