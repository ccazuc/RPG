package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;

public class ScrollBar {

	/*private final float Y_ASCENSOR_DOWN_SHIFT = -22;
	private final float Y_ASCENSOR_UP_SHIFT = 22;
	private float x;
	private float y;
	private float y_size;
	private float x_frame_size;
	private float y_frame_size;
	private float scroll_tick_size;
	private float y_ascensor = this.Y_ASCENSOR_UP_SHIFT;
	private float y_ascensor_onclick = this.Y_ASCENSOR_UP_SHIFT;
	private float y_ascensor_lastclick = this.Y_ASCENSOR_UP_SHIFT;
	private int mouseWheel;
	private boolean down;
	private boolean dragAndScroll;
	
	public ScrollBar(float x, float y, float y_size, float x_frame_size, float y_frame_size, boolean dragAndScroll, float scroll_tick_size) {
		this.x = x;
		this.y = y;
		this.y_size = y_size;
		this.x_frame_size = x_frame_size;
		this.y_frame_size = y_frame_size;
		this.dragAndScroll = dragAndScroll;
		this.scroll_tick_size = scroll_tick_size;
	}
	
	public void draw() {
		Draw.drawQuad(Sprites.scrollbar, this.x-2, this.y+Sprites.top_button.getImageHeight()*Mideas.getDisplayXFactor(), Sprites.scrollbar.getImageWidth()*Mideas.getDisplayXFactor(), this.y_size+17-Sprites.top_button.getImageHeight()*Mideas.getDisplayXFactor()-Sprites.bot_button.getImageHeight()*Mideas.getDisplayXFactor());
		Draw.drawQuad(Sprites.top_button, this.x-2, this.y);
		Draw.drawQuad(Sprites.bot_button, this.x-2, this.y+19+this.y_size-Sprites.top_button.getImageHeight()*Mideas.getDisplayXFactor());
		drawUpArrow();
		drawDownArrow();
		Draw.drawQuad(Sprites.ascensor, this.x+5, this.y+this.y_ascensor);
	}
	
	public float getX() {
		return this.x;
	}
	
	public void event() {
		this.mouseWheel = Mouse.getDWheel()/12;
		mouseDragEvent();
		if(this.down) {
			if(this.dragAndScroll) {
				mouseScroll();
				return;
			}
			this.mouseWheel = 0;
		}
		else {
			mouseScroll();
		}
	}
	
	private void mouseDragEvent() {
		if(Mideas.mouseX() >= this.x+4 && Mideas.mouseX() <= this.x+24) {
			if(Mideas.mouseY() >= this.y+this.y_ascensor && Mideas.mouseY() <= this.y+this.y_ascensor+16) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					if(Mouse.getEventButtonState()) {
						this.down = true;
						this.y_ascensor_onclick = Mideas.mouseY();
					}
				}
			}
		}
		if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
			if(!Mouse.getEventButtonState()) {
				this.down = false;
				this.y_ascensor_lastclick = this.y_ascensor;
			}
		}
		if(this.down) {
			if(Mideas.mouseY() >= this.y+this.Y_ASCENSOR_UP_SHIFT+11 && Mideas.mouseY() <= this.y+this.y_size+7+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayXFactor()) {
				this.y_ascensor = Mideas.mouseY()-this.y_ascensor_onclick+this.y_ascensor_lastclick;
			}
			else if(Mideas.mouseY() <= this.y+this.Y_ASCENSOR_UP_SHIFT+11) {
				this.y_ascensor = this.Y_ASCENSOR_UP_SHIFT;
			}
			else if(Mideas.mouseY() >= this.y+this.y_size+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayXFactor()) {
				this.y_ascensor = this.y_size+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayXFactor();
			}
		}
	}
	
	private void mouseScroll() {
		if(Mideas.mouseX() >= this.x-this.x_frame_size+25 && Mideas.mouseX() <= this.x+25 && Mideas.mouseY() >= this.y-25 && Mideas.mouseY() <= this.y+this.y_frame_size+25) {
			if(this.mouseWheel != 0 && this.y_ascensor-this.mouseWheel >= 17 && this.y_ascensor-this.mouseWheel < this.y_size-15) {
				this.y_ascensor-= this.mouseWheel;
				this.y_ascensor_lastclick = this.y_ascensor;
			}
			else if(this.mouseWheel != 0 && this.y_ascensor-this.mouseWheel < 17 && this.y_ascensor-this.mouseWheel <= this.y_size) {
				this.y_ascensor = this.Y_ASCENSOR_UP_SHIFT;
				this.y_ascensor_lastclick = this.y_ascensor;
			}
			else if(this.mouseWheel != 0 && this.y_ascensor-this.mouseWheel >= this.y_size-15 && this.y_ascensor-this.mouseWheel >= 17) {
				this.y_ascensor = this.y_size+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayXFactor();
				this.y_ascensor_lastclick = this.y_ascensor;
			}
		}
	}
	
	private void drawUpArrow() {
		if(this.y_ascensor == this.Y_ASCENSOR_UP_SHIFT) {
			Draw.drawQuad(Sprites.scrollbar_grey_up_arrow, this.x+5, this.y+5);
		}
		else {
			Draw.drawQuad(Sprites.scrollbar_up_arrow, this.x+5, this.y+5);
		}
	}
	
	private void drawDownArrow() {
		if(this.y_ascensor == this.y_size+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayXFactor()) {
			Draw.drawQuad(Sprites.scrollbar_grey_down_arrow, this.x+5, this.y+this.y_size);
		}
		else {
			Draw.drawQuad(Sprites.scrollbar_down_arrow, this.x+5, this.y+this.y_size);
		}
	}
	
	public float getScrollPercentage() {
		if(this.y_ascensor == this.Y_ASCENSOR_UP_SHIFT) {
			return 0;
		}
		if(this.y_ascensor == this.y_size+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayXFactor()) {
			return 1;
		}
		return (this.y_ascensor)/(this.y_size-Sprites.ascensor.getImageHeight());
	}
	
	public void update(float x, float y, float y_size) {
		this.x = x;
		this.y = y;
		this.y_size = y_size;
	}*/
	
