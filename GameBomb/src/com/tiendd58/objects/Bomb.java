package com.tiendd58.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import com.tiendd58.map.CompsMap;

public class Bomb extends Actor {

	public static final int SIZE = 45;
	private static final int SIZE_ANIMATION_BOMB = 6;

	public static final int STATUS_BOMB_WAIT = 0;
	public static final int STATUS_BOMB_BANG = 1;

	public static final int ONE_MOVE = 6;
	private static final int TIME_OUT_IMG = 10;

	private int time;
	private Bang bang;
	private boolean clickBang = false;
	private boolean clickCharacter = false;
	private boolean clickMonster = false;

	private Image imgFrame[] = new Image[SIZE_ANIMATION_BOMB];
	private int next = 0;

	public Bomb(int x, int y, int damage, int status, Image[] img, int time,
			Image imgBang) {
		super(x, y, damage, status, img);
		status = STATUS_BOMB_WAIT;
		this.time = time;
		bang = new Bang(x, y, 1 + damage, imgBang);
		initImage();
	}

	public void countDownTime(int time) {
		if (this.time > 0) {
			this.time -= time;
			if (this.time == 0) {
				this.status = STATUS_BOMB_BANG;
			}
			if (this.time < 30) {
				bang.setStatus(Bang.VISIABLE);
			}
		}

	}

	public int getTime() {
		return time;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		if (status == STATUS_BOMB_WAIT) {
			g.drawImage(img[STATUS_BOMB_WAIT], x, y, null);
		}
		if (bang.getStatus() == Bang.VISIABLE) {
			bang.draw(g);
		}
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Bang getBang() {
		return bang;
	}

	public void setBang(Bang bang) {
		this.bang = bang;
	}

	public int getStatusBang() {
		return bang.getStatus();
	}

	public boolean checkCollisionVsBox(CompsMap box) {
		return bang.checkCollisionBox(box);
	}

	public void checkBombBang(Bomb bom) {
		if (!clickBang) {
			if (bang.getStatus() == Bang.VISIABLE
					&& bang.checkCollisionBomb(bom)) {
				bom.setTime(time);
			}
		}
	}

	public void checkCollisionBomber(Character bomber) {
		if (!clickCharacter) {
			if (bang.getStatus() == Bang.VISIABLE
					&& bang.checkCollisionBomber(bomber)) {
				bomber.setStatus(Character.CHOKE_WATER);
				clickCharacter = true;
			}
		}
	}

	public void checkCollisionMonster(Monster monster) {
		if (!clickMonster) {
			if (bang.getStatus() == Bang.VISIABLE
					&& bang.checkCollisionMonster(monster)) {
				monster.setStatus(Monster.HIDE);
				clickMonster = true;
			}
		}
	}

	public void changeImage(int time) {
		if (time % TIME_OUT_IMG == 0) {
			img[STATUS_BOMB_WAIT] = imgFrame[next];
			next = (next + 1) % 6;
		}
	}

	private void initImage() {
		for (int i = 0; i < SIZE_ANIMATION_BOMB; i++) {
			URL url = getClass().getResource("/Asset/bomb_" + (i + 1) + ".png");
			imgFrame[i] = new ImageIcon(url).getImage();
		}
	}

}
