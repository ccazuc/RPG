package com.mideas.rpg.v2.chat.new_chat;

import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.UIElement;
import com.mideas.rpg.v2.utils.UIElementType;

public class ChatFrame extends UIElement
{
	private final ArrayList<ChatFrameTab> tabList;
	private ChatFrameTab activeFrameTab;
	private boolean isLocked;
	
	public final static short DEFAULT_X = 20;
	public final static short DEFAULT_Y = 200;
	public final static short DEFAULT_WIDTH = 400;
	public final static short MIN_WIDTH = 200;
	public final static short DEFAULT_HEIGHT = 250;
	public final static short MIN_HEIGHT = 150;
	
	public ChatFrame(String name, int x, int y, int width, int height, boolean isLocked)
	{
		super(name, UIElementType.CHAT_FRAME);
		this.xSave = (short)x;
		this.x = (short)(x * Mideas.getDisplayXFactor());
		this.ySave = (short)y;
		this.y = (short)(y * Mideas.getDisplayYFactor());
		this.widthSave = (short)width;
		this.width = (short)(width * Mideas.getDisplayXFactor());
		this.heightSave = (short)height;
		this.height = (short)(height * Mideas.getDisplayYFactor());
		this.tabList = new ArrayList<ChatFrameTab>();
		this.isLocked = isLocked;
	}
	
	@Override
	public void draw()
	{
		//ArrayList<Message> messageList = this.activeFrameTab.getMessageList();
		updateSize();
		Draw.drawColorQuad(this.x, this.y, this.width, this.height, Color.WHITE);
		for (int i = 0; i < this.tabList.size(); ++i)
			this.tabList.get(i).draw();
	}
	
	@Override
	public boolean mouseEvent()	
	{
		for (int i = 0; i < this.tabList.size(); ++i)
			if (this.tabList.get(i).mouseEvent())
				return (true);
		if (Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x + this.width && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y + this.height)
			Mideas.setHover(this, false);
		return (false);
	}

	public void addMessage(Message message)
	{
		for (int i = 0; i < this.tabList.size(); ++i)
			if (this.tabList.get(i).isMessageTypeAccepted(message.getType()))
				this.tabList.get(i).addMessage(message);
	}
	
	public void removeChatFrameTab(ChatFrameTab tab)
	{
		for (int i = 0; i < this.tabList.size(); ++i)
			if (this.tabList.get(i) == tab)
				this.tabList.remove(i);
		if (this.activeFrameTab == tab)
		{
			if (this.tabList.size() == 0)
				System.out.println("Error ChatFrame.removeChatFrameTab, tabList.size == 0");
			else
				this.activeFrameTab = this.tabList.get(0);
		}
	}
	
	public void addChatFrameTab(ChatFrameTab tab)
	{
		tab.setX(20 + this.tabList.size() * (ChatFrameTabButton.BUTTON_WIDTH + 20));
		tab.setY(-ChatFrameTabButton.BUTTON_HEIGHT + 5);
		this.tabList.add(tab);
		if (this.tabList.size() == 1)
			this.activeFrameTab = tab;
	}
	
	public void setActiveTab(ChatFrameTab tab)
	{
		this.activeFrameTab = tab;
	}
	
	public ChatFrameTab getActiveTab()
	{
		return (this.activeFrameTab);
	}
	
	public ArrayList<ChatFrameTab> getTabList()
	{
		return (this.tabList);
	}
	
	public boolean getIsLocked()
	{
		return (this.isLocked);
	}
	
	@Override
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		for (int i = 0; i < this.tabList.size(); ++i)
			this.tabList.get(i).updateSize();
		this.shouldUpdateSize = false;
	}
}
