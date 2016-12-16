package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;

public class Arrow {


	private int x;
	private int y;
	private int x_size = (int)(Sprites.arrow_top.getImageWidth()*Mideas.getDisplayXFactor());
	private int y_size = (int)(Sprites.arrow_top.getImageHeight()*Mideas.getDisplayYFactor());
	private Texture texture;
	private Texture hoverTexture = Sprites.arrow_hover;
	private Texture downTexture;
	private Texture defaultTexture;
	private boolean buttonDown;
	private boolean buttonHover;
	private boolean hasClicked;
	
	public Arrow(float x, float y, float x_size, float y_size, ArrowDirection direction) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		initSprite(direction);
	}

	public Arrow(float x, float y, ArrowDirection direction) {
		this(x, y, Sprites.arrow_top.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.arrow_top.getImageHeight()*Mideas.getDisplayYFactor(), direction);
	}
	
	public void draw() {
		if(!activateCondition()) {
			//this.texture = Sprites.button_disabled;
		}
		Draw.drawQuad(this.texture, this.x, this.y, this.x_size, this.y_size);
		if(this.buttonHover) {
			Draw.drawQuadBlend(this.hoverTexture, this.x, this.y, this.x_size, this.y_size);
		}
	}
	
	public boolean event() {
		if(!activateCondition()) {
			this.texture = Sprites.button_disabled;
			return false;
		}
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.y_size) {
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
					eventButtonClick();
					this.hasClicked = true;
					this.texture = this.defaultTexture;
					return true;
				}
				else if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDown = false;
					this.texture = this.defaultTexture;
				}
			}
		}
		else if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				this.buttonDown = false;
				this.hasClicked = false;
				this.texture = this.defaultTexture;
			}
		}
		if(this.buttonDown) {
			this.texture = this.downTexture;
		}
		return false;
	}
	
	public void setX(float x) {
		this.x = (int)x;
	}
	
	public void setY(float y) {
		this.y = (int)y;
	}
	
	public void setHoverFalse() {
		this.buttonHover = false;
	}
	
	public void setButtonWidth(float width) {
		this.x_size = (int)width;
	}
	
	public void setButtonHeight(float height) {
		this.y_size = (int)(height);
	}
	
	protected void eventButtonClick() {}
	protected boolean activateCondition() {return true;}
	protected boolean hoverSpriteActivateCondition() {return false;}
	
	public boolean getButtonDown() {
		return this.buttonDown;
	}
	
	public boolean hasClicked() {
		return this.hasClicked;
	}
	
	public boolean isHover() {
		return this.buttonHover;
	}
	
	private void initSprite(ArrowDirection direction) {
		if(direction == ArrowDirection.TOP) {
			this.defaultTexture = Sprites.arrow_top;
			this.downTexture = Sprites.arrow_top_down;
			this.texture = this.defaultTexture;
		}
		else if(direction == ArrowDirection.BOT) {
			this.defaultTexture = Sprites.arrow_bot;
			this.downTexture = Sprites.arrow_bot_down;
			this.texture = this.defaultTexture;
		}
		else if(direction == ArrowDirection.LEFT) {
			
		}
		else if(direction == ArrowDirection.RIGHT) {
			
		}
	}
	
	public void reset() {
		this.buttonDown = false;
		this.buttonHover = false;
		this.hasClicked = false;
		this.texture = this.defaultTexture;
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
	}
	
	public void update(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)(Sprites.arrow_bot.getImageWidth()*Mideas.getDisplayXFactor());
		this.y_size = (int)(Sprites.arrow_bot.getImageHeight()*Mideas.getDisplayYFactor());
	}
}
