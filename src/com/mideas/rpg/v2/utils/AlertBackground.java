package com.mideas.rpg.v2.utils;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;

public class AlertBackground {

	private int x;
	private int y;
	private int x_size;
	private int y_size;
	private String title;
	private int titleWidth;
	private int titleBorderWidth;
	//private Color bgColor;
	
	public AlertBackground(float x, float y, float x_size, float y_size, float opacity, String title, float titleBorderWidth) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.title = title;
		this.titleWidth = FontManager.get("FRIZQT", 14).getWidth(this.title);
		//this.bgColor = new Color(0, 0, 0, opacity);
		this.titleBorderWidth = (int)titleBorderWidth;
	}
	
	public AlertBackground(float x, float y, float x_size, float y_size, float opacity) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		//this.bgColor = new Color(0, 0, 0, opacity);
	}
	
	public void draw() {
		float xFac = Mideas.getDisplayXFactor();
		float yFac = Mideas.getDisplayYFactor();
		int imageWidth = (int)(Sprites.top_left_corner_alert.getImageWidth()*xFac);
		int imageHeight = (int)(Sprites.top_left_corner_alert.getImageHeight()*yFac);
		/*int borderWidthShift = (int)(13*Mideas.getDisplayXFactor());
		int borderHeightShift = (int)(13*Mideas.getDisplayYFactor());
		Draw.drawColorQuad(this.x+borderWidthShift, this.y+borderHeightShift, this.x_size-2*borderWidthShift, this.y_size-2*borderHeightShift+1, this.bgColor);
		Draw.drawQuad(Sprites.top_left_corner_alert, this.x, this.y, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.width_top_border_alert, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.top_right_corner_alert, this.x+this.x_size-imageWidth, this.y, imageWidth, imageHeight);
		
		Draw.drawQuad(Sprites.bot_left_corner_alert, this.x, this.y+this.y_size-imageHeight, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.width_bot_border_alert, this.x+imageWidth, this.y+this.y_size-imageHeight, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.bot_right_corner_alert, this.x+this.x_size-imageWidth, this.y+this.y_size-imageHeight, imageWidth, imageHeight);
		
		Draw.drawQuad(Sprites.height_left_border_alert, this.x, this.y+imageHeight, imageWidth, this.y_size-2*imageHeight);
		Draw.drawQuad(Sprites.height_right_border_alert, this.x+this.x_size-imageWidth, this.y+imageHeight, imageWidth, this.y_size-2*imageHeight);*/
		if(this.title != null) {
			imageWidth = (int)(Sprites.alert_title_left_border.getImageWidth()*Mideas.getDisplayXFactor());
			Draw.drawQuad(Sprites.alert_title_left_border, this.x+this.x_size/2-this.titleBorderWidth/2, this.y-15*Mideas.getDisplayXFactor(), imageWidth, Sprites.alert_title_left_border.getImageHeight()*Mideas.getDisplayYFactor());
			Draw.drawQuad(Sprites.alert_title_middle_border, this.x+this.x_size/2-this.titleBorderWidth/2+imageWidth, this.y-15*Mideas.getDisplayXFactor(), this.titleBorderWidth-2*imageWidth, Sprites.alert_title_middle_border.getImageHeight()*Mideas.getDisplayYFactor());
			Draw.drawQuad(Sprites.alert_title_right_border, this.x+this.x_size/2+this.titleBorderWidth/2-imageWidth, this.y-15*Mideas.getDisplayXFactor(), imageWidth, Sprites.alert_title_right_border.getImageHeight()*Mideas.getDisplayYFactor());
			FontManager.get("FRIZQT", 14).drawStringShadow(this.x+this.x_size/2-this.titleWidth/2, this.y-8*Mideas.getDisplayYFactor(), this.title, Color.YELLOW, Color.BLACK, 1, 0, 0);
		}
		
		
		/*Sprites.alert_background.drawBegin();
		Draw.drawQuadPart(Sprites.alert_background, this.x, this.y, imageWidth, imageHeight, 0, 0, 27, 24);								//top_left_corner	
		Draw.drawQuadPart(Sprites.alert_background, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight, 54, 24, 6, 24);					//top_border
		Draw.drawQuadPart(Sprites.alert_background, this.x+this.x_size-imageWidth, this.y, imageWidth, imageHeight, 27, 0, 27, 24);					//top_right_corner
		
		Draw.drawQuadPart(Sprites.alert_background, this.x, this.y+this.y_size-imageHeight, imageWidth, imageHeight, 54, 0, 27, 24);					//bot_left_corner
		Draw.drawQuadPart(Sprites.alert_background, this.x+imageWidth, this.y+this.y_size-imageHeight, this.x_size-2*imageWidth, imageHeight, 60, 24, 6, 24);		//bot_border
		Draw.drawQuadPart(Sprites.alert_background, this.x+this.x_size-imageWidth, this.y+this.y_size-imageHeight, imageWidth, imageHeight, 81, 0, 27, 24);		//bot_right_corner
		
		Draw.drawQuadPart(Sprites.alert_background, this.x, this.y+imageHeight, imageWidth, this.y_size-2*imageHeight, 0, 25, 27, 6);					//left_border
		Draw.drawQuadPart(Sprites.alert_background, this.x+this.x_size-imageWidth, this.y+imageHeight, imageWidth, this.y_size-2*imageHeight, 27, 25, 27, 6);		//right_border
		Sprites.alert_background.drawEnd();*/
		
		Sprites.alert_background.drawBegin();
		Draw.drawQuadPart(Sprites.alert_background, this.x, this.y, imageWidth, imageHeight, 0, 0, 27, 24);								//top_left_corner	
		Draw.drawQuadPart(Sprites.alert_background, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight, 55, 24, 2, 24);					//top_border
		Draw.drawQuadPart(Sprites.alert_background, this.x+this.x_size-imageWidth, this.y, imageWidth, imageHeight, 27, 0, 27, 24);					//top_right_corner
		
		Draw.drawQuadPart(Sprites.alert_background, this.x, this.y+this.y_size-imageHeight, imageWidth, imageHeight, 54, 0, 27, 24);					//bot_left_corner
		Draw.drawQuadPart(Sprites.alert_background, this.x+imageWidth, this.y+this.y_size-imageHeight, this.x_size-2*imageWidth, imageHeight, 61, 24, 2, 24);		//bot_border
		Draw.drawQuadPart(Sprites.alert_background, this.x+this.x_size-imageWidth, this.y+this.y_size-imageHeight, imageWidth, imageHeight, 81, 0, 27, 24);		//bot_right_corner
		
		Draw.drawQuadPart(Sprites.alert_background, this.x, this.y+imageHeight, imageWidth, this.y_size-2*imageHeight, 0, 24, 27, 2);					//left_border
		Draw.drawQuadPart(Sprites.alert_background, this.x+this.x_size-imageWidth, this.y+imageHeight, imageWidth, this.y_size-2*imageHeight, 27, 24, 27, 2);		//right_border
		Sprites.alert_background.drawEnd();
	}
	
	public boolean isHover() {
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y+this.y_size) {
			Mideas.setHover(false);
			return true;
		}
		return false;
	}
	
	public void setX(float x) {
		this.x = (int)x;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setY(float y) {
		this.y = (int)y;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setWidth(float x_size) {
		this.x_size = (int)x_size;
	}
	
	public int getWidth() {
		return this.x_size;
	}
	
	public void setHeight(float y_size) {
		this.y_size = (int)y_size;
	}
	
	public int getHeight() {
		return this.y_size;
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
	}
	
	public void update(float x, float y, float x_size, float y_size, float titleBorderWidth) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.titleBorderWidth = (int)titleBorderWidth;
	}
}
