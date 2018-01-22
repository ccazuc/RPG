package com.mideas.rpg.v2.utils;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;
import com.mideas.rpg.v2.FontManager;

public class Button {

	private float x;
	private float y;
	private short width;
	private short height;
	private short widthSave;
	private short heightSave;
	private float xSave;
	private float ySave;
	private Texture texture = Sprites.button;
	private String text;
	private int textWidth;
	private int shadow_size;
	protected TTF font;
	private boolean leftClickDown;
	private boolean rightClickDown;
	private boolean buttonHover;
	private Color color = Color.YELLOW;
	private Color hoveredColor;
	private Color baseColor;
	private boolean hasClicked;
	private boolean isEnable = true;
	private Frame parentFrame;
	private final Texture baseTexture;
	private final Texture hoverTextured;
	private final Texture hoverDownTexture;
	private final Texture downTexture;
	private final Texture disabledTexture;

	public Button(float x, float y, short width, short height, String text, float font_size, int shadow_size, Color baseColor, Color hoveredColor) {
		this.font = FontManager.get("FRIZQT", font_size);
		this.textWidth = this.font.getWidth(text);
		this.hoveredColor = hoveredColor;
		this.shadow_size = shadow_size;
		this.baseColor = baseColor;
		this.width = (short)(width * Mideas.getDisplayXFactor());
		this.height = (short)(height * Mideas.getDisplayYFactor());
		this.widthSave = width;
		this.heightSave = height;
		this.text = text;
		this.x = x;
		this.y = y;
		this.baseTexture = Sprites.button;
		this.downTexture = Sprites.button_down;
		this.hoverDownTexture = Sprites.button_down_hover;
		this.disabledTexture = Sprites.button_disabled;
		this.hoverTextured = Sprites.button_hover;
	}

	public Button(float x, float y, short width, short height, String text, TTF font, int shadow_size, Color baseColor, Color hoveredColor) {
		this.font = font;
		this.textWidth = this.font.getWidth(text);
		this.hoveredColor = hoveredColor;
		this.shadow_size = shadow_size;
		this.baseColor = baseColor;
		this.width = (short)(width * Mideas.getDisplayXFactor());
		this.height = (short)(height * Mideas.getDisplayYFactor());
		this.widthSave = width;
		this.heightSave = height;
		this.text = text;
		this.x = x;
		this.y = y;
		this.baseTexture = Sprites.button;
		this.downTexture = Sprites.button_down;
		this.hoverDownTexture = Sprites.button_down_hover;
		this.disabledTexture = Sprites.button_disabled;
		this.hoverTextured = Sprites.button_hover;
	}
	
	public Button(float x, float y, short width, short height, String text, float font_size, int shadow_size) {
		this(x, y, width, height, text, font_size, shadow_size, Color.YELLOW, Color.WHITE);
	}
	
	public Button(Frame parentFrame, float x, float y, short width, short height, String text, float font_size, int shadow_size) {
		this.font = FontManager.get("FRIZQT", font_size);
		this.baseColor = Color.YELLOW;
		this.hoveredColor = Color.WHITE;
		this.textWidth = this.font.getWidth(text);
		this.shadow_size = shadow_size;
		this.width = (short)(width * Mideas.getDisplayXFactor());
		this.height = (short)(height * Mideas.getDisplayYFactor());
		this.widthSave = width;
		this.heightSave = height;
		this.text = text;
		this.x = x * Mideas.getDisplayXFactor();
		this.y = y * Mideas.getDisplayYFactor();
		this.xSave = x;
		this.ySave = y;
		this.baseTexture = Sprites.new_button;
		this.downTexture = Sprites.new_button_down;
		this.hoverDownTexture = Sprites.new_button_down;
		this.disabledTexture = Sprites.new_button_disabled;
		this.hoverTextured = Sprites.new_button_hover;
	}

	public Button(float x, float y, String text, float font_size) {
		this(x, y, (short)Sprites.button.getImageWidth(), (short)Sprites.button.getImageHeight(), text, font_size, 0, Color.YELLOW, Color.WHITE);
	}
	
	public void draw() {
		if(!this.isEnable) {
			return;
		}
		if(!activateCondition()) {
			this.texture = this.disabledTexture;
			this.color = Color.GREY;
		}
		else if(!this.leftClickDown && !this.rightClickDown && !this.buttonHover && !hoverSpriteActivateCondition()) {
			this.texture = this.baseTexture;
			this.color = this.baseColor;
		}
		else if(!this.leftClickDown && !this.rightClickDown && this.buttonHover) {
			this.texture = this.hoverTextured;
			this.color = this.hoveredColor;
		}
		else if(hoverSpriteActivateCondition()) {
			this.color = this.hoveredColor;
		}
		Draw.drawQuad(this.texture, this.x, this.y, this.width, this.height);
		this.font.drawStringShadow(this.x-this.textWidth/2+this.width/2, this.y-this.font.getLineHeight()/2+this.height/2, this.text, this.color, Color.BLACK, this.shadow_size, 0, 0);
	}
	
