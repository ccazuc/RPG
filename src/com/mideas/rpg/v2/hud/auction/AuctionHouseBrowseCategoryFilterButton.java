package com.mideas.rpg.v2.hud.auction;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.auction.AuctionHouseFilter;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.TTF;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.UIElement;
import com.mideas.rpg.v2.utils.UIElementType;

public class AuctionHouseBrowseCategoryFilterButton extends UIElement
{

	private final ArrayList<AuctionHouseBrowseSubcategoryFilterButton> filterList;
	@SuppressWarnings("hiding")
	private AuctionHouseBrowseFrame parentFrame;
	private final AuctionHouseFilter filter;
	private boolean buttonHover;
	private boolean leftButtonDown;
	private boolean rightButtonDown;
	private boolean isExpanded;
	private boolean isVisible;
	public final static TTF font = FontManager.get("FRIZQT", 11);
	
	public AuctionHouseBrowseCategoryFilterButton(AuctionHouseBrowseFrame frame, AuctionHouseFilter filter, int number, int x, int y, short width)
	{
		super("AuctionHouseBrowseFrameFilterButton" + number, UIElementType.AUCTION_HOUSE_BROWSE_CATEGORY_FILTER_BUTTON);
		this.filter = filter;
		this.name = filter.getName();
		this.parentFrame = frame;
		this.width = (short)(width * Mideas.getDisplayXFactor());
		this.height = (short)(28 * Mideas.getDisplayYFactor());
		this.xSave = (short)x;
		this.ySave = (short)y;
		this.widthSave = width;
		this.heightSave = (short)(20);
		this.isVisible = true;
		this.filterList = new ArrayList<AuctionHouseBrowseSubcategoryFilterButton>();
	}
	
	public void initParentFrame(AuctionHouseBrowseFrame parentFrame)
	{
		this.parentFrame = parentFrame;
		int i = -1;
		while (++i < this.filterList.size())
			this.filterList.get(i).initParentFrame(parentFrame);
		updateSize();
	}
	
	public void drawTexturePart()
	{
		if (!this.isVisible)
			return;
		Draw.drawQuadPart(Sprites.chat_channel_button, this.x, this.y, this.width, this.height);
		if (!this.isExpanded)
			return;
		int i = -1;
		while (++i < this.filterList.size())
			this.filterList.get(i).drawTexturePart();
	}
	
