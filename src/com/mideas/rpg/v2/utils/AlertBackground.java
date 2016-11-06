package com.mideas.rpg.v2.utils;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;

public class AlertBackground {

	private int x;
	private int y;
	private int x_size;
	private int y_size;
	private Color bgColor;
	
	public AlertBackground(float x, float y, float x_size, float y_size, float opacity) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
		this.bgColor = new Color(0, 0, 0, opacity);
	}
	
	public void draw() {
		/*float xFac = Mideas.getDisplayXFactor();
		float yFac = Mideas.getDisplayYFactor();
		int xTemp = this.x;
		int yTemp = this.y;
		int xSizeAlertTemp = (int) (this.x_size_alert*xFac);
		int ySizeAlertTemp = (int) (this.y_size_alert*yFac);
		int cornerWidth = (int) (Sprites.top_left_corner_alert.getImageWidth()*xFac);
		int cornerHeight = (int) (Sprites.top_left_corner_alert.getImageHeight()*yFac);
		int heightBorderWidth = (int) (Sprites.height_left_border_alert.getImageWidth()*xFac);
		int botBorderHeight = (int) (Sprites.width_bot_border_alert.getImageHeight()*yFac);
		
		Draw.drawQuad(Sprites.top_left_corner_alert, xTemp, yTemp);
		Draw.drawQuad(Sprites.top_right_corner_alert, xTemp+xSizeAlertTemp-cornerWidth, yTemp);
		Draw.drawQuad(Sprites.bot_left_corner_alert, xTemp, yTemp+ySizeAlertTemp-cornerHeight);
		Draw.drawQuad(Sprites.bot_right_corner_alert, xTemp+xSizeAlertTemp-cornerWidth, yTemp+ySizeAlertTemp-cornerHeight);
		
		Draw.drawQuad(Sprites.height_left_border_alert, xTemp, yTemp+cornerHeight, heightBorderWidth, ySizeAlertTemp-cornerHeight*2);
		Draw.drawQuad(Sprites.height_right_border_alert, xTemp+xSizeAlertTemp-cornerWidth, yTemp+cornerHeight, heightBorderWidth, ySizeAlertTemp-cornerHeight*2);
		Draw.drawQuad(Sprites.width_bot_border_alert, xTemp+cornerWidth, yTemp+ySizeAlertTemp-botBorderHeight, xSizeAlertTemp-2*cornerWidth, botBorderHeight);
		Draw.drawQuad(Sprites.width_top_border_alert, xTemp+cornerWidth, yTemp, xSizeAlertTemp-2*cornerWidth, botBorderHeight);
		
		Draw.drawColorQuad(xTemp+cornerWidth, yTemp+cornerHeight, xSizeAlertTemp-2*cornerWidth, ySizeAlertTemp-2*cornerHeight, this.bgColor);*/

		float xFac = Mideas.getDisplayXFactor();
		float yFac = Mideas.getDisplayYFactor();
		int imageWidth = (int) (Sprites.top_left_corner_alert.getImageWidth()*xFac);
		int imageHeight = (int) (Sprites.top_left_corner_alert.getImageHeight()*yFac);
		Draw.drawQuad(Sprites.top_left_corner_alert, this.x, this.y, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.width_top_border_alert, this.x+imageWidth, this.y, this.x_size-2*imageWidth, imageHeight);
		Draw.drawQuad(Sprites.top_right_corner_alert, this.x+this.x_size-imageWidth, this.y, imageWidth, imageHeight);
		
		Draw.drawQuad(Sprites.bot_left_corner_alert, this.x, this.y+this.y_size-imageHeight, imageWidth, imageHeight);
		Draw.drawQuad(Sprites.width_bot_border_alert, this.x+imageWidth, this.y+this.y_size-imageHeight, this.x_size-2*imageWidth, imageHeight);
		
		Draw.drawQuad(Sprites.bot_right_corner_alert, this.x+this.x_size-imageWidth, this.y+this.y_size-imageHeight);
		
		Draw.drawQuad(Sprites.height_left_border_alert, this.x, this.y+imageHeight, imageWidth, this.y_size-2*imageHeight);
		Draw.drawQuad(Sprites.height_right_border_alert, this.x+this.x_size-imageWidth, this.y+imageHeight, imageWidth, this.y_size-2*imageHeight);
		Draw.drawColorQuad(this.x+imageWidth, this.y+imageHeight, this.x_size-2*imageWidth, this.y_size-2*imageHeight, this.bgColor);
	}
	
	public void setX(float x) {
		this.x = (int)x;
	}
	
	public void setY(float y) {
		this.y = (int)y;
	}
	
	public void update(float x, float y, float x_size, float y_size) {
		this.x = (int)x;
		this.y = (int)y;
		this.x_size = (int)x_size;
		this.y_size = (int)y_size;
	}
}
