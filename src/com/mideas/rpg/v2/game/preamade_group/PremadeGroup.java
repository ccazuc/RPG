package com.mideas.rpg.v2.game.preamade_group;

public class PremadeGroup {

	private String title;
	private String description;
	private String leaderName;
	private int requiredLevel;
	private int numberMembers;
	private boolean isAutoAccept;
	private final long id;
	private final long createTimer;
	
	/*
	 * Used for other PremadeGroup
	 */
	public PremadeGroup(long id, String title, String description, String leaderName, int requiredLevel, int numberMembers, boolean isAutoAccept,  long createTimer)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.numberMembers = numberMembers;
		this.leaderName = leaderName;
		this.createTimer = createTimer;
		this.requiredLevel = requiredLevel;
		this.isAutoAccept = isAutoAccept;
	}
	
	/*
	 * Used for your own Premadegroup
	 */
	public PremadeGroup(long id, String title, String description, int requiredLevel, boolean isAutoAccept,  long createTimer)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.createTimer = createTimer;
		this.requiredLevel = requiredLevel;
		this.isAutoAccept = isAutoAccept;
	}
	
	public final int getNumberMembers()
	{
		return (this.numberMembers);
	}
	
	public final int getRequiredLevel()
	{
		return (this.requiredLevel);
	}
	
	public final boolean getIsAutoAccept()
	{
		return (this.isAutoAccept);
	}
	
	public final String getTitle()
	{
		return (this.title);
	}
	
	public final String getDescription()
	{
		return (this.description);
	}
	
	public final String getLeaderName()
	{
		return (this.leaderName);
	}
	
	public final long getId()
	{
		return (this.id);
	}
	
	public final long createTimer()
	{
		return (this.createTimer);
	}
}
