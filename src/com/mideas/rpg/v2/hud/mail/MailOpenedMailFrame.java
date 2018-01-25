package com.mideas.rpg.v2.hud.mail;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.CommandMail;
import com.mideas.rpg.v2.game.mail.Mail;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Frame;
import com.mideas.rpg.v2.utils.LargeTextDisplay;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

public class MailOpenedMailFrame implements Frame {

	private final MailFrame frame;
	private Mail openedMail;
	private short openedMailFrameX;
	private short openedMailFrameY;
	private boolean shouldUpdateSize;
	private short openedMailSubjectX;
	private short openedMailSubjectY;
	private short openedMailSenderNameX;
	private short openedMailSenderNameY;
	private final static TTF openedMailSenderFont = FontManager.get("FRIZQT", 13);
	private final static TTF openedMailSubjectFont = FontManager.get("FRIZQT", 10);
	private final static short OPENED_MAIL_X_OFFSET = 407;
	private final static short OPENED_MAIL_Y_OFFSET = 0;
	private final static short OPENED_MAIL_SUBJECT_X_OFFSET = 128;
	private final static short OPENED_MAIL_SUBJECT_Y_OFFSET = 68;
	private final static short OPENED_MAIL_SENDER_NAME_X_OFFSET = 128;
	private final static short OPENED_MAIL_SENDER_NAME_Y_OFFSET = 46;
	private final static short BUTTON_WIDTH = 85;
	private final static short BUTTON_HEIGHT = 21;
	private final static short REPLY_BUTTON_X = 105;
	private final static short REPLY_BUTTON_Y = 410;
	private final static short CLOSE_BUTTON_X = 279;
	private final static short CLOSE_BUTTON_Y = 410;
	private final static short DELETE_BUTTON_X = 192;
	private final static short DELETE_BUTTON_Y = 410;
	private final static short MAIL_CONTENT_X = 19;
	private final static short MAIL_CONTENT_Y = 95;
	private final static short MAIL_CONTENT_WIDTH = 300;
	private final static short MAIL_CONTENT_HEIGHT = 251;
	private final static short MAIL_CONTENT_TEXT_X_OFFSET = 12;
	private final static short MAIL_CONTENT_TEXT_Y_OFFSET = 6;
	private final Button replyButton = new Button(this, REPLY_BUTTON_X, REPLY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, "Reply", 13, 1)
	{
	
		@SuppressWarnings("synthetic-access")
		@Override
		public void onLeftClickUp()
		{
			MailOpenedMailFrame.this.frame.replayOpenedMail(MailOpenedMailFrame.this.openedMail);
		}
		
		@SuppressWarnings("synthetic-access")
		@Override
		public boolean activateCondition()
		{
			return (MailOpenedMailFrame.this.openedMail.canReply());
		}
	};
	private final Button deleteButton = new Button(this, DELETE_BUTTON_X, DELETE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, "Delete", 13, 1)
	{

		@SuppressWarnings("synthetic-access")
		@Override
		public void onLeftClickUp()
		{
			CommandMail.deleteMail(MailOpenedMailFrame.this.openedMail);
		}
	};
	private final Button returnButton = new Button(this, DELETE_BUTTON_X, DELETE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, "Return", 13, 1)
	{

		@SuppressWarnings("synthetic-access")
		@Override
		public void onLeftClickUp()
		{
			CommandMail.returnMail(MailOpenedMailFrame.this.openedMail);
		}
	};
	private final Button closeButton = new Button(this, CLOSE_BUTTON_X, CLOSE_BUTTON_Y, (short)(BUTTON_WIDTH - 2), BUTTON_HEIGHT, "Close", 13, 1)
	{

		@Override
		public void onLeftClickUp()
		{
			close();
		}
	};
	private final LargeTextDisplay openedMailContent = new LargeTextDisplay(this, MAIL_CONTENT_X, MAIL_CONTENT_Y, MAIL_CONTENT_WIDTH, MAIL_CONTENT_HEIGHT, MAIL_CONTENT_TEXT_X_OFFSET, MAIL_CONTENT_TEXT_Y_OFFSET, (short)15, (short)0, (short)0, (short)0, (short)0, FontManager.get("MORPHEUS", 15), Color.BLACK);
	
