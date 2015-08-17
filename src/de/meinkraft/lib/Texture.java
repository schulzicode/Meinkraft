package de.meinkraft.lib;

import static org.lwjgl.opengl.GL13.GL_MAX_TEXTURE_UNITS;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_3D;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL12.glTexImage3D;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.OpenGLException;

public class Texture {
	
	private static int imageWidth;
	private static int imageHeight;
	
	// TEXTURE_2D
	public static void bindTexture2D(int id) {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public static void bindTexture2D(int id, int unit) {
		if(unit >= 0 && unit <= GL_MAX_TEXTURE_UNITS) {
			glActiveTexture(unit);
			glBindTexture(GL_TEXTURE_2D, id);
		} else
			throw new OpenGLException("Texture unit " + unit + " is not valid");
	}
	
	public static void unbindTexture2D() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public static int loadTexture2D(InputStream iS, int magFilter, int minFilter, int wrapMode) throws IOException {
		ByteBuffer data = getImageData(iS);
		
		int id = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, id);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapMode);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapMode);
        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, imageWidth, imageHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        
        if(minFilter == GL_NEAREST_MIPMAP_NEAREST || minFilter == GL_NEAREST_MIPMAP_LINEAR || minFilter == GL_LINEAR_MIPMAP_NEAREST || minFilter == GL_LINEAR_MIPMAP_LINEAR)
    		glGenerateMipmap(GL_TEXTURE_2D);
        
        glBindTexture(GL_TEXTURE_2D, 0);
        
        return id;
	}
	
	// TEXTURE_2D_ARRAY
	public static void bindTexture2DArray(int id) {
		glBindTexture(GL_TEXTURE_2D_ARRAY, id);
	}
	
	public static void bindTexture2DArray(int id, int unit) {
		if(unit >= 0 && unit <= GL_MAX_TEXTURE_UNITS) {
			glActiveTexture(unit);
			glBindTexture(GL_TEXTURE_2D_ARRAY, id);
		} else
			throw new OpenGLException("Texture unit " + unit + " is not valid");
	}
	
	public static void unbindTexture2DArray() {
		glBindTexture(GL_TEXTURE_2D_ARRAY, 0);
	}
	
	public static int loadTexture2DArray(InputStream iS, int width, int height, int magFilter, int minFilter, int wrapMode) throws IOException {
		int id = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D_ARRAY, id);
		
		glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, magFilter);
		glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, minFilter);
		
		glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_S, wrapMode);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_T, wrapMode);
        
    	ByteBuffer[] data = getImageDataArray(iS, width, height);
    	
    	int depth = (imageWidth / width) * (imageHeight / height);
    	
    	glTexImage3D(GL_TEXTURE_2D_ARRAY, 0, GL_RGBA8, width, height, depth, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
    	
    	for(int i = 0; i < data.length; i++)
    		glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, i, width, height, 1, GL_RGBA, GL_UNSIGNED_BYTE, data[i]);
    	
    	if(minFilter == GL_NEAREST_MIPMAP_NEAREST || minFilter == GL_NEAREST_MIPMAP_LINEAR || minFilter == GL_LINEAR_MIPMAP_NEAREST || minFilter == GL_LINEAR_MIPMAP_LINEAR)
    		glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
    	
    	glBindTexture(GL_TEXTURE_2D_ARRAY, 0);
        
		return id;
	}
	
	// TEXTURE_3D
	public static void bindTexture3D(int id) {
		glBindTexture(GL_TEXTURE_3D, id);
	}
	
	public static void bindTexture3D(int id, int unit) {
		if(unit >= 0 && unit <= GL_MAX_TEXTURE_UNITS) {
			glActiveTexture(unit);
			glBindTexture(GL_TEXTURE_3D, id);
		} else
			throw new OpenGLException("Texture unit " + unit + " is not valid");
	}
	
	public static void unbindTexture3D() {
		glBindTexture(GL_TEXTURE_3D, 0);
	}
	
	public static int loadTexture3D(InputStream iS, int width, int height, int magFilter, int minFilter, int wrapMode) throws IOException {
		int id = glGenTextures();
		
		glBindTexture(GL_TEXTURE_3D, id);
		
		glTexParameteri(GL_TEXTURE_3D, GL_TEXTURE_MAG_FILTER, magFilter);
		glTexParameteri(GL_TEXTURE_3D, GL_TEXTURE_MIN_FILTER, minFilter);
		
		glTexParameteri(GL_TEXTURE_3D, GL_TEXTURE_WRAP_S, wrapMode);
        glTexParameteri(GL_TEXTURE_3D, GL_TEXTURE_WRAP_T, wrapMode);
        glTexParameteri(GL_TEXTURE_3D, GL_TEXTURE_WRAP_R, wrapMode);
        
    	ByteBuffer[] data = getImageDataArray(iS, width, height);
    	
    	int depth = (imageWidth / width) * (imageHeight / height);
    	
    	glTexImage3D(GL_TEXTURE_3D, 0, GL_RGBA8, width, height, depth, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
    	
    	for(int i = 0; i < data.length; i++)
    		glTexSubImage3D(GL_TEXTURE_3D, 0, 0, 0, i, width, height, 1, GL_RGBA, GL_UNSIGNED_BYTE, data[i]);
    	
    	if(minFilter == GL_NEAREST_MIPMAP_NEAREST || minFilter == GL_NEAREST_MIPMAP_LINEAR || minFilter == GL_LINEAR_MIPMAP_NEAREST || minFilter == GL_LINEAR_MIPMAP_LINEAR)
    		glGenerateMipmap(GL_TEXTURE_3D);
    	
    	glBindTexture(GL_TEXTURE_3D, 0);
        
		return id;
	}
	
	private static ByteBuffer getImageData(InputStream iS) throws IOException {
		BufferedImage image = ImageIO.read(iS);
		
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		
		int[] pixels = image.getRGB(0, 0, imageWidth, imageHeight, null, 0, imageWidth);
		ByteBuffer buffer = BufferUtils.createByteBuffer(imageWidth * imageHeight * 4);
		
		for(int y = 0; y < imageHeight; y++) {
			for(int x = 0; x < imageWidth; x++) {
				int pixel = pixels[y * imageWidth + x];
				
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	private static ByteBuffer[] getImageDataArray(InputStream iS, int singleW, int singleH) throws IOException {
		BufferedImage image = ImageIO.read(iS);
		
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		
		int[] pixels = image.getRGB(0, 0, imageWidth, imageHeight, null, 0, imageWidth);
		
		int layerX = imageWidth / singleW;
		int layerY = imageHeight / singleH;
		int depth = layerX * layerY;
		ByteBuffer[] buffers = new ByteBuffer[depth];
		
		int index = 0;
		for(int ly = 0; ly < layerY; ly++) {
			for(int lx = 0; lx < layerX; lx++) {
				buffers[index] = BufferUtils.createByteBuffer(singleW * singleH * 4);
				
				for(int y = 0; y < singleH; y++) {
					for(int x = 0; x < singleW; x++) {
						int pixel = pixels[(ly * singleH + y) * imageWidth + lx * singleW + x];
						
						buffers[index].put((byte) ((pixel >> 16) & 0xFF));
						buffers[index].put((byte) ((pixel >> 8) & 0xFF));
						buffers[index].put((byte) (pixel & 0xFF));
						buffers[index].put((byte) ((pixel >> 24) & 0xFF));
					}
				}
				
				buffers[index].flip();
				index++;
			}
		}
		
		return buffers;
	}
	
}
