package com.mideas.rpg.v2.hud.instance.premade_group;

import com.mideas.rpg.v2.game.preamade_group.PremadeGroupType;
import com.mideas.rpg.v2.utils.UIElement;
import com.mideas.rpg.v2.utils.UIElementType;

public class InstancePremadeGroupTypeButton extends UIElement
{
	
	private final PremadeGroupType type;
	private final String text;
	private final InstancePremadeGroupBrowseCategoryFrame parentFrame;

	public final static short X_OFFSET = 30;
	public final static short Y_DEFAULT_OFFSET = 30;
	public final static short Y_OFFSET = 30;

	InstancePremadeGroupTypeButton(InstancePremadeGroupBrowseCategoryFrame parentFrame, String name, PremadeGroupType type)
	{
		super(name, UIElementType.BUTTON);
		this.type = type;
		this.text = type.getText();
		this.parentFrame = parentFrame;
	}
	
	@Override
	public void draw()
	{
		
	}
	
	public PremadeGroupType getType()
	{
		return (this.type);
	}
	
	@Override
	public boolean mouseEvent()
	{
		return (false);
	}
}
