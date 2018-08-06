package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.Connection;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.preamade_group.PremadeGroup;
import com.mideas.rpg.v2.game.preamade_group.PremadeGroupApplicationMember;
import com.mideas.rpg.v2.game.preamade_group.PremadeGroupMgr;
import com.mideas.rpg.v2.game.preamade_group.PremadeGroupReceivedApplication;
import com.mideas.rpg.v2.game.preamade_group.PremadeGroupSentApplication;
import com.mideas.rpg.v2.game.unit.ClassType;

public class CommandPremadeGroup extends Command {
	
	@Override
	public void read() {
		Connection connection = ConnectionManager.getWorldServerConnection();
		short packetId = connection.readShort();
		if (packetId == PacketID.PREMADE_GROUP_CREATE)
		{
			long id = connection.readLong();
			String title = connection.readString();
			String description = connection.readString();
			int requiredLevel = connection.readInt();
			long createTimer = connection.readLong();
			boolean isAutoAccept = connection.readBoolean();
			Mideas.joueur1().setPremadeGroup(new PremadeGroup(id, title, description, requiredLevel, isAutoAccept, createTimer));
		}
		else if (packetId == PacketID.PREMADE_GROUP_DELIST)
		{
			long id = connection.readLong();
			if (Mideas.joueur1().getPremadeGroup() != null && Mideas.joueur1().getPremadeGroup().getId() == id)
				Mideas.joueur1().setPremadeGroup(null);
			else
				PremadeGroupMgr.delistGroup(id);
		}
		else if (packetId == PacketID.PREMADE_GROUP_REQUEST_FETCH)
		{
			PremadeGroupMgr.clearPremadeGroupList();
			short numberGroup = connection.readShort();
			for (int i = 0; i < numberGroup; ++i)
			{
				long id = connection.readLong();
				String title = connection.readString();
				String description = connection.readString();
				String leaderName = connection.readString();
				byte numberMembers = connection.readByte();
				int requiredLevel = connection.readInt();
				long createTimer = connection.readLong();
				boolean isAutoAccept = connection.readBoolean();
				PremadeGroupMgr.addGroup(new PremadeGroup(id, title, description, leaderName, requiredLevel, numberMembers, isAutoAccept, createTimer));
			}
		}
		else if (packetId == PacketID.PREMADE_GROUP_APPLICATION_CANCELED_FROM_GROUP)
		{
			long applicationId = connection.readLong();
			PremadeGroupMgr.removeReceivedApplication(applicationId);
		}
		else if (packetId == PacketID.PREMADE_GROUP_ADD_APPLICATION)
		{
			long applicationId = connection.readLong();
			String description = connection.readString();
			long createTimer = connection.readLong();
			byte numberMembers = connection.readByte();
			if (numberMembers == 1)
				PremadeGroupMgr.addReceivedApplication(new PremadeGroupReceivedApplication(applicationId, createTimer, description, readApplicationMember(connection)));
			else
			{
				PremadeGroupApplicationMember[] memberArray = new PremadeGroupApplicationMember[numberMembers];
				for (int i = 0; i < numberMembers; ++i)
					memberArray[i] = readApplicationMember(connection);
				PremadeGroupMgr.addReceivedApplication(new PremadeGroupReceivedApplication(applicationId, createTimer, description, memberArray));
			}
		}
		else if (packetId == PacketID.PREMADE_GROUP_SEND_APPLICATION)
		{
			long applicationId = connection.readLong();
			long groupId = connection.readLong();
			long createTimer = connection.readLong();
			PremadeGroupMgr.addSentApplication(new PremadeGroupSentApplication(applicationId, groupId, createTimer));
		}
		else if (packetId == PacketID.PREMADE_GROUP_CANCEL_APPLICATION)
		{
			long applicationId = connection.readLong();
			PremadeGroupMgr.removeSentApplication(applicationId);
		}
	}
	
	private static PremadeGroupApplicationMember readApplicationMember(Connection connection)
	{
		//						1: Name			2: Level			3: ClassType				4: isLeader
		return (new PremadeGroupApplicationMember(connection.readString(), connection.readInt(), ClassType.getValue(connection.readByte()), connection.readBoolean()));
	}
}
