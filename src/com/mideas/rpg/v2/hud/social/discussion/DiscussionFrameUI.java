package com.mideas.rpg.v2.hud.social.discussion;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.channel.ChannelMember;
import com.mideas.rpg.v2.chat.channel.ChatChannel;
import com.mideas.rpg.v2.command.chat.CommandChannel;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.TextMenu;
import com.mideas.rpg.v2.utils.TooltipMenu;

public class DiscussionFrameUI {

	private final ArrayList<ChatChannelCategoryButton> categoryList = new ArrayList<ChatChannelCategoryButton>();
	private static ChatChannelButton selectedChannel;
	protected float x;
	private float y;
	private static float yDraw;
	private static float yShift;
	private static float buttonXOffset;
	private static float buttonYOffset;
	private static float xSize;
	private static float ySize;
	private static float memberBorderWidth;
	private static float memberX;
	private static float memberY;
	private static int hoveredMember = -1;
	private static int rightClickDownMember = -1;
	private static float memberYShift;
	private final static TTF memberListFont = FontManager.get("FRIZQT", 12);
	public final static TTF channelFont = FontManager.get("FRIZQT", 12);
	private final static int BUTTON_X_OFFSET = 20;
	private final static int BUTTON_Y_OFFSET = 79;
	private final static int MEMBER_X_OFFSET = 225;
	private final static int MEMBER_Y_OFFSET = 78;
	private final static int MEMBER_Y_SHIFT = 15;
	private final static int MEMBER_BORDER_WIDTH_SCROLLBAR = 160;
	private final static int MEMBER_BORDER_WIDTH_NOSCROLLBAR = 180;
	private final static int Y_SHIFT = Sprites.chat_channel_button.getImageHeight()+1;
	private final static int X_SIZE = 176;
	private final static int Y_SIZE = Sprites.chat_channel_button.getImageHeight();
	final static TooltipMenu tooltipMenu = new TooltipMenu(0, 0, 0);
	static ChatChannel tooltipChannel;
	static ChannelMember tooltipMember;
	private final static TextMenu leaveChannelMenu = new TextMenu(0, 0, 0, "Leave", 12, 1, 0) {
		
		@Override
		public void eventButtonClick() {
			CommandChannel.leaveChannel(tooltipChannel.getID());
			tooltipMenu.setActive(false);
		}
	};
	private final static TextMenu cancelMenu = new TextMenu(0, 0, 0, "Cancel", 12, 1, 0) {
		
		@Override
		public void eventButtonClick() {
			tooltipMenu.setActive(false);
		}
	};
	private final static TextMenu whisperPlayerMenu = new TextMenu(0, 0, 0, "Whisper", 12, 1, 0) {
		
		@Override
		public void eventButtonClick() {
			ChatFrame.setWhisper(tooltipMember.getName());
			ChatFrame.setChatActive(true);
			tooltipMenu.setActive(false);
		}
	};
	private final static TextMenu targetPlayerMenu = new TextMenu(0, 0, 0, "Target", 12, 1, 0) {
		
		@Override
		public void eventButtonClick() {
			tooltipMenu.setActive(false);
		}
	};
	
	public DiscussionFrameUI(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
		buttonXOffset = BUTTON_X_OFFSET*Mideas.getDisplayXFactor();
		buttonYOffset = BUTTON_Y_OFFSET*Mideas.getDisplayYFactor();
		yShift = Y_SHIFT*Mideas.getDisplayYFactor();
		xSize = X_SIZE*Mideas.getDisplayXFactor();
		ySize = Y_SIZE*Mideas.getDisplayYFactor();
		memberYShift = MEMBER_Y_SHIFT*Mideas.getDisplayYFactor();
		memberBorderWidth = MEMBER_BORDER_WIDTH_NOSCROLLBAR*Mideas.getDisplayXFactor();
		memberX = this.x+MEMBER_X_OFFSET*Mideas.getDisplayXFactor();
		memberY = this.y+MEMBER_Y_OFFSET*Mideas.getDisplayYFactor();
		addCategory("Group");
		addCategory("World");
		addCategory("Custom");
	}
	
	public DiscussionFrameUI() {}
	
	public void draw() {
		int i = 0;
		yDraw = this.y+buttonYOffset;
		Draw.drawQuad(Sprites.discussion_frame, this.x, this.y);
		while(i < this.categoryList.size()) {
			this.categoryList.get(i).draw();
			i++;
		}
		tooltipMenu.draw();
	}
	
