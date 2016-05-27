package com.tiendd58.objects;

import java.awt.Graphics2D;
import java.awt.Image;

public class Actor {
	protected int x, y, damage, status;
	protected Image img[];

	public Actor(int x, int y, int damage, int status, Image[] img) {
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.status = status;
		this.img = img;
	}

	public void setImg(Image[] img) {
		this.img = img;
	}

	public int getX() {
		return x;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getY() {
		return y;
	}

	public int getDamage() {
		return damage;
	}

	public int getStatus() {
		return status;
	}

	public Image[] getImg() {
		return img;
	}

	public void draw(Graphics2D g) {

	}
}
