package com.tiendd58.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.tiendd58.hanhVi.OnCollisionListener;
import com.tiendd58.map.CompsMap;

public class Bang implements OnCollisionListener {

	public static final int DAMAGE_DEFAULT = 0;
	public static final int TIME_LIFE = 50;

	public static final int HIDE = 0;
	public static final int VISIABLE = 1;
	public static final int CLEAR = 2;

	private static final int BODY = 15;

	private int x, y, size, timeLine, status;
	private Image img;

	private int collisionLeft;
	private int collisionRight;
	private int collisionUp;
	private int collisionDown;

	private boolean clearLeft = false;
	private boolean clearRight = false;
	private boolean clearUp = false;
	private boolean clearDown = false;

	public Bang(int x, int y, int size, Image img) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.timeLine = TIME_LIFE;
		this.status = HIDE;
		this.img = img;
	}

	public void countDownTime(int time) {
		if (status == VISIABLE) {
			if (this.timeLine > 0) {
				status = CLEAR;
			}
		}

	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void upSize() {
		size++;
	}

	public void draw(Graphics2D g) {
		if (status == VISIABLE) {
			for (int i = x - 45; i >= collisionLeft; i -= 45) {
				g.drawImage(img, i, y, null);

			}
			for (int i = x + 45; i <= collisionRight; i += 45) {
				g.drawImage(img, i, y, null);

			}
			for (int i = y - 45; i >= collisionUp; i -= 45) {
				g.drawImage(img, x, i, null);

			}
			for (int i = y + 45; i <= collisionDown; i += 45) {
				g.drawImage(img, x, i, null);

			}
			g.drawImage(img, x, y, null);

		}
	}

	@Override
	public boolean checkCollisionBomb(Bomb bom) {
		Rectangle rectBom = new Rectangle(bom.getX() + 5, bom.getY() + 5, BODY,
				BODY);
		Rectangle rectBangVertical = new Rectangle(x, collisionUp, Bomb.SIZE,
				collisionDown - collisionUp + Bomb.SIZE);
		Rectangle rectBangHorizontal = new Rectangle(collisionLeft, y,
				collisionRight - collisionLeft + Bomb.SIZE, Bomb.SIZE);
		for (int i = 0; i < size; i++) {
			if (rectBangHorizontal.intersects(rectBom)
					|| rectBangVertical.intersects(rectBom)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkCollisionBomber(Character bomber) {
		Rectangle rectBomber = new Rectangle(bomber.getX() + 5, bomber.getY()
				+ Character.SIZE_MARGIN + 5, Character.SIZE_CHARACTER - 10,
				Character.SIZE_CHARACTER - 10);
		Rectangle rectBangVertical = new Rectangle(x, collisionUp, Bomb.SIZE,
				collisionDown - collisionUp + Bomb.SIZE);
		Rectangle rectBangHorizontal = new Rectangle(collisionLeft, y,
				collisionRight - collisionLeft + Bomb.SIZE, Bomb.SIZE);
		for (int i = 0; i < size; i++) {
			if (rectBangHorizontal.intersects(rectBomber)
					|| rectBangVertical.intersects(rectBomber)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkCollisionMonster(Monster monster) {
		Rectangle rectMonster = new Rectangle(monster.getX(), monster.getY()
				+ Monster.MARGIN, Monster.SIZE, Monster.SIZE);
		Rectangle rectBangVertical = new Rectangle(x, collisionUp, Bomb.SIZE,
				collisionDown - collisionUp + Bomb.SIZE);
		Rectangle rectBangHorizontal = new Rectangle(collisionLeft, y,
				collisionRight - collisionLeft + Bomb.SIZE, Bomb.SIZE);
		for (int i = 0; i < size; i++) {
			if (rectBangHorizontal.intersects(rectMonster)
					|| rectBangVertical.intersects(rectMonster)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkCollisionBox(CompsMap box) {
		Rectangle rectBox = new Rectangle(box.getX() + 3, box.getY() + 3, BODY,
				BODY);
		if (!clearLeft) {
			for (int i = 0; i < size; i++) {
				Rectangle rectLeft = new Rectangle(x - (i + 1) * Bomb.SIZE, y,
						Bomb.SIZE, Bomb.SIZE);
				if (rectLeft.intersects(rectBox)) {
					collisionLeft = x - (i + 1) * Bomb.SIZE;
					clearLeft = true;
					return true;
				} else {
					collisionLeft = x - (i + 1) * Bomb.SIZE;
				}
			}
		}
		if (!clearRight) {
			for (int i = 0; i < size; i++) {
				Rectangle rectRight = new Rectangle(x + (i + 1) * Bomb.SIZE, y,
						Bomb.SIZE, Bomb.SIZE);
				if (rectRight.intersects(rectBox)) {
					collisionRight = x + (i + 1) * Bomb.SIZE;
					clearRight = true;
					return true;
				} else {
					collisionRight = x + (i + 1) * Bomb.SIZE;
				}
			}
		}
		if (!clearUp) {
			for (int i = 0; i < size; i++) {
				Rectangle rectUp = new Rectangle(x, y - (i + 1) * Bomb.SIZE,
						Bomb.SIZE, Bomb.SIZE);
				if (rectUp.intersects(rectBox)) {
					collisionUp = y - (i + 1) * Bomb.SIZE;
					clearUp = true;
					return true;
				} else {
					collisionUp = y - (i + 1) * Bomb.SIZE;
				}
			}
		}
		if (!clearDown) {
			for (int i = 0; i < size; i++) {
				Rectangle rectDown = new Rectangle(x, y + (i + 1) * Bomb.SIZE,
						Bomb.SIZE, Bomb.SIZE);
				if (rectDown.intersects(rectBox)) {
					collisionDown = y + (i + 1) * Bomb.SIZE;
					clearDown = true;
					return true;
				} else {
					collisionDown = y + (i + 1) * Bomb.SIZE;
				}
			}
		}
		return false;
	}
}
