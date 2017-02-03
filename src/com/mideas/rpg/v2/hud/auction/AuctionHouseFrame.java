package com.mideas.rpg.v2.hud.auction;

public class AuctionHouseFrame {

	private final static AuctionFrameUI frame = new AuctionFrameUI();
	
	public static void draw() {
		frame.draw();
	}
	
	public static boolean mouseEvent() {
		return frame.mouseEvent();
	}
	
	public static boolean keyboardEvent() {
		return frame.keyboardEvent();
	}
	
	public static void frameClosed() {
		frame.frameClosed();
	}
	
	public static void shouldUpdate() {
		frame.shouldUpdate();
	}
}
