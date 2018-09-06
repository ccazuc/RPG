package com.mideas.rpg.v2.hud.instance.pvp;

import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.hud.instance.InstanceFrame;
import com.mideas.rpg.v2.hud.instance.InstanceCategoryButton;
import com.mideas.rpg.v2.utils.Frame;

public class PremadeGroupPvPFrame extends Frame
{

	private final InstanceFrame parentFrame;
	private final ArrayList<InstanceCategoryButton> buttonList;
	private InstanceCategoryButton selectedMenu;
	
	public PremadeGroupPvPFrame(InstanceFrame frame)
	{
		super("PremadeGroupPvPFrame");
		this.parentFrame = frame;
		this.buttonList = new ArrayList<InstanceCategoryButton>();
		/*this.buttonList.add(new PremadeGroupCategoryButton(this.parentFrame, null, "Quick Match", PremadeGroupCategoryButton.BUTTON_X_OFFSET, PremadeGroupCategoryButton.BUTTON_Y_OFFSET));
		this.buttonList.add(new PremadeGroupCategoryButton(this.parentFrame, null, "Rated", PremadeGroupCategoryButton.BUTTON_X_OFFSET, (short)(PremadeGroupCategoryButton.BUTTON_Y_OFFSET + PremadeGroupCategoryButton.BUTTON_HEIGHT)));
		this.buttonList.add(new PremadeGroupCategoryButton(this.parentFrame, null, "Premade Groups", PremadeGroupCategoryButton.BUTTON_X_OFFSET, (short)(PremadeGroupCategoryButton.BUTTON_Y_OFFSET + 2 * PremadeGroupCategoryButton.BUTTON_HEIGHT)));*/
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
