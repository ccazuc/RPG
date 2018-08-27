package com.mideas.rpg.v2.hud.instance;

import com.mideas.rpg.v2.render.Texture;
import com.mideas.rpg.v2.utils.Frame;

public abstract class InstanceCategoryFrame extends Frame
{
	
	protected final InstanceCategoryButton button;
	protected final Texture texture;
	
	public InstanceCategoryFrame(String name, InstanceCategoryButton button, Texture texture)
	{
		super(name);
		this.button = button;
		this.texture = texture;
	}
	
	@Override
	public boolean isOpen()
	{
		return (this.button.isSelected());
	}
}
