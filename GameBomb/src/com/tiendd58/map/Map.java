package com.tiendd58.map;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Map {
	private static final int BOX_X = 0;
	private static final int BOX_Y = 1;
	private static final int BOX_TYPE = 2;
	private static final int BOX_SRC = 3;
	private ArrayList<CompsMap> listMapComps = new ArrayList<CompsMap>();
	private String path;

	public Map(String path) {
		this.path = path;
	}

	public ArrayList<CompsMap> getListMapComps() {
		return listMapComps;
	}

	public void loadMap() {
		try {
			File file = new File(this.path);
			RandomAccessFile rd = new RandomAccessFile(file, "r");
			String line = rd.readLine();
			String arr[];
			while (line != null) {
				arr = line.split(":");
				CompsMap box = new CompsMap(Integer.parseInt(arr[BOX_X]),
						Integer.parseInt(arr[BOX_Y]),
						Integer.parseInt(arr[BOX_TYPE]),
						(new ImageIcon(getClass().getResource(arr[BOX_SRC]))
								.getImage()));
				listMapComps.add(box);
				line = rd.readLine();
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void draw(Graphics2D g) {
		for (CompsMap box : listMapComps) {
			box.draw(g);
		}
	}

	public void removeItem(CompsMap item) {
		listMapComps.remove(item);
	}

	public void removeItem(int i) {
		listMapComps.remove(i);
	}

	public int getSizeMap() {
		return listMapComps.size();
	}

	public CompsMap getComps(int i) {
		return listMapComps.get(i);
	}

}
