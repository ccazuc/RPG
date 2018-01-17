package com.mideas.rpg.v2.hud.mail;

import com.mideas.rpg.v2.game.mail.Mail;
import com.mideas.rpg.v2.game.mail.MailMgr;
import com.mideas.rpg.v2.utils.Frame;

public class MailInboxFrame implements Frame {

	private final MailFrame frame;
	private int currentPage;
	private final MailInboxButton[] buttonList;
	private Mail openedMail;
	private boolean shouldUpdateSize;
	private final static byte MAIL_PER_PAGE = 7;
	
	public MailInboxFrame(MailFrame frame)
	{
		this.frame = frame;
		this.buttonList = new MailInboxButton[MAIL_PER_PAGE];
		int i = -1;
		while (++i < this.buttonList.length)
			this.buttonList[i] = new MailInboxButton(this, (short)(45 * i));
		fillMailPage();
			
	}
	
	@Override
	public void draw()
	{
		updateSize();
		if (this.openedMail != null)
			drawOpenedMail();
		if (this.frame.getActiveFrame() == this)
			drawFrame();
	}
	
	public  void drawFrame()
	{
		
	}
	
	public void drawOpenedMail()
	{
		
	}
	
	@Override
	public boolean keyboardEvent()
	{
		return (false);
	}
	
	@Override
	public boolean mouseEvent()
	{
		int i = -1;
		while (++i < this.buttonList.length)
			if (this.buttonList[i].mouseEvent())
				return (true);
		return (false);
	}
	
	@Override
	public void onOpen()
	{
		
	}
	
	@Override
	public void onClose()
	{
		
	}
	
	public void nextPage()
	{
		
	}
	
	public void previousPage()
	{
		
	}
	
	public void fillMailPage()
	{
		int i = -1;
		final int pageOffset = MAIL_PER_PAGE * this.currentPage;
		while (++i < this.buttonList.length && i + pageOffset < MailMgr.getMailList().size())
			this.buttonList[i].setMail(MailMgr.getMailList().get(i + pageOffset));
	}

	@Override
	public int getX()
	{
		return (this.frame.getX());
	}
	
	@Override
	public int getY()
	{
		return (this.frame.getY());
	}
	
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		int i = -1;
		while (++i < this.buttonList.length)
			this.buttonList[i].updateSize();	
	}
	
	@Override
	public void shouldUpdateSize()
	{
		this.shouldUpdateSize = true;
	}
	
	@Override
	public boolean isOpen()
	{
		return (this.frame.getActiveFrame() == this);
	}
	
	public void setOpenedMail(Mail mail)
	{
		if (this.openedMail == mail)
			this.openedMail = null;
		else
			this.openedMail = mail;
	}
}
