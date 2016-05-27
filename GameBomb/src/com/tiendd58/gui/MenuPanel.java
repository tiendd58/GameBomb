package com.tiendd58.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.tiendd58.hanhVi.OnActionMenuListener;
import com.tiendd58.managesound.SoundManager;

public class MenuPanel extends BaseContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int POSITION_X_BACKGROUND = 0;
	private static final int POSITION_Y_BACKGROUND = 0;
	private static final int POSITION_X_PLAY = 50;
	private static final int POSITION_Y_PLAY = 200;
	private static final int POSITION_X_OPTION = 50;
	private static final int POSITION_Y_OPTION = 300;
	private static final int POSITION_X_EXIT = 50;
	private static final int POSITION_Y_EXIT = 400;
	public static final int MENU_SOUND = 0;

	private Image bg;
	private OnActionMenuListener action;
	private JButton play, option, exit;
	private SoundManager soundMenu;

	public void setActionMenuPanel(OnActionMenuListener action) {
		this.action = action;
	}

	@Override
	protected void initContainer() {
		setLayout(null);
		setBackground(Color.blue);
	}

	@Override
	protected void initComps() {
		soundMenu = new SoundManager();
		soundMenu.addSound(MENU_SOUND, "menu.wav");
		soundMenu.getListSound().get(MENU_SOUND).loop(Clip.LOOP_CONTINUOUSLY);
		ImageIcon bgIcon, optionIcon, exitIcon, playIcon;
		bgIcon = new ImageIcon(getClass().getResource("/Asset/bg.jpg"));
		playIcon = new ImageIcon(getClass().getResource("/Asset/Play.png"));
		optionIcon = new ImageIcon(getClass().getResource("/Asset/Option.png"));
		exitIcon = new ImageIcon(getClass().getResource("/Asset/Exit.png"));
		bg = bgIcon.getImage();
		play = new JButton();
		play.setIcon(playIcon);
		play.setBounds(POSITION_X_PLAY, POSITION_Y_PLAY,
				playIcon.getIconWidth(), playIcon.getIconHeight());
		option = new JButton();
		option.setIcon(optionIcon);
		option.setBounds(POSITION_X_OPTION, POSITION_Y_OPTION,
				optionIcon.getIconWidth(), optionIcon.getIconHeight());
		exit = new JButton();
		exit.setIcon(exitIcon);
		exit.setBounds(POSITION_X_EXIT, POSITION_Y_EXIT,
				exitIcon.getIconWidth(), exitIcon.getIconHeight());
		play.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				soundMenu.getListSound().get(MENU_SOUND).stop();
				action.showPlayPanel();
			}

		});
		option.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				action.showOption();
			}

		});
		exit.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				action.exitGUI();
			}

		});
	}

	@Override
	protected void addComps() {
		add(play);
		add(option);
		add(exit);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bg, POSITION_X_BACKGROUND, POSITION_Y_BACKGROUND,
				GUI.GUI_WIDTH, GUI.GUI_HEIGHT, this);
	}

}
