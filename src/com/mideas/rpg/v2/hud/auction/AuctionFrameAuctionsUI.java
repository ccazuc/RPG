package com.mideas.rpg.v2.hud.auction;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.ButtonMenuSort;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.InputBox;
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

public class AuctionFrameAuctionsUI {

	private final short CLOSE_BUTTON_X = 744;
	private final short CLOSE_BUTTON_Y = 409;
	private final short BUTTON_WIDTH = 75;
	private final short BUTTON_HEIGHT = 22;
	private final short STARTING_PRICE_EDIT_BOX_Y = 257;
	private final short BUYOUT_PRICE_EDIT_BOX_Y = 302;
	private final short GOLD_STARTING_PRICE_EDIT_BOX_X = 28;
	private final short GOLD_BUYOUT_PRICE_EDIT_BOX_X = 28;
	private final short GOLD_PRICE_EDIT_BOX_WIDTH = 75;
	private final short SILVER_STARTING_PRICE_EDIT_BOX_X = 108;
	private final short SILVER_BUYOUT_PRICE_EDIT_BOX_X = 108;
	private final short SILVER_PRICE_EDIT_BOX_WIDTH = 43;
	private final short COPPER_STARTING_PRICE_EDIT_BOX_X = 156;
	private final short COPPER_BUYOUT_PRICE_EDIT_BOX_X = 156;
	private final short COPPER_PRICE_EDIT_BOX_WIDTH = 43;
	
	private final short SORT_BY_RARITY_BUTTON_X = 217;
	private final short SORT_BY_TIME_LEFT_BUTTON_X = 400;
	private final short SORT_BY_HIGH_BIDDER_BUTTON_X = 498;
	private final short SORT_BY_CURRENT_BID_BUTTON_X = 604;
	private final short SORT_BUTTON_Y = 49;
	private final short SORT_BUTTON_HEIGHT = 21;
	private final short SORT_BY_RARITY_BUTTON_WIDTH = 185;
	private final short SORT_BY_TIME_LEFT_BUTTON_WIDTH = 100;
	private final short SORT_BY_HIGH_BIDDER_BUTTON_WIDTH = 108;
	private final short SORT_BY_CURRENT_BID_BUTTON_WIDTH = 213;
	private final TTF sortFont = FontManager.get("FRIZQT", 10);
	
	private final AuctionFrameUI frame;
	private boolean shouldUpdate;
	
