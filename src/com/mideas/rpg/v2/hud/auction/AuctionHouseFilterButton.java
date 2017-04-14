package com.mideas.rpg.v2.hud.auction;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.auction.AuctionHouseFilter;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;

public class AuctionHouseFilterButton {

	private final AuctionFrameBrowseUI frame;
	private final String name;
	private boolean buttonHover;
	private boolean buttonDown;
	private final AuctionHouseFilter filter;
	
	public AuctionHouseFilterButton(AuctionFrameBrowseUI frame, AuctionHouseFilter filter) {
		this.name = filter.getName();
		this.filter = filter;
		this.frame = frame;
	}
	
	public void drawTexturePart() {
		Draw.drawQuadPart(Sprites.chat_channel_button, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight(), .4f);
	}
	
	public void drawStringPart() {
		if(this.buttonDown) {
			this.frame.filterFont.drawStringShadowPart(this.frame.getBrowseFilterX()+15*Mideas.getDisplayXFactor(), this.frame.getBrowseFilterY()+5*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		else {
			this.frame.filterFont.drawStringShadowPart(this.frame.getBrowseFilterX()+13*Mideas.getDisplayXFactor(), this.frame.getBrowseFilterY()+3*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
	}
	
	public void drawHoverPart() {
		if(this.buttonHover || this.frame.getSelectedFilter() == this.filter) {
			Draw.drawQuadBlendPart(Sprites.button_menu_hover, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight());
		}
	}
	
	public void draw() {
		Draw.drawQuad(Sprites.chat_channel_button, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight(), .4f);
		if(this.buttonDown) {
			this.frame.filterFont.drawStringShadow(this.frame.getBrowseFilterX()+15*Mideas.getDisplayXFactor(), this.frame.getBrowseFilterY()+5*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		else {
			this.frame.filterFont.drawStringShadow(this.frame.getBrowseFilterX()+13*Mideas.getDisplayXFactor(), this.frame.getBrowseFilterY()+3*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		if(this.buttonHover || this.frame.getSelectedFilter() == this.filter) {
			Draw.drawQuadBlend(Sprites.button_menu_hover, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight());
		}
	}
	
	public boolean event() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.frame.getBrowseFilterX() && Mideas.mouseX() <= this.frame.getBrowseFilterX()+this.frame.getBrowseFilterWidth() && Mideas.mouseY() >= this.frame.getBrowseFilterY() && Mideas.mouseY() <= this.frame.getBrowseFilterY()+this.frame.getBrowseFilterHeight()) {
			this.buttonHover = true;
			Mideas.setHover(false);
		}
		else {
			this.buttonHover = false;
		}
		this.frame.incrementBrowseFilterY();
		if(this.buttonHover) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = true;
				}
			}
			else if(this.buttonDown) {
				if(Mouse.getEventButton() == 0) {
					if(this.frame.getSelectedFilter() == this.filter) {
						this.frame.setSelectedFilter(AuctionHouseFilter.NONE);
					}
					else {
						this.frame.setSelectedFilter(this.filter);
					}
					this.buttonDown = false;
					return true;
				}
				else if(Mouse.getEventButton() == 1) {
					this.buttonDown = false;
					return true;
				}
			}
		}
		else if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				this.buttonDown = false;
			}
		}
		return false;
	}
}
