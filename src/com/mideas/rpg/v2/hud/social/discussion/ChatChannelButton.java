package com.mideas.rpg.v2.hud.social.discussion;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.chat.channel.ChatChannel;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;

public class ChatChannelButton extends DiscussionFrameUI {

	private final ChatChannel channel;
	private boolean buttonHover;
	private boolean buttonDown;
	
	public ChatChannelButton(ChatChannel channel, float x) {
		this.channel = channel;
		this.x = x;
	}
	
	@Override
	public void draw() {
		Draw.drawQuad(Sprites.chat_channel_button, this.x, DiscussionFrameUI.getYDraw(), DiscussionFrameUI.getXSize(), DiscussionFrameUI.getYSize(), .4f);
		if(this.buttonDown) {
			DiscussionFrameUI.channelFont.drawStringShadow(this.x+10, DiscussionFrameUI.getYDraw()+2*Mideas.getDisplayYFactor(), this.channel.getChannelNameDisplayed(), Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		else {
			DiscussionFrameUI.channelFont.drawStringShadow(this.x+8, DiscussionFrameUI.getYDraw(), this.channel.getChannelNameDisplayed(), Color.WHITE, Color.BLACK, 1, 0, 0);
		}
		if(getSelectedChannel() == this || this.buttonHover) {
			Draw.drawQuadBlend(Sprites.button_menu_hover, this.x, DiscussionFrameUI.getYDraw(), DiscussionFrameUI.getXSize(), DiscussionFrameUI.getYSize());
		}
	}
	
	protected boolean event() {
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
					setSelectedChannel(this);
					return true;
				}
				else if(Mouse.getEventButton() == 1) {
					this.buttonDown = false;
					DiscussionFrameUI.enableChannelTooltip(this.channel, Mideas.mouseX()-5, Mideas.mouseY()-5);
					return true;
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
	
	protected void updateSize(float x) {
		this.x = x;
	}
	
	protected boolean isHover() {
		return this.buttonHover;
	}
	
	protected ChatChannel getChannel() {
		return this.channel;
	}
}
