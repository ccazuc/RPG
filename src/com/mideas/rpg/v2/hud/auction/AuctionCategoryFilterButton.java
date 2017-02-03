package com.mideas.rpg.v2.hud.auction;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.auction.AuctionHouseFilter;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;

public class AuctionCategoryFilterButton extends AuctionFrameUI {

	private final ArrayList<AuctionHouseFilterButton> filterList;
	private final AuctionFrameUI frame;
	private final String name;
	private final AuctionHouseFilter filter;
	private boolean buttonHover;
	private boolean buttonDown;
	private boolean isExpanded;
	
	public AuctionCategoryFilterButton(AuctionFrameUI frame, AuctionHouseFilter filter) {
		this.filter = filter;
		this.name = filter.getName();
		this.frame = frame;
		this.filterList = new ArrayList<AuctionHouseFilterButton>();
	}
	
	@Override
	public void draw() {
		Draw.drawQuad(Sprites.chat_channel_button, this.frame.getBrowseCategoryX(), this.frame.getBrowseCategoryY(), this.frame.getBrowseCategoryWidth(), this.frame.getBrowseCategoryHeight(), .4f);
		if(this.buttonDown) {
			this.frame.browseCategoryFont.drawStringShadow(this.frame.getBrowseCategoryX()+10, this.frame.getBrowseCategoryY()+2*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		else {
			this.frame.browseCategoryFont.drawStringShadow(this.frame.getBrowseCategoryX()+8, this.frame.getBrowseCategoryY(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		if(getSelectedCategoryFilter() == this.filter || this.buttonHover) {
			Draw.drawQuadBlend(Sprites.button_menu_hover, this.frame.getBrowseCategoryX(), this.frame.getBrowseCategoryY(), this.frame.getBrowseCategoryWidth(), this.frame.getBrowseCategoryHeight());
		}
		this.frame.incrementBrowseCateogryY();
		if(!this.isExpanded) {
			return;
		}
		int i = -1;
		while(++i < this.filterList.size()) {
			this.filterList.get(i).draw();
		}
	}
	
	protected boolean event() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.frame.getBrowseCategoryX() && Mideas.mouseX() <= this.frame.getBrowseCategoryX()+this.frame.getBrowseCategoryWidth() && Mideas.mouseY() >= this.frame.getBrowseCategoryY() && Mideas.mouseY() <= this.frame.getBrowseCategoryY()+this.frame.getBrowseCategoryYShift()) {
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
					if(this.isExpanded) {
						setSelectedCategoryFilter(AuctionHouseFilter.NONE);
					}
					else {
						setSelectedCategoryFilter(this.filter);
					}
					setSelectedFilter(AuctionHouseFilter.NONE);
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
	
	protected boolean isHover() {
		return this.buttonHover;
	}
}
