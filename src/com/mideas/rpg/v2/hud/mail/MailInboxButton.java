package com.mideas.rpg.v2.hud.mail;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.mail.Mail;

public class MailInboxButton {

	private final MailInboxFrame frame;
	private Mail mail;
	private final static short BUTTON_X_OFFSET = 100;
	private short buttonX;
	private short buttonY;
	private short buttonWidth;
	private short buttonHeight;
	private final short yOffset;
	private boolean isMouseHover;
	
	public MailInboxButton(MailInboxFrame frame, short yOffset)
	{
		this.frame = frame;
		this.buttonX = (short)(this.frame.getX() + Mideas.getDisplayXFactor() * BUTTON_X_OFFSET);
		this.buttonY = (short)(this.frame.getY() * Mideas.getDisplayXFactor() * yOffset);
		this.yOffset = yOffset;		
	}
	
	public void draw()
	{
		if (this.isMouseHover)
			;
	}
	
	public boolean mouseEvent()
	{
		this.isMouseHover = false;
		if (Mideas.mouseX() >= this.buttonX && Mideas.mouseX() <= this.buttonX + this.buttonWidth && Mideas.mouseY() >= this.buttonY && Mideas.mouseY() <= this.buttonY + this.buttonHeight)
			this.isMouseHover = true;
		if (!Mouse.getEventButtonState())
			if (Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1)
				onOpen();
		return (false);
	}
	
	public void setMail(Mail mail)
	{
		this.mail = mail;
	}
	
	public void onOpen()
	{
		this.frame.setOpenedMail(this.mail);
	}
	
	public void updateSize()
	{
		this.buttonX = (short)(this.frame.getX() + Mideas.getDisplayXFactor() * BUTTON_X_OFFSET);
		this.buttonY = (short)(this.frame.getY() * Mideas.getDisplayXFactor() * this.yOffset);
	}
}
