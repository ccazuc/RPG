package com.mideas.rpg.v2.utils;

public interface Frame extends UIElement {

	abstract void open();
	abstract void close();
	abstract void setX(int x);
	abstract void setY(int y);
	abstract void shouldUpdateSize();
	abstract boolean isOpen();
}
