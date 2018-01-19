package com.mideas.rpg.v2.hud.mail;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.CommandMail;
import com.mideas.rpg.v2.game.mail.Mail;
import com.mideas.rpg.v2.game.mail.MailMgr;
import com.mideas.rpg.v2.utils.Arrow;
import com.mideas.rpg.v2.utils.ArrowDirection;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Frame;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

public class MailInboxFrame implements Frame {

	private final MailFrame frame;
	int currentPage;
	private final MailInboxButton[] buttonList;
	private Mail openedMail;
	private short openedMailX;
	private short openedMailY;
	private boolean shouldUpdateSize;
	private short openedMailSubjectX;
	private short openedMailSubjectY;
	private short openedMailSenderNameX;
	private short openedMailSenderNameY;
	private short nextPageArrowTextX;
	private short nextPageArrowTextY;
	private short previousPageArrowTextX;
	private short previousPageArrowTextY;
	private final static TTF openedMailSenderFont = FontManager.get("FRIZQT", 13);
	private final static TTF openedMailSubjectFont = FontManager.get("FRIZQT", 10);
	private final static TTF changePageFont = FontManager.get("FRIZQT", 13);
	private final static byte MAIL_PER_PAGE = 7;
	private final static short OPENED_MAIL_X_OFFSET = 407;
	private final static short OPENED_MAIL_Y_OFFSET = 0;
	private final static short OPENED_MAIL_SUBJECT_X_OFFSET = 128;
	private final static short OPENED_MAIL_SUBJECT_Y_OFFSET = 68;
	private final static short OPENED_MAIL_SENDER_NAME_X_OFFSET = 128;
	private final static short OPENED_MAIL_SENDER_NAME_Y_OFFSET = 46;
	private final static short NEXT_PAGE_ARROW_X_OFFSET = 319;
	private final static short NEXT_PAGE_ARROW_Y_OFFSET = 396;
	private final static short PREVIOUS_PAGE_ARROW_X_OFFSET = 27;
	private final static short PREVIOUS_PAGE_ARROW_Y_OFFSET = 396;
	private final static short CHANGE_PAGE_ARROW_WIDTH = 28;
	private final static short CHANGE_PAGE_ARROW_HEIGHT = 26;
	private final static short NEXT_PAGE_ARROW_TEXT_X_OFFSET = 285;
	private final static short NEXT_PAGE_ARROW_TEXT_Y_OFFSET = 402;
	private final static short PREVIOUS_PAGE_ARROW_TEXT_X_OFFSET = 57;
	private final static short PREVIOUS_PAGE_ARROW_TEXT_Y_OFFSET = 402;
	int totalPage;
	
	private final Arrow nextPageArrow = new Arrow(0, 0, CHANGE_PAGE_ARROW_WIDTH, CHANGE_PAGE_ARROW_HEIGHT, ArrowDirection.RIGHT)
	{
	
		@Override
		public void eventButtonClick()
		{
			nextPage();
		}
		
		@Override
		public boolean activateCondition()
		{
			return (MailInboxFrame.this.currentPage < MailInboxFrame.this.totalPage);
		}
	};
	private final Arrow previousPageArrow = new Arrow(0, 0, CHANGE_PAGE_ARROW_WIDTH, CHANGE_PAGE_ARROW_HEIGHT, ArrowDirection.LEFT)
	{
	
		@Override
		public void eventButtonClick()
		{
			previousPage();
		}
		
		@Override
		public boolean activateCondition()
		{
			return (MailInboxFrame.this.currentPage > 0);
		}
	};
	
