package de.meinkraft.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.meinkraft.lib.Shader;
import de.meinkraft.lib.Texture;
import de.meinkraft.lib.Transform;
import de.meinkraft.lib.Utils;
import de.meinkraft.lib.Vector3;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL20.*;

public class GUIFont {
	
	public static Shader SHADER;
	static {
		try {
			SHADER = Shader.loadShader("/font.vs", "/font.fs", null);
			SHADER.addUniform("p");
			SHADER.addUniform("v");
			SHADER.addUniform("m");
			SHADER.addUniform("size");
			
			SHADER.bind();
			SHADER.setUniform("p", GUI.ORTHO);
			SHADER.setUniform("v", new Transform().getTransformationMatrix());
			SHADER.setUniform("m", new Transform().getTransformationMatrix());
			SHADER.setUniform("size", 1.0f);
			Shader.unbind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private GUIFontCharacter[] chars;
	private int atlas;
	private int fontsize;
	private Vector3 color;
	
	public GUIFont(String name, int size) {
		chars = new GUIFontCharacter[256];
		
		BufferedReader bR = new BufferedReader(new InputStreamReader(Utils.getResourceAsStream("/" + name + ".fnt")));
		String line;
		try {
			while((line = bR.readLine()) != null) {
				line = line.replaceAll("\\s+", " ");
				
				if(line.startsWith("char ")) {
					String[] meta = line.split(" ");
					chars[Integer.parseInt(meta[1].split("=")[1])] = new GUIFontCharacter(Integer.parseInt(meta[2].split("=")[1]), Integer.parseInt(meta[3].split("=")[1]), Integer.parseInt(meta[4].split("=")[1]), Integer.parseInt(meta[5].split("=")[1]), Integer.parseInt(meta[6].split("=")[1]), Integer.parseInt(meta[7].split("=")[1]), Integer.parseInt(meta[8].split("=")[1]));
				} else if(line.startsWith("info "))
					fontsize = Integer.parseInt(line.substring(line.indexOf("size=")).split(" ")[0].split("=")[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			atlas = Texture.loadTexture2D(Utils.getResourceAsStream("/" + name + ".png"), GL_LINEAR, GL_LINEAR_MIPMAP_NEAREST, GL_CLAMP_TO_EDGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setSize(size);
		color = new Vector3();
	}
	
	public void drawString(String text, int x, int y) {
		int adv = 0;
		
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		SHADER.bind();
		Texture.bindTexture2D(atlas);
		glBegin(GL_QUADS);
		{
			glVertexAttrib3f(2, color.getX(), color.getY(), color.getZ());
			
			for(char c : text.toCharArray()) {
				GUIFontCharacter gfc = chars[(int) c];
				
				glVertexAttrib2f(1, gfc.getX() / 512.0f, gfc.getY() / 512.0f);
				glVertex2f(adv + gfc.getXoff() + x, gfc.getYoff() + y - 8);
				
				glVertexAttrib2f(1, (gfc.getX() + gfc.getW()) / 512.0f, gfc.getY() / 512.0f);
				glVertex2f(adv + gfc.getXoff() + x + gfc.getW(), gfc.getYoff() + y - 8);
				
				glVertexAttrib2f(1, (gfc.getX() + gfc.getW()) / 512.0f, (gfc.getY() + gfc.getH()) / 512.0f);
				glVertex2f(adv + gfc.getXoff() + x + gfc.getW(), gfc.getYoff() + y + gfc.getH() - 8);
				
				glVertexAttrib2f(1, gfc.getX() / 512.0f, (gfc.getY() + gfc.getH()) / 512.0f);
				glVertex2f(adv + gfc.getXoff() + x, gfc.getYoff() + y + gfc.getH() - 8);
				
				adv += gfc.getXadv() - 16; // padding left & right
			}
		}
		glEnd();
	}
	
	public int getWidth(String text) {
		int width = 0;
		
		for(char c : text.toCharArray()) {
			GUIFontCharacter gfc = chars[(int) c];
			
			width += gfc.getW() + gfc.getXoff();
		}
		
		return width;
	}
	
	public void setSize(int size) {
		SHADER.bind();
		SHADER.setUniform("m", new Transform(new Vector3(), new Vector3(), new Vector3((float) size / fontsize)).getTransformationMatrix());
		
		float fs = (float) size / fontsize;
		
		if(fs < 1)
			fs = 0.8f;
		
		SHADER.setUniform("size", fs);
		Shader.unbind();
	}
	
	public void setColor(Vector3 color) {
		this.color = color;
	}
}
