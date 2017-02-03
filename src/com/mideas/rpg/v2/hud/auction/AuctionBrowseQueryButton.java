package com.mideas.rpg.v2.hud.auction;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.auction.AuctionEntry;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.StringUtils;

public class AuctionBrowseQueryButton {

	private final AuctionFrameUI frame;
	private final AuctionBrowseItemBorder border;
	private AuctionEntry entry;
	private short buttonWidth;
	private short buttonHeight;
	private short backgroundX;
	private short itemNameX;
	private short itemNameYOffset;
	private short itemNameYOffsetSave;
	private short stringY;
	private short levelX;
	private short durationX;
	private short sellerNameX;
	private String sellerName;
	private String itemName;
	private String duration;
	private String level;
	private int durationWidth;
	private Item item;
	private boolean buttonHovered;
	private boolean buttonDown;
	private boolean durationHovered;
	private final static short ITEM_NAME_MAX_WIDTH = 70;
	public final static TTF DURATION_FONT = FontManager.get("FRIZQT", 13);
	public final static TTF ITEM_NAME_FONT = FontManager.get("FRIZQT", 15);
	private final static short BORDER_HEIGHT = 30;
	private final static short BORDER_X_OFFSET = 35;
	
	public AuctionBrowseQueryButton(AuctionFrameUI frame) {
		this.frame = frame;
		this.border = new AuctionBrowseItemBorder(this.frame, (short)(this.frame.getBrowseItemX()+BORDER_X_OFFSET), this.frame.getBrowseItemWidth());
	}
	
	public void draw() {
		Draw.drawQuad(IconsManager.getSprite37(this.item.getSpriteId()), this.frame.getBrowseItemX(), this.frame.getBrowseItemY());
		this.border.draw();
		if(this.buttonHovered || this.frame.getSelectedBrowseEntry() == this.entry) {
			Draw.drawQuad(Sprites.friend_border, this.backgroundX, this.frame.getBrowseItemY(), this.frame.getBrowseItemWidth(), this.frame.getBrowseItemHeight());
		}
		if(this.durationHovered) {
			//DURATION_FONT.drawStringShadow(this.durationX, this., text, color, shadowColor, shadowSize, shadowX, shadowY, scaleX, scaleY);
		}
		this.frame.incrementBrowseItemY();
	}
	
	public void drawItemName() {
		ITEM_NAME_FONT.drawStringShadowPart(this.itemNameX, this.frame.getBrowseItemY()+this.itemNameYOffset, this.itemName, this.entry.getItem().getNameColor(), Color.BLACK, 1, 0, 0);
	}
	
	public void drawString() {
		DURATION_FONT.drawStringShadowPart(this.levelX, this.frame.getBrowseItemY(), this.level, Color.WHITE, Color.BLACK, 1, 0, 0);
		DURATION_FONT.drawStringShadowPart(this.durationX, this.frame.getBrowseItemY(), this.duration, Color.WHITE, Color.BLACK, 1, 0, 0);
		DURATION_FONT.drawStringShadowPart(this.sellerNameX, this.frame.getBrowseItemY(), this.sellerName, Color.WHITE, Color.BLACK, 1, 0, 0);
	}
	
	public void drawGoldPiece() {
		
	}
	
	public void drawSilverPiece() {
		
	}
	
	public void drawCopperPiece() {
		
	}
	
	public boolean mouseEvent() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.durationX && Mideas.mouseX() <= this.durationX+this.durationWidth && Mideas.mouseY() >= this.stringY && Mideas.mouseY() <= this.stringY+DURATION_FONT.getLineHeight()) {
			Mideas.setHover(false);
			this.buttonHovered = true;
			this.durationHovered = true;
		}
		else if(Mideas.getHover() && Mideas.mouseX() >= this.frame.getBrowseItemX() && Mideas.mouseX() <= this.frame.getBrowseItemX()+this.buttonWidth && Mideas.mouseY() >= this.frame.getBrowseItemY() && Mideas.mouseY() <= this.frame.getBrowseItemY()+this.buttonHeight) {
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
		if(entry == null) {
			this.entry = null;
			return;
		}
		this.entry = entry;
		this.item = entry.getItem();
		this.sellerName = entry.getSellerName();
		this.duration = entry.getDuration().getDuration();
		this.durationWidth = DURATION_FONT.getWidth(this.duration)+(short)(20*Mideas.getDisplayXFactor());
		this.itemName = formatItemName(entry.getItem().getStuffName());
		this.level = StringUtils.value[this.entry.getItem().getLevel()];
	}
	
	protected void updateSize() {
		this.itemNameYOffset = (short)(this.itemNameYOffsetSave*Mideas.getDisplayYFactor());
	}
	
	private String formatItemName(String name) {
		short nameMaxWidth = (short)(ITEM_NAME_MAX_WIDTH*Mideas.getDisplayXFactor());
		StringBuilder builder = new StringBuilder();
		int width = 0;
		int numberLine = 1;
		int i = -1;
		while(++i < name.length()) {
			builder.append(name.charAt(i));
			width+= ITEM_NAME_FONT.getWidth(name.charAt(i));
			if(width > nameMaxWidth && i < name.length()) {
				builder.append('\n');
				numberLine++;
			}
		}
		if(numberLine == 1) {
			this.itemNameYOffset = (short)(15*Mideas.getDisplayYFactor());
			this.itemNameYOffsetSave = 15;
		}
		else if(numberLine == 2) {
			this.itemNameYOffset = (short)(5*Mideas.getDisplayYFactor());
			this.itemNameYOffsetSave = 5;
		}
		return builder.toString();
	}
	
	protected AuctionEntry getEntry() {
		return this.entry;
	}
}