	private float Y_ASCENSOR_DOWN_SHIFT = -22;
	private final float Y_ASCENSOR_UP_SHIFT = 0;
	private int x;
	private int y;
	private int scrollBarHeight;
	private int frameWidth;
	private int frameHeight;
	private int scroll_tick_size;
	private float y_ascensor = this.Y_ASCENSOR_UP_SHIFT;
	private float y_ascensor_onclick = this.Y_ASCENSOR_UP_SHIFT;
	private float y_ascensor_lastclick = this.Y_ASCENSOR_UP_SHIFT;
	private int mouseWheel;
	private boolean down;
	private boolean dragAndScroll;
	private boolean buttonDownTopArrow;
	private boolean buttonHoverTopArrow;
	private boolean buttonDownBotArrow;
	private boolean buttonHoverBotArrow;
	private boolean scrollbar_anchor_visible = true;
	private Frame parentFrame;
	private short xSave;
	private short ySave;
	private short scrollBarHeightSave;
	private short frameWidthSave;
	private short frameHeightSave;
	private short scrollTickSizeSave;
	private boolean isEnabled = true;
	
	public ScrollBar(float x, float y, float y_size, float x_frame_size, float y_frame_size, boolean dragAndScroll, float scroll_tick_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.scrollBarHeight = (int)y_size;
		this.frameWidth = (int)x_frame_size;
		this.frameHeight = (int)y_frame_size;
		this.dragAndScroll = dragAndScroll;
		this.scroll_tick_size = (int)scroll_tick_size;
		this.Y_ASCENSOR_DOWN_SHIFT = this.scrollBarHeight-45*Mideas.getDisplayYFactor();
	}
	public ScrollBar(Frame parentFrame, int x, int y, int scrollBarHeight, int frameWidth, int frameHeight, boolean dragAndScroll, float scroll_tick_size, boolean isEnabled)
	{
		this.parentFrame = parentFrame;
		this.xSave = (short)x;
		this.ySave = (short)y;
		this.scrollBarHeightSave = (short)scrollBarHeight;
		this.frameWidthSave = (short)frameWidth;
		this.frameHeightSave = (short)frameHeight;
		this.dragAndScroll = dragAndScroll;
		this.scrollTickSizeSave = (short)scroll_tick_size;
		this.Y_ASCENSOR_DOWN_SHIFT = this.scrollBarHeight-45*Mideas.getDisplayYFactor();
		this.isEnabled = isEnabled;
	}
	
