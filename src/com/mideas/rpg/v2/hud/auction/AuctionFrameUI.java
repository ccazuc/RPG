package com.mideas.rpg.v2.hud.auction;

import com.mideas.rpg.v2.Mideas;

public class AuctionFrameUI {


	private final short X_FRAME = 18;
	private final short Y_FRAME = 118;
	private short x_frame = (short)(this.X_FRAME*Mideas.getDisplayXFactor());
	private short y_frame = (short)(this.Y_FRAME*Mideas.getDisplayYFactor());
	private boolean shouldUpdate;
	private final AuctionFrameBrowseUI browseFrame;
	private AuctionFrameTab selectedTab = AuctionFrameTab.BROWSE;

	public AuctionFrameUI() {
		this.browseFrame = new AuctionFrameBrowseUI(this);
	}
	
	public void draw() {
		updateSize();
		if(this.selectedTab == AuctionFrameTab.BROWSE) {
			this.browseFrame.draw();
		}
		else if(this.selectedTab == AuctionFrameTab.BIDS) {
			drawBidsFrame();
		}
		else if(this.selectedTab == AuctionFrameTab.AUCTIONS) {
			drawAuctionsFrame();
		}
		else {
			System.out.println("AuctionFrameUI.draw error, no tab selected");
		}
	}
	
	private void drawBidsFrame() {
		
	}
	
	private void drawAuctionsFrame() {
		
	}
	
	public boolean mouseEvent() {
		if(this.selectedTab == AuctionFrameTab.BROWSE) {
			return this.browseFrame.mouseEvent();
		}
		else if(this.selectedTab == AuctionFrameTab.BIDS) {
			
		}
		else if(this.selectedTab == AuctionFrameTab.AUCTIONS) {
			
		}
		System.out.println("AuctionFrameUI.mouseEvent error, no tab selected");
		return false;
	}
	
	public boolean keyboardEvent() {
		if(this.selectedTab == AuctionFrameTab.BROWSE) {
			return this.browseFrame.keyboardEvent();
		}
		else if(this.selectedTab == AuctionFrameTab.BIDS) {
			
		}
		else if(this.selectedTab == AuctionFrameTab.AUCTIONS) {
			
		}
		System.out.println("AuctionFrameUI.keyboardEvent error, no tab selected");
		return false;
	}
	
	private void updateSize() {
		if(!this.shouldUpdate) {
			return;
		}
		this.x_frame = (short)(this.X_FRAME*Mideas.getDisplayXFactor());
		this.y_frame = (short)(this.Y_FRAME*Mideas.getDisplayYFactor());
		this.browseFrame.shouldUpdate();
		this.shouldUpdate = false;
	}
	
	protected short getXFrame() {
		return this.x_frame;
	}
	
	protected short getYFrame() {
		return this.y_frame;
	}
	
	protected void queryReceived() {
		this.browseFrame.queryReceived();
	}
	
	public void buildResultString(byte result, int totalResult, short page, short totalPage) {
		this.browseFrame.buildResultString(result, totalResult, page, totalPage);
	}
	
	public void updateQueryButtonList() {
		this.browseFrame.updateQueryButtonList();
	}
	
	public void noItemFound() {
		this.browseFrame.noItemFound();
	}
	
	public void updateUnloadedBrowseItem(int itemID) {
		this.browseFrame.updateUnloadedBrowseItem(itemID);
	}
	
	public void shouldUpdate() {
		this.shouldUpdate = true;
	}
	
	protected void frameClosed() {
		this.browseFrame.frameClosed();
	}
}
