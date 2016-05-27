package com.tiendd58.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import com.tiendd58.managesound.SoundManager;
import com.tiendd58.objects.Character;
import com.tiendd58.objects.Manager;

public class PlayPanel extends BaseContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int PLAY_PANEL_SOUND = 0;
	public static final int BOMBER_DIE_SOUND = 1;

	private static final Font FONT_MESSAGE = new Font("Tahoma", Font.BOLD, 100);
	// vi tri cac phan tu trong game
	private static final int POSITION_X_BACKGROUND = 0;
	private static final int POSITION_Y_BACKGROUND = 0;
	private static final int POSITION_Y_INFO = 0;
	private static final int POSITION_X_INFO = 675;
	private static final int OPTION_WIDTH = 230;

	private Image bgPlay;
	private ImageIcon bgIcon;
	private Manager manager;
	private BitSet mKeysValue;
	private Image infoPlay;
	private SoundManager soundPlayGame;
	private int time = 0;
	private boolean allowPutBom = false;

	@Override
	protected void initContainer() {
		setLayout(null);
		mKeysValue = new BitSet(256);
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				mKeysValue.set(e.getKeyCode());
			}

			public void keyReleased(KeyEvent e) {
				mKeysValue.clear(e.getKeyCode());
			}

		});
		setFocusable(true);
		setRequestFocusEnabled(true);
	}

	private void excuteTaskByKeysPress() {

		if (mKeysValue.get(KeyEvent.VK_UP)) {
			manager.changeOrientBomber(Character.UP);
			manager.moveBomber(time);
		}
		if (mKeysValue.get(KeyEvent.VK_DOWN)) {
			manager.changeOrientBomber(Character.DOWN);
			manager.moveBomber(time);
		}
		if (mKeysValue.get(KeyEvent.VK_LEFT)) {
			manager.changeOrientBomber(Character.LEFT);
			manager.moveBomber(time);
		}
		if (mKeysValue.get(KeyEvent.VK_RIGHT)) {
			manager.changeOrientBomber(Character.RIGHT);
			manager.moveBomber(time);
		}
		if (mKeysValue.get(KeyEvent.VK_SPACE)) {
			manager.putBomb();
		}
	}

	public Manager getManager() {
		return manager;
	}

	@Override
	protected void initComps() {
		bgIcon = new ImageIcon(getClass().getResource(
				"/Asset/background_Play.png"));
		bgPlay = bgIcon.getImage();
		infoPlay = new ImageIcon(getClass().getResource(
				"/Asset/background_Info.png")).getImage();
		soundPlayGame = new SoundManager();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(bgPlay, POSITION_X_BACKGROUND, POSITION_Y_BACKGROUND,
				GUI.GUI_WIDTH, GUI.GUI_HEIGHT, this);
		manager.drawObjects(g2d);
		g2d.drawImage(infoPlay, POSITION_X_INFO, POSITION_Y_INFO, OPTION_WIDTH,
				GUI.GUI_HEIGHT, this);

		manager.drawInfoPlayer(g2d);

		if (manager.isEndGame()) {
			g2d.setFont(FONT_MESSAGE);
			g2d.setColor(Color.RED);
			g2d.drawString("Game over", 100, 350);
			soundPlayGame.getListSound().get(PLAY_PANEL_SOUND).stop();
			soundPlayGame.getListSound().get(BOMBER_DIE_SOUND).play();
		}
	}

	@Override
	protected void addComps() {

	}

	public void initPlayGame() {
		manager = new Manager();
		manager.reset();
		soundPlayGame.addSound(PLAY_PANEL_SOUND, "playgame.mid");
		soundPlayGame.addSound(BOMBER_DIE_SOUND, "bomber_die.wav");
		soundPlayGame.getListSound().get(PLAY_PANEL_SOUND)
				.loop(Clip.LOOP_CONTINUOUSLY);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!manager.isEndGame()) {
					excuteTaskByKeysPress();
					if (allowPutBom) {
						manager.putBomb();
					}
					manager.changeIMG(time);
					manager.moveAllMonster(time);
					manager.countDownTimeAllBomb(2);
					manager.countDownTimeChokeWater(1);
					manager.bombBang();
					repaint();
					time++;
					try {
						Thread.sleep(17);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					if (manager.isEndGame()) {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						setVisible(false);
					}
				}
			}
		});
		t.start();
	}

}
