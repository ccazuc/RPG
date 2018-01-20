package com.mideas.rpg.v2.hud.mail;

import org.lwjgl.input.Keyboard;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.Frame;
import com.mideas.rpg.v2.utils.Input;
import com.mideas.rpg.v2.utils.InputBox;
import com.mideas.rpg.v2.utils.render.Draw;
import com.mideas.rpg.v2.utils.render.Sprites;
import com.mideas.rpg.v2.utils.render.TTF;

public class MailSendMailFrame implements Frame
{

	private final MailFrame frame;
	private boolean shouldUpdateSize;
	private final static short SENDER_NAME_INPUT_BOX_X = 97;
	private final static short SENDER_NAME_INPUT_BOX_Y = 41;
	private final static short SENDER_NAME_INPUT_BOX_WIDTH = 123;
	private final static short SUBJECT_INPUT_BOX_X = 97;
	private final static short SUBJECT_INPUT_BOX_Y = 66;
	private final static short SUBJECT_INPUT_BOX_WIDTH = 251;
	private final static short GOLD_INPUT_BOX_X = 26;
	private final static short GOLD_INPUT_BOX_Y = 379;
	private final static short GOLD_INPUT_BOX_WIDTH = 79;
	private final static short SILVER_INPUT_BOX_X = 111;
	private final static short SILVER_INPUT_BOX_Y = 379;
	private final static short SILVER_INPUT_BOX_WIDTH = 45;
	private final static short COPPER_INPUT_BOX_X = 162;
	private final static short COPPER_INPUT_BOX_Y = 379;
	private final static short COPPER_INPUT_BOX_WIDTH = 45;
	private final static TTF EDIT_BOX_FONT = FontManager.get("ARIALN", 14);
	final InputBox senderNameInputBox = new InputBox(0, 0, SENDER_NAME_INPUT_BOX_WIDTH, 10, 8, SENDER_NAME_INPUT_BOX_WIDTH, EDIT_BOX_FONT, false, 5, 14, "", false)
	{
		
		@Override
		public boolean keyEvent(char c)
		{
			if (c == Input.ENTER_CHAR_VALUE || (c == Input.TAB_CHAR_VALUE && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
			{
				MailSendMailFrame.this.subjectInputBox.setActive(true);
				return (true);
			}
			if (c == Input.TAB_CHAR_VALUE && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
			{
				return (true);
			}
			if (c == Input.ESCAPE_CHAR_VALUE)
			{
				setActive(false);
				return (true);
			}
			return (false);
		}
	};
	final InputBox subjectInputBox = new InputBox(0, 0, SUBJECT_INPUT_BOX_WIDTH, 60, 8, SUBJECT_INPUT_BOX_WIDTH - 20, EDIT_BOX_FONT, false, 5, 14, "", false)
	{
		
		@Override
		public boolean keyEvent(char c)
		{
			if (c == Input.ENTER_CHAR_VALUE || (c == Input.TAB_CHAR_VALUE && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
			{
				//InputBox.setActive(true);
				return (true);
			}
			if (c == Input.TAB_CHAR_VALUE && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
			{
				MailSendMailFrame.this.senderNameInputBox.setActive(true);
				return (true);
			}
			if (c == Input.ESCAPE_CHAR_VALUE)
			{
				setActive(false);
				return (true);
			}
			return (false);
		}
	};
	final InputBox goldInputBox = new InputBox(0, 0, GOLD_INPUT_BOX_WIDTH, 6, 8, GOLD_INPUT_BOX_WIDTH - 10, EDIT_BOX_FONT, true, 5, 14, "", false)
	{
		
		@Override
		public boolean keyEvent(char c)
		{
			if (c == Input.ENTER_CHAR_VALUE || (c == Input.TAB_CHAR_VALUE && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
			{
				MailSendMailFrame.this.silverInputBox.setActive(true);
				return (true);
			}
			if (c == Input.TAB_CHAR_VALUE && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
			{
				MailSendMailFrame.this.senderNameInputBox.setActive(true);
				return (true);
			}
			if (c == Input.ESCAPE_CHAR_VALUE)
			{
				setActive(false);
				return (true);
			}
			return (false);
		}
	};
	final InputBox silverInputBox = new InputBox(0, 0, SILVER_INPUT_BOX_WIDTH, 2, 8, SILVER_INPUT_BOX_WIDTH - 10, EDIT_BOX_FONT, true, 5, 14, "", false)
	{
		
		@Override
		public boolean keyEvent(char c)
		{
			if (c == Input.ENTER_CHAR_VALUE || (c == Input.TAB_CHAR_VALUE && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
			{
				MailSendMailFrame.this.copperInputBox.setActive(true);
				return (true);
			}
			if (c == Input.TAB_CHAR_VALUE && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
			{
				MailSendMailFrame.this.goldInputBox.setActive(true);
				return (true);
			}
			if (c == Input.ESCAPE_CHAR_VALUE)
			{
				setActive(false);
				return (true);
			}
			return (false);
		}
	};
	final InputBox copperInputBox = new InputBox(0, 0, COPPER_INPUT_BOX_WIDTH, 2, 8, COPPER_INPUT_BOX_WIDTH - 10, EDIT_BOX_FONT, true, 5, 14, "", false)
	{
		
		@Override
		public boolean keyEvent(char c)
		{
			if (c == Input.ENTER_CHAR_VALUE || (c == Input.TAB_CHAR_VALUE && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
			{
				MailSendMailFrame.this.senderNameInputBox.setActive(true);
				return (true);
			}
			if (c == Input.TAB_CHAR_VALUE && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
			{
				MailSendMailFrame.this.silverInputBox.setActive(true);
				return (true);
			}
			if (c == Input.ESCAPE_CHAR_VALUE)
			{
				setActive(false);
				return (true);
			}
			return (false);
		}
	};
	
	public MailSendMailFrame(MailFrame frame)
	{
		this.frame = frame;
		this.senderNameInputBox.update(this.frame.getX() + SENDER_NAME_INPUT_BOX_X * Mideas.getDisplayXFactor(), this.frame.getY() + SENDER_NAME_INPUT_BOX_Y * Mideas.getDisplayYFactor());
		this.subjectInputBox.update(this.frame.getX() + SUBJECT_INPUT_BOX_X * Mideas.getDisplayXFactor(), this.frame.getY() + SUBJECT_INPUT_BOX_Y * Mideas.getDisplayYFactor());
		this.goldInputBox.update(this.frame.getX() + GOLD_INPUT_BOX_X * Mideas.getDisplayXFactor(), this.frame.getY() + GOLD_INPUT_BOX_Y * Mideas.getDisplayYFactor());
		this.silverInputBox.update(this.frame.getX() + SILVER_INPUT_BOX_X * Mideas.getDisplayXFactor(), this.frame.getY() + SILVER_INPUT_BOX_Y * Mideas.getDisplayYFactor());
		this.copperInputBox.update(this.frame.getX() + COPPER_INPUT_BOX_X * Mideas.getDisplayXFactor(), this.frame.getY() + COPPER_INPUT_BOX_Y * Mideas.getDisplayYFactor());
	}
	
	@Override
	public void draw()
	{
		long time = System.nanoTime();
		if (this.frame.getActiveFrame() != this)
			return;
		updateSize();
		Draw.drawQuad(Sprites.mail_send_mail_frame, this.frame.getX(), this.frame.getY());
		Sprites.edit_box.drawBegin();
		this.senderNameInputBox.drawTexturePart();
		this.subjectInputBox.drawTexturePart();
		this.goldInputBox.drawTexturePart();
		this.silverInputBox.drawTexturePart();
		this.copperInputBox.drawTexturePart();
		Sprites.edit_box.drawEnd();
		this.senderNameInputBox.drawString();
		this.subjectInputBox.drawString();
		this.goldInputBox.drawString();
		this.silverInputBox.drawString();
		this.copperInputBox.drawString();
		Mideas.nTime(time, "MailSendMailFrame draw time");
	}
	
	@Override
	public boolean mouseEvent()
	{
		if (this.frame.getActiveFrame() != this)
			return (false);
		if (this.senderNameInputBox.mouseEvent())
			return (true);
		if (this.subjectInputBox.mouseEvent())
			return (true);
		if (this.goldInputBox.mouseEvent())
			return (true);
		if (this.silverInputBox.mouseEvent())
			return (true);
		if (this.copperInputBox.mouseEvent())
			return (true);
		return (false);
	}
	
	@Override
	public boolean keyboardEvent()
	{
		if (this.frame.getActiveFrame() != this)
			return (false);
		if (this.senderNameInputBox.keyboardEvent())
			return (true);
		if (this.subjectInputBox.keyboardEvent())
			return (true);
		if (this.goldInputBox.keyboardEvent())
			return (true);
		if (this.silverInputBox.keyboardEvent())
			return (true);
		if (this.copperInputBox.keyboardEvent())
			return (true);
		return (false);
	}
	
	@Override
	public void onOpen()
	{
		this.senderNameInputBox.setActive(true);
		this.senderNameInputBox.setCursorEndOfText();
	}
	
	@Override
	public void onClose()
	{
		this.senderNameInputBox.setActive(false);
		this.subjectInputBox.setActive(false);
	}
	
	@Override
	public boolean isOpen()
	{
		return (this.frame.getActiveFrame() == this);
	}
	
	@Override
	public void shouldUpdateSize()
	{
		this.shouldUpdateSize = true;
	}
	
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.senderNameInputBox.update(this.frame.getX() + SENDER_NAME_INPUT_BOX_X * Mideas.getDisplayXFactor(), this.frame.getY() + SENDER_NAME_INPUT_BOX_Y * Mideas.getDisplayYFactor());
		this.subjectInputBox.update(this.frame.getX() + SUBJECT_INPUT_BOX_X * Mideas.getDisplayXFactor(), this.frame.getY() + SUBJECT_INPUT_BOX_Y * Mideas.getDisplayYFactor());
		this.goldInputBox.update(this.frame.getX() + GOLD_INPUT_BOX_X * Mideas.getDisplayXFactor(), this.frame.getY() + GOLD_INPUT_BOX_Y * Mideas.getDisplayYFactor());
		this.silverInputBox.update(this.frame.getX() + SILVER_INPUT_BOX_X * Mideas.getDisplayXFactor(), this.frame.getY() + SILVER_INPUT_BOX_Y * Mideas.getDisplayYFactor());
		this.copperInputBox.update(this.frame.getX() + COPPER_INPUT_BOX_X * Mideas.getDisplayXFactor(), this.frame.getY() + COPPER_INPUT_BOX_Y * Mideas.getDisplayYFactor());
		this.shouldUpdateSize = false;
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
}
