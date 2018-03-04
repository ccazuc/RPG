package com.mideas.rpg.v2.hud.auction.hold;

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
	
	public static void queryReceived() {
		frame.queryReceived();
	}
	
	public static void shouldUpdate() {
		frame.shouldUpdate();
	}
	
	public static void buildResultString(byte result, int totalResult, short page, short totalPage) {
		frame.buildResultString(result, totalResult, page, totalPage);
	}
	
	public static void updateQueryButtonList() {
		frame.updateQueryButtonList();
	}
	
	public static void noItemFound() {
		frame.noItemFound();
	}
	
	public static void updateUnloadedBrowseItem(int itemID) {
		frame.updateUnloadedBrowseItem(itemID);
	}
}