	public void drawStringPart()
	{
		if (!this.isVisible)
			return;
		if (this.isExpanded || this.buttonHover)
		{
			if (this.leftButtonDown || this.rightButtonDown)
				font.drawStringShadowPart(this.x+7, this.y+5*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
			else
				font.drawStringShadowPart(this.x+5, this.y+3*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		else if (this.leftButtonDown || this.rightButtonDown)
			font.drawStringShadowPart(this.x+7, this.y+5*Mideas.getDisplayYFactor(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		else
			font.drawStringShadowPart(this.x+5, this.y+3*Mideas.getDisplayYFactor(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		if (!this.isExpanded)
			return;
		int i = -1;
		while (++i < this.filterList.size())
			this.filterList.get(i).drawStringPart();
	}
	
	public void drawHoverPart()
	{
		if (!this.isVisible)
			return;
		if (this.isExpanded || this.buttonHover)
			Draw.drawQuadBlendPart(Sprites.button_menu_hover, this.x, this.y, this.width, this.height);
		if (!this.isExpanded)
			return;
		int i = -1;
		while (++i < this.filterList.size())
			this.filterList.get(i).drawHoverPart();
	}
	
	@Override
	public void draw()
	{
		/*Draw.drawQuad(Sprites.chat_channel_button, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight());
		if(this.frame.getSelectedCategoryFilter() == this.filter || this.buttonHover)
		{
			if(this.buttonDown)
				this.frame.filterFont.drawStringShadow(this.frame.getBrowseFilterX()+7, this.frame.getBrowseFilterY()+5*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
			else
				this.frame.filterFont.drawStringShadow(this.frame.getBrowseFilterX()+5, this.frame.getBrowseFilterY()+3*Mideas.getDisplayYFactor(), this.name, Color.WHITE, Color.BLACK, 1, 0, 0);
			Draw.drawQuadBlend(Sprites.button_menu_hover, this.frame.getBrowseFilterX(), this.frame.getBrowseFilterY(), this.frame.getBrowseFilterWidth(), this.frame.getBrowseFilterHeight());
		}
		else if(this.buttonDown)
			this.frame.filterFont.drawStringShadow(this.frame.getBrowseFilterX()+7, this.frame.getBrowseFilterY()+5*Mideas.getDisplayYFactor(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		else
			this.frame.filterFont.drawStringShadow(this.frame.getBrowseFilterX()+5, this.frame.getBrowseFilterY()+3*Mideas.getDisplayYFactor(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		this.frame.incrementBrowseFilterY();
		if(!this.isExpanded)
			return;
		int i = -1;
		while(++i < this.filterList.size())
		{
			this.filterList.get(i).draw();
			this.frame.incrementBrowseFilterY();
		}*/
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
		if(this.buttonHover)
		{
			if(Mouse.getEventButtonState())
			{
				if(Mouse.getEventButton() == 0)
						this.leftButtonDown = true;
				else if (Mouse.getEventButton() == 1)
					this.rightButtonDown = true;
			}
			else if(this.leftButtonDown || this.rightButtonDown)
			{
				if(Mouse.getEventButton() == 0)
				{
					boolean tmp = this.isExpanded;
					this.parentFrame.unexpandAllCategoryFilter();
					this.isExpanded = !tmp;
					if(tmp)
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
		}
		else if(!Mouse.getEventButtonState())
		{
			if(Mouse.getEventButton() == 0)
				this.leftButtonDown = false;	
			else if (Mouse.getEventButton() == 1)
				this.rightButtonDown = false;
		}
		if(!this.isExpanded)
			return (false);
		if (!this.buttonHover)
		{
			int i = -1;
			while(++i < this.filterList.size())
				if(this.filterList.get(i).mouseEvent())
				{
					if (this.parentFrame.getSelectedFilter() != AuctionHouseFilter.NONE)
						this.isExpanded = true;
					return (true);
				}
		}
		return (false);
	}
	
	public void updateSize()
	{
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
		int i = -1;
		while (++i < this.filterList.size())
			this.filterList.get(i).updateSize();
	}
	
	@Override
	public void setWidth(float width)
	{
		this.width = (short)(width * Mideas.getDisplayXFactor());
		this.widthSave = (short)width;
		int i = -1;
		while (++i < this.filterList.size())
			this.filterList.get(i).setWidth(width);
	}

	@Override
	public void setY(float y)
	{
		this.y = (short)(this.parentFrame.getY() + y * Mideas.getDisplayYFactor());
		this.ySave = (short)y;
		this.isVisible = this.y + this.filterList.size() * AuctionHouseBrowseFrame.CATEGORY_FILTER_BUTTON_Y_SHIFT >= this.parentFrame.getY() + AuctionHouseBrowseFrame.CATEGORY_FILTER_MIN_Y * Mideas.getDisplayYFactor() && this.y <= this.parentFrame.getY() + AuctionHouseBrowseFrame.CATEGORY_FILTER_MAX_Y * Mideas.getDisplayYFactor();
		int i = -1;
		while (++i < this.filterList.size())
			this.filterList.get(i).setY((short)(y + (i + 1) * AuctionHouseBrowseFrame.CATEGORY_FILTER_BUTTON_Y_SHIFT));
	}
	
	public void addFilter(AuctionHouseFilter filter)
	{
		this.filterList.add(new AuctionHouseBrowseSubcategoryFilterButton(this.parentFrame, filter, this.name + "Subcategory" + this.filterList.size() + 1, this.xSave, (short)0));
		this.filterList.get(this.filterList.size() - 1).setWidth(this.widthSave);
		this.filterList.get(this.filterList.size() - 1).setHeight(this.heightSave);
	}
	
	public boolean isExpanded()
	{
		return (this.isExpanded);
	}
	
	public int getNumberLine()
	{
		return (this.isExpanded ? this.filterList.size() + 1 : 1);
	}
	
	public void unexpand()
	{
		this.isExpanded = false;
	}
}
