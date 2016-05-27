package com.tiendd58.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.tiendd58.managesound.SoundManager;
import com.tiendd58.map.CompsMap;
import com.tiendd58.map.Map;

public class Manager {

	// am thanh game
	public static final int BANG_SOUND = 1;
	public static final int NEW_BOMB_SOUND = 3;
	public static final int ITEM_SOUND = 0;
	public static final int MONSTER_DIE_SOUND = 2;

	// khởi tạo các đối tượng game
	private Character play1;
	private ArrayList<Bomb> listBomb;
	private ArrayList<Monster> listMonster;
	private Map mapBox1, mapItem;
	private int hp1;
	private boolean endGame = false;

	// vị trí khởi tạo nhân vật
	public static final int POSITION_X_CHARACTER = 0;
	public static final int POSITION_Y_CHARACTER = 0;
	// loại vật phẩm hỗ trợ
	private static final int ITEM_SIZE_BOMB = 2;
	private static final int ITEM_BOMB = 1;
	private static final int ITEM_SHOE = 3;
	// số mạng của nhân vật
	private static final int HP_DEFAULT = 1;
	// vị trí bảng thông tin
	private static final int POSITION_INFO_X = 680;
	private static final int POSITION_INFO_Y = 50;
	// độ dài mảng animation
	private static final int SIZE_LIST_MONSTER = 4;
	private static final int SIZE_LIST_BOSS = 18;

	private Font font = new Font("Tahoma", Font.BOLD, 15);
	// ảnh bomber
	private ImageIcon playChoke;
	private ImageIcon playDie;
	// ảnh bom nổ
	private ImageIcon bombBang;
	private Image imgBang;
	// ảnh số mạng
	private ImageIcon heart;
	// danh sách các ảnh của bomber
	private Image listIMG[] = new Image[6];
	// danh sách các ảnh của bomb
	private Image listBomIMG[] = new Image[2];

	// thuộc tính damage dùng để tăng độ dài của bom sau khi ăn vật phẩm
	private int damage = 0;
	// danh sách ảnh của quái
	private Image[] imgMonster;
	// danh sách ảnh của boss
	private Image[] imgBoss;
	// âm thanh
	private SoundManager soundPlay;
	// time game
	private int timeInGame = 0;
	// score
	private int score = 0;

