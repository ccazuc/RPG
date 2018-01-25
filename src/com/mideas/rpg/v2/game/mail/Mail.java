package com.mideas.rpg.v2.game.mail;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.utils.StringUtils;

public class Mail {

	private final long GUID;
	private final long deleteDate;
	private final String authorName;
	private final String title;
	private String content;
	private final int gold;
	private final boolean isCR;
	private final byte template;
	private final boolean canReply;
	private String expireDateString;
	private boolean isLoaded;
	private boolean read;
	
	public Mail(long GUID, long deleteDate, String authorName, String title, String content, int gold, boolean isCR, byte template, boolean read, boolean canReply)
	{
		this.GUID = GUID;
		this.deleteDate = deleteDate;
		this.authorName = authorName;
		this.title = title;
		this.content = content;
		this.gold = gold;
		this.isCR = isCR;
		this.template = template;
		this.read = read;
		this.canReply = canReply;
		updateExpireDateString();
	}
	
	public boolean isLoaded()
	{
		return (this.isLoaded);
	}
	
	public void setIsLoaded()
	{
		this.isLoaded = true;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}

	public void updateExpireDateString()
	{
		this.expireDateString = StringUtils.convertTimeToStringSimple(this.deleteDate - Mideas.getLoopTickTimer());
	}
	
	public String getExpireDateString()
	{
		return (this.expireDateString);
	}
	
	public long getGUID()
	{
		return (this.GUID);
	}
	
	public long getDeleteDate()
	{
		return (this.deleteDate);
	}
	
	public String getAuthorName()
	{
		return (this.authorName);
	}
	
	public String getTitle()
	{
		return (this.title);
	}
	
	public String getContent()
	{
		return (this.content);
	}
	
	public int getGold()
	{
		return (this.gold);
	}
	
	public boolean isCR()
	{
		return (this.isCR);
	}
	
	public byte getTemplate()
	{
		return (this.template);
	}
	
	public boolean getRead()
	{
		return (this.read);
	}
	
	public void readed()
	{
		this.read = true;
	}
	
	public boolean canReply()
	{
		return (this.canReply);
	}
}
