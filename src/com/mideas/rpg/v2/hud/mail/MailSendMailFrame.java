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
	private final static short CUROSR_WIDTH = 5;
	private final static short CUROSR_HEIGHT = 14;
	private final static TTF EDIT_BOX_FONT = FontManager.get("ARIALN", 14);
	final InputBox senderNameInputBox = new InputBox(this, SENDER_NAME_INPUT_BOX_X, SENDER_NAME_INPUT_BOX_Y, SENDER_NAME_INPUT_BOX_WIDTH, 10, 8, SENDER_NAME_INPUT_BOX_WIDTH, EDIT_BOX_FONT, false, CUROSR_WIDTH, CUROSR_HEIGHT, "", false)
	{
		
		@Override
		public boolean keyEvent(char c)
		{
			return (handleInputTab(this, MailSendMailFrame.this.subjectInputBox, MailSendMailFrame.this.copperInputBox, c));
		}
	};
	final InputBox subjectInputBox = new InputBox(this, SUBJECT_INPUT_BOX_X, SUBJECT_INPUT_BOX_Y, SUBJECT_INPUT_BOX_WIDTH, 60, 8, SUBJECT_INPUT_BOX_WIDTH - 20, EDIT_BOX_FONT, false, CUROSR_WIDTH, CUROSR_HEIGHT, "", false)
	{
		
		@Override
		public boolean keyEvent(char c)
		{
			return (handleInputTab(this, MailSendMailFrame.this.goldInputBox, MailSendMailFrame.this.senderNameInputBox, c));
		}
	};
	final InputBox goldInputBox = new InputBox(this, GOLD_INPUT_BOX_X, GOLD_INPUT_BOX_Y, GOLD_INPUT_BOX_WIDTH, 6, 8, GOLD_INPUT_BOX_WIDTH - 10, EDIT_BOX_FONT, true, CUROSR_WIDTH, CUROSR_HEIGHT, "", false)
	{
		
		@Override
		public boolean keyEvent(char c)
		{
			return (handleInputTab(this, MailSendMailFrame.this.silverInputBox, MailSendMailFrame.this.subjectInputBox, c));
		}
	};
	final InputBox silverInputBox = new InputBox(this, SILVER_INPUT_BOX_X, SILVER_INPUT_BOX_Y, SILVER_INPUT_BOX_WIDTH, 2, 8, SILVER_INPUT_BOX_WIDTH - 10, EDIT_BOX_FONT, true, CUROSR_WIDTH, CUROSR_HEIGHT, "", false)
	{
		
		@Override
		public boolean keyEvent(char c)
		{
			return (handleInputTab(this, MailSendMailFrame.this.copperInputBox, MailSendMailFrame.this.goldInputBox, c));
		}
	};
	final InputBox copperInputBox = new InputBox(this, COPPER_INPUT_BOX_X, COPPER_INPUT_BOX_Y, COPPER_INPUT_BOX_WIDTH, 2, 8, COPPER_INPUT_BOX_WIDTH - 10, EDIT_BOX_FONT, true, CUROSR_WIDTH, CUROSR_HEIGHT, "", false)
	{
		
		@Override
		public boolean keyEvent(char c)
		{
			return (handleInputTab(this, MailSendMailFrame.this.senderNameInputBox, MailSendMailFrame.this.silverInputBox, c));
		}
	};
	
	public MailSendMailFrame(MailFrame frame)
	{
		this.frame = frame;
		this.senderNameInputBox.initParentFrame(this);
		this.subjectInputBox.initParentFrame(this);
		this.goldInputBox.initParentFrame(this);
		this.silverInputBox.initParentFrame(this);
		this.copperInputBox.initParentFrame(this);
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
	
	public boolean handleInputTab(InputBox currentBox, InputBox onTabPressed, InputBox onShiftTabPressed, char c)
	{
		if (c == Input.ENTER_CHAR_VALUE || (c == Input.TAB_CHAR_VALUE && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
		{
			onTabPressed.setActive(true);
			return (true);
		}
		if (c == Input.TAB_CHAR_VALUE && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
		{
			onShiftTabPressed.setActive(true);
			return (true);
		}
		if (c == Input.ESCAPE_CHAR_VALUE)
		{
			currentBox.setActive(false);
			return (true);
		}
		return (false);
	}
	
	public void updateSize()
	{
		if (!this.shouldUpdateSize)
			return;
		this.senderNameInputBox.updateSize();
		this.subjectInputBox.updateSize();
		this.goldInputBox.updateSize();
		this.silverInputBox.updateSize();
		this.copperInputBox.updateSize();
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
	
	public void setTargetName(String name)
	{
		this.senderNameInputBox.setText(name);
	}
	
	public void setSubject(String subject)
	{
		this.subjectInputBox.setText(subject);
	}
	
	@Override
	public String getName()
	{
		return ("MailSendMailFrame");
	}
}
