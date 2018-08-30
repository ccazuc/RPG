package com.mideas.rpg.v2.hud.auction.old;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;

public class AuctionBrowseItemBorder {

	private final AuctionFrameBrowseUI frame;
	private short x;
	private short width;
	private short height;
	
	public AuctionBrowseItemBorder(AuctionFrameBrowseUI frame, short x, short width) {
		this.frame = frame;
		this.x = x;
		this.width = width;
		this.height = (short)(Sprites.auction_browse_item_left_border.getImageHeight()*Mideas.getDisplayYFactor());
	}
	
	public void draw() {
		Draw.drawQuad(Sprites.auction_browse_item_left_border, this.x, this.frame.getBrowseItemY(), Sprites.auction_browse_item_left_border.getImageWidth(), this.height);
		Draw.drawQuad(Sprites.auction_browse_item_middle_border, this.x+Sprites.auction_browse_item_left_border.getImageWidth(), this.frame.getBrowseItemY(), this.width-2*Sprites.auction_browse_item_left_border.getImageWidth(), this.height);
		Draw.drawQuad(Sprites.auction_browse_item_right_border, this.x-Sprites.auction_browse_item_left_border.getImageWidth()+this.width, this.frame.getBrowseItemY(), Sprites.auction_browse_item_right_border.getImageWidth(), this.height);
	}
	
	public void updateSize(short x, short width) {
		this.x = x;
		this.width = width;
		this.height = (short)(Sprites.auction_browse_item_left_border.getImageHeight()*Mideas.getDisplayYFactor());
	}
	
	public short getWidth() {
		return this.width;
	}
	
	public short getHeight() {
		return this.height;
	}
	
	public short getX() {
		return this.x;
	}
}
