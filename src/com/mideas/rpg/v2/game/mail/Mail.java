package com.mideas.rpg.v2.game.mail;

public class Mail {

	private final long GUID;
	private final long deleteDate;
	private final String authorName;
	private final String title;
	private final String content;
	private final int gold;
	private final boolean isCR;
	private final byte template;
	private boolean read;
	
	public Mail(long GUID, long deleteDate, String authorName, String title, String content, int gold, boolean isCR, byte template, boolean read)
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
}
