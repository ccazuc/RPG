package com.mideas.rpg.v2.hud.auction;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.auction.AuctionEntry;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.utils.Draw;

public class AuctionBrowseQueryButton {

	private final AuctionFrameUI frame;
	private AuctionEntry entry;
	private short buttonWidth;
	private short buttonHeight;
	private short buttonX;
	private short itemX;
	private short levelX;
	private short levelY;
	private short durationX;
	private short durationY;
	private short sellerNameX;
	private short sellerNameY;
	private String sellerName;
	private String duration;
	private int durationWidth;
	private Item item;
	private boolean buttonHovered;
	private boolean buttonDown;
	private boolean durationHovered;
	private final AuctionBrowseItemBorder border;
	private final static TTF durationFont = FontManager.get("FRIZQT", 13);
	private final static short BORDER_WIDTH = 150;
	private final static short BORDER_HEIGHT = 30;
	private final static short BORDER_X_OFFSET = 35;
	
	public AuctionBrowseQueryButton(AuctionFrameUI frame) {
		this.frame = frame;
		this.border = new AuctionBrowseItemBorder(this.frame, this.frame.getBrowseItemX()+BORDER_X_OFFSET, this.frame.getBrowseItemWidth(), this.frame.getBrowseItemHeight());
	}
	
	public void draw() {
		short y = this.frame.getBrowseItemY();
		Draw.drawQuad(IconsManager.getSprite37(this.item.getSpriteId()), this.buttonX, y);
		//draw level
		//draw sellername
		//draw prices
		if(this.buttonHovered || this.frame.getSelectedBrowseEntry() == this.entry) {
			Draw.drawQuad(texture, x, y, width, height, texXOrg, texYOrg, texCoWidth, texCoHeight, Colors);
		}
		this.frame.incrementBrowseItemY();
	}
	
	public boolean mouseEvent() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.durationX && Mideas.mouseX() <= this.durationX+this.durationWidth && Mideas.mouseY() >= this.durationY && Mideas.mouseY() <= this.durationY+durationFont.getLineHeight()) {
			Mideas.setHover(false);
			this.buttonHovered = true;
			this.durationHovered = true;
		}
		else if(Mideas.getHover() && Mideas.mouseX() >= this.buttonX && Mideas.mouseX() <= this.buttonX+this.buttonWidth && Mideas.mouseY() >= this.frame.getBrowseItemY() && Mideas.mouseY() <= this.frame.getBrowseItemY()+this.buttonHeight) {
			Mideas.setHover(false);
			this.buttonHovered = true;
			this.durationHovered = false;
		}
		else {
			this.buttonHovered = false;
		}
		if(this.buttonHovered) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = true;
				}
			}
			else if(this.buttonDown) {
				if(Mouse.getEventButton() == 0) {
					this.buttonDown = false;
					this.frame.setSelectedBrowseEntry(this.entry);
					return true;
				}
				else if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = false;
					return true;
				}
			}
		}
		else if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				this.buttonDown = false;
				return true;
			}
		}
		return false;
	}
	
	public void setEntry(AuctionEntry entry) {
		this.entry = entry;
		this.item = entry.getItem();
		this.sellerName = entry.getSellerName();
		this.duration = entry.getDuration().getDuration();
		this.durationWidth = durationFont.getWidth(this.duration)+(short)(20*Mideas.getDisplayXFactor());
	}
}