	public void draw2()
	{
		if(!this.isEnable) {
			return;
		}
		if(!activateCondition()) {
			this.texture = this.disabledTexture;
			this.color = Color.GREY;
		}
		else if(!this.leftClickDown && !this.rightClickDown && !this.buttonHover && !hoverSpriteActivateCondition()) {
			this.texture = this.baseTexture;
			this.color = this.baseColor;
		}
		else if(hoverSpriteActivateCondition()) {
			this.color = this.hoveredColor;
		}
		else if (this.buttonHover && !this.leftClickDown && !this.rightClickDown)
			this.texture = this.baseTexture;
		Draw.drawQuad(this.texture, this.x, this.y, this.width, this.height);
		//if (this.buttonHover && !this.leftClickDown && !this.rightClickDown)
			//Draw.drawQuadBlend(Sprites.new_button_hover, this.x + 4, this.y + 4, this.width - 6, this.height - 6);
		if (this.buttonHover)
			Draw.drawQuadBlend(Sprites.new_button_hover, this.x + 4 , this.y + 4, this.width - 6, this.height - 6);
		this.font.drawStringShadow(this.x-this.textWidth/2+this.width/2, this.y-this.font.getLineHeight()/2+this.height/2, this.text, this.color, Color.BLACK, this.shadow_size, 0, 0);	
	}
	
	public void drawTexture() {
		if(!this.isEnable) {
			return;
		}
		if(!activateCondition()) {
			this.texture = this.disabledTexture;
		}
		else if(!this.rightClickDown && !this.leftClickDown && !this.buttonHover && !hoverSpriteActivateCondition()) {
			this.texture = this.baseTexture;
		}
		Draw.drawQuad(this.texture, this.x, this.y, this.width, this.height);
	}
	
	public void drawText() {
		if(!this.isEnable) {
			return;
		}
		if(!activateCondition()) {
			this.color = Color.GREY;
		}
		else if(!this.leftClickDown && !this.rightClickDown && !this.buttonHover && !hoverSpriteActivateCondition()) {
			this.color = this.baseColor;
		}
		else if(hoverSpriteActivateCondition()) {
			this.color = this.hoveredColor;
		}
		this.font.drawStringShadowPart(this.x-this.textWidth/2+this.width/2, this.y-this.font.getLineHeight()/2+this.height/2, this.text, this.color, Color.BLACK, this.shadow_size, 0, 0);
	}
	
	public boolean event() {
		if(!(this.isEnable && activateCondition())) {
			this.buttonHover = false;
			return false;
		}
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.width && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.height) {
			this.buttonHover = true;
			Mideas.setHover(false);
		}
		else {
			this.color = this.baseColor;
			this.buttonHover = false;
		}
		if(this.buttonHover) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0)
				{
					this.leftClickDown = true;
					onLeftClickDown();
				}
				else if (Mouse.getEventButton() == 1)
				{
					this.rightClickDown = true;
					onRightClickDown();
				}
			}
			else if(this.leftClickDown && Mouse.getEventButton() == 0) {
				this.leftClickDown = false;
				this.rightClickDown = false;
				this.color = this.hoveredColor;
				this.texture = this.hoverTextured;
				this.hasClicked = true;
				onLeftClickUp();
				return (true);
			}
			else if (this.rightClickDown && Mouse.getEventButton() == 1)
			{
				this.leftClickDown = false;
				this.rightClickDown = false;
				onRightClickUp();
			}
			this.color = this.hoveredColor;
		}
		else if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				this.leftClickDown = false;
				this.rightClickDown = false;
				this.hasClicked = false;
			}
		}
		if(this.leftClickDown || this.rightClickDown) {
			if(this.buttonHover || hoverSpriteActivateCondition()) {
				this.texture = this.hoverDownTexture;
			}
			else {
				this.texture = this.downTexture;
			}
		}
		else if(this.buttonHover || hoverSpriteActivateCondition()) {
			this.texture = this.hoverTextured;
		}
		else {
			this.texture = this.baseTexture;
		}
		return false;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setHoverFalse() {
		this.buttonHover = false;
	}
	
	public void setButtonWidth(short width) {
		this.width = (short)(width * Mideas.getDisplayXFactor());
		this.widthSave = width;
	}
	
	public void setButtonHeight(short height) {
		this.height = (short)(height * Mideas.getDisplayYFactor());
		this.heightSave = height;
	}
	
	public void setText(String text) {
		this.text = text;
		this.textWidth = this.font.getWidth(text);
	}
	
	public void enable() {
		this.isEnable = true;
	}
	
	public void disable() {
		reset();
		this.isEnable = false;
	}
	
	protected void onLeftClickUp() {}
	protected void onLeftClickDown() {}
	protected void onRightClickUp() {}
	protected void onRightClickDown() {}
	protected boolean activateCondition() {return true;}
	protected boolean hoverSpriteActivateCondition() {return false;}
	protected void popupClosed() {}
	
	public boolean getLeftClickDown() {
		return this.leftClickDown;
	}
	
	public boolean hasClicked() {
		return this.hasClicked;
	}
	
	public boolean isHover() {
		return this.buttonHover;
	}
	
	public void reset() {
		this.leftClickDown = false;
		this.rightClickDown = false;
		this.buttonHover = false;
		this.hasClicked = false;
		this.texture = Sprites.button;
		this.color = this.baseColor;
	}
	
	public void update(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
	}
	
	public void updateSize()
	{
		this.x = this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor();
		this.y = this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor();
		this.width = (short)(this.widthSave * Mideas.getDisplayXFactor());
		this.height = (short)(this.heightSave * Mideas.getDisplayYFactor());
	}
	
	public void initParentFrame(Frame frame)
	{
		this.parentFrame = frame;
		this.x = this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor();
		this.y = this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor();
	}
}
