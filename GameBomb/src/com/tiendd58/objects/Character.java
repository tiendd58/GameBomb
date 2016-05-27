package com.tiendd58.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;

import com.tiendd58.hanhVi.OnCollisionListener;
import com.tiendd58.map.CompsMap;

public class Character extends Actor implements OnCollisionListener {

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	public static final int CHOKE_WATER = 4;
	public static final int DIED = 5;
	public static final int NORMAL = 6;

	private static final int TIME_OUT_IMG = 5;
	public static final int TIME_CHOKE_WATER = 200;
	private static final int SPEED_CHOKE_WATER = 5;
	public static final int SPACE_MOVE = 3;

	public static final int SIZE_MARGIN = 20;
	public static final int SIZE_CHARACTER = 45;
	private static final int SIZE_MAP = 675;
	private static final int SIZE_ANIMATION = 8;

	private int nextDown = 0;
	private int nextRight = 0;

	private Image imgFramDownAnim[] = new Image[SIZE_ANIMATION];
	private Image imgFramRightAnim[] = new Image[SIZE_ANIMATION];
	private Image imgFramLeftAnim[] = new Image[SIZE_ANIMATION];
	private Image imgFramUpAnim[] = new Image[SIZE_ANIMATION];

	private int orient, speedMove, haveBomb, timeChokeWater, hp, newX, newY;
	private int nextLeft = 0;
	private int nextUp = 0;

	public Character(int x, int y, int damage, int status, Image[] img,
			int orient, int speedMove, int haveBomb) {
		super(x, y, damage, status, img);
		this.orient = orient;
		this.haveBomb = haveBomb;
		this.speedMove = speedMove;
		this.timeChokeWater = TIME_CHOKE_WATER;
		initImage();
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getOrient() {
		return orient;
	}

	public int getSpeedMove() {
		return speedMove;
	}

	public Image[] getImg() {
		return img;
	}

	public void addBomb() {
		haveBomb++;
	}

	public void upSpeed() {
		if (speedMove > 1) {
			speedMove--;
		}
	}

	public void downSpeed() {
		speedMove += SPEED_CHOKE_WATER;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		if (status == NORMAL) {
			switch (orient) {
			case LEFT:
				g.drawImage(img[LEFT], x - 33, y - 33, null);
				break;
			case RIGHT:
				g.drawImage(img[RIGHT], x - 33, y - 33, null);
				break;
			case UP:
				g.drawImage(img[UP], x - 33, y - 33, null);
				break;
			case DOWN:
				g.drawImage(img[DOWN], x - 33, y - 33, null);
				break;
			default:
				break;
			}
		}
		if (status == CHOKE_WATER) {
			g.drawImage(img[CHOKE_WATER], x, y, null);
		}
		if (status == DIED) {
			g.drawImage(img[DIED], x, y, null);
		}
	}

	public void countDownTime(int time) {
		if (status == CHOKE_WATER) {
			if (this.timeChokeWater > 0) {
				this.timeChokeWater -= time;
				if (this.timeChokeWater == 0) {
					this.status = DIED;
				}
			}
		}
	}

	public void move(int time) {
		if (time % speedMove == 0) {
			switch (orient) {
			case LEFT:
				if (x <= 0) {
					x = 0;
				} else {
					x = x - SPACE_MOVE;
				}
				break;
			case RIGHT:
				if (x < SIZE_MAP - SIZE_CHARACTER) {
					x = x + SPACE_MOVE;
				} else {
					x = SIZE_MAP - SIZE_CHARACTER;
				}
				break;
			case UP:
				if (y <= -SIZE_MARGIN) {
					y = -SIZE_MARGIN;
				} else {
					y = y - SPACE_MOVE;
				}
				break;
			case DOWN:
				if (y < SIZE_MAP - SIZE_CHARACTER)
					y = y + SPACE_MOVE;
				else
					y = SIZE_MAP - SIZE_CHARACTER;
				break;
			default:
				break;
			}
		} else {
			return;
		}
	}

	public void changeOrient(int newOrient) {
		orient = newOrient;
	}

	public int getHaveBomb() {
		return haveBomb;
	}

	public int getTimeChokeWater() {
		return timeChokeWater;
	}

	@Override
	public void setStatus(int status) {
		super.setStatus(status);
	}

	public void upBomb() {
		haveBomb++;
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

	public void changeImage(int time) {
		if (time % TIME_OUT_IMG == 0) {
			switch (orient) {
			case DOWN:
				img[DOWN] = imgFramDownAnim[nextDown];
				nextDown = (nextDown + 1) % 8;
				break;
			case RIGHT:
				img[RIGHT] = imgFramRightAnim[nextRight];
				nextRight = (nextRight + 1) % 8;
			case LEFT:
				img[LEFT] = imgFramLeftAnim[nextLeft];
				nextLeft = (nextLeft + 1) % 8;
			case UP:
				img[UP] = imgFramUpAnim[nextUp];
				nextUp = (nextUp + 1) % 8;

			default:
				break;
			}
		}
	}

	private void initImage() {
		for (int i = 0; i < SIZE_ANIMATION; i++) {
			URL url = getClass().getResource("/Asset/down" + i + ".png");
			imgFramDownAnim[i] = new ImageIcon(url).getImage();
		}

		for (int i = 0; i < SIZE_ANIMATION; i++) {
			URL url = getClass().getResource("/Asset/left" + i + ".png");
			imgFramLeftAnim[i] = new ImageIcon(url).getImage();
		}

		for (int i = 0; i < SIZE_ANIMATION; i++) {
			URL url = getClass().getResource("/Asset/right" + i + ".png");
			imgFramRightAnim[i] = new ImageIcon(url).getImage();
		}

		for (int i = 0; i < SIZE_ANIMATION; i++) {
			URL url = getClass().getResource("/Asset/up" + i + ".png");
			imgFramUpAnim[i] = new ImageIcon(url).getImage();
		}
	}

	@Override
	public boolean checkCollisionBomb(Bomb bom) {
		initNewPosition();
		Rectangle rectBomer = new Rectangle(newX, newY, SIZE_CHARACTER,
				SIZE_CHARACTER);
		Rectangle rectBomb = new Rectangle(bom.getX(), bom.getY(), Bomb.SIZE,
				Bomb.SIZE);
		return rectBomer.intersects(rectBomb);
	}

	@Override
	public boolean checkCollisionBox(CompsMap box) {
		initNewPosition();
		Rectangle rectBomber = new Rectangle(newX, newY + SIZE_MARGIN,
				SIZE_CHARACTER, SIZE_CHARACTER);
		Rectangle rectBox = new Rectangle(box.getX() + 5, box.getY() + 5,
				CompsMap.SIZE - 10, CompsMap.SIZE - 10);
		if (rectBomber.intersects(rectBox)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkCollisionBomber(Character bomber) {
		return false;
	}

	@Override
	public boolean checkCollisionMonster(Monster monster) {
		return false;
	}
}