	public ScrollBar(float x, float y, float y_size, float x_frame_size, float y_frame_size, boolean dragAndScroll, float scroll_tick_size, boolean scrollbar_anchor_visible) {
		this.x = (int)x;
		this.y = (int)y;
		this.scrollBarHeight = (int)y_size;
		this.frameWidth = (int)x_frame_size;
		this.frameHeight = (int)y_frame_size;
		this.dragAndScroll = dragAndScroll;
		this.scroll_tick_size = (int)scroll_tick_size;
		this.Y_ASCENSOR_DOWN_SHIFT = this.scrollBarHeight-45*Mideas.getDisplayYFactor();
		this.scrollbar_anchor_visible = scrollbar_anchor_visible;
	}
	
	public void initParentFrame(Frame parentFrame)
	{
		this.parentFrame = parentFrame;
		updateSize();
	}
	
	public void draw() {
		if (!this.isEnabled)
			return;
		if(this.scrollbar_anchor_visible)
			Draw.drawQuad(Sprites.scrollbar, this.x, this.y+Sprites.top_button.getImageHeight()*Mideas.getDisplayYFactor(), Sprites.scrollbar.getImageWidth()*Mideas.getDisplayYFactor(), this.scrollBarHeight+19-Sprites.top_button.getImageHeight()*Mideas.getDisplayYFactor()-Sprites.bot_button.getImageHeight()*Mideas.getDisplayYFactor());
		Draw.drawQuad(Sprites.top_button, this.x-3*Mideas.getDisplayXFactor(), this.y);
		Draw.drawQuad(Sprites.bot_button, this.x-3*Mideas.getDisplayXFactor(), this.y+19+this.scrollBarHeight-Sprites.top_button.getImageHeight()*Mideas.getDisplayYFactor());
		drawUpArrow();
		drawDownArrow();
		Draw.drawQuad(Sprites.ascensor, this.x+3, this.y+this.y_ascensor+22*Mideas.getDisplayYFactor());
	}
	
	public void resetScroll() {
		setYAscensor(0);
	}
	
	public float getX() {
		return this.x;
	}
	
	public boolean event() {
		if (!this.isEnabled)
			return (false);
		this.mouseWheel = Mideas.getMouseScrolledTick()/12;
		if(mouseDragEvent()) return true;
		if(topArrowEvent()) return true;
		if(botArrowEvent()) return true;
		if(this.down) {
			if(this.dragAndScroll) {
				mouseScroll();
				return true;
			}
			this.mouseWheel = 0;
		}
		else {
			mouseScroll();
		}
		return false;
	}
	
