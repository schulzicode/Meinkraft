package de.meinkraft.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Utils {
	
	public static String readFileToString(String path) throws IOException {
		BufferedReader bR = new BufferedReader(new InputStreamReader(getResourceAsStream(path)));
		StringBuilder sB = new StringBuilder();
		String line;
		
		while((line = bR.readLine()) != null)
			sB.append(line).append("\n");
		
		bR.close();
		
		return sB.toString();
	}
	
	public static InputStream getResourceAsStream(String path) {
		return Class.class.getResourceAsStream(path);
	}
	
	public static FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
}
