package com.mideas.rpg.v2.hud.auction;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;

public class AuctionFrameAuctionsUI {

	private final short CLOSE_BUTTON_X = 744;
	private final short CLOSE_BUTTON_Y = 409;
	private final short BUTTON_WIDTH = 75;
	private final short BUTTON_HEIGHT = 22;
	
	private final AuctionFrameUI frame;
	private boolean shouldUpdate;
	
	private final Button closeButton = new Button(this.CLOSE_BUTTON_X*Mideas.getDisplayXFactor(), this.CLOSE_BUTTON_Y*Mideas.getDisplayYFactor(), this.BUTTON_WIDTH*Mideas.getDisplayXFactor(), this.BUTTON_HEIGHT*Mideas.getDisplayYFactor(), "Close", 12, 1) {
	
		@Override
		public void eventButtonClick() {
			Interface.closeAuctionFrame();
			this.reset();
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
		font.drawBegin();
		font.drawStringShadowPart(this.frame.getXFrame()+138*Mideas.getDisplayXFactor(), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getCoppierPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.frame.getXFrame()+102*Mideas.getDisplayXFactor(), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getSilverPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
		font.drawStringShadowPart(this.frame.getXFrame()+78*Mideas.getDisplayXFactor()-font.getWidth(Mideas.joueur1().getGoldPieceString()), this.frame.getYFrame()+409*Mideas.getDisplayYFactor(), Mideas.joueur1().getGoldPieceString(), Color.WHITE, Color.BLACK, 1, 0, 0);
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
		return false;
	}
	
	protected boolean keyboardEvent() {
		return false;
	}
	
	protected void shouldUpdate() {
		this.shouldUpdate = true;
	}
	
	private void updateSize() {
		if(!this.shouldUpdate) {
			return;
		}
		this.shouldUpdate = false;
	}
}
