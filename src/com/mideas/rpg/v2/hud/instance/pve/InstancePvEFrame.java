package com.mideas.rpg.v2.hud.instance.pve;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.hud.instance.InstanceFrame;
import com.mideas.rpg.v2.hud.instance.InstanceCategoryButton;
import com.mideas.rpg.v2.utils.Frame;

public class InstancePvEFrame extends Frame
{

	private final InstanceFrame parentFrame;
	private InstanceCategoryButton selectedMenu;
	private short xSave;
	private short ySave;
	
	public InstancePvEFrame(InstanceFrame frame)
	{
		super("PremadeGroupPvEFrame");
		this.parentFrame = frame;
		this.xSave = this.parentFrame.getX();
		this.ySave = this.parentFrame.getY();
		this.x = (short)(this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.ySave * Mideas.getDisplayYFactor());
	}
	
	@Override
	public void draw()
	{
		
	}
	
	@Override
	public boolean mouseEvent()
	{
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
		
	}
	
	@Override
	public void shouldUpdateSize()
	{
		
	}
	
	@Override
	public void updateSize()
	{
		
	}
}
