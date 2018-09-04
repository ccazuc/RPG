package com.mideas.rpg.v2.chat.new_chat;

import java.util.ArrayList;
import java.util.HashSet;

import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.utils.UIElement;
import com.mideas.rpg.v2.utils.UIElementType;

public class ChatFrameTab extends UIElement
{

	private final HashSet<MessageType> allowedMessageType;
	private final ArrayList<Message> messageList;
	private final ArrayList<String> rawMessageList;
	private final ChatFrame parentFrame;
	private final ChatFrameTabButton button;
	
	public ChatFrameTab(ChatFrame parentFrame, String name)
	{
		super(name, UIElementType.CHAT_FRAME_TAB);
		this.allowedMessageType = new HashSet<MessageType>();
		this.messageList = new ArrayList<Message>();
		this.rawMessageList = new ArrayList<String>();
		this.parentFrame = parentFrame;
	}
	
	@Override
	public void draw()
	{
		
	}
	
	@Override
	public boolean mouseEvent()
	{
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
	
	public boolean acceptMessageType(MessageType type)
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
}