	public Manager() {
		initImage();
		listIMG[Character.CHOKE_WATER] = playChoke.getImage();
		listIMG[Character.DIED] = playDie.getImage();
		listBomIMG[Bomb.STATUS_BOMB_BANG] = bombBang.getImage();
		imgBang = bombBang.getImage();
		play1 = new Character(POSITION_X_CHARACTER, POSITION_Y_CHARACTER, 0,
				Character.NORMAL, listIMG, Character.DOWN, 2, 5);
		hp1 = HP_DEFAULT;
		play1.setHp(hp1);
		listBomb = new ArrayList<Bomb>();
		listMonster = new ArrayList<Monster>();
		mapBox1 = new Map(getClass().getResource("/map1/BOX.txt").getPath());
		mapBox1.loadMap();
		mapItem = new Map(getClass().getResource("/map1/ITEM.txt").getPath());
		mapItem.loadMap();
		loadMonster();
		soundPlay = new SoundManager();
		soundPlay.addSound(ITEM_SOUND, "item.wav");
		soundPlay.addSound(BANG_SOUND, "bomb_bang.wav");
		soundPlay.addSound(MONSTER_DIE_SOUND, "monster_die.wav");
		soundPlay.addSound(NEW_BOMB_SOUND, "newbomb.wav");
		Thread timeGame = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!endGame) {
					try {
						Thread.sleep(1000);
						timeInGame++;
						if (timeInGame % 10 == 0 && timeInGame > 30) {
							loadMonster();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		timeGame.start();
	}

	public void drawObjects(Graphics2D g) {
		mapItem.draw(g);
		for (int i = 0; i < listBomb.size(); i++) {
			listBomb.get(i).draw(g);
		}
		mapBox1.draw(g);
		for (int i = 0; i < listMonster.size(); i++) {
			if (listMonster.get(i).getStatus() == Monster.HIDE) {
				listMonster.remove(i);
				soundPlay.getListSound().get(MONSTER_DIE_SOUND).play();
				score++;
				i--;
			} else {
				listMonster.get(i).draw(g);
			}
		}
		play1.draw(g);
		if (play1.getStatus() == Character.DIED) {
			if (play1.getHp() > 0) {
				this.hp1--;
				backUp();
			} else {
				endGame = true;
			}
		}
	}

	public void drawInfoPlayer(Graphics2D g) {
		g.setFont(font);
		g.setColor(Color.YELLOW);
		g.drawString("Time :   " + timeInGame, POSITION_INFO_X + 80, 20);
		g.drawString("Score :   " + score, POSITION_INFO_X + 80, 50);
		if (play1.getStatus() == Character.NORMAL) {
			switch (play1.getOrient()) {
			case Character.LEFT:
				g.drawImage(listIMG[Character.LEFT], POSITION_INFO_X,
						POSITION_INFO_Y, null);
				break;
			case Character.RIGHT:
				g.drawImage(listIMG[Character.RIGHT], POSITION_INFO_X,
						POSITION_INFO_Y, null);
				break;
			case Character.UP:
				g.drawImage(listIMG[Character.UP], POSITION_INFO_X,
						POSITION_INFO_Y, null);
				break;
			case Character.DOWN:
				g.drawImage(listIMG[Character.DOWN], POSITION_INFO_X,
						POSITION_INFO_Y, null);
				break;
			default:
				break;
			}
		}
		if (play1.getStatus() == Character.CHOKE_WATER) {
			g.drawImage(listIMG[Character.CHOKE_WATER], POSITION_INFO_X
					+ Character.SIZE_MARGIN, POSITION_INFO_Y
					+ Character.SIZE_MARGIN, null);
		}
		if (play1.getStatus() == Character.DIED) {
			g.drawImage(listIMG[Character.DIED], POSITION_INFO_X
					+ Character.SIZE_MARGIN, POSITION_INFO_Y
					+ Character.SIZE_MARGIN, null);
		}

		g.drawImage(heart.getImage(), POSITION_INFO_X + 110,
				POSITION_INFO_Y + 70, null);
		g.setColor(Color.RED);
		g.setFont(font);
		g.drawString("X", POSITION_INFO_X + 150, POSITION_INFO_Y + 90);
		g.setColor(Color.YELLOW);
		g.drawString(play1.getHp() + "", POSITION_INFO_X + 180,
				POSITION_INFO_Y + 90);
	}

	public boolean isEndGame() {
		return endGame;
	}

	public void upDamage() {
		damage++;
	}

	public void moveBomber(int time) {
		if (play1.getStatus() == Character.NORMAL
				|| play1.getStatus() == Character.CHOKE_WATER) {
			int sizeMap = mapBox1.getListMapComps().size();
			for (int i = 0; i < sizeMap; i++) {
				if (play1.checkCollisionBox(mapBox1.getListMapComps().get(i))) {
					return;
				}
			}
			int sizeMapItem = mapItem.getSizeMap();
			for (int i = 0; i < sizeMapItem; i++) {
				CompsMap item = mapItem.getComps(i);
				if (play1.checkCollisionBox(item)) {
					soundPlay.getListSound().get(ITEM_SOUND).play();
					switch (item.getType()) {
					case ITEM_SIZE_BOMB:
						i--;
						mapItem.removeItem(item);
						upDamage();
						break;
					case ITEM_BOMB:
						play1.upBomb();
						i--;
						mapItem.removeItem(item);
						break;
					case ITEM_SHOE:
						play1.upSpeed();
						i--;
						mapItem.removeItem(item);
						break;
					default:
						break;
					}
				}
				sizeMapItem = mapItem.getSizeMap();
			}
			int sizeBomb = listBomb.size();
			for (int i = 0; i < sizeBomb; i++) {
				if (play1.checkCollisionBomb(listBomb.get(i))
						&& ((play1.getX() >= listBomb.get(i).getX() + Bomb.SIZE)
								|| (play1.getX() + Bomb.SIZE <= listBomb.get(i)
										.getX())
								|| (play1.getY() >= listBomb.get(i).getY()
										+ Bomb.SIZE) || (play1.getY()
								+ Bomb.SIZE <= listBomb.get(i).getY()))) {
					return;
				}
			}

			play1.move(time);
			if (play1.getStatus() == Character.CHOKE_WATER) {
				play1.downSpeed();
			}

		}
	}

	public void changeOrientBomber(int newOrient) {
		play1.changeOrient(newOrient);
	}

	public void putBomb() {
		if (listBomb.size() < play1.getHaveBomb()
				&& play1.getStatus() == Character.NORMAL) {
			int checkContainBomb = 0;
			int x = (play1.getX() + Bomb.SIZE / 2) / Character.SIZE_CHARACTER
					* Character.SIZE_CHARACTER;
			int y = (play1.getY() + Bomb.SIZE / 2 + Character.SIZE_MARGIN)
					/ Character.SIZE_CHARACTER * Character.SIZE_CHARACTER;
			for (Bomb bomb : listBomb) {
				if (x == bomb.getX() && y == bomb.getY()) {
					checkContainBomb++;
					break;
				}
			}
			if (checkContainBomb == 0) {
				Bomb bom = new Bomb(x, y, damage, Bomb.STATUS_BOMB_WAIT,
						listBomIMG, 400, imgBang);
				listBomb.add(bom);
				soundPlay.getListSound().get(NEW_BOMB_SOUND).play();
			}
		}
	}

	public void bombBang() {
		for (int i = 0; i < listBomb.size(); i++) {
			if (listBomb.get(i).getStatusBang() == Bang.VISIABLE) {
				for (int j = 0; j < mapBox1.getSizeMap(); j++) {
					if (mapBox1.getComps(j).getType() == CompsMap.TYPE_BOX_WOOD) {
						if (listBomb.get(i).checkCollisionVsBox(
								mapBox1.getComps(j))) {
							mapBox1.removeItem(j);
							j--;
						}
					}
				}
				if (listBomb.size() >= 2) {
					for (int t = i + 1; t < listBomb.size(); t++) {
						listBomb.get(i).checkBombBang(listBomb.get(t));
					}
				}
				int sizeMonster = listMonster.size();
				for (int j = 0; j < sizeMonster; j++) {
					listBomb.get(i).checkCollisionMonster(listMonster.get(j));
				}
				listBomb.get(i).checkCollisionBomber(play1);
			}
			if (listBomb.get(i).getStatus() == Bomb.STATUS_BOMB_BANG) {
				listBomb.remove(i);
				soundPlay.getListSound().get(BANG_SOUND).play();
				i--;
			}

		}
	}

	public void countDownTimeChokeWater(int time) {
		play1.countDownTime(time);
	}

	public void countDownTimeAllBomb(int time) {
		for (int i = 0; i < listBomb.size(); i++) {
			listBomb.get(i).countDownTime(time);
		}
	}

	public void moveAllMonster(int time) {
		int sizeMonster = listMonster.size();
		for (int i = 0; i < sizeMonster; i++) {
			for (int j = 0; j < mapBox1.getSizeMap(); j++) {
				listMonster.get(i).checkCollisionBox(mapBox1.getComps(j));
			}
			int sizeBomb = listBomb.size();
			for (int j = 0; j < sizeBomb; j++) {
				listMonster.get(i).checkCollisionBomb(listBomb.get(j));
			}
			if (listMonster.get(i).checkCollisionBomber(play1)) {
				play1.setStatus(Character.DIED);
			}
			listMonster.get(i).moveMonster(time);
			if (listMonster.get(i).getType() == Monster.TYPE_BOSS) {
				((Boss) listMonster.get(i)).bigBangBoss(time, play1);
			}
		}
	}

	public void changeIMG(int time) {
		play1.changeImage(time);
		for (int i = 0; i < listBomb.size(); i++) {
			listBomb.get(i).changeImage(time);
		}
		for (int i = 0; i < listMonster.size(); i++) {
			listMonster.get(i).changeIMG(time);
		}
	}

	public void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}

	private void loadMonster() {
		initArrMonster(getClass().getResource("/map1/MONSTER.txt").getPath());
	}

	private void initArrMonster(String path) {
		try {
			FileReader file = new FileReader(path);
			BufferedReader input = new BufferedReader(file);
			String line;
			while ((line = input.readLine()) != null) {
				String str[] = line.split(":");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				int type = Integer.parseInt(str[2]);
				Monster monster;
				if (type == Monster.TYPE_MONSTER_NORMAL) {
					monster = new Monster(x, y, type, imgMonster);
				} else {
					monster = new Boss(x, y, type, imgBoss);
				}
				listMonster.add(monster);
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initImage() {
		imgMonster = new Image[SIZE_LIST_MONSTER];
		imgBoss = new Image[SIZE_LIST_BOSS];

		playChoke = new ImageIcon(getClass().getResource(
				"/asset/bomber_dead.png"));
		playDie = new ImageIcon(getClass().getResource("/asset/ghost.png"));
		bombBang = new ImageIcon(getClass().getResource("/asset/bang0.png"));
		heart = new ImageIcon(getClass().getResource("/asset/heart.png"));

		for (int i = 0; i < SIZE_LIST_MONSTER; i++) {
			URL url = getClass().getResource("/asset/pet" + i + ".png");
			imgMonster[i] = new ImageIcon(url).getImage();
		}

		for (int i = 0; i < SIZE_LIST_BOSS; i++) {
			URL url = getClass().getResource(
					"/asset/boss (" + (i + 1) + ").png");
			imgBoss[i] = new ImageIcon(url).getImage();
		}
	}

	public void backUp() {
		play1 = new Character(POSITION_X_CHARACTER, POSITION_Y_CHARACTER, 0,
				Character.NORMAL, listIMG, Character.DOWN, 2, 5);
		play1.setHp(hp1);
		listBomb = new ArrayList<Bomb>();
		listMonster = new ArrayList<Monster>();
		mapBox1 = new Map(getClass().getResource("/map1/BOX.txt").getPath());
		mapBox1.loadMap();
		mapItem = new Map(getClass().getResource("/map1/ITEM.txt").getPath());
		mapItem.loadMap();
		loadMonster();
	}

	public void reset() {
		hp1 = HP_DEFAULT;
		endGame = false;
		backUp();
	}
}
