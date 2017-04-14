package com.mideas.rpg.v2.hud.social.discussion;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.channel.ChatChannel;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;

public class ChatChannelCategoryButton extends DiscussionFrameUI {

	private final ArrayList<ChatChannelButton> channelList;
	private final String name;
	private boolean isExpanded = true;
	private boolean buttonHover;
	private boolean buttonDown;
	
	public ChatChannelCategoryButton(String name, float x) {
		this.channelList = new ArrayList<ChatChannelButton>();
		this.name = name;
		this.x = x;
	}
	
	public ArrayList<ChatChannelButton> getChannelList() {
		return this.channelList;
	}
	
	public boolean isExpanded() {
		return this.isExpanded;
	}
	
	@Override
	public void draw() {
		Draw.drawQuad(Sprites.chat_channel_button, this.x, DiscussionFrameUI.getYDraw(), DiscussionFrameUI.getXSize(), DiscussionFrameUI.getYSize());
		if(this.buttonDown) {
			DiscussionFrameUI.channelFont.drawStringShadow(this.x+10, DiscussionFrameUI.getYDraw()+2*Mideas.getDisplayYFactor(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		}
		else {
			DiscussionFrameUI.channelFont.drawStringShadow(this.x+8, DiscussionFrameUI.getYDraw(), this.name, Color.YELLOW, Color.BLACK, 1, 0, 0);
		}
		if(this.buttonHover) {
			Draw.drawQuadBlend(Sprites.button_menu_hover, this.x, DiscussionFrameUI.getYDraw(), DiscussionFrameUI.getXSize(), DiscussionFrameUI.getYSize());
		}
		DiscussionFrameUI.incrementYDraw();
		if(this.channelList.size() > 0 && this.isExpanded) {
			//draw "-"
			int i = 0;
			while(i < this.channelList.size()) {
				this.channelList.get(i).draw();
				if(getSelectedChannel() == this.channelList.get(i) && this.isExpanded) {
					drawMemberList(this.channelList.get(i));
				}
				DiscussionFrameUI.incrementYDraw();
				i++;
			}
		}
		else if(!this.isExpanded) {
			//draw "+"
		}
	}
	
	public boolean event() {
		if(this.channelList.size() == 0) {
			DiscussionFrameUI.incrementYDraw();
			return false;
		}
		if(Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x+DiscussionFrameUI.getXSize() && Mideas.mouseY() >= DiscussionFrameUI.getYDraw() && Mideas.mouseY() <= DiscussionFrameUI.getYDraw()+DiscussionFrameUI.getYShift()) {
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
					DiscussionFrameUI.incrementYDraw();
					return true;
				}
				else if(Mouse.getEventButton() == 1) {
					this.buttonDown = false;
					return true;
				}
			}
		}
		else if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
				this.buttonDown = false;
			}
		}
		DiscussionFrameUI.incrementYDraw();
		if(!this.isExpanded) {
			return false;
		}
		int i = 0;
		while(i < this.channelList.size()) {
			if(this.channelList.get(i).event()) {
				return true;
			}
			if(getSelectedChannel() == this.channelList.get(i)) {
				if(memberListEvent(this.channelList.get(i))) {
					return true;
				}
			}
			DiscussionFrameUI.incrementYDraw();
			i++;
		}
		return false;
	}
	
	@Override
	public void addChannel(ChatChannel channel) {
		this.channelList.add(new ChatChannelButton(channel, this.x));
	}
	
	@Override
	public void removeChannel(ChatChannel channel) {
		int i = this.channelList.size();
		while(--i >= 0) {
			if(this.channelList.get(i).getChannel() == channel) {
				if(getSelectedChannel() == this.channelList.get(i)) {
					setSelectedChannel(null);
				}
				this.channelList.remove(i);
				return;
			}
		}
	}
	
	public void updateSize(float x) {
		this.x = x;
		int i = 0;
		while(i < this.channelList.size()) {
			this.channelList.get(i).updateSize(x);
			i++;
		}
	}
	
	public String getName() {
		return this.name;
	}
}
