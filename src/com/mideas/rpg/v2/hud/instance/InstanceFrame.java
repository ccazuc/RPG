package com.mideas.rpg.v2.hud.instance;

import com.mideas.rpg.v2.game.preamade_group.PremadeGroupType;
import com.mideas.rpg.v2.hud.instance.premade_group.InstancePremadeGroupFrame;
import com.mideas.rpg.v2.utils.Frame;

public class InstanceFrame extends Frame
{

	private final InstanceMenuFrame pvpFrame;
	private final InstanceMenuFrame pveFrame;
	private Frame activeFrame;
	private boolean isOpen;

	public InstanceFrame()
	{
		super("InstanceFrame");
		this.pvpFrame = new InstanceMenuFrame("InstanceFramePvPFrame", this);
		this.pveFrame = new InstanceMenuFrame("InstanceFramePvEFrame", this);
		this.activeFrame = this.pveFrame;
		InstanceCategoryButton button = new InstanceCategoryButton(this.pvpFrame, "InstanceFramePvPFrameQuickMatch", (short)100);
		button.setFrame(new InstancePremadeGroupFrame(this, button, new PremadeGroupType[]{PremadeGroupType.ARENAS, PremadeGroupType.ARENAS_SKIRMISHES}));
		this.pvpFrame.addButton(button);
	}
	
	@Override
	public void draw()
	{
		if (!this.isOpen)
			return;
		this.activeFrame.draw();
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (!this.isOpen)
			return (false);
		if (this.activeFrame.mouseEvent())
			return (true);
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		if (!this.isOpen)
			return (false);
		if (this.activeFrame.keyboardEvent())
			return (true);
		return (false);
	}
	
	@Override
	public void open()
	{
		this.isOpen = true;
	}
	
	@Override
	public void close()
	{
		this.isOpen = false;
	}
	
	@Override
	public boolean isOpen()
	{
		return (this.isOpen);
	}
	
	@Override
	public void reset()
	{
		this.pvpFrame.reset();
		this.pveFrame.reset();
		this.activeFrame = this.pveFrame;
	}
	
	@Override
	public void shouldUpdateSize()
	{
		this.pvpFrame.shouldUpdateSize();
		this.pveFrame.shouldUpdateSize();
	}
	
	@Override
	public void updateSize()
	{
		
	}
}
