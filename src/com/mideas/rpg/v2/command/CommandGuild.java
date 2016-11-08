package com.mideas.rpg.v2.command;

import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.guild.Guild;
import com.mideas.rpg.v2.game.guild.GuildMember;
import com.mideas.rpg.v2.game.guild.GuildRank;

public class CommandGuild extends Command {
	
	@Override
	public void read() {
		byte packetId = ConnectionManager.getConnection().readByte();
		if(packetId == PacketID.GUILD_UPDATE_PERMISSION) {
			int rank_order = ConnectionManager.getConnection().readInt();
			int permission = ConnectionManager.getConnection().readInt();
			Mideas.joueur1().getGuild().getRank(rank_order).setPermission(permission);
		}
		else if(packetId == PacketID.GUILD_INVITE_PLAYER) {
			String player_name = ConnectionManager.getConnection().readString();
			String guild_name = ConnectionManager.getConnection().readString();
			//enable popup
		}
		else if(packetId == PacketID.GUILD_INIT) {
			int i = 0;
			int guildId = ConnectionManager.getConnection().readInt();
			int leaderId = ConnectionManager.getConnection().readInt();
			String guildName = ConnectionManager.getConnection().readString();
			String information = ConnectionManager.getConnection().readString();
			String motd = ConnectionManager.getConnection().readString();
			int rankListSize = ConnectionManager.getConnection().readInt();
			boolean isLeader = ConnectionManager.getConnection().readBoolean();
			ArrayList<GuildRank> rankList = new ArrayList<GuildRank>(rankListSize);
			while(i < rankListSize) {
				int rank_order = ConnectionManager.getConnection().readInt();
				String name = ConnectionManager.getConnection().readString();
				if(isLeader) {
					int permission = ConnectionManager.getConnection().readInt();
					rankList.add(new GuildRank(rank_order, permission, name));
				}
				else {
					rankList.add(new GuildRank(rank_order, 0, name));
				}
				i++;
			}
			i = 0;
			int memberListSize = ConnectionManager.getConnection().readInt();
			boolean canSeeOfficerNote = ConnectionManager.getConnection().readBoolean();
			ArrayList<GuildMember> memberList = new ArrayList<GuildMember>(memberListSize);
			if(canSeeOfficerNote) {
				while(i < memberListSize) {
					int id = ConnectionManager.getConnection().readInt();
					int level = ConnectionManager.getConnection().readInt();
					String name = ConnectionManager.getConnection().readString();
					ClassType type = ClassType.values()[ConnectionManager.getConnection().readChar()];
					String note = ConnectionManager.getConnection().readString();
					String officerNote = ConnectionManager.getConnection().readString();
					int rank = ConnectionManager.getConnection().readInt();
					boolean isOnline = ConnectionManager.getConnection().readBoolean();
					GuildRank guildRank = null;
					int j = 0;
					while(j < rankList.size()) {
						if(rankList.get(j).getOrder() == rank) {
							guildRank = rankList.get(j);
							break;
						}
						j++;
					}
					memberList.add(new GuildMember(id, name, level, guildRank, isOnline, note, officerNote, type));
				}
			}
			else {
				while(i < memberListSize) {
					int id = ConnectionManager.getConnection().readInt();
					int level = ConnectionManager.getConnection().readInt();
					String name = ConnectionManager.getConnection().readString();
					ClassType type = ClassType.values()[ConnectionManager.getConnection().readChar()];
					String note = ConnectionManager.getConnection().readString();
					int rank = ConnectionManager.getConnection().readInt();
					boolean isOnline = ConnectionManager.getConnection().readBoolean();
					GuildRank guildRank = null;
					int j = 0;
					while(j < rankList.size()) {
						if(rankList.get(j).getOrder() == rank) {
							guildRank = rankList.get(j);
							break;
						}
						j++;
					}
					memberList.add(new GuildMember(id, name, level, guildRank, isOnline, note, "", type));
				}
			}
			Mideas.joueur1().setGuild(new Guild(guildId, leaderId, guildName, information, motd, memberList, rankList));
		}
	}
}
