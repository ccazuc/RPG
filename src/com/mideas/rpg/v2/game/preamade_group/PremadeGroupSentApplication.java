package com.mideas.rpg.v2.game.preamade_group;

public class PremadeGroupSentApplication {

	private final long id;
	private final long groupId;
	private final long createTimer;
	
	public PremadeGroupSentApplication(long id, long groupId, long createTimer)
	{
		this.id = id;
		this.groupId = groupId;
		this.createTimer = createTimer;
	}
	
	public final long getId()
	{
		return (this.id);
	}
	
	public final long getGroupId()
	{
		return (this.groupId);
	}
	
	public final long getCreateTimer()
	{
		return (this.createTimer);
	}
}
