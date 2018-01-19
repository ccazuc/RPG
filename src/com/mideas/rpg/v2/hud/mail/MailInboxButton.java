package com.mideas.rpg.v2.hud.mail;

import org.lwjgl.input.Mouse;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.mail.Mail;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Texture;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

public class MailInboxButton {

	private final MailInboxFrame frame;
	private Mail mail;
	private final static short BUTTON_X_OFFSET = 29;
	private final static short BUTTON_Y_OFFSET = 86;
	private final static short SENDER_NAME_X_OFFSET = 74;
	private final static short SENDER_NAME_Y_OFFSET = 85;
	private final static short SUBJECT_X_OFFSET = 74;
	private final static short SUBJECT_Y_OFFSET = 97;
	private final static short EXPIRE_DATE_X_OFFSET = 340;
	private final static short EXPIRE_DATE_Y_OFFSET = 86;
	private short buttonX;
	private short buttonY;
	private short buttonWidth;
	private short buttonHeight;
	private short senderNameX;
	private short senderNameY;
	private short subjectX;
	private short subjectY;
	private short expireDateX;
	private short expireDateY;
	private final short yOffset;
	private boolean isMouseHover;
	private boolean mouseLeftClickDown;
	private boolean mouseRightClickDown;
	public final static Texture mailIcon = IconsManager.getSprite37("inv_misc_note_01");
	public final static Texture mailReadIcon = IconsManager.getSprite37("inv_misc_note_01_bw");
	//public final static Color senderNameFontShadowColor = Color.decode("#473517");
	public final static Color senderNameFontShadowColor = Color.decode("#271507");
	public final static Color senderNameFontColor = Color.decode("#B5B4B3");
	public final static TTF senderNameFont = FontManager.get("FRIZQT", 13);
	public final static TTF expireDateFont = FontManager.get("FRIZQT", 11);
	
	public MailInboxButton(MailInboxFrame frame, short yOffset)
	{
		this.frame = frame;
		this.buttonX = (short)(this.frame.getX() + BUTTON_X_OFFSET * Mideas.getDisplayXFactor());
		this.buttonY = (short)(this.frame.getY() + BUTTON_Y_OFFSET * Mideas.getDisplayYFactor() + yOffset * Mideas.getDisplayYFactor());
		this.buttonWidth = (short)(mailIcon.getWidth() * Mideas.getDisplayXFactor());
		this.buttonHeight = (short)(mailIcon.getHeight() * Mideas.getDisplayYFactor());
		this.senderNameX = (short)(this.frame.getX() + SENDER_NAME_X_OFFSET * Mideas.getDisplayXFactor());
		this.senderNameY = (short)(this.frame.getY() + SENDER_NAME_Y_OFFSET * Mideas.getDisplayYFactor() + yOffset * Mideas.getDisplayYFactor());
		this.subjectX = (short)(this.frame.getX() + SUBJECT_X_OFFSET * Mideas.getDisplayXFactor());
		this.subjectY = (short)(this.frame.getY() + SUBJECT_Y_OFFSET * Mideas.getDisplayYFactor() + yOffset * Mideas.getDisplayYFactor());
		this.expireDateX = (short)(this.frame.getX() + EXPIRE_DATE_X_OFFSET * Mideas.getDisplayXFactor());
		this.expireDateY = (short)(this.frame.getY() + EXPIRE_DATE_Y_OFFSET * Mideas.getDisplayYFactor() + yOffset * Mideas.getDisplayYFactor());
		this.yOffset = yOffset;		
	}
	
	public void drawTextureNotRead()
	{
		if (!this.mail.getRead())
			Draw.drawQuadPart(mailIcon, this.buttonX, this.buttonY, this.buttonWidth, this.buttonHeight);
	}
	
	public void drawTextureRead()
	{
		if (this.mail.getRead())
			Draw.drawQuadPart(mailReadIcon, this.buttonX, this.buttonY, this.buttonWidth, this.buttonHeight);
	}
	
	public void drawSenderAndSubject()
	{
		if (this.mail.getRead())
		{
			senderNameFont.drawStringShadowPart(this.senderNameX, this.senderNameY, this.mail.getAuthorName(), senderNameFontColor, senderNameFontShadowColor, 1, 1, 1);
			senderNameFont.drawStringShadowPart(this.subjectX, this.subjectY, this.mail.getTitle(), senderNameFontColor, senderNameFontShadowColor, 1, 1, 1);
		}
		else
		{
			senderNameFont.drawStringShadowPart(this.senderNameX, this.senderNameY, this.mail.getAuthorName(), Color.YELLOW, senderNameFontShadowColor, 1, 1, 1);
			senderNameFont.drawStringShadowPart(this.subjectX, this.subjectY, this.mail.getTitle(), Color.WHITE, senderNameFontShadowColor, 1, 1, 1);
		}
	}
	
