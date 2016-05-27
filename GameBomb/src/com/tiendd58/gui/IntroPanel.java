package com.tiendd58.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class IntroPanel extends BaseContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int LOCATION_X = 80;
	private static final int LOCATION_Y = 80;
	private static final String LABEL_BACK = "BACK";
	private static final int POSITION_BACK_HEIGHT = 30;
	private static final int POSITION_BACK_WIDTH = 100;
	private static final int POSITION_BACK_Y = 20;
	private static final int POSITION_BACK_X = 400;
	private Image intro;
	private JButton btBack;

	@Override
	protected void initContainer() {
		setLayout(null);
		setBackground(Color.CYAN);
		setFocusable(true);
		setRequestFocusEnabled(true);
	}

	@Override
	protected void initComps() {
		ImageIcon imgIntro = new ImageIcon(getClass().getResource(
				"/Asset/introduction.png"));
		intro = imgIntro.getImage();
		btBack = new JButton(LABEL_BACK);
		btBack.setBounds(POSITION_BACK_X, POSITION_BACK_Y, POSITION_BACK_WIDTH,
				POSITION_BACK_HEIGHT);
	}

	@Override
	protected void addComps() {
		add(btBack);
		btBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(intro, LOCATION_X, LOCATION_Y, this);
	}

}