	private final Button closeButton = new Button(this.CLOSE_BUTTON_X*Mideas.getDisplayXFactor(), this.CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BUTTON_WIDTH, this.BUTTON_HEIGHT, "Close", 12, 1) {
	
		@Override
		public void onLeftClickUp() {
			Interface.closeAuctionFrame();
			this.reset();
		}
	};
	final InputBox goldStartingPriceEditBox = new InputBox(this.GOLD_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.GOLD_PRICE_EDIT_BOX_WIDTH, 6, 5, this.GOLD_PRICE_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true, 0, 3) {
		
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
	final InputBox silverStartingPriceEditBox = new InputBox(this.SILVER_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.SILVER_PRICE_EDIT_BOX_WIDTH, 2, 5, this.SILVER_PRICE_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true, 0, 3) {
		
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
	final InputBox copperStartingPriceEditBox = new InputBox(this.COPPER_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.COPPER_PRICE_EDIT_BOX_WIDTH, 2, 5, this.COPPER_PRICE_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true, 0, 3) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameAuctionsUI.this.goldBuyoutPriceEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 99;
		}
	};
	final InputBox goldBuyoutPriceEditBox = new InputBox(this.GOLD_BUYOUT_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.BUYOUT_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.GOLD_PRICE_EDIT_BOX_WIDTH, 6, 5, this.GOLD_PRICE_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true, 0, 3) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameAuctionsUI.this.silverBuyoutPriceEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 999999;
		}
	};
	final InputBox silverBuyoutPriceEditBox = new InputBox(this.SILVER_BUYOUT_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.BUYOUT_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.SILVER_PRICE_EDIT_BOX_WIDTH, 2, 5, this.SILVER_PRICE_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true, 0, 3) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameAuctionsUI.this.copperBuyoutPriceEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 99;
		}
	};
	final InputBox copperBuyoutPriceEditBox = new InputBox(this.COPPER_BUYOUT_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.BUYOUT_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor(), this.COPPER_PRICE_EDIT_BOX_WIDTH, 2, 5, this.COPPER_PRICE_EDIT_BOX_WIDTH, FontManager.get("FRIZQT", 11), true, 0, 3) {
		
		@Override
		public boolean keyEvent(char c) {
			if(c == Input.ESCAPE_CHAR_VALUE) {
				this.input.setIsActive(false);
				return true;
			}
			if(c == Input.TAB_CHAR_VALUE || c == Input.ENTER_CHAR_VALUE) {
				AuctionFrameAuctionsUI.this.goldStartingPriceEditBox.setActive(true);
				return true;
			}
			return false;
		}
		
		@Override
		public boolean checkValue(int value) {
			return value <= 99;
		}
	};
	private final ButtonMenuSort sortByRarityButton = new ButtonMenuSort(this.SORT_BY_RARITY_BUTTON_X*Mideas.getDisplayXFactor(), this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_BY_RARITY_BUTTON_WIDTH, this.SORT_BUTTON_HEIGHT, "Rarity", this.sortFont) {
		
		@Override
		public void eventButtonClick() {
		}
	};
	private final ButtonMenuSort sortByTimeLeftButton = new ButtonMenuSort(this.SORT_BY_TIME_LEFT_BUTTON_X*Mideas.getDisplayXFactor(), this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_BY_TIME_LEFT_BUTTON_WIDTH, this.SORT_BUTTON_HEIGHT, "Time Left", this.sortFont) {
		
		@Override
		public void eventButtonClick() {
		}
	};
	private final ButtonMenuSort sortByHighBidderButton = new ButtonMenuSort(this.SORT_BY_HIGH_BIDDER_BUTTON_X*Mideas.getDisplayXFactor(), this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_BY_HIGH_BIDDER_BUTTON_WIDTH, this.SORT_BUTTON_HEIGHT, "High Bidder", this.sortFont) {
		
		@Override
		public void eventButtonClick() {
		}
	};
	private final ButtonMenuSort sortByCurrentBidButton = new ButtonMenuSort(this.SORT_BY_CURRENT_BID_BUTTON_X*Mideas.getDisplayXFactor(), this.SORT_BUTTON_Y*Mideas.getDisplayYFactor(), this.SORT_BY_CURRENT_BID_BUTTON_WIDTH, this.SORT_BUTTON_HEIGHT, "Current Bid", this.sortFont) {
		
		@Override
		public void eventButtonClick() {
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
		
		Sprites.edit_box.drawBegin();
		this.goldStartingPriceEditBox.drawTexturePart();
		this.silverStartingPriceEditBox.drawTexturePart();
		this.copperStartingPriceEditBox.drawTexturePart();
		this.goldBuyoutPriceEditBox.drawTexturePart();
		this.silverBuyoutPriceEditBox.drawTexturePart();
		this.copperBuyoutPriceEditBox.drawTexturePart();
		Sprites.edit_box.drawEnd();
		this.goldStartingPriceEditBox.drawString();
		this.silverStartingPriceEditBox.drawString();
		this.copperStartingPriceEditBox.drawString();
		this.goldBuyoutPriceEditBox.drawString();
		this.silverBuyoutPriceEditBox.drawString();
		this.copperBuyoutPriceEditBox.drawString();
		
		Sprites.button_menu_sort.drawBegin();
		this.sortByRarityButton.drawTexturePart();
		this.sortByTimeLeftButton.drawTexturePart();
		this.sortByHighBidderButton.drawTexturePart();
		this.sortByCurrentBidButton.drawTexturePart();
		Sprites.button_menu_sort.drawEnd();
		this.sortFont.drawBegin();
		this.sortByRarityButton.drawStringPart();
		this.sortByTimeLeftButton.drawStringPart();
		this.sortByHighBidderButton.drawStringPart();
		this.sortByCurrentBidButton.drawStringPart();
		this.sortFont.drawEnd();
		Sprites.button_menu_hover.drawBlendBegin();
		this.sortByRarityButton.drawHoverPart();
		this.sortByTimeLeftButton.drawHoverPart();
		this.sortByHighBidderButton.drawHoverPart();
		this.sortByCurrentBidButton.drawHoverPart();
		Sprites.button_menu_hover.drawBlendEnd();
		
		TTF font = FontManager.get("ARIALN", 14);
		font.drawBegin();
		font.drawStringShadowPartReversed(this.frame.getXFrame()+150*Mideas.getDisplayXFactor(), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getCoppierPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPartReversed(this.frame.getXFrame()+115*Mideas.getDisplayXFactor(), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getSilverPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPartReversed(this.frame.getXFrame()+77*Mideas.getDisplayXFactor(), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getGoldPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawEnd();
		font = FontManager.get("FRIZQT", 11);
		font.drawBegin();
		font.drawStringShadowPart(this.frame.getXFrame()+83*Mideas.getDisplayXFactor(), this.frame.getYFrame()+52*Mideas.getDisplayYFactor(), "Create auction", Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawEnd();
		Sprites.copper_coin.drawBegin();
		Draw.drawQuadPart(Sprites.copper_coin, this.frame.getXFrame()+153*Mideas.getDisplayXFactor(), this.frame.getYFrame()+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.copper_coin, this.frame.getXFrame()+182*Mideas.getDisplayXFactor(), this.frame.getYFrame()+260*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 14*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.copper_coin, this.frame.getXFrame()+182*Mideas.getDisplayXFactor(), this.frame.getYFrame()+305*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 14*Mideas.getDisplayYFactor());
		Sprites.copper_coin.drawEnd();
		Sprites.silver_coin.drawBegin();
		Draw.drawQuadPart(Sprites.silver_coin, this.frame.getXFrame()+118*Mideas.getDisplayXFactor(), this.frame.getYFrame()+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.silver_coin, this.frame.getXFrame()+134*Mideas.getDisplayXFactor(), this.frame.getYFrame()+260*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 14*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.silver_coin, this.frame.getXFrame()+134*Mideas.getDisplayXFactor(), this.frame.getYFrame()+305*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 14*Mideas.getDisplayYFactor());
		Sprites.silver_coin.drawEnd();
		Sprites.gold_coin.drawBegin();
		Draw.drawQuadPart(Sprites.gold_coin, this.frame.getXFrame()+83*Mideas.getDisplayXFactor(), this.frame.getYFrame()+412*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 13*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.gold_coin, this.frame.getXFrame()+86*Mideas.getDisplayXFactor(), this.frame.getYFrame()+260*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 14*Mideas.getDisplayYFactor());
		Draw.drawQuadPart(Sprites.gold_coin, this.frame.getXFrame()+86*Mideas.getDisplayXFactor(), this.frame.getYFrame()+305*Mideas.getDisplayYFactor(), 13*Mideas.getDisplayXFactor(), 14*Mideas.getDisplayYFactor());
		Sprites.gold_coin.drawEnd();
	}
	
	protected boolean mouseEvent() {
		if(this.closeButton.event()) return true;
		if(this.goldStartingPriceEditBox.mouseEvent()) return true;
		if(this.silverStartingPriceEditBox.mouseEvent()) return true;
		if(this.copperStartingPriceEditBox.mouseEvent()) return true;
		if(this.goldBuyoutPriceEditBox.mouseEvent()) return true;
		if(this.silverBuyoutPriceEditBox.mouseEvent()) return true;
		if(this.copperBuyoutPriceEditBox.mouseEvent()) return true;
		if(this.sortByRarityButton.event()) return true;
		if(this.sortByTimeLeftButton.event()) return true;
		if(this.sortByHighBidderButton.event()) return true;
		if(this.sortByCurrentBidButton.event()) return true;
		return false;
	}
	
	protected boolean keyboardEvent() {
		if(this.goldStartingPriceEditBox.keyboardEvent()) return true;
		if(this.silverStartingPriceEditBox.keyboardEvent()) return true;
		if(this.copperStartingPriceEditBox.keyboardEvent()) return true;
		if(this.goldBuyoutPriceEditBox.keyboardEvent()) return true;
		if(this.silverBuyoutPriceEditBox.keyboardEvent()) return true;
		if(this.copperBuyoutPriceEditBox.keyboardEvent()) return true;
		return false;
	}
	
	protected void shouldUpdate() {
		this.shouldUpdate = true;
	}
	
	private void updateSize() {
		if(!this.shouldUpdate) {
			return;
		}
		this.sortByRarityButton.update(this.frame.getXFrame()+this.SORT_BY_RARITY_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor());
		this.sortByTimeLeftButton.update(this.frame.getXFrame()+this.SORT_BY_TIME_LEFT_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor());
		this.sortByHighBidderButton.update(this.frame.getXFrame()+this.SORT_BY_HIGH_BIDDER_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor());
		this.sortByCurrentBidButton.update(this.frame.getXFrame()+this.SORT_BY_CURRENT_BID_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.SORT_BUTTON_Y*Mideas.getDisplayYFactor());
		this.closeButton.update(this.frame.getXFrame()+this.CLOSE_BUTTON_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.CLOSE_BUTTON_Y*Mideas.getDisplayYFactor());
		this.goldStartingPriceEditBox.update(this.frame.getXFrame()+this.GOLD_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.silverStartingPriceEditBox.update(this.frame.getXFrame()+this.SILVER_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.copperStartingPriceEditBox.update(this.frame.getXFrame()+this.COPPER_STARTING_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.STARTING_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.goldBuyoutPriceEditBox.update(this.frame.getXFrame()+this.GOLD_BUYOUT_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.BUYOUT_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.silverBuyoutPriceEditBox.update(this.frame.getXFrame()+this.SILVER_BUYOUT_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.BUYOUT_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.copperBuyoutPriceEditBox.update(this.frame.getXFrame()+this.COPPER_BUYOUT_PRICE_EDIT_BOX_X*Mideas.getDisplayXFactor(), this.frame.getYFrame()+this.BUYOUT_PRICE_EDIT_BOX_Y*Mideas.getDisplayYFactor());
		this.shouldUpdate = false;
	}
}
