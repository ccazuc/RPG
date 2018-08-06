package com.mideas.rpg.v2.game.preamade_group;

public class PremadeGroupReceivedApplication {

	private final long id;
	private final long applyTimer;
	private final String description;
	private final PremadeGroupApplicationMember[] memberList;
	private final PremadeGroupApplicationMember member;
	
	public PremadeGroupReceivedApplication(long id, long applyTimer, String description, PremadeGroupApplicationMember[] memberList)
	{
		this.id = id;
		this.applyTimer = applyTimer;
		this.description = description;
		this.memberList = memberList;
		this.member = null;
	}
	
	public PremadeGroupReceivedApplication(long id, long applyTimer, String description, PremadeGroupApplicationMember member)
	{
		this.id = id;
		this.applyTimer = applyTimer;
		this.description = description;
		this.memberList = null;
		this.member = member;
	}
	
	public long getId()
	{
		return (this.id);
	}
	
	public long getApplyTimer()
	{
		return (this.applyTimer);
	}
	
	public String getDescription()
	{
		return (this.description);
	}
	
	public boolean isParty()
	{
		return (this.memberList != null);
	}
	
	public PremadeGroupApplicationMember[] getMemberList()
	{
		return (this.memberList);
	}
	
	public PremadeGroupApplicationMember getMember()
	{
		return (this.member);
	}
}
