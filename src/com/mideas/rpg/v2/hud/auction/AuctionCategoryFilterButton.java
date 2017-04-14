package com.mideas.rpg.v2.hud.auction;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.auction.AuctionHouseFilter;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;

public class AuctionCategoryFilterButton {

	private final ArrayList<AuctionHouseFilterButton> filterList;
	private final AuctionFrameBrowseUI frame;
	private final String name;
	private final AuctionHouseFilter filter;
	private boolean buttonHover;
	private boolean buttonDown;
	private boolean isExpanded;
	
	public AuctionCategoryFilterButton(AuctionFrameBrowseUI frame, AuctionHouseFilter filter) {
		this.filter = filter;
		this.name = filter.getName();
		this.frame = frame;
		this.filterList = new ArrayList<AuctionHouseFilterButton>();
	}
	
	public void drawTexturePart() {
		Draw.drawQuadPart(Sprites.chat_channel_button, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight());
		this.frame.incrementBrowseFilterY();
		if(!this.isExpanded) {
			return;
		}
		int i = -1;
		while(++i < this.filterList.size()) {
			this.filterList.get(i).drawTexturePart();
			this.frame.incrementBrowseFilterY();
		}
	}
	
	public void drawStringPart() {
		if(this.frame.getSelectedCategoryFilter() == this.filter || this.buttonHover) {
			if(this.buttonDown) {
				this.frame.filterFont.drawStringShadowPart(this.frame.getBrowseFilterX()+7, this.frame.getBrowseFilterY()+5*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			else {
				this.frame.filterFont.drawStringShadowPart(this.frame.getBrowseFilterX()+5, this.frame.getBrowseFilterY()+3*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
			}
		}
		else if(this.buttonDown) {
			this.frame.filterFont.drawStringShadowPart(this.frame.getBrowseFilterX()+7, this.frame.getBrowseFilterY()+5*Mideas.getDisplayYFactor(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		}
		else {
			this.frame.filterFont.drawStringShadowPart(this.frame.getBrowseFilterX()+5, this.frame.getBrowseFilterY()+3*Mideas.getDisplayYFactor(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		}
		this.frame.incrementBrowseFilterY();
		if(!this.isExpanded) {
			return;
		}
		int i = -1;
		while(++i < this.filterList.size()) {
			this.filterList.get(i).drawStringPart();
			this.frame.incrementBrowseFilterY();
		}
	}
	
	public void drawHoverPart() {
		if(this.frame.getSelectedCategoryFilter() == this.filter || this.buttonHover) {
			Draw.drawQuadBlendPart(Sprites.button_menu_hover, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight());
		}
		this.frame.incrementBrowseFilterY();
		if(!this.isExpanded) {
			return;
		}
		int i = -1;
		while(++i < this.filterList.size()) {
			this.filterList.get(i).drawHoverPart();
			this.frame.incrementBrowseFilterY();
		}
	}
	
	public void draw() {
		Draw.drawQuad(Sprites.chat_channel_button, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight());
		if(this.frame.getSelectedCategoryFilter() == this.filter || this.buttonHover) {
			if(this.buttonDown) {
				this.frame.filterFont.drawStringShadow(this.frame.getBrowseFilterX()+7, this.frame.getBrowseFilterY()+5*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			else {
				this.frame.filterFont.drawStringShadow(this.frame.getBrowseFilterX()+5, this.frame.getBrowseFilterY()+3*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
			}
			Draw.drawQuadBlend(Sprites.button_menu_hover, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight());
		}
		else if(this.buttonDown) {
			this.frame.filterFont.drawStringShadow(this.frame.getBrowseFilterX()+7, this.frame.getBrowseFilterY()+5*Mideas.getDisplayYFactor(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		}
		else {
			this.frame.filterFont.drawStringShadow(this.frame.getBrowseFilterX()+5, this.frame.getBrowseFilterY()+3*Mideas.getDisplayYFactor(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		}
		this.frame.incrementBrowseFilterY();
		if(!this.isExpanded) {
			return;
		}
		int i = -1;
		while(++i < this.filterList.size()) {
			this.filterList.get(i).draw();
			this.frame.incrementBrowseFilterY();
		}
	}
	
	protected boolean event() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.frame.getBrowseFilterX() && Mideas.mouseX() <= this.frame.getBrowseFilterX()+this.frame.getBrowseFilterWidth() && Mideas.mouseY() >= this.frame.getBrowseFilterY() && Mideas.mouseY() <= this.frame.getBrowseFilterY()+this.frame.getBrowseFilterYShift()) {
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
					if(this.isExpanded) {
						this.frame.setSelectedCategoryFilter(AuctionHouseFilter.NONE);
					}
					else {
						this.frame.unexpandAllCategoryFilter();
						this.frame.setSelectedCategoryFilter(this.filter);
					}
					this.frame.setSelectedFilter(AuctionHouseFilter.NONE);
					this.buttonDown = false;
					this.isExpanded = !this.isExpanded;
					this.frame.checkBrowseFilterScrollbar();
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
		if(!this.isExpanded) {
			return false;
		}
		int i = -1;
		while(++i < this.filterList.size()) {
			if(this.filterList.get(i).event()) {
				return true;
			}
		}
		return false;
	}
	
	protected void addFilter(AuctionHouseFilter filter) {
		this.filterList.add(new AuctionHouseFilterButton(this.frame, filter));
	}
	
	protected int getNumberLine() {
		return this.isExpanded ? this.filterList.size()+1 : 1;
	}
	
	protected void unexpand() {
		this.isExpanded = false;
	}
}
