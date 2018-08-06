package com.mideas.rpg.v2.game.preamade_group;

import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.utils.Color;

public class PremadeGroupApplicationMember {

	private final String name;
	private final int level;
	private final ClassType type;
	private final Color color;
	private final boolean isLeader;
	
	public PremadeGroupApplicationMember(String name, int level, ClassType type, boolean isLeader)
	{
		this.name = name;
		this.level = level;
		this.type = type;
		this.isLeader = isLeader;
		this.color = null;
	}
	
	public String getName()
	{
		return (this.name);
	}
	
	public int getLevel()
	{
		return (this.level);
	}
	
	public boolean isLeader()
	{
		return (this.isLeader);
	}
}