	public MailInboxFrame(MailFrame frame)
	{
		this.frame = frame;
		this.openedMailX = (short)(this.frame.getX() + OPENED_MAIL_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailY = (short)(this.frame.getY() + OPENED_MAIL_Y_OFFSET * Mideas.getDisplayYFactor());
		this.openedMailSubjectX = (short)(this.openedMailX + OPENED_MAIL_SUBJECT_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailSubjectY = (short)(this.openedMailY + OPENED_MAIL_SUBJECT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.openedMailSenderNameX = (short)(this.openedMailX + OPENED_MAIL_SENDER_NAME_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailSenderNameY = (short)(this.openedMailY + OPENED_MAIL_SENDER_NAME_Y_OFFSET * Mideas.getDisplayYFactor());
		this.nextPageArrowTextX = (short)(this.frame.getX() + NEXT_PAGE_ARROW_TEXT_X_OFFSET * Mideas.getDisplayXFactor());
		this.nextPageArrowTextY = (short)(this.frame.getY() + NEXT_PAGE_ARROW_TEXT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.previousPageArrowTextX = (short)(this.frame.getX() + PREVIOUS_PAGE_ARROW_TEXT_X_OFFSET * Mideas.getDisplayXFactor());
		this.previousPageArrowTextY = (short)(this.frame.getY() + PREVIOUS_PAGE_ARROW_TEXT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.nextPageArrow.update((this.frame.getX() + NEXT_PAGE_ARROW_X_OFFSET * Mideas.getDisplayXFactor()), this.frame.getY() + NEXT_PAGE_ARROW_Y_OFFSET * Mideas.getDisplayYFactor());
		this.previousPageArrow.update((this.frame.getX() + PREVIOUS_PAGE_ARROW_X_OFFSET * Mideas.getDisplayXFactor()), this.frame.getY() + PREVIOUS_PAGE_ARROW_Y_OFFSET * Mideas.getDisplayYFactor());
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
		drawOpenedMail();
		if (this.frame.getActiveFrame() == this)
			drawFrame();
	}
	
	public  void drawFrame()
	{
		Draw.drawQuad(Sprites.mail_inbox_frame, this.frame.getX(), this.frame.getY());
		this.nextPageArrow.draw();
		this.previousPageArrow.draw();
		changePageFont.drawBegin();
		changePageFont.drawStringShadowPart(this.nextPageArrowTextX, this.nextPageArrowTextY, "Next", Color.YELLOW, MailInboxButton.senderNameFontShadowColor, 1, 1, 1);
		changePageFont.drawStringShadowPart(this.previousPageArrowTextX, this.previousPageArrowTextY, "Prev", Color.YELLOW, MailInboxButton.senderNameFontShadowColor, 1, 1, 1);
		changePageFont.drawEnd();
		int i = -1;
		MailInboxButton.mailIcon.drawBegin();
		while (++i < this.buttonList.length && this.buttonList[i].getMail() != null)
			this.buttonList[i].drawTextureNotRead();
		MailInboxButton.mailIcon.drawEnd();
		i = -1;
		MailInboxButton.mailReadIcon.drawBegin();
		while (++i < this.buttonList.length && this.buttonList[i].getMail() != null)
			this.buttonList[i].drawTextureRead();
		MailInboxButton.mailReadIcon.drawEnd();
		i = -1;
		MailInboxButton.senderNameFont.drawBegin();
		while (++i < this.buttonList.length && this.buttonList[i].getMail() != null)
			this.buttonList[i].drawSenderAndSubject();
		MailInboxButton.senderNameFont.drawEnd();
		i = -1;
		MailInboxButton.expireDateFont.drawBegin();
		while (++i < this.buttonList.length && this.buttonList[i].getMail() != null)
			this.buttonList[i].drawExpireDate();
		MailInboxButton.expireDateFont.drawEnd();
		i = -1;
		Sprites.border.drawBegin();
		while (++i < this.buttonList.length && this.buttonList[i].getMail() != null)
			this.buttonList[i].drawBorder();
		Sprites.border.drawEnd();
		i = -1;
		while (++i < this.buttonList.length && this.buttonList[i].getMail() != null)
			this.buttonList[i].drawHover();
	}
	
	public void drawOpenedMail()
	{
		if (this.openedMail == null)
			return;
		Draw.drawQuad(Sprites.mail_opened_mail_frame, this.openedMailX, this.openedMailY);
		openedMailSenderFont.drawStringShadow(this.openedMailSenderNameX, this.openedMailSenderNameY, this.openedMail.getAuthorName(), Color.YELLOW, MailInboxButton.senderNameFontShadowColor, 1, 1, 1);
		openedMailSubjectFont.drawStringShadow(this.openedMailSubjectX, this.openedMailSubjectY, this.openedMail.getTitle(), Color.YELLOW, MailInboxButton.senderNameFontShadowColor, 1, 1, 1);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		return (false);
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (this.openedMail != null && openedMailMouseEvent())
			return (true);
		if (this.frame.getActiveFrame() != this)
			return (false);
		int i = -1;
		while (++i < this.buttonList.length)
			if (this.buttonList[i].mouseEvent())
				return (true);
		if (this.nextPageArrow.event())
			return (true);
		if (this.previousPageArrow.event())
			return (true);
		return (false);
	}
	
	public boolean openedMailMouseEvent()
	{
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
		++this.currentPage;
		fillMailPage();
	}
	
	public void previousPage()
	{
		--this.currentPage;
		fillMailPage();
	}
	
	public void fillMailPage()
	{
		int i = -1;
		final int pageOffset = this.currentPage * MAIL_PER_PAGE;
		while (++i < this.buttonList.length)
			if (i + pageOffset < MailMgr.getMailList().size())
				this.buttonList[i].setMail(MailMgr.getMailList().get(i + pageOffset));
			else
				this.buttonList[i].setMail(null);
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
		this.openedMailX = (short)(this.frame.getX() + OPENED_MAIL_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailY = (short)(this.frame.getY() + OPENED_MAIL_Y_OFFSET * Mideas.getDisplayYFactor());
		this.openedMailSubjectX = (short)(this.openedMailX + OPENED_MAIL_SUBJECT_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailSubjectY = (short)(this.openedMailY + OPENED_MAIL_SUBJECT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.openedMailSenderNameX = (short)(this.openedMailX + OPENED_MAIL_SENDER_NAME_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailSenderNameY = (short)(this.openedMailY + OPENED_MAIL_SENDER_NAME_Y_OFFSET * Mideas.getDisplayYFactor());
		this.nextPageArrowTextX = (short)(this.frame.getX() + NEXT_PAGE_ARROW_TEXT_X_OFFSET * Mideas.getDisplayXFactor());
		this.nextPageArrowTextY = (short)(this.frame.getY() + NEXT_PAGE_ARROW_TEXT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.previousPageArrowTextX = (short)(this.frame.getX() + PREVIOUS_PAGE_ARROW_TEXT_X_OFFSET * Mideas.getDisplayXFactor());
		this.previousPageArrowTextY = (short)(this.frame.getY() + PREVIOUS_PAGE_ARROW_TEXT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.nextPageArrow.update((this.frame.getX() + NEXT_PAGE_ARROW_X_OFFSET * Mideas.getDisplayXFactor()), this.frame.getY() + NEXT_PAGE_ARROW_Y_OFFSET * Mideas.getDisplayYFactor());
		this.previousPageArrow.update((this.frame.getX() + PREVIOUS_PAGE_ARROW_X_OFFSET * Mideas.getDisplayXFactor()), this.frame.getY() + PREVIOUS_PAGE_ARROW_Y_OFFSET * Mideas.getDisplayYFactor());
		int i = -1;
		while (++i < this.buttonList.length)
			this.buttonList[i].updateSize();
		this.shouldUpdateSize = false;
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
		{
			this.openedMail = mail;
			if (!this.openedMail.getRead())
				CommandMail.mailOpened(this.openedMail);
		}
	}
	
	public void onMailReceived()
	{
		fillMailPage();
		this.totalPage = MailMgr.getMailList().size() / MAIL_PER_PAGE;
		if (MailMgr.getMailList().size() % MAIL_PER_PAGE == 0)
			--this.totalPage;
	}
	
	public Mail getOpenedMail()
	{
		return (this.openedMail);
	}
}
