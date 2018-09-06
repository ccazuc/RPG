package com.mideas.rpg.v2.hud.mail;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.mail.MailMgr;
import com.mideas.rpg.v2.render.Draw;
import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.TTF;
import com.mideas.rpg.v2.utils.Arrow;
import com.mideas.rpg.v2.utils.ArrowDirection;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Frame;

public class MailInboxFrame extends Frame
{

	private final MailFrame frame;
	int currentPage;
	private final MailInboxButton[] buttonList;
	private short nextPageArrowTextX;
	private short nextPageArrowTextY;
	private short previousPageArrowTextX;
	private short previousPageArrowTextY;
	int totalPage;
	
	private final static TTF changePageFont = FontManager.get("FRIZQT", 13);
	private final static byte MAIL_PER_PAGE = 7;
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
	
	private final Arrow nextPageArrow = new Arrow(0, 0, CHANGE_PAGE_ARROW_WIDTH, CHANGE_PAGE_ARROW_HEIGHT, ArrowDirection.RIGHT)
	{
	
		@Override
		public void onLeftClickUp()
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
		public void onLeftClickUp()
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
		super("MailFrameInboxFrame");
		this.frame = frame;
		this.nextPageArrowTextX = (short)(this.frame.getX() + NEXT_PAGE_ARROW_TEXT_X_OFFSET * Mideas.getDisplayXFactor());
		this.nextPageArrowTextY = (short)(this.frame.getY() + NEXT_PAGE_ARROW_TEXT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.previousPageArrowTextX = (short)(this.frame.getX() + PREVIOUS_PAGE_ARROW_TEXT_X_OFFSET * Mideas.getDisplayXFactor());
		this.previousPageArrowTextY = (short)(this.frame.getY() + PREVIOUS_PAGE_ARROW_TEXT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.nextPageArrow.update((this.frame.getX() + NEXT_PAGE_ARROW_X_OFFSET * Mideas.getDisplayXFactor()), this.frame.getY() + NEXT_PAGE_ARROW_Y_OFFSET * Mideas.getDisplayYFactor());
		this.previousPageArrow.update((this.frame.getX() + PREVIOUS_PAGE_ARROW_X_OFFSET * Mideas.getDisplayXFactor()), this.frame.getY() + PREVIOUS_PAGE_ARROW_Y_OFFSET * Mideas.getDisplayYFactor());
		this.buttonList = new MailInboxButton[MAIL_PER_PAGE];
		for (int i = 0; i < this.buttonList.length; ++i)
			this.buttonList[i] = new MailInboxButton(this, "MailFrameMailInboxButton" + (i + 1), (short)(45 * i));
		fillMailPage();			
	}
	
	@Override
	public void draw()
	{
		if (this.frame.getActiveFrame() == this)
		{
			updateSize();
			drawFrame();
		}
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
		MailInboxButton.mailIcon.drawBegin();
		for (int i = 0; i < this.buttonList.length && this.buttonList[i].getMail() != null; ++i)
			this.buttonList[i].drawTextureNotRead();
		MailInboxButton.mailIcon.drawEnd();
		MailInboxButton.mailReadIcon.drawBegin();
		for (int i = 0; i < this.buttonList.length && this.buttonList[i].getMail() != null; ++i)
			this.buttonList[i].drawTextureRead();
		MailInboxButton.mailReadIcon.drawEnd();
		MailInboxButton.senderNameFont.drawBegin();
		for (int i = 0; i < this.buttonList.length && this.buttonList[i].getMail() != null; ++i)
			this.buttonList[i].drawSenderAndSubject();
		MailInboxButton.senderNameFont.drawEnd();
		MailInboxButton.expireDateFont.drawBegin();
		for (int i = 0; i < this.buttonList.length && this.buttonList[i].getMail() != null; ++i)
			this.buttonList[i].drawExpireDate();
		MailInboxButton.expireDateFont.drawEnd();
		Sprites.border.drawBegin();
		for (int i = 0; i < this.buttonList.length && this.buttonList[i].getMail() != null; ++i)
			this.buttonList[i].drawBorder();
		Sprites.border.drawEnd();
		for (int i = 0; i < this.buttonList.length && this.buttonList[i].getMail() != null; ++i)
			this.buttonList[i].drawHover();
	}
	
	@Override
	public boolean keyboardEvent()
	{
		return (false);
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (this.frame.getActiveFrame() != this)
			return (false);
		for (int i = 0; i < this.buttonList.length; ++i)
			if (this.buttonList[i].mouseEvent())
				return (true);
		if (this.nextPageArrow.event())
			return (true);
		if (this.previousPageArrow.event())
			return (true);
		if (Mideas.getMouseScrolledTick() < 0 && this.currentPage < this.totalPage)
			nextPage();
		else if (Mideas.getMouseScrolledTick() > 0 && this.currentPage > 0)
			previousPage();
		if (Mideas.getHover() && Mideas.mouseX() >= this.x && Mideas.mouseX() <= this.x + Sprites.mail_inbox_frame.getImageWidth() * Mideas.getDisplayXFactor() && Mideas.mouseY() >= this.y && Mideas.mouseY() <= this.y + Sprites.mail_inbox_frame.getImageHeight() * Mideas.getDisplayYFactor())
			Mideas.setHover(this, false);
		return (false);
	}
	
	@Override
	public void open()
	{
		
	}
	
	@Override
	public void close()
	{
		this.currentPage = 0;
	}
	
	@Override
	public void reset()
	{
		this.nextPageArrow.reset();
		this.previousPageArrow.reset();
		this.totalPage = 0;
		this.currentPage = 0;
		int i = -1;
		while (++i < this.buttonList.length)
			this.buttonList[i].setMail(null);
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
	
	public void updateButtons()
	{
		final int pageOffset = this.currentPage * MAIL_PER_PAGE;
		for (int i = 0; i < this.buttonList.length; ++i)
			if (i + pageOffset < MailMgr.getMailList().size())
				this.buttonList[i].setMail(MailMgr.getMailList().get(i + pageOffset));
			else
				this.buttonList[i].setMail(null);	
	}
	
	public void fillMailPage()
	{
		updateButtons();
		this.totalPage = MailMgr.getMailList().size() / MAIL_PER_PAGE;
		if (MailMgr.getMailList().size() % MAIL_PER_PAGE == 0)
			--this.totalPage;
		if (this.currentPage > this.totalPage)
		{
			this.currentPage = this.totalPage <= 0 ? 0 : this.totalPage;
			updateButtons();
		}
	}

	@Override
	public short getX()
	{
		return (this.frame.getX());
	}
	
	@Override
	public void setX(float x)
	{
		this.frame.setX(x);
	}
	
	@Override
	public short getY()
	{
		return (this.frame.getY());
	}
	
	@Override
	public void setY(float y)
	{
		this.frame.setX(y);
	}
	
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.nextPageArrowTextX = (short)(this.frame.getX() + NEXT_PAGE_ARROW_TEXT_X_OFFSET * Mideas.getDisplayXFactor());
		this.nextPageArrowTextY = (short)(this.frame.getY() + NEXT_PAGE_ARROW_TEXT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.previousPageArrowTextX = (short)(this.frame.getX() + PREVIOUS_PAGE_ARROW_TEXT_X_OFFSET * Mideas.getDisplayXFactor());
		this.previousPageArrowTextY = (short)(this.frame.getY() + PREVIOUS_PAGE_ARROW_TEXT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.nextPageArrow.update((this.frame.getX() + NEXT_PAGE_ARROW_X_OFFSET * Mideas.getDisplayXFactor()), this.frame.getY() + NEXT_PAGE_ARROW_Y_OFFSET * Mideas.getDisplayYFactor());
		this.previousPageArrow.update((this.frame.getX() + PREVIOUS_PAGE_ARROW_X_OFFSET * Mideas.getDisplayXFactor()), this.frame.getY() + PREVIOUS_PAGE_ARROW_Y_OFFSET * Mideas.getDisplayYFactor());
		for (int i = 0; i < this.buttonList.length; ++i)
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
		return (this.frame.isOpen() && this.frame.getActiveFrame() == this);
	}
	
	public void onMailReceived()
	{
		fillMailPage();
	}
	
	public void onMailDeleted()
	{
		fillMailPage();
	}
	
	@Override
	public MailFrame getParentFrame()
	{
		return (this.frame);
	}
}
