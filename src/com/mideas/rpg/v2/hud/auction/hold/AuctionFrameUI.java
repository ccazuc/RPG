package com.mideas.rpg.v2.hud.auction.hold;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.ButtonMenu;

public class AuctionFrameUI {


	private final short X_FRAME = 18;
	private final short Y_FRAME = 118;
	private short x_frame = (short)(this.X_FRAME*Mideas.getDisplayXFactor());
	private short y_frame = (short)(this.Y_FRAME*Mideas.getDisplayYFactor());
	private final short BROWSE_FRAME_TAB_X = 23;
	private final short BIDS_FRAME_TAB_X = 95;
	private final short AUCTIONS_FRAME_TAB_X = 163;
	private final short FRAME_TAB_Y = 429;
	private final short BROWSE_FRAME_TAB_WIDTH = 63;
	private final short BIDS_FRAME_TAB_WIDTH = 58;
	private final short AUCTIONS_FRAME_TAB_WIDTH = 68;
	private final short FRAME_TAB_HEIGHT = 31;
	private boolean shouldUpdate;
	private final AuctionFrameBrowseUI browseFrame;
	private final AuctionFrameAuctionsUI auctionsFrame;
	AuctionFrameTab selectedTab = AuctionFrameTab.BROWSE;
	
	private final ButtonMenu browseFrameTab = new ButtonMenu(this.x_frame+this.BROWSE_FRAME_TAB_X*Mideas.getDisplayXFactor(), this.y_frame+this.FRAME_TAB_Y*Mideas.getDisplayYFactor(), this.BROWSE_FRAME_TAB_WIDTH*Mideas.getDisplayXFactor(), this.FRAME_TAB_HEIGHT*Mideas.getDisplayYFactor(), "Browse", 11, 1, true) {
		
		@Override
		public void eventButtonClick() {
			AuctionFrameUI.this.selectedTab = AuctionFrameTab.BROWSE;
			unselectAllTabs();
			this.setIsSelected(true);
		}
	};
	private final ButtonMenu bidsFrameTab = new ButtonMenu(this.x_frame+this.BIDS_FRAME_TAB_X*Mideas.getDisplayXFactor(), this.y_frame+this.FRAME_TAB_Y*Mideas.getDisplayYFactor(), this.BIDS_FRAME_TAB_WIDTH*Mideas.getDisplayXFactor(), this.FRAME_TAB_HEIGHT*Mideas.getDisplayYFactor(), "Bids", 11, 1, false) {
		
		@Override
		public void eventButtonClick() {
			AuctionFrameUI.this.selectedTab = AuctionFrameTab.BIDS;
			unselectAllTabs();
			this.setIsSelected(true);
		}
	};
	private final ButtonMenu auctionsFrameTab = new ButtonMenu(this.x_frame+this.AUCTIONS_FRAME_TAB_X*Mideas.getDisplayXFactor(), this.y_frame+this.FRAME_TAB_Y*Mideas.getDisplayYFactor(), this.AUCTIONS_FRAME_TAB_WIDTH*Mideas.getDisplayXFactor(), this.FRAME_TAB_HEIGHT*Mideas.getDisplayYFactor(), "Auctions", 11, 1, false) {
		
		@Override
		public void eventButtonClick() {
			AuctionFrameUI.this.selectedTab = AuctionFrameTab.AUCTIONS;
			unselectAllTabs();
			this.setIsSelected(true);
		}
	};

	public AuctionFrameUI() {
		this.browseFrame = new AuctionFrameBrowseUI(this);
		this.auctionsFrame = new AuctionFrameAuctionsUI(this);
	}
	
	public void draw() {
		updateSize();
		if(this.selectedTab == AuctionFrameTab.BROWSE) {
			this.browseFrame.draw();
		}
		else if(this.selectedTab == AuctionFrameTab.BIDS) {
		}
		else if(this.selectedTab == AuctionFrameTab.AUCTIONS) {
			this.auctionsFrame.draw();
		}
		else {
			System.out.println("AuctionFrameUI.draw error, no tab selected");
		}
		this.browseFrameTab.draw();
		this.bidsFrameTab.draw();
		this.auctionsFrameTab.draw();
	}
	
	public boolean mouseEvent() {
		if(this.browseFrameTab.event()) return true;
		if(this.bidsFrameTab.event()) return true;
		if(this.auctionsFrameTab.event()) return true;
		if(this.selectedTab == AuctionFrameTab.BROWSE) {
			return this.browseFrame.mouseEvent();
		}
		else if(this.selectedTab == AuctionFrameTab.BIDS) {
			return false;
		}
		else if(this.selectedTab == AuctionFrameTab.AUCTIONS) {
			return this.auctionsFrame.mouseEvent();
		}
		System.out.println("AuctionFrameUI.mouseEvent error, no tab selected");
		return false;
	}
	
	public boolean keyboardEvent() {
		if(this.selectedTab == AuctionFrameTab.BROWSE) {
			return this.browseFrame.keyboardEvent();
		}
		else if(this.selectedTab == AuctionFrameTab.BIDS) {
			return false;
		}
		else if(this.selectedTab == AuctionFrameTab.AUCTIONS) {
			return this.auctionsFrame.keyboardEvent();
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
		this.browseFrameTab.update(this.x_frame+this.BROWSE_FRAME_TAB_X*Mideas.getDisplayXFactor(), this.y_frame+this.FRAME_TAB_Y*Mideas.getDisplayYFactor(), this.BROWSE_FRAME_TAB_WIDTH*Mideas.getDisplayXFactor(), this.FRAME_TAB_HEIGHT*Mideas.getDisplayYFactor());
		this.bidsFrameTab.update(this.x_frame+this.BIDS_FRAME_TAB_X*Mideas.getDisplayXFactor(), this.y_frame+this.FRAME_TAB_Y*Mideas.getDisplayYFactor(), this.BIDS_FRAME_TAB_WIDTH*Mideas.getDisplayXFactor(), this.FRAME_TAB_HEIGHT*Mideas.getDisplayYFactor());
		this.auctionsFrameTab.update(this.x_frame+this.AUCTIONS_FRAME_TAB_X*Mideas.getDisplayXFactor(), this.y_frame+this.FRAME_TAB_Y*Mideas.getDisplayYFactor(), this.AUCTIONS_FRAME_TAB_WIDTH*Mideas.getDisplayXFactor(), this.FRAME_TAB_HEIGHT*Mideas.getDisplayYFactor());
		this.browseFrame.shouldUpdate();
		this.auctionsFrame.shouldUpdate();
		this.shouldUpdate = false;
	}
	
	void unselectAllTabs() {
		this.browseFrameTab.setIsSelected(false);
		this.bidsFrameTab.setIsSelected(false);
		this.auctionsFrameTab.setIsSelected(false);
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
