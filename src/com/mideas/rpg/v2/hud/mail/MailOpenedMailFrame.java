package com.mideas.rpg.v2.hud.mail;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.CommandMail;
import com.mideas.rpg.v2.game.mail.Mail;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Frame;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

public class MailOpenedMailFrame implements Frame {

	private final MailFrame frame;
	private Mail openedMail;
	private short openedMailX;
	private short openedMailY;
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
	
	public MailOpenedMailFrame(MailFrame frame)
	{
		this.frame = frame;
		this.openedMailX = (short)(this.frame.getX() + OPENED_MAIL_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailY = (short)(this.frame.getY() + OPENED_MAIL_Y_OFFSET * Mideas.getDisplayYFactor());
		this.openedMailSubjectX = (short)(this.openedMailX + OPENED_MAIL_SUBJECT_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailSubjectY = (short)(this.openedMailY + OPENED_MAIL_SUBJECT_Y_OFFSET * Mideas.getDisplayYFactor());
		this.openedMailSenderNameX = (short)(this.openedMailX + OPENED_MAIL_SENDER_NAME_X_OFFSET * Mideas.getDisplayXFactor());
		this.openedMailSenderNameY = (short)(this.openedMailY + OPENED_MAIL_SENDER_NAME_Y_OFFSET * Mideas.getDisplayYFactor());		
	}
	
	@Override
	public void draw()
	{
		if (this.openedMail == null)
			return;
		updateSize();
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
		if (this.openedMail == null)
			return (false);
		
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
	
	@Override
	public int getX()
	{
		return (this.frame.getX());
	}
	
	@Override
	public void setX(int x)
	{
		
	}
	
	@Override
	public int getY()
	{
		return (this.frame.getY());
	}
	
	@Override
	public void setY(int y)
	{
		
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
		}
	}
	
	public Mail getOpenedMail()
	{
		return (this.openedMail);
	}
}
