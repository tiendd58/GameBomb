package com.tiendd58.managesound;

import java.util.ArrayList;

public class SoundManager {
	private ArrayList<SourceEffect> listSound;

	public SoundManager() {
		listSound = new ArrayList<SourceEffect>();
	}

	public void addSound(int i, String nameAudio) {
		listSound.add(i, new SourceEffect(nameAudio));
	}

	public ArrayList<SourceEffect> getListSound() {
		return listSound;
	}

}
