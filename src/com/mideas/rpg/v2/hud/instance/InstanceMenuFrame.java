package com.mideas.rpg.v2.hud.instance;

import java.util.ArrayList;

import com.mideas.rpg.v2.utils.Frame;

public class InstanceMenuFrame extends Frame {

	private InstanceCategoryButton selectedMenu;
	private final Frame parentFrame;
	private final ArrayList<InstanceCategoryButton> buttonList;
	
	public InstanceMenuFrame(String name, Frame parentFrame)
	{
		super(name);
		this.parentFrame = parentFrame;
		this.buttonList = new ArrayList<InstanceCategoryButton>();
	}
	
	@Override
	public void draw()
	{
		for (int i = 0; i < this.buttonList.size(); ++i)
			this.buttonList.get(i).draw();
	}
	
	@Override
	public boolean mouseEvent()
	{
		for (int i = 0; i < this.buttonList.size(); ++i)
			if (this.buttonList.get(i).mouseEvent())
				return (true);
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		for (int i = 0; i < this.buttonList.size(); ++i)
			if (this.buttonList.get(i).keyboardEvent())
				return (true);
		return (false);
	}
	
	@Override
	public void open()
	{
		mouseEvent();
	}
	
	@Override
	public void close()
	{
		for (int i = 0; i < this.buttonList.size(); ++i)
			this.buttonList.get(i).resetState();
	}
	
	@Override
	public boolean isOpen()
	{
		return (false);
	}
	
	@Override
	public void reset()
	{
		
	}
	
	public InstanceCategoryButton getSelectedMenu()
	{
		return (this.selectedMenu);
	}
	
	public void setSelectedMenu(InstanceCategoryButton menu)
	{
		this.selectedMenu = menu;
	}
	
	@Override
	public void shouldUpdateSize()
	{
		for (int i = 0; i < this.buttonList.size(); ++i)
			this.buttonList.get(i).shouldUpdateSize();
	}
	
	public void addButton(InstanceCategoryButton button)
	{
		button.setX(InstanceCategoryButton.BUTTON_X_OFFSET);
		button.setY(InstanceCategoryButton.BUTTON_Y_DEFAULT_OFFSET + this.buttonList.size() * InstanceCategoryButton.BUTTON_Y_OFFSET);
		this.buttonList.add(button);
		if (this.buttonList.size() == 1)
			this.selectedMenu = button;
	}
}
