package com.mideas.rpg.v2.hud.social.discussion;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.utils.Draw;

public class ChatChannelCategoryButton extends DiscussionFrame {

	private final ArrayList<ChatChannelButton> channelList;
	private final String name;
	private boolean isExpanded;
	private boolean buttonHover;
	private boolean buttonDown;
	private int x_size;
	private int y_size;
	
	public ChatChannelCategoryButton(String name) {
		this.channelList = new ArrayList<ChatChannelButton>();
		this.name = name;
		this.x_size = (int)(Sprites.chat_channel_button.getImageWidth()*Mideas.getDisplayXFactor());
		this.y_size = (int)(Sprites.chat_channel_button.getImageHeight()*Mideas.getDisplayYFactor());
		
	}
	
	public ArrayList<ChatChannelButton> getChannelList() {
		return this.channelList;
	}
	
	public boolean isExpanded() {
		return this.isExpanded;
	}
	
	@Override
	public void draw() {
		Draw.drawQuad(Sprites.chat_channel_button, this.x, this.yDraw);
		this.yDraw+= this.yShift;
		if(this.channelList.size() > 0) {
			if(this.isExpanded) {
				
			}
			else {
				
			}
			int i = 0;
			while(i < this.channelList.size()) {
				this.channelList.get(i).draw();
				if(getSelectedChannel() == this.channelList.get(i) && this.isExpanded) {
					//draw channel member list
				}
				this.yDraw+= this.yShift;
				i++;
			}
		}
	}
	
	public boolean event(float y) {
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+this.x_size && Mideas.mouseY() >= y && Mideas.mouseY() <= y+this.y_size) {
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
					this.isExpanded = !this.isExpanded;
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
	
	public void addChannel(ChatChannelButton channel) {
		this.channelList.add(channel);
	}
	
	public void removeChannel(ChatChannelButton channel) {
		int i = this.channelList.size();
		while(--i >= 0) {
			if(this.channelList.get(i) == channel) {
				this.channelList.remove(i);
				return;
			}
		}
	}
	
	public String getName() {
		return this.name;
	}
}
