package com.tiendd58.gui;

import java.awt.CardLayout;

import javax.swing.JFrame;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int GUI_WIDTH = 905;
	public static final int GUI_HEIGHT = 679;
	private BaseContainer myContainer;
	
	
	public GUI() {
		initGUI();
		initComps();
		addComps();
	}


	private void initGUI() {
		// TODO Auto-generated method stub
		setTitle("Bomb offline");
		setSize(GUI_WIDTH, GUI_HEIGHT);
		setLocationRelativeTo(null);
		setLayout(new CardLayout());
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	private void initComps() {
		// TODO Auto-generated method stub
		myContainer= new MyContainer();
	}


	private void addComps() {
		// TODO Auto-generated method stub
		add(myContainer);
	}
}
