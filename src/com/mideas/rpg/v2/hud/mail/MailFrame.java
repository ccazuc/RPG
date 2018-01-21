package com.mideas.rpg.v2.hud.mail;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.mail.Mail;
import com.mideas.rpg.v2.utils.Frame;

public class MailFrame implements Frame {

	private final static int FRAME_X = 7;
	private final static int FRAME_Y = 105;
	private int x;
	private int y;
	private final MailInboxFrame inboxFrame;
	private final MailSendMailFrame sendMailFrame;
	private final MailOpenedMailFrame openedMailFrame;
	private boolean shouldUpdateSize;
	private Frame activeFrame;
	private boolean shouldCloseBagOnClose;
	private boolean isOpen;
	
	public MailFrame()
	{
		this.x = (int)(FRAME_X * Mideas.getDisplayXFactor());
		this.y = (int)(FRAME_Y * Mideas.getDisplayYFactor());
		this.inboxFrame = new MailInboxFrame(this);
		this.sendMailFrame = new MailSendMailFrame(this);
		this.openedMailFrame = new MailOpenedMailFrame(this);
		this.activeFrame = this.sendMailFrame;
	}
	
	@Override
	public void draw()
	{
		if (!this.isOpen)
			return;
		updateSize();
		this.inboxFrame.draw();
		this.sendMailFrame.draw();
		this.openedMailFrame.draw();
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (!this.isOpen)
			return (false);
		if (this.inboxFrame.mouseEvent())
			return (true);
		if (this.sendMailFrame.mouseEvent())
			return (true);
		if (this.openedMailFrame.mouseEvent())
			return (true);
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		if (!this.isOpen)
			return (false);
		if (this.inboxFrame.keyboardEvent())
			return (true);
		if (this.sendMailFrame.keyboardEvent())
			return (true);
		if (this.openedMailFrame.keyboardEvent())
			return (true);
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
	public void setX(int x)
	{
		this.x = x;
		shouldUpdateSize();
	}
	
	@Override
	public int getY()
	{
		return (this.y);
	}
	
	@Override
	public void setY(int y)
	{
		this.y = y;
		shouldUpdateSize();
	}
	
	public void replayOpenedMail(Mail mail)
	{
		openSendMailFrame();
		this.sendMailFrame.setTargetName(mail.getAuthorName());
		this.sendMailFrame.setSubject("RE: " + mail.getTitle());
	}
	
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.x = (int)(FRAME_X * Mideas.getDisplayXFactor());
		this.y = (int)(FRAME_Y * Mideas.getDisplayYFactor());
		this.inboxFrame.shouldUpdateSize();
		this.sendMailFrame.shouldUpdateSize();
		this.openedMailFrame.shouldUpdateSize();
		this.shouldUpdateSize = false;
	}
	
	public void openSendMailFrame()
	{
		if (this.activeFrame == this.openedMailFrame)
			return;
		this.activeFrame.onClose();
		this.activeFrame = this.openedMailFrame;
		this.activeFrame.onOpen();
	}
	
	public void openInboxMailFrame()
	{
		if (this.activeFrame == this.inboxFrame)
			return;
		this.activeFrame.onClose();
		this.activeFrame = this.inboxFrame;
		this.activeFrame.onOpen();
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
	
	public void onMailReceived()
	{
		this.inboxFrame.onMailReceived();
	}
	
	public void onMailDeleted(Mail mail)
	{
		this.openedMailFrame.onMailDeleted(mail);
	}
	
	public MailInboxFrame getMailInboxFrame()
	{
		return (this.inboxFrame);
	}
	
	public MailSendMailFrame getMailSendMailFrame()
	{
		return (this.sendMailFrame);
	}
	
	public Frame getActiveFrame()
	{
		return (this.activeFrame);
	}
	
	public void setOpenedMail(Mail mail)
	{
		this.openedMailFrame.setOpenedMail(mail);
	}
	
	public Mail getOpenedMail()
	{
		return (this.openedMailFrame.getOpenedMail());
	}
	
	@Override
	public String getName()
	{
		return ("MailFrame");
	}
}
