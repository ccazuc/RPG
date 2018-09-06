package com.mideas.rpg.v2.hud.instance.premade_group;

import java.util.ArrayList;

import com.mideas.rpg.v2.command.CommandPremadeGroup;
import com.mideas.rpg.v2.game.preamade_group.PremadeGroupType;
import com.mideas.rpg.v2.hud.instance.InstanceCategoryButton;
import com.mideas.rpg.v2.hud.instance.InstanceFrame;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Frame;

public class InstancePremadeGroupBrowseCategoryFrame extends Frame
{

	private final InstanceCategoryButton parentButton;
	private final ArrayList<InstancePremadeGroupTypeButton> premadeGroupButtonList;
	InstancePremadeGroupTypeButton selectedButton;
	private final Button startGroupButton = new Button(this, 0, 0, (short)100, (short)80, "Start a Group", 12, 1)
	{
	
		@Override
		public void onLeftClickUp()
		{
			if (InstancePremadeGroupBrowseCategoryFrame.this.selectedButton.getType() == PremadeGroupType.ARENAS)
				;
			else if (InstancePremadeGroupBrowseCategoryFrame.this.selectedButton.getType() == PremadeGroupType.ARENAS_SKIRMISHES)
				;
		}
		
		@Override
		public boolean activateCondition()
		{
			return (InstancePremadeGroupBrowseCategoryFrame.this.selectedButton != null);
		}
	};
	private final Button findGroupButton = new Button(this, 0, 0, (short)100, (short)80, "Find a Group", 12, 1)
	{
	
		@Override
		public void onLeftClickUp()
		{
			CommandPremadeGroup.fetchGroupList(InstancePremadeGroupBrowseCategoryFrame.this.selectedButton.getType());
		}
		
		@Override
		public boolean activateCondition()
		{
			return (InstancePremadeGroupBrowseCategoryFrame.this.selectedButton != null);
		}
	};
	
	public InstancePremadeGroupBrowseCategoryFrame(InstanceFrame frame, InstanceCategoryButton button, PremadeGroupType[] type)
	{
		super("PremadeGroupPvEPremadeGroupFrame");
		this.parentFrame = frame;
		this.parentButton = button;
		this.premadeGroupButtonList = new ArrayList<InstancePremadeGroupTypeButton>();
		/*for (int i = 0; i < type.length; ++i)
			this.premadeGroupButtonList.add(new InstancePremadeGroupTypeButton(this, "InstancePremadeGroupFrameButton" + i, type[i]));*/
	}
	
	@Override
	public void draw()
	{
		updateSize();
		this.startGroupButton.draw();
		this.findGroupButton.draw();
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (this.startGroupButton.event())
			return (true);
		if (this.findGroupButton.event())
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
		
		return (this.parentFrame.isOpen());
	}
	
	@Override
	public void reset()
	{
		this.selectedButton = null;
		this.startGroupButton.reset();
		this.findGroupButton.reset();
	}
	
	@Override
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.shouldUpdateSize = false;
		this.startGroupButton.updateSize();
		this.findGroupButton.updateSize();
	}
	
	public void setSelectedButton(InstancePremadeGroupTypeButton button)
	{
		this.selectedButton = button;
	}
}
