package com.tiendd58.map;

import java.awt.Graphics2D;
import java.awt.Image;

public class CompsMap {
	public static final int TYPE_BOX_STONE = 1;
	public static final int TYPE_BOX_WOOD = 0;
	public static final int SIZE = 45;
	private int x, y, type;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}

	public Image getImg() {
		return img;
	}

	private Image img;

	public CompsMap(int x, int y, int type, Image img) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.img = img;
	}

	public void draw(Graphics2D g) {
		g.drawImage(img, x, y, null);
	}
}
