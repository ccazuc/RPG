package com.mideas.rpg.v2.hud.mail;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.CommandMail;
import com.mideas.rpg.v2.game.mail.Mail;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Frame;
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
	private boolean canReply;
	private final static TTF openedMailSenderFont = FontManager.get("FRIZQT", 13);
	private final static TTF openedMailSubjectFont = FontManager.get("FRIZQT", 10);
	private final static short OPENED_MAIL_X_OFFSET = 407;
	private final static short OPENED_MAIL_Y_OFFSET = 0;
	private final static short OPENED_MAIL_SUBJECT_X_OFFSET = 128;
	private final static short OPENED_MAIL_SUBJECT_Y_OFFSET = 68;
	private final static short OPENED_MAIL_SENDER_NAME_X_OFFSET = 128;
	private final static short OPENED_MAIL_SENDER_NAME_Y_OFFSET = 46;
	private final static short BUTTON_WIDTH = 100;
	private final static short BUTTON_HEIGHT = 20;
	private final static short REPLY_BUTTON_X = 0;
	private final static short REPLY_BUTTON_Y = 30;
	private final static short CLOSE_BUTTON_X = 0;
	private final static short CLOSE_BUTTON_Y = 30;
	private final static short DELETE_BUTTON_X = 0;
	private final static short DELETE_BUTTON_Y = 30;
	private final Button replyButton = new Button(this.frame, REPLY_BUTTON_X, REPLY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, "Reply", 12, 1)
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
			return (MailOpenedMailFrame.this.canReply);
		}
	};
	private final Button deleteButton = new Button(this.frame, DELETE_BUTTON_X, DELETE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, "Delete", 12, 1)
	{

		@SuppressWarnings("synthetic-access")
		@Override
		public void onLeftClickUp()
		{
			CommandMail.deleteMail(MailOpenedMailFrame.this.openedMail);
			this.reset();
		}
		
		@SuppressWarnings("synthetic-access")
		@Override
		public boolean activateCondition()
		{
			return (MailOpenedMailFrame.this.canReply);
		}
	};
	private final Button closeButton = new Button(this.frame, CLOSE_BUTTON_X, CLOSE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, "Close", 12, 1)
	{

		@Override
		public void onLeftClickUp()
		{
			onClose();
			this.reset();
		}
		
		@SuppressWarnings("synthetic-access")
		@Override
		public boolean activateCondition()
		{
			return (MailOpenedMailFrame.this.canReply);
		}
	};
	
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
		this.closeButton.initParentFrame(this);
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
		this.replyButton.draw();
		this.deleteButton.draw();
		this.closeButton.draw();
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
		if (this.deleteButton.event())
			return (true);
		if (this.closeButton.event())
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
		this.openedMail = null;
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
		this.closeButton.updateSize();
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
			if (!this.openedMail.getRead())
				CommandMail.mailOpened(this.openedMail);
			//this.canReply = 
		}
	}
	
	public void onMailDeleted(Mail mail)
	{
		if (this.openedMail == mail)
			onClose();
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
