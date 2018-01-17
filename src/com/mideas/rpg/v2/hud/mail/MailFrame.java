package com.mideas.rpg.v2.hud.mail;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.utils.Frame;

public class MailFrame implements Frame {

	private int x;
	private int y;
	private final MailInboxFrame inboxFrame;
	private boolean shouldUpdateSize;
	private Frame activeFrame;
	private boolean shouldCloseBagOnClose;
	private boolean isOpen;
	
	public MailFrame()
	{
		this.inboxFrame = new MailInboxFrame(this);
		this.activeFrame = this.inboxFrame;
	}
	
	@Override
	public void draw()
	{
		if (!this.isOpen)
			return;
		updateSize();
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (!this.isOpen)
			return (false);
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		if (!this.isOpen)
			return (false);
		return (false);
	}
	
	@Override
	public void onOpen()
	{
		this.isOpen = true;
		if (!Interface.getContainerFrameStatus())
		{
			this.shouldCloseBagOnClose = true;
			Interface.setContainerFrameStatsu(true);
		}
	}
	
	@Override
	public void onClose()
	{
		this.isOpen = false;
		if (this.shouldCloseBagOnClose)
			Interface.closeContainerFrame();
	}

	@Override
	public int getX()
	{
		return (this.x);
	}
	
	@Override
	public int getY()
	{
		return (this.y);
	}
	
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.inboxFrame.updateSize();
	}
	
	@Override
	public void shouldUpdateSize()
	{
		this.shouldUpdateSize = true;
	}
	
	@Override
	public boolean isOpen()
	{
		return (this.isOpen);
	}
	
	public MailInboxFrame getMailInboxFrame()
	{
		return (this.inboxFrame);
	}
	
	public Frame getActiveFrame()
	{
		return (this.activeFrame);
	}
}
