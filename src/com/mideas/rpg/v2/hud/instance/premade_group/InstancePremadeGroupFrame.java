package com.mideas.rpg.v2.hud.instance.premade_group;

import com.mideas.rpg.v2.game.preamade_group.PremadeGroupType;
import com.mideas.rpg.v2.hud.instance.InstanceCategoryButton;
import com.mideas.rpg.v2.hud.instance.InstanceCategoryFrame;
import com.mideas.rpg.v2.hud.instance.InstanceFrame;
import com.mideas.rpg.v2.utils.Frame;

public class InstancePremadeGroupFrame extends InstanceCategoryFrame
{
	private final InstancePremadeGroupBrowseCategoryFrame browseCategoryFrame;
	private Frame activeFrame;
	
	public InstancePremadeGroupFrame(InstanceFrame frame, InstanceCategoryButton button, PremadeGroupType[] type)
	{
		super("PremadeGroupPvEPremadeGroupFrame", button, null);
		this.parentFrame = frame;
		this.browseCategoryFrame = new InstancePremadeGroupBrowseCategoryFrame(frame, button, type);
		this.activeFrame = this.browseCategoryFrame;
	}
	
	@Override
	public void draw()
	{
		this.activeFrame.draw();
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (this.activeFrame.mouseEvent())
			return (true);
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		return (false);
	}
	
	@Override
	public void open()
	{
		
	}
	
	@Override
	public void close()
	{
		
	}
	
	@Override
	public boolean isOpen()
	{
		
		return (this.button.isSelected());
	}
	
	@Override
	public void reset()
	{
		
	}
	
	@Override
	public void updateSize()
	{
		
	}
}
