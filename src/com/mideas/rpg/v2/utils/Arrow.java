package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.Texture;

public class Arrow {


	private short x;
	private short y;
	private short x_size;
	private short y_size;
	private short x_size_save;
	private short y_size_save;
	private Texture texture;
	private Texture hoverTexture = Sprites.arrow_hover;
	private Texture downTexture;
	private Texture defaultTexture;
	private Texture disabledTexture;
	private boolean leftClickDown;
	private boolean rightClickDown;
	private boolean buttonHover;
	private boolean hasClicked;
	private boolean lastActivateCondition;
	
	public Arrow(float x, float y, float x_size, float y_size, ArrowDirection direction)
	{
		initSprite(direction);
		this.x = (short)x;
		this.y = (short)y;
		this.x_size_save = (short)x_size;
		this.x_size = (short)(x_size*Mideas.getDisplayXFactor());
		this.y_size_save = (short)y_size;
		this.y_size = (short)(y_size*Mideas.getDisplayYFactor());
	}

	public Arrow(float x, float y, ArrowDirection direction)
	{
		this(x, y, Sprites.arrow_top.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.arrow_top.getImageHeight()*Mideas.getDisplayYFactor(), direction);
	}
	
	public void draw()
	{
		if(!activateCondition())
		{
			Draw.drawQuad(this.disabledTexture, this.x, this.y, this.x_size, this.y_size);
			return;
		}
		if (!this.lastActivateCondition)
		{
			event();
			this.lastActivateCondition = true;
		}
		Draw.drawQuad(this.texture, this.x, this.y, this.x_size, this.y_size);
		if(this.buttonHover)
			Draw.drawQuadBlend(this.hoverTexture, this.x, this.y, this.x_size, this.y_size);
	}
	
	public boolean event()
	{
		if(!activateCondition())
		{
			this.lastActivateCondition = false;
			return false;
		}
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.y_size)
		{
			this.buttonHover = true;
			Mideas.setHover(false);
		}
		else
			this.buttonHover = false;
		if(this.buttonHover)
		{
			if(Mouse.getEventButtonState()) 
			{
				if(Mouse.getEventButton() == 0)
				{
					onLeftClickDown();
					this.leftClickDown = true;
				}
				else if (Mouse.getEventButton() == 1)
				{
					onRightClickDown();
					this.rightClickDown = true;
				}
			}
			else if(this.leftClickDown && Mouse.getEventButton() == 0)
			{
				this.leftClickDown = false;
				onLeftClickUp();
				this.hasClicked = true;
				this.texture = this.defaultTexture;
				if (!activateCondition())
					this.buttonHover = false;
				return (true);
			}
			else if(this.rightClickDown && Mouse.getEventButton() == 1)
			{
				this.rightClickDown = false;
				this.texture = this.defaultTexture;
			}
		}
		else if(!Mouse.getEventButtonState())
		{
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1)
			{
				this.leftClickDown = false;
				this.rightClickDown = false;
				this.hasClicked = false;
				this.texture = this.defaultTexture;
			}
		}
		if(this.leftClickDown || this.rightClickDown)
			this.texture = this.downTexture;
		return false;
	}
	
	public void setX(float x)
	{
		this.x = (short)x;
	}
	
	public void setY(float y)
	{
		this.y = (short)y;
	}
	
	public void setHoverFalse()
	{
		this.buttonHover = false;
	}
	
	public void setButtonWidth(float width)
	{
		this.x_size = (short)width;
		this.x_size_save = (short)width;
	}
	
	public void setButtonHeight(float height)
	{
		this.y_size = (short)height;
		this.y_size_save = (short)height;
	}
	
	protected void onLeftClickUp() {}
	protected void onLeftClickDown() {}
	protected void onRightClickUp() {}
	protected void onRightClickDown() {}
	protected boolean activateCondition() {return true;}
	protected boolean hoverSpriteActivateCondition() {return false;}
	
	public boolean getLeftClickDown()
	{
		return this.leftClickDown;
	}
	
	public boolean hasClicked()
	{
		return this.hasClicked;
	}
	
	public boolean isHover()
	{
		return this.buttonHover;
	}
	
	private void initSprite(ArrowDirection direction)
	{
		if(direction == ArrowDirection.TOP) {
			this.defaultTexture = Sprites.arrow_top;
			this.downTexture = Sprites.arrow_top_down;
		}
		else if(direction == ArrowDirection.BOT) {
			this.defaultTexture = Sprites.arrow_bot;
			this.downTexture = Sprites.arrow_bot_down;
		}
		else if(direction == ArrowDirection.LEFT) {
			this.defaultTexture = Sprites.arrow_left;
			this.downTexture = Sprites.arrow_left_down;
			this.disabledTexture = Sprites.arrow_left_disabled;
		}
		else if(direction == ArrowDirection.RIGHT) {
			this.defaultTexture = Sprites.arrow_right;
			this.downTexture = Sprites.arrow_right_down;
			this.disabledTexture = Sprites.arrow_right_disabled;
		}
		this.texture = this.defaultTexture;
	}
	
	public void reset()
	{
		this.leftClickDown = false;
		this.rightClickDown = false;
		this.buttonHover = false;
		this.hasClicked = false;
		this.texture = this.defaultTexture;
	}
	
	public void update(float x, float y)
	{
		this.x = (short)x;
		this.y = (short)y;
		this.x_size = (short)(this.x_size_save*Mideas.getDisplayXFactor());
		this.y_size = (short)(this.y_size_save*Mideas.getDisplayYFactor());
	}
}