	public boolean mouseEvent() {
		int i = 0;
		yDraw = this.y+buttonYOffset;
		if(tooltipMenu.event()) return true;
		while(i < this.categoryList.size()) {
			if(this.categoryList.get(i).event()) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	protected void drawMemberList(ChatChannelButton channel) {
		ArrayList<ChannelMember> list = channel.getChannel().getPlayerList();
		int i = -1;
		float y = memberY;
		memberListFont.drawBegin();
		while(++i < list.size()) {
			memberListFont.drawStringShadow(memberX, y, list.get(i).getName(), Color.WHITE, Color.BLACK, 1, 0, 0);
			y+= memberYShift;
		}
		memberListFont.drawEnd();
		if(hoveredMember != -1) {
			y = memberY;
			Draw.drawQuadBlend(Sprites.friend_border, memberX-20*Mideas.getDisplayXFactor(), y+hoveredMember*memberYShift+1, memberBorderWidth, memberYShift);
		}
	}
	
	protected boolean memberListEvent(ChatChannelButton channel) {
		int length = channel.getChannel().getPlayerList().size();
		int i = -1;
		float y = memberY;
		float x = memberX-20*Mideas.getDisplayXFactor();
		hoveredMember = -1;
		while(++i < length) {
			if(Mideas.getHover() && Mideas.mouseX() >= x && Mideas.mouseX() <= x+memberBorderWidth &&
					Mideas.mouseY() >= y && Mideas.mouseY() <= y+memberYShift)
			{
				Mideas.setHover(false);
				hoveredMember = i;
				break;
			}
			y+= memberYShift;
		}
		if(hoveredMember != -1) {
			Draw.drawQuad(Sprites.friend_border, x, y, memberBorderWidth, memberYShift);
			if(Mouse.getEventButtonState()) {
				rightClickDownMember = hoveredMember;
			}
			else {
				if(Mouse.getEventButton() == 1) {
					if(rightClickDownMember == hoveredMember) {
						enableMemberTooltip(channel.getChannel().getPlayerList().get(hoveredMember), Mideas.mouseX(), Mideas.mouseY());
						return true;
					}
				}
			}
		}
		return false;
	}
	
	protected static float getYDraw() {
		return yDraw;
	}
	
	protected static void incrementYDraw() {
		yDraw+= yShift;
	}
	
	protected static float getYShift() {
		return yShift;
	}
	
	protected static void incrementYDraw(float value) {
		yDraw+= value;
	}
	
	protected static float getXSize() {
		return xSize;
	}
	
	protected static float getYSize() {
		return ySize;
	}
	
	public void addCategory(String name) {
		this.categoryList.add(new ChatChannelCategoryButton(name, this.x+buttonXOffset));
	}
	
	public void addChannel(ChatChannel channel) {
		if(isWorldChannel(channel.getName())) {
			this.categoryList.get(1).addChannel(channel);
		}
		else {
			this.categoryList.get(2).addChannel(channel);
		}
	}
	
	public void removeChannel(ChatChannel channel) {
		if(isWorldChannel(channel.getName())) {
			this.categoryList.get(1).removeChannel(channel);
		}
		else {
			this.categoryList.get(2).removeChannel(channel);
		}
	}
	
	protected static ChatChannelButton getSelectedChannel() {
		return selectedChannel;
	}
	
	protected static void setSelectedChannel(ChatChannelButton channel) {
		selectedChannel = channel;
	}
	
	protected static void enableChannelTooltip(ChatChannel channel, float x, float y) {
		tooltipMenu.clearMenu();
		tooltipMenu.setName(channel.getName());
		tooltipMenu.addMenu(leaveChannelMenu);
		tooltipMenu.addMenu(cancelMenu);
		tooltipMenu.updateSize(x, y, true);
		tooltipMenu.setActive(true);
		tooltipMember = null;
		tooltipChannel = channel;
	}
	
	protected static void enableMemberTooltip(ChannelMember member, float x, float y) {
		tooltipMenu.clearMenu();
		tooltipMenu.setName(member.getName());
		tooltipMenu.addMenu(whisperPlayerMenu);
		tooltipMenu.addMenu(targetPlayerMenu);
		tooltipMenu.addMenu(cancelMenu);
		tooltipMenu.updateSize(x, y, true);
		tooltipMenu.setActive(true);
		tooltipMember = member;
		tooltipChannel = null;
	}
	
	public void updateSize(float x, float y) {
		this.x = (int)x;
		this.y = (int)y;
		buttonXOffset = BUTTON_X_OFFSET*Mideas.getDisplayXFactor();
		buttonYOffset = BUTTON_Y_OFFSET*Mideas.getDisplayYFactor();
		yShift = Y_SHIFT*Mideas.getDisplayYFactor();
		xSize = X_SIZE*Mideas.getDisplayXFactor();
		ySize = Y_SIZE*Mideas.getDisplayYFactor();
		memberX = this.x+MEMBER_X_OFFSET*Mideas.getDisplayXFactor();
		memberY = this.y+MEMBER_Y_OFFSET*Mideas.getDisplayYFactor();
		memberYShift = MEMBER_Y_SHIFT*Mideas.getDisplayYFactor();
		memberBorderWidth = MEMBER_BORDER_WIDTH_NOSCROLLBAR*Mideas.getDisplayXFactor();
		memberYShift = MEMBER_Y_SHIFT*Mideas.getDisplayYFactor();
		tooltipMenu.updateSize();
		int i = 0;
		while(i < this.categoryList.size()) {
			this.categoryList.get(i).updateSize(this.x+buttonXOffset);
			i++;
		}
	}
	
	private static boolean isWorldChannel(String name) {
		return name.equals("General") || name.equals("Commerce");
	}
}
