package de.meinkraft.lib;

import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VAO {
	
	private final int mode;
	private final int vaoID;
	private final int vboID;
	private final int iboID;
	
	public VAO(int mode) {
		this.mode = mode;
		this.vaoID = glGenVertexArrays();
		this.vboID = glGenBuffers();
		this.iboID = glGenBuffers();
	}
	
	/**
	 * @param values - "SIZE STRIDE OFFSET"
	 */
	public void initVAO(String[] attributes) {
		glBindVertexArray(vaoID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		
		for(int i = 0; i < attributes.length; i++) {
			String[] values = attributes[i].split(" ");
			
			glEnableVertexAttribArray(i);
			glVertexAttribPointer(i, Integer.valueOf(values[0]), GL_FLOAT, false, 4 * Integer.valueOf(values[1]), 4 * Integer.valueOf(values[2]));
		}
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		glBindVertexArray(0);
	}
	
	public void initVBO(FloatBuffer buffer) {
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void updateVBO(FloatBuffer buffer, long offset) {
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferSubData(GL_ARRAY_BUFFER, 4 * offset, buffer);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void initIBO(IntBuffer buffer) {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void render(int indices_count, int indices_offset) {
		glBindVertexArray(vaoID);
		glDrawElements(mode, indices_count, GL_UNSIGNED_INT, 4 * indices_offset);
		glBindVertexArray(0);
	}
	
	public void delete() {
		glDeleteBuffers(vboID);
		glDeleteBuffers(iboID);
		glDeleteVertexArrays(vaoID);
	}

}
