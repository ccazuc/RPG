package com.mideas.rpg.v2.hud.auction;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.EditBox;
import com.mideas.rpg.v2.utils.Input;

public class AuctionFrameAuctionsUI {

	private final short CLOSE_BUTTON_X = 744;
	private final short CLOSE_BUTTON_Y = 409;
	private final short BUTTON_WIDTH = 75;
	private final short BUTTON_HEIGHT = 22;
	private final short GOLD_STARTING_PRICE_EDIT_BOX_X = 35;
	private final byte GOLD_PRICE_EDIT_BOX_WIDTH = 80;
	private final short STARTING_PRICE_EDIT_BOX_Y = 400;
	private final short SILVER_STARTING_PRICE_EDIT_BOX_X = 130;
	private final byte SILVER_PRICE_EDIT_BOX_WIDTH = 40;
	private final byte COPPER_PRICE_EDIT_BOX_WIDTH = 40;
	private final short COPPER_STARTING_PRICE_EDIT_BOX_X = 210;
	
	private final AuctionFrameUI frame;
	private boolean shouldUpdate;
	
	private final Button closeButton = new Button(this.CLOSE_BUTTON_X*Mideas.getDisplayXFactor(), this.CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Close", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			Interface.closeAuctionFrame();
			this.reset();
		}
	};
	final EditBox goldStartingPriceEditBox = new EditBox(this.GOLD_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.GOLD_PRICE_EDIT_BOX_WIDTH, 6, 5, this.GOLD_PRICE_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameAuctionsUI.this.silverStartingPriceEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 999999;
		}
	};
	final EditBox silverStartingPriceEditBox = new EditBox(this.SILVER_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.SILVER_PRICE_EDIT_BOX_WIDTH, 2, 5, this.SILVER_PRICE_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameAuctionsUI.this.copperStartingPriceEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 99;
		}
	};
	final EditBox copperStartingPriceEditBox = new EditBox(this.COPPER_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.COPPER_PRICE_EDIT_BOX_WIDTH, 2, 5, this.COPPER_PRICE_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameAuctionsUI.this.copperStartingPriceEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 99;
		}
	};
	
	public AuctionFrameAuctionsUI(AuctionFrameUI frame) {
		this.frame = frame;
		this.shouldUpdate = true;
		updateSize();
	}
	
	protected void draw() {
		updateSize();
		Draw.drawQuad(Sprites.auction_auctions_frame, this.frame.getXFrame(), this.frame.getYFrame());
		this.closeButton.draw();
		TTF font = FontManager.get("ARIALN", 14);
		Sprites.edit_box.drawBegin();
		this.goldStartingPriceEditBox.drawTexturePart();
		this.silverStartingPriceEditBox.drawTexturePart();
		this.copperStartingPriceEditBox.drawTexturePart();
		Sprites.edit_box.drawEnd();
		this.goldStartingPriceEditBox.drawString();
		this.silverStartingPriceEditBox.drawString();
		this.copperStartingPriceEditBox.drawString();
		font.drawBegin();
		font.drawStringShadowPart(this.frame.getXFrame()+138*Mideas.getDisplayXFactor(), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getCoppierPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.frame.getXFrame()+102*Mideas.getDisplayXFactor(), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getSilverPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.frame.getXFrame()+78*Mideas.getDisplayXFactor()-font.getWidth(Mideas.joueur1().getGoldPieceString()), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getGoldPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.frame.getXFrame()+50*Mideas.getDisplayXFactor(), this.frame.getYFrame()+50*Mideas.getDisplayYFactor(), "Create auction", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawEnd();
		Sprites.copper_coin.drawBegin();
		Draw.drawQuadPart(Sprites.copper_coin, this.frame.getXFrame()+153*Mideas.getDisplayXFactor(), this.frame.getYFrame()+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Sprites.copper_coin.drawEnd();
		Sprites.silver_coin.drawBegin();
		Draw.drawQuadPart(Sprites.silver_coin, this.frame.getXFrame()+118*Mideas.getDisplayXFactor(), this.frame.getYFrame()+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Sprites.silver_coin.drawEnd();
		Sprites.gold_coin.drawBegin();
		Draw.drawQuadPart(Sprites.gold_coin, this.frame.getXFrame()+83*Mideas.getDisplayXFactor(), this.frame.getYFrame()+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Sprites.gold_coin.drawEnd();
	}
	
	protected boolean mouseEvent() {
		if(this.closeButton.event()) return true;
		if(this.goldStartingPriceEditBox.mouseEvent()) return true;
		if(this.silverStartingPriceEditBox.mouseEvent()) return true;
		if(this.copperStartingPriceEditBox.mouseEvent()) return true;
		return false;
	}
	
	protected boolean keyboardEvent() {
		if(this.goldStartingPriceEditBox.keyboardEvent()) return true;
		if(this.silverStartingPriceEditBox.keyboardEvent()) return true;
		if(this.copperStartingPriceEditBox.keyboardEvent()) return true;
		return false;
	}
	
	protected void shouldUpdate() {
		this.shouldUpdate = true;
	}
	
	private void updateSize() {
		if(!this.shouldUpdate) {
			return;
		}
		this.goldStartingPriceEditBox.update(this.GOLD_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.silverStartingPriceEditBox.update(this.SILVER_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.copperStartingPriceEditBox.update(this.COPPER_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.shouldUpdate = false;
	}
}
