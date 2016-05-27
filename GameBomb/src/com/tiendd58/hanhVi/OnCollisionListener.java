package com.tiendd58.hanhVi;

import com.tiendd58.map.CompsMap;
import com.tiendd58.objects.Bomb;
import com.tiendd58.objects.Character;
import com.tiendd58.objects.Monster;

public interface OnCollisionListener {
	public boolean checkCollisionBomb(Bomb bom);

	public boolean checkCollisionBomber(Character bomber);

	public boolean checkCollisionMonster(Monster monster);

	public boolean checkCollisionBox(CompsMap box);

}
