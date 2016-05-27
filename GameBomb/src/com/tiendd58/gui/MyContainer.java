package com.tiendd58.gui;

import java.awt.CardLayout;

import com.tiendd58.hanhVi.OnActionMenuListener;

public class MyContainer extends BaseContainer implements OnActionMenuListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String INTRO_ID = "2";
	private static final String PLAY_ID = "1";
	private static final String MENU_ID = "0";
	private CardLayout mcard;
	private BaseContainer menuPanel, playPanel, introPanel;

	@Override
	protected void initContainer() {
		mcard = new CardLayout();
		setLayout(mcard);
	}

	@Override
	protected void initComps() {
		menuPanel = new MenuPanel();
		playPanel = new PlayPanel();
		introPanel = new IntroPanel();
		((MenuPanel) menuPanel).setActionMenuPanel(this);
	}

	@Override
	protected void addComps() {
		add(menuPanel, MENU_ID);
		add(playPanel, PLAY_ID);
		add(introPanel, INTRO_ID);
	}

	@Override
	public void showPlayPanel() {
		((PlayPanel) playPanel).initPlayGame();
		mcard.show(this, PLAY_ID);
		playPanel.requestFocus();
		playPanel.setFocusable(true);
	}

	@Override
	public void showOption() {
		mcard.show(this, INTRO_ID);
		introPanel.requestFocus();
		introPanel.setFocusable(true);
	}

	@Override
	public void exitGUI() {
		System.exit(1);
	}

}
