package com.mideas.rpg.v2.utils;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;

public class Alert {

	private Button button;
	private String text;
	private int x;
	private int y;
	private int x_size_alert;
	private int y_size_alert;
	private Color bgColor = new Color(0, 0, 0, .6f);
	private boolean isActive = true;
	
	public Alert(String text, int x, int y, int x_size_alert, int y_size_alert, int x_size_button, int y_size_button, int font_size, String button_text) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.x_size_alert = x_size_alert;
		this.y_size_alert = y_size_alert;
		this.button = new Button(x+x_size_alert/2-x_size_button/2, y+y_size_alert/2-y_size_button/2, x_size_button, y_size_button, button_text, font_size);
	}
	
	public void draw() {
		if(this.isActive) {
			this.button.draw();
			float xFac = Mideas.getDisplayXFactor();
			float yFac = Mideas.getDisplayYFactor();
			float bot = this.y*yFac+this.y_size_alert*yFac-Sprites.width_bot_border_alert.getImageHeight()*yFac-1f*Mideas.getDisplayYFactor();
			float top = this.y*yFac+Sprites.width_top_border_alert.getImageHeight()*xFac;
			Draw.drawQuad(Sprites.width_top_border_alert, this.x*xFac+Sprites.top_left_corner_alert.getImageWidth()*xFac, this.y*yFac, .5f+this.x_size_alert*xFac-Sprites.top_right_corner_alert.getImageWidth()*xFac-Sprites.top_left_corner_alert.getImageWidth()*xFac, Sprites.width_top_border_alert.getImageHeight()*xFac);
			Draw.drawQuad(Sprites.width_bot_border_alert, this.x*xFac+Sprites.top_left_corner_alert.getImageWidth()*xFac, this.y*yFac+this.y_size_alert*yFac-Sprites.width_bot_border_alert.getImageHeight()*yFac-1, this.x_size_alert-Sprites.top_right_corner_alert.getImageWidth()-Sprites.top_left_corner_alert.getImageWidth(), Sprites.width_bot_border_alert.getImageHeight());
			Draw.drawQuad(Sprites.height_left_border_alert, this.x*xFac+2f, this.y*yFac+Sprites.top_left_corner_alert.getImageWidth()*xFac-3, Sprites.height_left_border_alert.getImageWidth()*xFac, this.y_size_alert-Sprites.top_left_corner_alert.getImageWidth()*2*xFac);
			Draw.drawQuad(Sprites.height_right_border_alert, this.x*xFac+this.x_size_alert*xFac-Sprites.height_right_border_alert.getImageWidth()*xFac+0.8f*xFac, this.y*yFac+Sprites.top_right_corner_alert.getImageWidth()*xFac-3, Sprites.height_right_border_alert.getImageWidth()*xFac, this.y_size_alert-Sprites.top_left_corner_alert.getImageWidth()*2*xFac);
			Draw.drawColorQuad(this.x*xFac+Sprites.top_left_corner_alert.getImageWidth()*xFac, this.y*yFac+Sprites.width_top_border_alert.getImageHeight()*xFac, this.x_size_alert*xFac-Sprites.top_right_corner_alert.getImageWidth()*xFac-Sprites.top_left_corner_alert.getImageWidth()*xFac, bot-top, this.bgColor);
			Draw.drawQuad(Sprites.top_left_corner_alert, this.x*xFac, this.y*yFac);
			Draw.drawQuad(Sprites.top_right_corner_alert, this.x*xFac+this.x_size_alert*xFac-Sprites.top_right_corner_alert.getImageWidth()*xFac, this.y*yFac);
			Draw.drawQuad(Sprites.bot_left_corner_alert, this.x*xFac, this.y*yFac+this.y_size_alert*yFac-Sprites.bot_left_corner_alert.getImageHeight()*yFac);
			Draw.drawQuad(Sprites.bot_right_corner_alert, this.x*xFac+this.x_size_alert*xFac-Sprites.bot_right_corner_alert.getImageWidth()*xFac, this.y*yFac+this.y_size_alert*yFac-Sprites.bot_left_corner_alert.getImageHeight()*yFac);int i = 0;
			int j = 0;
			int y_shift = 0;
			int x_shift = 0;
			while(i < this.text.length()) {
				if(TTF2.loginScreenPassword.getWidth(this.text.substring(j, i)) < this.x_size_alert-20) {
					TTF2.loginScreenPassword.drawChar(this.x*xFac+10+x_shift, this.y*yFac+10+y_shift, this.text.charAt(i), Color.decode("#FFC700"));
				}
				else {
					j = i;
					i--;
					y_shift+= TTF2.loginScreenPassword.getLineHeight()+3;
					x_shift = -TTF2.loginScreenPassword.getWidth(this.text.charAt(i));
				}
				x_shift+= TTF2.loginScreenPassword.getWidth(this.text.charAt(i));
				i++;
			}
		}
	}
	
	public void event() throws NoSuchAlgorithmException, SQLException {
		if(this.isActive) {
			this.button.event();
			if(this.button.hasClicked()) {
				this.isActive = false;
			}
		}
	}
}
