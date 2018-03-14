package com.mideas.rpg.v2.hud.auction;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.auction.AuctionHouseFilter;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.UIElement;
import com.mideas.rpg.v2.utils.UIElementType;

public class AuctionHouseBrowseSubcategoryFilterButton extends UIElement
{

	@SuppressWarnings("hiding")
	protected AuctionHouseBrowseFrame parentFrame;
	private final String name;
	private boolean buttonHover;
	private boolean leftButtonDown;
	private boolean rightButtonDown;
	private final AuctionHouseFilter filter;
	private short xSave;
	private short ySave;
	private short width;
	private short height;
	private short widthSave;
	private short heightSave;
	private boolean isVisible;
	
	public AuctionHouseBrowseSubcategoryFilterButton(AuctionHouseBrowseFrame parentFrame, AuctionHouseFilter filter, String name, int x, int y)
	{
		super(name, UIElementType.AUCTION_HOUSE_BROWSE_CATEGORY_FILTER_BUTTON);
		this.name = filter.getName();
		this.filter = filter;
		this.parentFrame = parentFrame;
		this.xSave = (short)x;
		this.ySave = (short)y;
		this.isVisible = true;
	}
	
	public void initParentFrame(AuctionHouseBrowseFrame parentFrame)
	{
		this.parentFrame = parentFrame;
		updateSize();
	}
	
	public void drawTexturePart()
	{
		if (!this.isVisible)
			return;
		Draw.drawQuadPart(Sprites.chat_channel_button, this.x, this.y, this.width, this.height, .4f);
	}
	
	public void drawStringPart()
	{
		if (!this.isVisible)
			return;
		if(this.leftButtonDown || this.rightButtonDown)
			AuctionHouseBrowseCategoryFilterButton.font.drawStringShadowPart(this.x + 15*Mideas.getDisplayXFactor(), this.y + 5*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
		else
			AuctionHouseBrowseCategoryFilterButton.font.drawStringShadowPart(this.x + 13*Mideas.getDisplayXFactor(), this.y + 3*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
	}
	
	public void drawHoverPart()
	{
		if (!this.isVisible)
			return;
		if(this.buttonHover || this.parentFrame.getSelectedFilter() == this.filter)
			Draw.drawQuadBlendPart(Sprites.button_menu_hover, this.x, this.y, this.width, this.height);
	}
	
	@Override
	public void draw()
	{
		
	}
	
	@Override
	public boolean mouseEvent()
	{
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x + this.width && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y + this.height)
		{
			this.buttonHover = true;
			Mideas.setHover(this, false);
		}
		else
			this.buttonHover = false;
		if(this.buttonHover) {
			if(Mouse.getEventButtonState())
			{
				if(Mouse.getEventButton() == 0)
					this.leftButtonDown = true;	
				else if (Mouse.getEventButton() == 1)
					this.rightButtonDown = true;
			}
			else if(this.leftButtonDown || this.rightButtonDown)
				if(Mouse.getEventButton() == 0)
				{
					//this.parentFrame.unexpandAllCategoryFilter();
					if(isSelected())
						this.parentFrame.setSelectedFilter(AuctionHouseFilter.NONE);
					else
						this.parentFrame.setSelectedFilter(this.filter);
					this.leftButtonDown = false;
					return (true);
				}
				else if(Mouse.getEventButton() == 1)
				{
					this.rightButtonDown = false;
					return (true);
				}
		}
		else if(!Mouse.getEventButtonState())
			if(Mouse.getEventButton() == 0)
				this.leftButtonDown = false;	
			else if (Mouse.getEventButton() == 1)
				this.rightButtonDown = false;
		return (false);
	}
	
	public boolean isSelected()
	{
		return (this.parentFrame.getSelectedFilter() == this.filter);
	}
	
	public void updateSize()
	{
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
	}
	
	@Override
	public void setWidth(int width)
	{
		this.width = (short)(width * Mideas.getDisplayXFactor());
		this.widthSave = (short)width;
	}
	
	@Override
	public void setHeight(int height)
	{
		this.height = (short)(height * Mideas.getDisplayYFactor());
		this.heightSave = (short)(height);
	}
	
	@Override
	public void setY(int y)
	{
		this.y = (short)(this.parentFrame.getY() + y * Mideas.getDisplayYFactor());
		this.ySave = (short)y;
		this.isVisible = this.y >= this.parentFrame.getY() + AuctionHouseBrowseFrame.CATEGORY_FILTER_MIN_Y * Mideas.getDisplayYFactor() && this.y <= this.parentFrame.getY() + AuctionHouseBrowseFrame.CATEGORY_FILTER_MAX_Y * Mideas.getDisplayYFactor();
	}
}
