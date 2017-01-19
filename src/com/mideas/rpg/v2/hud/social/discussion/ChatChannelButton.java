package com.mideas.rpg.v2.hud.social.discussion;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.channel.ChatChannel;

public class ChatChannelButton extends DiscussionFrame {

	private final ChatChannel channel;
	private boolean buttonHover;
	private boolean buttonDown;
	
	public ChatChannelButton(ChatChannel channel) {
		this.channel = channel;
	}
	
	@Override
	public void draw() {
		//draw button
		if(getSelectedChannel() == this || this.buttonHover) {
			//draw hover
		}
	}
	
	public boolean event(float y) {
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= y && Mideas.mouseY() <= y+this.yShift) {
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
		return false;
	}
}
