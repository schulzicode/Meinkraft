package de.meinkraft.lib;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetInputMode;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Input {
	
	private static final boolean[] KEY_BUTTONS = new boolean[349];
	private static final boolean[] MOUSE_BUTTONS = new boolean[8];
	
	private static GLFWScrollCallback scrollCallback;
	static {
		glfwSetScrollCallback(Display.getWindow(), scrollCallback = new GLFWScrollCallback() {
			
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				mouseWheel = (int) yoffset;
				scrolled = true;
			}
			
		});
	}
	private static int mouseWheel;
	private static boolean scrolled;
	
	private static int lastMouseX = (int) getMousePos().getX();
	private static int lastMouseY = (int) getMousePos().getY();
	
	private static int mouseDX;
	private static int mouseDY;
	
	public static void update() {
		for(int i = 0; i < KEY_BUTTONS.length; i++)
			KEY_BUTTONS[i] = getKey(i);
		
		for(int i = 0; i < MOUSE_BUTTONS.length; i++)
			MOUSE_BUTTONS[i] = getMouse(i);
		
		int mouseX = (int) getMousePos().getX();
		int mouseY = (int) getMousePos().getY();
		
		mouseDX = mouseX - lastMouseX;
		mouseDY = mouseY - lastMouseY;
		
		lastMouseX = mouseX;
		lastMouseY = mouseY;
		
		scrolled = false;
	}
	
	public static void destroy() {
		if(scrollCallback != null)
			scrollCallback.release();
	}
	
	public static boolean getKey(int key) {
		return glfwGetKey(Display.getWindow(), key) == GLFW_PRESS;
	}
	
	public static boolean getKeyDown(int key) {
		return !KEY_BUTTONS[key] && glfwGetKey(Display.getWindow(), key) == GLFW_PRESS;
	}
	
	public static boolean getKeyUp(int key) {
		return KEY_BUTTONS[key] && glfwGetKey(Display.getWindow(), key) == GLFW_RELEASE;
	}
	
	public static boolean getMouse(int mouse) {
		return glfwGetMouseButton(Display.getWindow(), mouse) == GLFW_PRESS;
	}
	
	public static boolean getMouseDown(int mouse) {
		return !MOUSE_BUTTONS[mouse] && glfwGetMouseButton(Display.getWindow(), mouse) == GLFW_PRESS;
	}
	
	public static boolean getMouseUp(int mouse) {
		return MOUSE_BUTTONS[mouse] && glfwGetMouseButton(Display.getWindow(), mouse) == GLFW_RELEASE;
	}
	
	public static int getMouseDX() {
		return mouseDX;
	}
	
	public static int getMouseDY() {
		return mouseDY;
	}
	
	public static int getMouseWheel() {
		return scrolled ? mouseWheel : 0;
	}
	
	public static int getCursorMode() {
		return glfwGetInputMode(Display.getWindow(), GLFW_CURSOR);
	}
	
	public static void setCursorMode(int mode) {
		glfwSetInputMode(Display.getWindow(), GLFW_CURSOR, mode);
	}
	
	public static Vector2 getMousePos() {
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1), y = BufferUtils.createDoubleBuffer(1);
		
		glfwGetCursorPos(Display.getWindow(), x, y);
		
		return new Vector2((float) x.get(), (float) y.get());
	}
	
	public static void setMousePos(int x, int y) {
		glfwSetCursorPos(Display.getWindow(), x, y);
	}
	
	public static String getClipboard() {
		return glfwGetClipboardString(Display.getWindow());
	}
	
	public static void setClipboard(String str) {
		glfwSetClipboardString(Display.getWindow(), str);
	}
	
}
