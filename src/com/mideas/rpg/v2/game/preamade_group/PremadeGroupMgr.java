package com.mideas.rpg.v2.game.preamade_group;

import java.util.ArrayList;

public class PremadeGroupMgr {

	private final static ArrayList<PremadeGroup> premadeGroupList = new ArrayList<PremadeGroup>();
	private final static ArrayList<PremadeGroupReceivedApplication> receivedApplicationList = new ArrayList<PremadeGroupReceivedApplication>();
	private final static ArrayList<PremadeGroupSentApplication> sentApplicationList = new ArrayList<PremadeGroupSentApplication>();
	
	public static void clearPremadeGroupList()
	{
		premadeGroupList.clear();
	}
	
	public static void addGroup(PremadeGroup group)
	{
		premadeGroupList.add(group);
	}
	
	public static void addReceivedApplication(PremadeGroupReceivedApplication application)
	{
		receivedApplicationList.add(application);
	}
	
	public static void removeReceivedApplication(long applicationId)
	{
		for (int i = 0; i < receivedApplicationList.size(); ++i)
			if (receivedApplicationList.get(i).getId() == applicationId)
			{
				receivedApplicationList.remove(i);
				return;
			}
	}
	
	public static void addSentApplication(PremadeGroupSentApplication application)
	{
		sentApplicationList.add(application);
	}

	public static void removeSentApplication(long applicationId)
	{
		for (int i = 0; i < sentApplicationList.size(); ++i)
			if (sentApplicationList.get(i).getId() == applicationId)
			{
				sentApplicationList.remove(i);
				return;
			}
	}
	
	public static void delistGroup(long groupId)
	{
		for (int i = 0; i < premadeGroupList.size(); ++i)
			if (premadeGroupList.get(i).getId() == groupId)
			{
				premadeGroupList.remove(i);
				return;
			}
	}
}
