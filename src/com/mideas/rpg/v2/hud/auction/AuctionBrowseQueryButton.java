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
	private short goldTextureX;
	private short silverTextureX;
	private short copperTextureX;
	private short goldStringX;
	private short silverStringX;
	private short copperStringX;
	private short coinWidth;
	private short coinHeight;
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
	private boolean lastItemScrollbarState;
	private final static short ITEM_NAME_MAX_WIDTH = 150;
	public final static TTF DURATION_FONT = FontManager.get("FRIZQT", 11);
	public final static TTF ITEM_NAME_FONT = FontManager.get("FRIZQT", 13);
	public final static TTF GOLD_FONT = FontManager.get("ARIALN", 14);
	private final static short DURATION_X_OFFSET = 295;
	private final static short LEVEL_X_OFFSET = 223;
	private final static short SELLER_NAME_X_OFFSET = 383;
	private final static short ITEM_NAME_X_OFFSET = 43;
	private final static short TEXT_Y_OFFSET = 9;
	private final static short BORDER_X_OFFSET = 33;
	private final static short ITEM_WIDTH = 32;
	private final static short ITEM_HEIGHT = 32;
	private final static short GOLD_STRING_X_NO_SCROLLBAR = 533;
	private final static short GOLD_STRING_X_SCROLLBAR = 508;
	private final static short GOLD_TEXTURE_X_NO_SCROLLBAR = 536;
	private final static short GOLD_TEXTURE_X_SCROLLBAR = 511;
	private final static short SILVER_TEXTURE_X_NO_SCROLLBAR = 572;
	private final static short SILVER_TEXTURE_X_SCROLLBAR = 547;
	private final static short SILVER_STRING_X_NO_SCROLLBAR = 569;
	private final static short SILVER_STRING_X_SCROLLBAR = 544;
	private final static short COPPER_TEXTURE_X_NO_SCROLLBAR = 608;
	private final static short COPPER_TEXTURE_X_SCROLLBAR = 583;
	private final static short COPPER_STRING_X_NO_SCROLLBAR = 605;
	private final static short COPPER_STRING_X_SCROLLBAR = 580;
	private final static short COIN_WIDTH = 13;
	private final static short COIN_HEIGHT = 13;
	
	public AuctionBrowseQueryButton(AuctionFrameUI frame) {
		this.frame = frame;
		this.border = new AuctionBrowseItemBorder(this.frame, (short)(this.frame.getBrowseItemX()+BORDER_X_OFFSET*Mideas.getDisplayXFactor()), this.frame.getBrowseItemWidth());
		this.itemWidth = (short)(ITEM_WIDTH*Mideas.getDisplayXFactor());
		this.itemHeight = (short)(ITEM_HEIGHT*Mideas.getDisplayYFactor());
		this.durationX = (short)(this.frame.getBrowseItemX()+DURATION_X_OFFSET*Mideas.getDisplayXFactor());
		this.levelX = (short)(this.frame.getBrowseItemX()+LEVEL_X_OFFSET*Mideas.getDisplayXFactor());
		this.stringY = (short)(TEXT_Y_OFFSET*Mideas.getDisplayYFactor());
		this.itemNameX = (short)(this.frame.getBrowseItemX()+ITEM_NAME_X_OFFSET*Mideas.getDisplayXFactor());
		this.goldTextureX = (short)(this.frame.getBrowseItemX()+GOLD_TEXTURE_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		this.silverTextureX = (short)(this.frame.getBrowseItemX()+SILVER_TEXTURE_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		this.copperTextureX = (short)(this.frame.getBrowseItemX()+COPPER_TEXTURE_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		this.goldStringX = (short)(this.frame.getBrowseItemX()+GOLD_STRING_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		this.silverStringX = (short)(this.frame.getBrowseItemX()+SILVER_STRING_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		this.copperStringX = (short)(this.frame.getBrowseItemX()+COPPER_STRING_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		this.coinWidth = (short)(COIN_WIDTH*Mideas.getDisplayXFactor());
		this.coinHeight = (short)(COIN_HEIGHT*Mideas.getDisplayYFactor());
		this.duration = "";
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
	}
	
	public void drawItemName() {
		ITEM_NAME_FONT.drawStringShadowPart(this.itemNameX, this.frame.getBrowseItemY()+this.itemNameYOffset, this.itemName, this.entry.getItem().getNameColor(), Color.BLACK, 1, 0, 0);
	}
	
	public void drawString() {
		DURATION_FONT.drawStringShadowPart(this.levelX, this.frame.getBrowseItemY()+this.stringY, this.level, Color.WHITE, Color.BLACK, 1, 0, 0);
		DURATION_FONT.drawStringShadowPart(this.durationX, this.frame.getBrowseItemY()+this.stringY, this.duration, Color.WHITE, Color.BLACK, 1, 0, 0);
		DURATION_FONT.drawStringShadowPart(this.sellerNameX, this.frame.getBrowseItemY()+this.stringY, this.sellerName, Color.WHITE, Color.BLACK, 1, 0, 0);
	}
	
	public void drawGoldString() {
		if(this.entry.canBeBuyout()) {
			GOLD_FONT.drawStringShadowPartReversed(this.goldStringX, this.frame.getBrowseItemY(), this.entry.getBidGoldString(), Color.WHITE, Color.BLACK, 1, 0, 0);
			GOLD_FONT.drawStringShadowPartReversed(this.goldStringX, this.frame.getBrowseItemY()+14*Mideas.getDisplayYFactor(), this.entry.getBuyoutGoldString(), Color.YELLOW, Color.BLACK, 1, 0, 0);
			GOLD_FONT.drawStringShadowPartReversed(this.silverStringX, this.frame.getBrowseItemY(), this.entry.getBidSilverString(), Color.WHITE, Color.BLACK, 1, 0, 0);
			GOLD_FONT.drawStringShadowPartReversed(this.silverStringX, this.frame.getBrowseItemY()+14*Mideas.getDisplayYFactor(), this.entry.getBuyoutSilverString(), Color.YELLOW, Color.BLACK, 1, 0, 0);
			GOLD_FONT.drawStringShadowPartReversed(this.copperStringX, this.frame.getBrowseItemY(), this.entry.getBidCopperString(), Color.WHITE, Color.BLACK, 1, 0, 0);
			GOLD_FONT.drawStringShadowPartReversed(this.copperStringX, this.frame.getBrowseItemY()+14*Mideas.getDisplayYFactor(), this.entry.getBuyoutCopperString(), Color.YELLOW, Color.BLACK, 1, 0, 0);
		}
		else  {
			GOLD_FONT.drawStringShadowPartReversed(this.goldStringX, this.frame.getBrowseItemY()+7*Mideas.getDisplayYFactor(), this.entry.getBuyoutGoldString(), Color.WHITE, Color.BLACK, 1, 0, 0);
			GOLD_FONT.drawStringShadowPartReversed(this.silverStringX, this.frame.getBrowseItemY()+7*Mideas.getDisplayYFactor(), this.entry.getBuyoutSilverString(), Color.WHITE, Color.BLACK, 1, 0, 0);
			GOLD_FONT.drawStringShadowPartReversed(this.copperStringX, this.frame.getBrowseItemY()+7*Mideas.getDisplayYFactor(), this.entry.getBuyoutCopperString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		}
	}
	
	public void drawGoldTexture() {
		if(this.entry.canBeBuyout()) {
			Draw.drawQuadPart(Sprites.gold_coin, this.goldTextureX, this.frame.getBrowseItemY()+2*Mideas.getDisplayYFactor(), this.coinWidth, this.coinHeight);
			Draw.drawQuadPart(Sprites.gold_coin, this.goldTextureX, this.frame.getBrowseItemY()+16*Mideas.getDisplayYFactor(), this.coinWidth, this.coinHeight);
		}
		else {
			Draw.drawQuadPart(Sprites.gold_coin, this.goldTextureX, this.frame.getBrowseItemY()+9*Mideas.getDisplayYFactor(), this.coinWidth, this.coinHeight);
		}
	}
	
	public void drawSilverTexture() {
		if(this.entry.canBeBuyout()) {
			Draw.drawQuadPart(Sprites.silver_coin, this.silverTextureX, this.frame.getBrowseItemY()+2*Mideas.getDisplayYFactor(), this.coinWidth, this.coinHeight);
			Draw.drawQuadPart(Sprites.silver_coin, this.silverTextureX, this.frame.getBrowseItemY()+16*Mideas.getDisplayYFactor(), this.coinWidth, this.coinHeight);
		}
		else {
			Draw.drawQuadPart(Sprites.silver_coin, this.silverTextureX, this.frame.getBrowseItemY()+9*Mideas.getDisplayYFactor(), this.coinWidth, this.coinHeight);
		}
	}
	
	public void drawCopperTexture() {
		if(this.entry.canBeBuyout()) {
			Draw.drawQuadPart(Sprites.copper_coin, this.copperTextureX, this.frame.getBrowseItemY()+2*Mideas.getDisplayYFactor(), this.coinWidth, this.coinHeight);
			Draw.drawQuadPart(Sprites.copper_coin, this.copperTextureX, this.frame.getBrowseItemY()+16*Mideas.getDisplayYFactor(), this.coinWidth, this.coinHeight);
		}
		else {
			Draw.drawQuadPart(Sprites.copper_coin, this.copperTextureX, this.frame.getBrowseItemY()+9*Mideas.getDisplayYFactor(), this.coinWidth, this.coinHeight);
		}
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
				if(Mouse.getEventButton() == 0 && this.frame.getSelectedBrowseEntry() == this.entry) {
					this.frame.setSelectedBrowseEntry(null);
				}
			}
		}
		return false;
	}
	
	public void setEntry(AuctionEntry entry) {
		if(entry == null) {
			this.entry = null;
			this.item = null;
			this.sellerName = null;
			this.duration = "";
			this.durationWidth = 0;
			this.itemName = null;
			this.level = null;
			return;
		}
		if(!entry.getItem().getIsLoaded()) {
			
		}
		this.entry = entry;
		this.item = entry.getItem();
		this.sellerName = entry.getSellerName();
		this.duration = entry.getDuration().getDuration();
		this.durationWidth = DURATION_FONT.getWidth(this.duration)+(short)(20*Mideas.getDisplayXFactor());
		this.itemName = formatItemName(entry.getItem().getStuffName());
		this.level = StringUtils.value[this.entry.getItem().getLevel()];
		this.sellerNameX = (short)(this.frame.getBrowseItemX()+SELLER_NAME_X_OFFSET*Mideas.getDisplayXFactor()-DURATION_FONT.getWidth(this.sellerName)/2);
		this.durationX = (short)(this.frame.getBrowseItemX()+DURATION_X_OFFSET*Mideas.getDisplayXFactor()-DURATION_FONT.getWidth(this.duration)/2);
		if(this.lastItemScrollbarState != this.frame.browseItemScrollbarEnabled()) {
			updateGoldSize();
			this.lastItemScrollbarState = this.frame.browseItemScrollbarEnabled();
		}
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
		this.durationX = (short)(this.frame.getBrowseItemX()+DURATION_X_OFFSET*Mideas.getDisplayXFactor()-DURATION_FONT.getWidth(this.duration)/2);
		this.levelX = (short)(this.frame.getBrowseItemX()+LEVEL_X_OFFSET*Mideas.getDisplayXFactor());
		this.stringY = (short)(TEXT_Y_OFFSET*Mideas.getDisplayYFactor());
		this.itemNameX = (short)(this.frame.getBrowseItemX()+ITEM_NAME_X_OFFSET*Mideas.getDisplayXFactor());
		this.itemNameYOffset = (short)(this.itemNameYOffsetSave*Mideas.getDisplayYFactor());
		this.coinWidth = (short)(COIN_WIDTH*Mideas.getDisplayXFactor());
		this.coinHeight = (short)(COIN_HEIGHT*Mideas.getDisplayYFactor());
		updateGoldSize();
		if(this.sellerName != null) {
			this.sellerNameX = (short)(this.frame.getBrowseItemX()+SELLER_NAME_X_OFFSET*Mideas.getDisplayXFactor()-DURATION_FONT.getWidth(this.sellerName)/2);
		}
	}
	
	private void updateGoldSize() {
		if(!this.frame.browseItemScrollbarEnabled()) {
			this.goldTextureX = (short)(this.frame.getBrowseItemX()+GOLD_TEXTURE_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
			this.silverTextureX = (short)(this.frame.getBrowseItemX()+SILVER_TEXTURE_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
			this.copperTextureX = (short)(this.frame.getBrowseItemX()+COPPER_TEXTURE_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
			this.goldStringX = (short)(this.frame.getBrowseItemX()+GOLD_STRING_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
			this.silverStringX = (short)(this.frame.getBrowseItemX()+SILVER_STRING_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
			this.copperStringX = (short)(this.frame.getBrowseItemX()+COPPER_STRING_X_NO_SCROLLBAR*Mideas.getDisplayXFactor());
		}
		else {
			this.goldTextureX = (short)(this.frame.getBrowseItemX()+GOLD_TEXTURE_X_SCROLLBAR*Mideas.getDisplayXFactor());
			this.silverTextureX = (short)(this.frame.getBrowseItemX()+SILVER_TEXTURE_X_SCROLLBAR*Mideas.getDisplayXFactor());
			this.copperTextureX = (short)(this.frame.getBrowseItemX()+COPPER_TEXTURE_X_SCROLLBAR*Mideas.getDisplayXFactor());
			this.goldStringX = (short)(this.frame.getBrowseItemX()+GOLD_STRING_X_SCROLLBAR*Mideas.getDisplayXFactor());
			this.silverStringX = (short)(this.frame.getBrowseItemX()+SILVER_STRING_X_SCROLLBAR*Mideas.getDisplayXFactor());
			this.copperStringX = (short)(this.frame.getBrowseItemX()+COPPER_STRING_X_SCROLLBAR*Mideas.getDisplayXFactor());
		}
	}
	
	protected void updateUnloadedItem(int itemID) {
		if(this.entry != null && this.entry.getItemID() == itemID) {
			this.itemName = formatItemName(this.entry.getItem().getStuffName());
		}
	}
	
	private String formatItemName(String name) {
		short nameMaxWidth = ITEM_NAME_MAX_WIDTH;
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
				this.itemNameYOffset = (short)(-2*Mideas.getDisplayYFactor());
				this.itemNameYOffsetSave = -2;
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