	public MailOpenedMailFrame(MailFrame frame)
	{
		this.frame = frame;
		this.openedMailFrameX = (short)(this.frame.getX() + OPENED_MAIL_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailFrameY = (short)(this.frame.getY() + OPENED_MAIL_Y_OFFSET * Mideas.getDisplayYFactor());
		this.openedMailSubjectX = (short)(this.openedMailFrameX + OPENED_MAIL_SUBJECT_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailSubjectY = (short)(this.openedMailFrameY + OPENED_MAIL_SUBJECT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.openedMailSenderNameX = (short)(this.openedMailFrameX + OPENED_MAIL_SENDER_NAME_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailSenderNameY = (short)(this.openedMailFrameY + OPENED_MAIL_SENDER_NAME_Y_OFFSET * Mideas.getDisplayYFactor());
		this.replyButton.initParentFrame(this);
		this.deleteButton.initParentFrame(this);
		this.returnButton.initParentFrame(this);
		this.closeButton.initParentFrame(this);
		this.openedMailContent.initParentFrame(this);
	}
	
	@Override
	public void draw()
	{
		if (this.openedMail == null)
			return;
		updateSize();
		Draw.drawQuad(Sprites.mail_opened_mail_frame, this.openedMailFrameX, this.openedMailFrameY);
		openedMailSenderFont.drawStringShadow(this.openedMailSenderNameX, this.openedMailSenderNameY, this.openedMail.getAuthorName(), Color.YELLOW, MailInboxButton.senderNameFontShadowColor, 1, 1, 1);
		openedMailSubjectFont.drawStringShadow(this.openedMailSubjectX, this.openedMailSubjectY, this.openedMail.getTitle(), Color.YELLOW, MailInboxButton.senderNameFontShadowColor, 1, 1, 1);
		this.openedMailContent.draw();
		this.replyButton.draw2();
		if (this.openedMail.isCR())
			this.returnButton.draw2();
		else
			this.deleteButton.draw2();
		this.closeButton.draw2();
	}
	
	@Override
	public boolean keyboardEvent()
	{
		return (false);
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (this.openedMail == null)
			return (false);
		if (this.replyButton.event())
			return (true);
		if (this.openedMail.isCR() && this.returnButton.event())
			return (true);
		if (!this.openedMail.isCR() && this.deleteButton.event())
			return (true);
		if (this.closeButton.event())
			return (true);
		return (false);
	}
	
	@Override
	public void open()
	{
		
	}
	
	@Override
	public void close()
	{
		this.openedMail = null;
		this.replyButton.reset();
		this.deleteButton.reset();
		this.closeButton.reset();
		this.returnButton.reset();
	}
	
	@Override
	public int getX()
	{
		return (this.openedMailFrameX);
	}
	
	@Override
	public void setX(int x)
	{
		
	}
	
	@Override
	public int getY()
	{
		return (this.openedMailFrameY);
	}
	
	@Override
	public void setY(int y)
	{
		
	}
	
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.openedMailFrameX = (short)(this.frame.getX() + OPENED_MAIL_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailFrameY = (short)(this.frame.getY() + OPENED_MAIL_Y_OFFSET * Mideas.getDisplayYFactor());
		this.openedMailSubjectX = (short)(this.openedMailFrameX + OPENED_MAIL_SUBJECT_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailSubjectY = (short)(this.openedMailFrameY + OPENED_MAIL_SUBJECT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.openedMailSenderNameX = (short)(this.openedMailFrameX + OPENED_MAIL_SENDER_NAME_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailSenderNameY = (short)(this.openedMailFrameY + OPENED_MAIL_SENDER_NAME_Y_OFFSET * Mideas.getDisplayYFactor());
		this.replyButton.updateSize();
		this.deleteButton.updateSize();
		this.returnButton.updateSize();
		this.closeButton.updateSize();
		this.openedMailContent.updateSize();
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
		return (this.openedMail != null);
	}
	
	public void setOpenedMail(Mail mail)
	{
		if (this.openedMail == mail)
			this.openedMail = null;
		else
		{
			this.openedMail = mail;
			this.openedMailContent.setText(this.openedMail.getContent());
			if (!this.openedMail.getRead())
				CommandMail.mailOpened(this.openedMail);
		}
	}
	
	public void onMailDeleted(Mail mail)
	{
		if (this.openedMail == mail)
			close();
	}
	
	public void onMailLoaded(Mail mail)
	{
		if (this.openedMail != null && this.openedMail.getGUID() == mail.getGUID())
			this.openedMailContent.setText(mail.getContent());
	}
	
	public Mail getOpenedMail()
	{
		return (this.openedMail);
	}
	
	@Override
	public String getName()
	{
		return ("MailOpenedMailFrame");
	}
}
