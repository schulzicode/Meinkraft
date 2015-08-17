package de.meinkraft.lib;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.OpenGLException;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class Shader {
	
	private HashMap<String,Integer> uniforms;
	
	private int id;
	
	public Shader(int id) {
		this.id = id;
		
		uniforms = new HashMap<String, Integer>();
	}
	
	public void bind() {
		glUseProgram(id);
	}
	
	public void bindAttribute(int index, String attribute) {
		glBindAttribLocation(id, index, attribute);
	}
	
	public void addUniform(String uniform) {
		int uniformLoc = glGetUniformLocation(id, uniform);
		
		if(uniformLoc == -1)
			throw new OpenGLException("Shader: could not find uniform " + uniform);
		
		uniforms.put(uniform, uniformLoc);
	}
	
	public void setUniform(String uniform, int v) {
		glUniform1i(uniforms.get(uniform), v);
	}
	
	public void setUniform(String uniform, float v) {
		glUniform1f(uniforms.get(uniform), v);
	}
	
	public void setUniform(String uniform, Vector2 v) {
		glUniform2f(uniforms.get(uniform), v.getX(), v.getY());
	}
	
	public void setUniform(String uniform, Vector3 v) {
		glUniform3f(uniforms.get(uniform), v.getX(), v.getY(), v.getZ());
	}
	
	public void setUniform(String uniform, Vector4 v) {
		glUniform4f(uniforms.get(uniform), v.getX(), v.getY(), v.getZ(), v.getW());
	}
	
	public void setUniform(String uniform, Matrix4 v) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		for(int x = 0; x < 4; x++)
			for(int y = 0; y < 4; y++)
				buffer.put(v.get(x, y));
		
		buffer.flip();
		
		glUniformMatrix4fv(uniforms.get(uniform), true, buffer);
	}
	
	public int getId() {
		return id;
	}
	
	public static void unbind() {
		glUseProgram(0);
	}
	
	public static Shader loadShader(String vs, String fs, String[] attributes) throws IOException {
		int id = glCreateProgram();
		
		if(id == 0)
			throw new OpenGLException("Could not find a valid memory location for shader program");
		
		// vertex shader
		int vsId = glCreateShader(GL_VERTEX_SHADER);
		
		if(vsId == 0)
			throw new OpenGLException("Could not find a valid memory location for shader type");
		
		glShaderSource(vsId, Utils.readFileToString(vs));
		glCompileShader(vsId);
		
		if(glGetShaderi(vsId, GL_COMPILE_STATUS) == 0)
			throw new OpenGLException(glGetShaderInfoLog(vsId, 1024));
		
		glAttachShader(id, vsId);
		
		// fragment shader
		int fsId = glCreateShader(GL_FRAGMENT_SHADER);
		
		if(fsId == 0)
			throw new OpenGLException("Could not find a valid memory location for shader type");
		
		glShaderSource(fsId, Utils.readFileToString(fs));
		glCompileShader(fsId);
		
		if(glGetShaderi(fsId, GL_COMPILE_STATUS) == 0)
			throw new OpenGLException(glGetShaderInfoLog(fsId, 1024));
		
		glAttachShader(id, fsId);
		
		Shader shader = new Shader(id);
		if(attributes != null) {
			for(int i = 0; i < attributes.length; i++)
				shader.bindAttribute(i, attributes[i]);
		}
		
		glLinkProgram(id);
		
		if(glGetProgrami(id, GL_LINK_STATUS) == 0)
			throw new OpenGLException(glGetProgramInfoLog(id, 1024));
		
		glValidateProgram(id);
		
		if(glGetProgrami(id, GL_VALIDATE_STATUS) == 0)
			throw new OpenGLException(glGetProgramInfoLog(id, 1024));
		
		return shader;
	}
	
}
