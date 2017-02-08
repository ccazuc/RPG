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
	private short buttonWidthSave;
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
	private short itemWidth;
	private short itemHeight;
	private final static short ITEM_NAME_MAX_WIDTH = 190;
	public final static TTF DURATION_FONT = FontManager.get("FRIZQT", 11);
	public final static TTF ITEM_NAME_FONT = FontManager.get("FRIZQT", 15);
	private final static short DURATION_X_OFFSET = 271;
	private final static short LEVEL_X_OFFSET = 223;
	private final static short SELLER_NAME_X_OFFSET = 383;
	private final static short ITEM_NAME_X_OFFSET = 43;
	private final static short TEXT_Y_OFFSET = 9;
	private final static short BORDER_X_OFFSET = 33;
	private final static short ITEM_WIDTH = 32;
	private final static short ITEM_HEIGHT = 32;
	
	public AuctionBrowseQueryButton(AuctionFrameUI frame) {
		this.frame = frame;
		this.border = new AuctionBrowseItemBorder(this.frame, (short)(this.frame.getBrowseItemX()+BORDER_X_OFFSET*Mideas.getDisplayXFactor()), this.frame.getBrowseItemWidth());
		this.itemWidth = (short)(ITEM_WIDTH*Mideas.getDisplayXFactor());
		this.itemHeight = (short)(ITEM_HEIGHT*Mideas.getDisplayYFactor());
		this.durationX = (short)(this.frame.getBrowseItemX()+DURATION_X_OFFSET*Mideas.getDisplayXFactor());
		this.levelX = (short)(this.frame.getBrowseItemX()+LEVEL_X_OFFSET*Mideas.getDisplayXFactor());
		this.stringY = (short)(TEXT_Y_OFFSET*Mideas.getDisplayYFactor());
		this.itemNameX = (short)(this.frame.getBrowseItemX()+ITEM_NAME_X_OFFSET*Mideas.getDisplayXFactor());
	}
	
	public void draw() {
		if(this.buttonHovered || this.frame.getSelectedBrowseEntry() == this.entry) {
			Draw.drawQuadBlend(Sprites.friend_border, this.border.getX(), this.frame.getBrowseItemY(), this.border.getWidth(), this.border.getHeight());
		}
		Draw.drawQuad(IconsManager.getSprite37(this.item.getSpriteId()), this.frame.getBrowseItemX(), this.frame.getBrowseItemY(), this.itemWidth, this.itemHeight);
		this.border.draw();
		if(this.durationHovered) {
			//DURATION_FONT.drawStringShadow(this.durationX, this., text, color, shadowColor, shadowSize, shadowX, shadowY, scaleX, scaleY);
		}
		this.frame.incrementBrowseItemY();
	}
	
	public void drawItemName() {
		ITEM_NAME_FONT.drawStringShadowPart(this.itemNameX, this.frame.getBrowseItemY()+this.itemNameYOffset, this.itemName, this.entry.getItem().getNameColor(), Color.BLACK, 1, 0, 0);
	}
	
	public void drawString() {
		DURATION_FONT.drawStringShadowPart(this.levelX, this.frame.getBrowseItemY()+this.stringY, this.level, Color.WHITE, Color.BLACK, 1, 0, 0);
		DURATION_FONT.drawStringShadowPart(this.durationX, this.frame.getBrowseItemY()+this.stringY, this.duration, Color.WHITE, Color.BLACK, 1, 0, 0);
		DURATION_FONT.drawStringShadowPart(this.sellerNameX, this.frame.getBrowseItemY()+this.stringY, this.sellerName, Color.WHITE, Color.BLACK, 1, 0, 0);
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
		else if(Mideas.getHover() && Mideas.mouseX() >= this.frame.getBrowseItemX() && Mideas.mouseX() <= this.frame.getBrowseItemX()+this.border.getWidth()+this.itemWidth && Mideas.mouseY() >= this.frame.getBrowseItemY() && Mideas.mouseY() <= this.frame.getBrowseItemY()+this.itemHeight) {
			Mideas.setHover(false);
			this.buttonHovered = true;
			this.durationHovered = false;
		}
		else {
			this.buttonHovered = false;
			this.durationHovered = false;
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
		this.sellerNameX = (short)(this.frame.getBrowseItemX()+SELLER_NAME_X_OFFSET*Mideas.getDisplayXFactor()-DURATION_FONT.getWidth(this.sellerName)/2);
	}
	
	protected void setWidth(short width) {
		this.buttonWidthSave = width;
		this.buttonWidth = (short)(this.buttonWidthSave*Mideas.getDisplayXFactor());
		this.border.updateSize((short)(this.frame.getBrowseItemX()+BORDER_X_OFFSET*Mideas.getDisplayXFactor()), this.buttonWidth);
	}
	
	protected void updateSize() {
		this.itemNameYOffset = (short)(this.itemNameYOffsetSave*Mideas.getDisplayYFactor());
		this.buttonWidth = (short)(this.buttonWidthSave*Mideas.getDisplayXFactor());
		this.border.updateSize((short)(this.frame.getBrowseItemX()+BORDER_X_OFFSET*Mideas.getDisplayXFactor()), this.buttonWidth);
		this.itemWidth = (short)(ITEM_WIDTH*Mideas.getDisplayXFactor());
		this.itemHeight = (short)(ITEM_HEIGHT*Mideas.getDisplayYFactor());
		this.durationX = (short)(this.frame.getBrowseItemX()+DURATION_X_OFFSET*Mideas.getDisplayXFactor());
		this.levelX = (short)(this.frame.getBrowseItemX()+LEVEL_X_OFFSET*Mideas.getDisplayXFactor());
		this.stringY = (short)(TEXT_Y_OFFSET*Mideas.getDisplayYFactor());
		this.itemNameX = (short)(this.frame.getBrowseItemX()+ITEM_NAME_X_OFFSET*Mideas.getDisplayXFactor());
		this.itemNameYOffset = (short)(this.itemNameYOffsetSave*Mideas.getDisplayYFactor());
		if(this.sellerName != null) {
			this.sellerNameX = (short)(this.frame.getBrowseItemX()+SELLER_NAME_X_OFFSET*Mideas.getDisplayXFactor()-DURATION_FONT.getWidth(this.sellerName)/2);
		}
	}
	
	private String formatItemName(String name) {
		short nameMaxWidth = (short)(ITEM_NAME_MAX_WIDTH*Mideas.getDisplayXFactor());
		StringBuilder builder = new StringBuilder();
		int width = 0;
		int numberLine = 1;
		int lastLine = 0;
		int i = -1;
		while(++i < name.length()) {
			width+= ITEM_NAME_FONT.getWidth(name.charAt(i));
			if(width > nameMaxWidth && i < name.length()) {
				lastLine = checkPreviousSpace(name, i);
				builder.append(name.substring(0, lastLine)+'\n'+name.substring(lastLine+1));
				this.itemNameYOffset = (short)(5*Mideas.getDisplayYFactor());
				this.itemNameYOffsetSave = 5;
				return builder.toString();
			}
		}
		if(numberLine == 1) {
			this.itemNameYOffset = (short)(7*Mideas.getDisplayYFactor());
			this.itemNameYOffsetSave = 7;
			builder.append(name);
		}
		return builder.toString();
	}
	
	private static int checkPreviousSpace(String message, int index) {
		int i = index;
		while(i >= 0) {
			if(message.charAt(i) == ' ') {
				return i;
			}
			i--;
		}
		return -1;
	}
	
	protected AuctionEntry getEntry() {
		return this.entry;
	}
}