	private boolean mouseDragEvent() {
		if(Mideas.mouseX() >= this.x+4 && Mideas.mouseX() <= this.x+24) {
			if(Mideas.getHover() && Mideas.mouseY() >= this.y+this.y_ascensor+22*Mideas.getDisplayYFactor() && Mideas.mouseY() <= this.y+this.y_ascensor+22*Mideas.getDisplayYFactor()+Sprites.ascensor.getImageHeight()*Mideas.getDisplayYFactor()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					if(Mouse.getEventButtonState()) {
						this.down = true;
						this.y_ascensor_onclick = Mideas.mouseY();
						return true;
					}
				}
				Mideas.setHover(false);
			}
			else if(Mideas.getHover() && Mideas.mouseY() > this.y+22*Mideas.getDisplayYFactor() && Mideas.mouseY() <= this.y+this.scrollBarHeight) {
				if(Mouse.getEventButtonState()) {
					if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
						setYAscensor(Mideas.mouseY()-this.y-28*Mideas.getDisplayYFactor());
						this.y_ascensor_lastclick = this.y_ascensor;
						if(this.y_ascensor < this.Y_ASCENSOR_UP_SHIFT) {
							setYAscensor(this.Y_ASCENSOR_UP_SHIFT);
							this.buttonHoverTopArrow = false;
						}
						else if(this.y_ascensor > this.Y_ASCENSOR_DOWN_SHIFT) {
							setYAscensor(this.Y_ASCENSOR_DOWN_SHIFT);
							this.buttonHoverBotArrow = false;
						}
						return true;
					}
				}
				Mideas.setHover(false);
			}
		}
		if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
			if(!Mouse.getEventButtonState()) {
				this.down = false;
				this.y_ascensor_lastclick = this.y_ascensor;
			}
		}
		if(this.down) {
			this.y_ascensor = Mideas.mouseY()-this.y_ascensor_onclick+this.y_ascensor_lastclick;
			if(this.y_ascensor < this.Y_ASCENSOR_UP_SHIFT) {
				setYAscensor(this.Y_ASCENSOR_UP_SHIFT);
				this.buttonHoverTopArrow = false;
			}
			else if(this.y_ascensor > this.Y_ASCENSOR_DOWN_SHIFT) {
				setYAscensor(this.Y_ASCENSOR_DOWN_SHIFT);
				this.buttonHoverBotArrow = false;
			}
		}
		return false;
	}
	
	private void mouseScroll() {
		if(Mideas.mouseX() >= this.x-this.frameWidth+25 && Mideas.mouseX() <= this.x+25 && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.frameHeight) {
			if(this.mouseWheel != 0 ) {
				if(this.mouseWheel > 0 && this.y_ascensor-this.scroll_tick_size > this.Y_ASCENSOR_UP_SHIFT) {
					setYAscensor(this.y_ascensor-this.scroll_tick_size);
				}
				else if(this.mouseWheel > 0 && this.y_ascensor-this.scroll_tick_size <= this.Y_ASCENSOR_UP_SHIFT) {
					setYAscensor(this.Y_ASCENSOR_UP_SHIFT);
					this.buttonHoverTopArrow = false;
				}
				else if(this.mouseWheel < 0 && this.y_ascensor+this.scroll_tick_size < this.Y_ASCENSOR_DOWN_SHIFT) {
					setYAscensor(this.y_ascensor+this.scroll_tick_size);
				}
				else if(this.mouseWheel < 0 && this.y_ascensor+this.scroll_tick_size >= this.Y_ASCENSOR_DOWN_SHIFT) {
					setYAscensor(this.Y_ASCENSOR_DOWN_SHIFT);
					this.buttonHoverBotArrow = false;
				}
				this.y_ascensor_lastclick = this.y_ascensor;
				this.mouseWheel = 0;
			}
		}
	}
	
	private boolean topArrowEvent() {
		if(this.y_ascensor == this.Y_ASCENSOR_UP_SHIFT) {
			return false;
		}
		if(Mideas.mouseX() >= this.x+4*Mideas.getDisplayXFactor() && Mideas.mouseX() <= this.x+4*Mideas.getDisplayXFactor()+Sprites.scrollbar_up_arrow.getImageWidth()*Mideas.getDisplayXFactor() && Mideas.mouseY() >= this.y+2*Mideas.getDisplayYFactor() && Mideas.mouseY() <= this.y+2*Mideas.getDisplayYFactor()+Sprites.scrollbar_down_arrow.getImageHeight()*Mideas.getDisplayYFactor()) {
			this.buttonHoverTopArrow = true;
		}
		else {
			this.buttonHoverTopArrow = false;
		}
		if(this.buttonHoverTopArrow) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDownTopArrow = true;
				}
			}
			else if(this.buttonDownTopArrow) {
				if(Mouse.getEventButton() == 0) {
					this.buttonDownTopArrow = false;
					if(this.y_ascensor-this.scroll_tick_size > this.Y_ASCENSOR_UP_SHIFT) {
						setYAscensor(this.y_ascensor-this.scroll_tick_size);
					}
					else if(this.y_ascensor-this.scroll_tick_size <= this.Y_ASCENSOR_UP_SHIFT) {
						setYAscensor(this.Y_ASCENSOR_UP_SHIFT);
						this.buttonHoverTopArrow = false;
					}
					this.y_ascensor_lastclick = this.y_ascensor;
					return true;
				}
				else if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDownTopArrow = false;
				}
			}
		}
		else if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				this.buttonDownTopArrow = false;
			}
		}
		return false;
	}

	
	private boolean botArrowEvent() {
		if(this.y_ascensor != this.Y_ASCENSOR_DOWN_SHIFT) {
			this.buttonHoverBotArrow = false;
			if(Mideas.mouseX() >= this.x+4*Mideas.getDisplayXFactor() && Mideas.mouseX() <= this.x+4*Mideas.getDisplayXFactor()+Sprites.scrollbar_up_arrow.getImageWidth()*Mideas.getDisplayXFactor() && Mideas.mouseY() >= this.y+this.scrollBarHeight+2*Mideas.getDisplayYFactor() && Mideas.mouseY() <= this.y+this.scrollBarHeight+2*Mideas.getDisplayYFactor()+Sprites.scrollbar_down_arrow.getImageHeight()*Mideas.getDisplayYFactor()) {
				this.buttonHoverBotArrow = true;
			}
			if(this.buttonHoverBotArrow) {
				if(Mouse.getEventButtonState()) {
					if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
						this.buttonDownBotArrow = true;
						return true;
					}
				}
				else if(this.buttonDownBotArrow) {
					if(Mouse.getEventButton() == 0) {
						this.buttonDownBotArrow = false;
						if(this.y_ascensor+this.scroll_tick_size < this.Y_ASCENSOR_DOWN_SHIFT) {
							setYAscensor(this.y_ascensor+this.scroll_tick_size);
						}
						else if(this.y_ascensor+this.scroll_tick_size >= this.Y_ASCENSOR_DOWN_SHIFT) {
							setYAscensor(this.Y_ASCENSOR_DOWN_SHIFT);
							this.buttonHoverBotArrow = false;
						}
						this.y_ascensor_lastclick = this.y_ascensor;
						return true;
					}
					else if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
						this.buttonDownBotArrow = false;
					}
				}
			}
			else if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					this.buttonDownBotArrow = false;
				}
			}
		}
		return false;
	}
	
	private void drawUpArrow() {
		if(this.y_ascensor == this.Y_ASCENSOR_UP_SHIFT) {
			Draw.drawQuad(Sprites.scrollbar_grey_up_arrow, this.x+4*Mideas.getDisplayXFactor(), this.y+2*Mideas.getDisplayYFactor());
		}
		else if(this.buttonDownTopArrow) {
			Draw.drawQuad(Sprites.scrollbar_up_arrow_down, this.x+4*Mideas.getDisplayXFactor(), this.y+2*Mideas.getDisplayYFactor());
		}
		else {
			Draw.drawQuad(Sprites.scrollbar_up_arrow, this.x+4*Mideas.getDisplayXFactor(), this.y+2*Mideas.getDisplayYFactor());
		}
		if(this.buttonHoverTopArrow) {
			Draw.drawQuadBlend(Sprites.scrollbar_arrow_hover, this.x+4*Mideas.getDisplayXFactor(), this.y+4*Mideas.getDisplayYFactor());
		}
	}
	
	private void drawDownArrow() {
		if(this.y_ascensor == this.Y_ASCENSOR_DOWN_SHIFT) {
			Draw.drawQuad(Sprites.scrollbar_grey_down_arrow, this.x+4*Mideas.getDisplayXFactor(), this.y+this.scrollBarHeight+1*Mideas.getDisplayYFactor());
		}
		else if(this.buttonDownBotArrow) {
			Draw.drawQuad(Sprites.scrollbar_down_arrow_down, this.x+4*Mideas.getDisplayXFactor(), this.y+this.scrollBarHeight+1*Mideas.getDisplayYFactor());
		}
		else {
			Draw.drawQuad(Sprites.scrollbar_down_arrow, this.x+4*Mideas.getDisplayXFactor(), this.y+this.scrollBarHeight+1*Mideas.getDisplayYFactor());
		}
		if(this.buttonHoverBotArrow) {
			Draw.drawQuadBlend(Sprites.scrollbar_arrow_hover, this.x+4*Mideas.getDisplayXFactor(), this.y+this.scrollBarHeight);
		}
	}
	
	private void setYAscensor(float value) {
		this.y_ascensor = value;
		onScroll();
	}
	
	public float getScrollPercentage() {
		/*if(this.y_ascensor == this.Y_ASCENSOR_UP_SHIFT) {
			System.out.println("y_ascensor: 0");
			return 0;
		}
		if(this.y_ascensor == this.y_size+this.Y_ASCENSOR_DOWN_SHIFT*Mideas.getDisplayYFactor()) {
			System.out.println("y_ascensor: 1");
			return 1;
		}*/
		//System.out.println("y_ascensor: "+(this.y_ascensor)/(this.y_size-45*Mideas.getDisplayYFactor()));
		//return (this.y_ascensor)/(this.y_size-2*Sprites.ascensor.getImageHeight()*Mideas.getDisplayYFactor());
		return this.y_ascensor/this.Y_ASCENSOR_DOWN_SHIFT;
	}
	
	public void update(float x, float y, float y_size, float scroll_tick_size) {
		this.x = (int)x;
		this.y = (int)y;
		boolean resize = this.y_ascensor == this.Y_ASCENSOR_DOWN_SHIFT;
		this.scrollBarHeight = (int)y_size;
		this.scroll_tick_size = (int)scroll_tick_size;
		this.Y_ASCENSOR_DOWN_SHIFT = this.scrollBarHeight-45*Mideas.getDisplayYFactor();
		if(resize) {
			this.y_ascensor = this.Y_ASCENSOR_DOWN_SHIFT;
		}
	}
	
	public void updateSize()
	{
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
		this.scroll_tick_size = (int)(this.scrollTickSizeSave * Mideas.getDisplayYFactor());
		this.scrollBarHeight = (short)(this.scrollBarHeightSave * Mideas.getDisplayYFactor());
		this.frameWidth = (int)(this.frameWidthSave * Mideas.getDisplayXFactor());
		this.frameHeight = (int)(this.frameHeightSave * Mideas.getDisplayYFactor());
		this.Y_ASCENSOR_DOWN_SHIFT = this.scrollBarHeight-45*Mideas.getDisplayYFactor();
		if(this.y_ascensor == this.Y_ASCENSOR_DOWN_SHIFT)
			this.y_ascensor = this.Y_ASCENSOR_DOWN_SHIFT;
	}
	
	public void update(float x, float y, float y_size, float x_frame_size, float y_frame_size, float scroll_tick_size) {
		this.x = (int)x;
		this.y = (int)y;
		boolean resize = this.y_ascensor == this.Y_ASCENSOR_DOWN_SHIFT;
		this.scrollBarHeight = (int)y_size;
		this.frameWidth = (int)x_frame_size;
		this.frameHeight = (int)y_frame_size;
		this.scroll_tick_size = (int)scroll_tick_size;
		this.Y_ASCENSOR_DOWN_SHIFT = this.scrollBarHeight-45*Mideas.getDisplayYFactor();
		if(resize) {
			this.y_ascensor = this.Y_ASCENSOR_DOWN_SHIFT;
		}
	}
	
	public void enable()
	{
		this.isEnabled = true;
	}
	
	public void disable()
	{
		this.isEnabled = false;
	}
	
	public void onScroll() {}
}
