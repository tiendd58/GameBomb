package com.tiendd58.gui;

import javax.swing.JPanel;

public abstract class BaseContainer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseContainer() {
		initContainer();
		initComps();
		addComps();
	}

	protected abstract void initContainer();

	protected abstract void initComps();

	protected abstract void addComps();

}