	public void drawHover()
	{
		if (this.frame.getOpenedMail() == this.mail)
			Draw.drawQuadBlend(Sprites.bag_open_border, this.buttonX - 1, this.buttonY - 1, this.buttonWidth + 2, this.buttonHeight + 2);
		if (this.isMouseHover)
			Draw.drawQuadBlend(Sprites.button_hover_spellbar, this.buttonX - 1, this.buttonY - 1, this.buttonWidth + 2, this.buttonHeight + 2);
	}
	
	public void drawExpireDate()
	{
		if (this.mail.getDeleteDate() - Mideas.getLoopTickTimer() <= 86400000)
			expireDateFont.drawStringShadowPartReversed(this.expireDateX, this.expireDateY, this.mail.getExpireDateString(), Color.RED, senderNameFontShadowColor, 1, 1, 1);
		else
			expireDateFont.drawStringShadowPartReversed(this.expireDateX, this.expireDateY, this.mail.getExpireDateString(), Color.GREEN, senderNameFontShadowColor, 1, 1, 1);
			
	}
	
	public void drawBorder()
	{
		Draw.drawQuadPart(Sprites.border, this.buttonX - 2, this.buttonY - 2, this.buttonWidth + 3, this.buttonHeight + 3);
	}
	
	public boolean mouseEvent()
	{
		if (Mideas.getHover() && Mideas.mouseX() >= this.buttonX && Mideas.mouseX() <= this.buttonX + this.buttonWidth && Mideas.mouseY() >= this.buttonY && Mideas.mouseY() <= this.buttonY + this.buttonHeight)
		{
			this.isMouseHover = true;
			Mideas.setHover(false);
		}
		else
			this.isMouseHover = false;
		if (this.isMouseHover && Mouse.getEventButtonState())
		{
			if (Mouse.getEventButton() == 0)
				this.mouseLeftClickDown = true;
			else if (Mouse.getEventButton() == 1)
				this.mouseRightClickDown = true;
		}
		else
			if (this.isMouseHover && ((this.mouseLeftClickDown && Mouse.getEventButton() == 0) || (this.mouseRightClickDown && Mouse.getEventButton() == 1)))
			{
				this.mouseLeftClickDown = false;
				this.mouseRightClickDown = false;
				onClick();
				return (true);
			}
		return (false);
	}
	
	public void setMail(Mail mail)
	{
		this.mail = mail;
		if (this.mail == null)
		{
			this.isMouseHover = false;
			this.mouseLeftClickDown = false;
			this.mouseRightClickDown = false;
		}
	}
	
	public void onClick()
	{
		this.frame.setOpenedMail(this.mail);
	}
	
	public void updateSize()
	{
		this.buttonX = (short)(this.frame.getX() + BUTTON_X_OFFSET * Mideas.getDisplayXFactor());
		this.buttonY = (short)(this.frame.getY() + BUTTON_Y_OFFSET * Mideas.getDisplayYFactor() + this.yOffset * Mideas.getDisplayYFactor());
		this.buttonWidth = (short)(mailIcon.getWidth() * Mideas.getDisplayXFactor());
		this.buttonHeight = (short)(mailIcon.getHeight() * Mideas.getDisplayYFactor());
		this.senderNameX = (short)(this.frame.getX() + SENDER_NAME_X_OFFSET * Mideas.getDisplayXFactor());
		this.senderNameY = (short)(this.frame.getY() + SENDER_NAME_Y_OFFSET * Mideas.getDisplayYFactor() + this.yOffset * Mideas.getDisplayYFactor());
		this.subjectX = (short)(this.frame.getX() + SUBJECT_X_OFFSET * Mideas.getDisplayXFactor());
		this.subjectY = (short)(this.frame.getY() + SUBJECT_Y_OFFSET * Mideas.getDisplayYFactor() + this.yOffset * Mideas.getDisplayYFactor());
		this.expireDateX = (short)(this.frame.getX() + EXPIRE_DATE_X_OFFSET * Mideas.getDisplayXFactor());
		this.expireDateY = (short)(this.frame.getY() + EXPIRE_DATE_Y_OFFSET * Mideas.getDisplayYFactor() + this.yOffset * Mideas.getDisplayYFactor());
	}
	
	public Mail getMail()
	{
		return (this.mail);
	}
	
	public short getYOffset()
	{
		return (this.yOffset);
	}
}
