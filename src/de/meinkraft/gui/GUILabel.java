package de.meinkraft.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import de.meinkraft.lib.Display;
import de.meinkraft.lib.Vector3;

public class GUILabel extends GUIObject {
	
	public static GUIFont font;
	
	public GUILabel(float x, float y, int width, int height) {
		super(x, y, width, height);
		
		font = new GUIFont("font", 20);
		font.setColor(new Vector3(1,0,0));
	}

	@Override
	public void draw() { 
		int displayW = Display.getWidth();
		int displayH = Display.getHeight();
				
		float x = getX() * displayW;
		if(getOrientationX() == 1)
			x -= getWidth() / 2.0f;
		else if(getOrientationX() == 2)
			x -= - getWidth();
		
		float y = getY() * displayH;
		if(getOrientationY() == 1)
			y -= getHeight() / 2.0f;
		else if(getOrientationY() == 2)
			y -= getHeight();
		
		GUI.SHADER.bind();
		glBegin(GL_QUADS);
		{
			glColor4f(0, 0, 1, 1);
			
			glVertex2f(x, y);
			glVertex2f(x + getWidth(), y);
			glVertex2f(x + getWidth(), y + getHeight());
			glVertex2f(x, y + getHeight());
		}
		glEnd();
		
		font.drawString("Text", (int) (getX() * displayW), (int) (getY() * displayH));
	}
	
	public void setFont(GUIFont font) {
		GUILabel.font = font;
	}

}
