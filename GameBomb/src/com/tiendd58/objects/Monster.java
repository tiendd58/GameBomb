package com.tiendd58.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import com.tiendd58.hanhVi.OnCollisionListener;
import com.tiendd58.map.CompsMap;

public class Monster implements OnCollisionListener {
	public static final int LEFT = 0;
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;

	protected static final int SPACE_MOVE = 3;
	protected static final int SPEED_MONSTER = 3;
	protected static final int SPEED_BOSS = 4;

	public static final int VISIABLE = 0;
	public static final int HIDE = 1;

	public static final int MARGIN = 23;
	public static final int SIZE = 45;

	protected static final int SPEED_CHANGE_IMAGE = 4;

	public static final int TYPE_MONSTER_NORMAL = 2;
	public static final int TYPE_BOSS = 1;
	protected static final int SIZE_IMG_BOSS = 18;
	private static final int SIZE_IMG_MONSTER = 4;
	protected static final int SIZE_CHARACTER = 45;
	protected static final int SIZE_MAP = 675;
	protected static final int SIZE_MARGIN = 20;

	protected int x, y, speed, orient, type, status, newX, newY;

	protected Image img[] = new Image[SIZE_IMG_MONSTER];
	protected int nextImg = 0;
	protected Image imgMonster;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Monster(int x, int y, int type, Image img[]) {
		this.x = x;
		this.y = y;
		this.img = img;
		this.type = type;
		this.speed = SPEED_MONSTER;
		Random rd = new Random();
		this.orient = rd.nextInt(3);
		this.status = VISIABLE;
	}

	public void moveMonster(int time) {
		if (this.status == VISIABLE) {
			if (time % speed == 0) {
				switch (orient) {
				case LEFT:
					if (x <= 0) {
						orient = (orient + 1) % 4;
					} else {
						x = x - SPACE_MOVE;
					}
					break;
				case RIGHT:
					if (x < SIZE_MAP - SIZE_CHARACTER) {
						x = x + SPACE_MOVE;
					} else {
						orient = (orient + 1) % 4;
					}
					break;
				case UP:
					if (y <= -SIZE_MARGIN) {
						orient = (orient + 1) % 4;
					} else {
						y = y - SPACE_MOVE;
					}
					break;
				case DOWN:
					if (y < SIZE_MAP - SIZE_CHARACTER)
						y = y + SPACE_MOVE;
					else {
						orient = (orient + 1) % 4;
					}
					break;
				default:
					break;
				}
			}
		}
	}

	public void draw(Graphics2D g) {
		if (status == VISIABLE) {
			if (type == TYPE_MONSTER_NORMAL) {
				g.drawImage(imgMonster, x - 33, y - 33, null);
			} else {
				g.drawImage(imgMonster, x - 45, y - 145, null);
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void initNewPosition() {
		newX = getX();
		newY = getY();
		switch (orient) {
		case LEFT:
			newX -= SPACE_MOVE;
			break;
		case RIGHT:
			newX += SPACE_MOVE;
			break;
		case UP:
			newY -= SPACE_MOVE;
			break;
		case DOWN:
			newY += SPACE_MOVE;
			break;
		default:
			break;
		}
	}

	public void follow(Character bomber) {
		if (Math.abs(bomber.getX() - x) <= 300
				&& Math.abs(bomber.getY() - y) <= 300) {
			if (bomber.getX() > x) {
				orient = RIGHT;
			} else if (bomber.getX() < x) {
				orient = LEFT;
			} else {
				if (bomber.getY() > y) {
					orient = DOWN;
				} else {
					orient = UP;
				}
			}
		}
	}

	public void changeIMG(int time) {
		if (time % SPEED_CHANGE_IMAGE == 0) {
			imgMonster = img[nextImg];
			nextImg = (nextImg + 1) % SIZE_IMG_MONSTER;
		}
	}

	@Override
	public boolean checkCollisionBomb(Bomb bom) {
		initNewPosition();
		Rectangle rectMonter = new Rectangle(newX, newY, SIZE, SIZE);
		Rectangle rectBom = new Rectangle(bom.getX(), bom.getY(), SIZE, SIZE);

		if (rectMonter.intersects(rectBom)) {
			orient = (orient + 1) % 4;
			return true;
		}
		return false;
	}

	@Override
	public boolean checkCollisionBomber(Character bomber) {
		initNewPosition();
		Rectangle rectMonter = new Rectangle(newX, newY, SIZE, SIZE);
		Rectangle rectCharacter = new Rectangle(bomber.getX() + 10,
				bomber.getY() + Character.SIZE_MARGIN + 10, SIZE - 20,
				SIZE - 20);

		if (rectMonter.intersects(rectCharacter)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkCollisionMonster(Monster monster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkCollisionBox(CompsMap box) {
		initNewPosition();
		Rectangle rectMonter = new Rectangle(newX, newY, SIZE, SIZE);
		Rectangle recctBox = new Rectangle(box.getX(), box.getY(), SIZE, SIZE);

		if (rectMonter.intersects(recctBox)) {
			orient = (orient + 1) % 4;
			return true;
		}
		return false;
	}

}
