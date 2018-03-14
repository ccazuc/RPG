package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.Texture;

public class EditBoxCrossButton extends UIElement
{

	private short width;
	private short height;
	private Texture texture;
	private boolean buttonDown;
	private boolean buttonHover;
	private boolean isEnable = true;
	private short xSave;
	private short ySave;

	public EditBoxCrossButton(String name, float x, float y)
	{
		super(name, UIElementType.INPUT_BOX_CROSS_BUTTON);
		this.x = (short)x;
		this.y = (short)y;
		this.width = (short)(Sprites.edit_box_cross_button.getImageWidth()*Mideas.getDisplayXFactor());
		this.height = (short)(Sprites.edit_box_cross_button.getImageHeight()*Mideas.getDisplayYFactor());
		this.texture = Sprites.edit_box_cross_button;
	}
	public EditBoxCrossButton(Frame parentFrame, float x, float y, String name) {
		super(name, UIElementType.INPUT_BOX_CROSS_BUTTON);
		this.parentFrame = parentFrame;
		this.xSave = (short)x;
		this.ySave = (short)y;
		this.width = (short)(Sprites.edit_box_cross_button.getImageWidth()*Mideas.getDisplayXFactor());
		this.height = (short)(Sprites.edit_box_cross_button.getImageHeight()*Mideas.getDisplayYFactor());
		this.texture = Sprites.edit_box_cross_button;
	}
	
	public void initParentFrame(Frame parentFrame)
	{
		this.parentFrame = parentFrame;
		updateSize();
	}
	
	@Override
	public void draw() {
		if(!this.isEnable) {
			return;
		}
		if(!activateCondition()) {
			return;
		}
		if(this.buttonDown) {
			Draw.drawQuad(this.texture, this.x+1, this.y+1, this.width, this.height);
		}
		else {
			Draw.drawQuad(this.texture, this.x, this.y, this.width, this.height);
		}
	}
	
	@Override
	public boolean mouseEvent() {
		if(!activateCondition()) {
			this.buttonHover = false;
			return false;
		}
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.width && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.height) {
			this.buttonHover = true;
			Mideas.setHover(false);
		}
		else {
			this.buttonHover = false;
		}
		if(this.buttonHover) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = true;
				}
			}
			else if(this.buttonDown) {
				if(Mouse.getEventButton() == 0) {
					this.buttonDown = false;
					this.buttonHover = false;
					this.texture = Sprites.edit_box_cross_button;
					eventButtonClick();
					return true;
				}
				else if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = false;
				}
			}
		}
		else if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				this.buttonDown = false;
			}
		}
		if(this.buttonHover) {
			this.texture = Sprites.edit_box_cross_button_hover;
		}
		else {
			this.texture = Sprites.edit_box_cross_button;
		}
		return false;
	}
	
	protected void eventButtonClick() {}
	
	protected boolean activateCondition() {return true;}
	
	protected void popupClosed() {}
	
	public boolean getButtonDown() {
		return this.buttonDown;
	}
	public boolean isHover() {
		return this.buttonHover;
	}
	
	public void updateSize()
	{
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
		this.width = (short)(Sprites.edit_box_cross_button.getImageWidth()*Mideas.getDisplayXFactor());
		this.height = (short)(Sprites.edit_box_cross_button.getImageHeight()*Mideas.getDisplayYFactor());
	}
	
	public void update(float x, float y) {
		this.x = (short)x;
		this.y = (short)y;
		this.width = (short)(Sprites.edit_box_cross_button.getImageWidth()*Mideas.getDisplayXFactor());
		this.height = (short)(Sprites.edit_box_cross_button.getImageHeight()*Mideas.getDisplayYFactor());
	}
}
