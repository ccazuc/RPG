package com.mideas.rpg.v2.chat.new_chat;

import java.util.ArrayList;
import java.util.HashSet;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.utils.UIElement;
import com.mideas.rpg.v2.utils.UIElementType;

public class ChatFrameTab extends UIElement
{

	private final HashSet<MessageType> allowedMessageType;
	private final ArrayList<Message> messageList;
	private final ArrayList<String> rawMessageList;
	private ChatFrame parentFrame;
	private final ChatFrameTabButton button;
	private String title;
	
	public ChatFrameTab(ChatFrame parentFrame, String name, String title)
	{
		super(name, UIElementType.CHAT_FRAME_TAB);
		this.allowedMessageType = new HashSet<MessageType>();
		this.messageList = new ArrayList<Message>();
		this.rawMessageList = new ArrayList<String>();
		this.parentFrame = parentFrame;
		this.button = new ChatFrameTabButton(this, name + "BUTTON", 0, 0);
	}
	
	public ChatFrameTab(String name, String title)
	{
		this(null, name, title);
	}
	
	@Override
	public void draw()
	{
		this.button.draw();
		//if (System.currentTimeMillis() % 2 == 0)
		//	Draw.drawColorQuad(this.x, this.y, 50, 50, Color.RED);
		//FontManager.get("FRIZQT", 10).drawString(this.x, this.y, this.name, Color.BLACK);
		if (isActiveTab())
		{
			
		}
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (this.button.mouseEvent())
			return (true);
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		return (false);
	}
	
	public boolean isActiveTab()
	{
		return (this.parentFrame.getActiveTab() == this);
	}
	
	public void setActiveTab()
	{
		this.parentFrame.setActiveTab(this);
	}
	
	public boolean isMessageTypeAccepted(MessageType type)
	{
		return (this.allowedMessageType.contains(type));
	}
	
	public void addAcceptedMessageType(MessageType type)
	{
		this.allowedMessageType.add(type);
	}
	
	public void removeAcceptedMessageType(MessageType type)
	{
		this.allowedMessageType.remove(type);
	}
	
	public void addMessage(Message message)
	{
		this.messageList.add(message);
	}
	
	public void addRawMessage(String str)
	{
		this.rawMessageList.add(str);
	}
	
	public HashSet<MessageType> getAllowedMessageType()
	{
		return (this.allowedMessageType);
	}
	
	public ArrayList<Message> getMessageList()
	{
		return (this.messageList);
	}
	
	public void setParentFrame(ChatFrame frame)
	{
		this.parentFrame = frame;
	}
	
	public ChatFrame getFrame()
	{
		return (this.parentFrame);
	}
	
	@Override
	public void setX(float x)
	{
		this.xSave = (short)x;
		this.x = (short)(this.parentFrame.getX() + x * Mideas.getDisplayXFactor());
		this.button.setX(x);
		updateSize();
	}
	
	@Override
	public void setY(float y)
	{
		this.ySave = (short)y;
		this.y = (short)(this.parentFrame.getY() + y * Mideas.getDisplayYFactor());
		this.button.setY(y);
		updateSize();
	}
	
	@Override
	public void updateSize()
	{
		this.x = (short)(this.parentFrame.getX() + this.xSave * Mideas.getDisplayXFactor());
		this.y = (short)(this.parentFrame.getY() + this.ySave * Mideas.getDisplayYFactor());
		this.button.updateSize();
	}
}
