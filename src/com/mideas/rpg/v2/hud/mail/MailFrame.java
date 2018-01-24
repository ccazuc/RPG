package com.mideas.rpg.v2.hud.mail;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.mail.Mail;
import com.mideas.rpg.v2.utils.Frame;
import com.mideas.rpg.v2.utils.FrameTab2;

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
	private final static short INBOX_FRAME_TAB_X = 65;
	private final static short INBOX_FRAME_TAB_Y = 434;
	private final static short INBOX_FRAME_TAB_WIDTH = 70;
	private final static short SEND_MAIL_FRAME_TAB_X = 144;
	private final static short SEND_MAIL_FRAME_TAB_Y = 434;
	private final static short SEND_MAIL_FRAME_TAB_WIDTH = 85;
	private final FrameTab2 inboxFrameTab = new FrameTab2(this, INBOX_FRAME_TAB_X, INBOX_FRAME_TAB_Y, INBOX_FRAME_TAB_WIDTH, "Inbox", FontManager.get("FRIZQT", 11), true)
	{
	
		@Override
		public void onLeftClickUp()
		{
			openInboxMailFrame();
		}
		
		@SuppressWarnings("synthetic-access")
		@Override
		public boolean isSelected()
		{
			return (MailFrame.this.activeFrame == MailFrame.this.inboxFrame);
		}
	};	
	private final FrameTab2 sendMailFrameTab = new FrameTab2(this, SEND_MAIL_FRAME_TAB_X, SEND_MAIL_FRAME_TAB_Y, SEND_MAIL_FRAME_TAB_WIDTH, "Send Mail", FontManager.get("FRIZQT", 11), false)
	{
	
		@Override
		public void onLeftClickUp()
		{
			openSendMailFrame();
		}
		
		@SuppressWarnings("synthetic-access")
		@Override
		public boolean isSelected()
		{
			return (MailFrame.this.activeFrame == MailFrame.this.sendMailFrame);
		}
	};
	
	public MailFrame()
	{
		this.x = (int)(FRAME_X * Mideas.getDisplayXFactor());
		this.y = (int)(FRAME_Y * Mideas.getDisplayYFactor());
		this.inboxFrame = new MailInboxFrame(this);
		this.sendMailFrame = new MailSendMailFrame(this);
		this.openedMailFrame = new MailOpenedMailFrame(this);
		this.inboxFrameTab.initParentFrame(this);
		this.sendMailFrameTab.initParentFrame(this);
		this.activeFrame = this.inboxFrame;
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
		this.inboxFrameTab.draw();
		this.sendMailFrameTab.draw();
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
		if (this.inboxFrameTab.mouseEvent())
			return (true);
		if (this.sendMailFrameTab.mouseEvent())
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
	public void open()
	{
		this.isOpen = true;
		this.activeFrame.open();
		if (!Interface.getContainerFrameStatus())
		{
			this.shouldCloseBagOnClose = true;
			Interface.setContainerFrameStatsu(true);
		}
	}
	
	@Override
	public void close()
	{
		this.isOpen = false;
		if (this.shouldCloseBagOnClose)
			Interface.closeContainerFrame();
		this.activeFrame.close();
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
		this.inboxFrameTab.updateSize();
		this.sendMailFrameTab.updateSize();
		this.shouldUpdateSize = false;
	}
	
	public void openSendMailFrame()
	{
		if (this.activeFrame == this.sendMailFrame)
			return;
		this.inboxFrameTab.setIsSelected(false);
		this.sendMailFrameTab.setIsSelected(true);
		this.activeFrame.close();
		this.activeFrame = this.sendMailFrame;
		this.activeFrame.open();
	}
	
	public void openInboxMailFrame()
	{
		if (this.activeFrame == this.inboxFrame)
			return;
		this.inboxFrameTab.setIsSelected(true);
		this.sendMailFrameTab.setIsSelected(false);
		this.activeFrame.close();
		this.activeFrame = this.inboxFrame;
		this.activeFrame.open();
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
		this.inboxFrame.onMailDeleted();
		this.openedMailFrame.onMailDeleted(mail);
	}
	
	public void onMailSent()
	{
		this.sendMailFrame.onMailSent();
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
