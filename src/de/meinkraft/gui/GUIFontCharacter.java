package de.meinkraft.gui;

public class GUIFontCharacter {
	
	private final int x, y, w, h, xoff, yoff, xadv;

	public GUIFontCharacter(int x, int y, int w, int h, int xoff, int yoff, int xadv) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.xoff = xoff;
		this.yoff = yoff;
		this.xadv = xadv;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int getXoff() {
		return xoff;
	}

	public int getYoff() {
		return yoff;
	}

	public int getXadv() {
		return xadv;
	}
	
}
