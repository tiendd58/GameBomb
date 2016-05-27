package com.tiendd58.objects;

import java.awt.Image;
import java.awt.Rectangle;

public class Boss extends Monster {

	public Boss(int x, int y, int type, Image[] img) {
		super(x, y, type, img);
		this.speed = SPEED_BOSS;
	}

	public void bigBangBoss(int time, Character player) {
		if (type == TYPE_BOSS) {
			if (time % (SPEED_CHANGE_IMAGE * SIZE_IMG_BOSS) == 0) {
				int newX = x - Bomb.SIZE;
				int newY = y - Bomb.SIZE;
				Rectangle rectBoss = new Rectangle(newX, newY, 3 * Bomb.SIZE,
						3 * Bomb.SIZE);
				Rectangle rectPlayer = new Rectangle(player.getX() + 2,
						player.getY() + 2, Character.SIZE_CHARACTER - 4,
						Character.SIZE_CHARACTER - 4);
				if (rectBoss.intersects(rectPlayer)) {
					player.setStatus(Character.CHOKE_WATER);
				}
			}
		}
	}

	@Override
	public void changeIMG(int time) {
		if (time % SPEED_CHANGE_IMAGE == 0) {
			imgMonster = img[nextImg];
			nextImg = (nextImg + 1) % SIZE_IMG_BOSS;
		}
	}
}
