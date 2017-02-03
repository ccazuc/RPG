package com.mideas.rpg.v2.hud.auction;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.auction.AuctionHouseFilter;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;

public class AuctionHouseFilterButton extends AuctionFrameUI {

	private final AuctionFrameUI frame;
	private final String name;
	private boolean buttonHover;
	private boolean buttonDown;
	private final AuctionHouseFilter filter;
	
	public AuctionHouseFilterButton(AuctionFrameUI frame, AuctionHouseFilter filter) {
		this.name = filter.getName();
		this.filter = filter;
		this.frame = frame;
	}
	
	@Override
	public void draw() {
		Draw.drawQuad(Sprites.chat_channel_button, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight(), .4f);
		if(this.buttonDown) {
			this.frame.browseFilterFont.drawStringShadow(this.frame.getBrowseFilterX()+10, this.frame.getBrowseFilterY()+2*Mideas.getDisplayYFactor(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		}
		else {
			this.frame.browseFilterFont.drawStringShadow(this.frame.getBrowseFilterX()+8, this.frame.getBrowseFilterY(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		}
		if(this.buttonHover) {
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
