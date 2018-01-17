package com.mideas.rpg.v2.utils;

public interface Frame {

	abstract void onOpen();
	abstract void onClose();
	abstract int getX();
	abstract int getY();
	abstract void draw();
	abstract boolean mouseEvent();
	abstract boolean keyboardEvent();
	abstract void shouldUpdateSize();
	abstract boolean isOpen();
}
