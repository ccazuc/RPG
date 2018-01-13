package com.mideas.rpg.v2.command;

import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.guild.Guild;
import com.mideas.rpg.v2.game.guild.GuildEvent;
import com.mideas.rpg.v2.game.guild.GuildJournalEventType;
import com.mideas.rpg.v2.game.guild.GuildMember;
import com.mideas.rpg.v2.game.guild.GuildRank;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.hud.PopupFrame;
import com.mideas.rpg.v2.hud.social.guild.GuildFrame;

public class CommandGuild extends Command {
	
	@Override
	public void read() {
		short packetId = ConnectionManager.getWorldServerConnection().readShort();
		if(packetId == PacketID.GUILD_UPDATE_PERMISSION) {
			int rank_order = ConnectionManager.getWorldServerConnection().readInt();
			int permission = ConnectionManager.getWorldServerConnection().readInt();
			String name = ConnectionManager.getWorldServerConnection().readString();
			Mideas.joueur1().getGuild().getRank(rank_order).setPermission(permission);
			Mideas.joueur1().getGuild().getRank(rank_order).setName(name);
			GuildFrame.fillDropDownMenuWithRank();
			Mideas.joueur1().setGuildRank(Mideas.joueur1().getGuildRank());
		}
		else if(packetId == PacketID.GUILD_INVITE_PLAYER) {
			String player_name = ConnectionManager.getWorldServerConnection().readString();
			String guild_name = ConnectionManager.getWorldServerConnection().readString();
			PopupFrame.activateGuildInvitationPopup(player_name, guild_name);
			ChatFrame.addMessage(new Message(" invited you to join ".concat(guild_name), player_name, false, MessageType.SELF, false));
		}
		else if(packetId == PacketID.GUILD_INIT) {
			int i = 0;
			int guildId = ConnectionManager.getWorldServerConnection().readInt();
			int leaderId = ConnectionManager.getWorldServerConnection().readInt();
			String guildName = ConnectionManager.getWorldServerConnection().readString();
			String information = ConnectionManager.getWorldServerConnection().readString();
			String motd = ConnectionManager.getWorldServerConnection().readString();
			int rankListSize = ConnectionManager.getWorldServerConnection().readInt();
			ArrayList<GuildRank> rankList = new ArrayList<GuildRank>(rankListSize);
			while(i < rankListSize) {
				int rank_order = ConnectionManager.getWorldServerConnection().readInt();
				String name = ConnectionManager.getWorldServerConnection().readString();
				int permission = ConnectionManager.getWorldServerConnection().readInt();
				rankList.add(new GuildRank(rank_order, permission, name));
				i++;
			}
			i = 0;
			int memberListSize = ConnectionManager.getWorldServerConnection().readInt();
			boolean canSeeOfficerNote = ConnectionManager.getWorldServerConnection().readBoolean();
			ArrayList<GuildMember> memberList = new ArrayList<GuildMember>(memberListSize);
			if(canSeeOfficerNote) {
				while(i < memberListSize) {
					int id = ConnectionManager.getWorldServerConnection().readInt();
					int level = ConnectionManager.getWorldServerConnection().readInt();
					String name = ConnectionManager.getWorldServerConnection().readString();
					ClassType type = ClassType.values()[ConnectionManager.getWorldServerConnection().readByte()];
					String note = ConnectionManager.getWorldServerConnection().readString();
					String officerNote = ConnectionManager.getWorldServerConnection().readString();
					int rank = ConnectionManager.getWorldServerConnection().readInt();
					boolean isOnline = ConnectionManager.getWorldServerConnection().readBoolean();
					long lastLoginTimer = ConnectionManager.getWorldServerConnection().readLong();
					GuildRank guildRank = null;
					int j = 0;
					while(j < rankList.size()) {
						if(rankList.get(j).getOrder() == rank) {
							guildRank = rankList.get(j);
							break;
						}
						j++;
					}
					memberList.add(new GuildMember(id, name, level, guildRank, isOnline, note, officerNote, type, lastLoginTimer));
					i++;
				}
			}
			else {
				while(i < memberListSize) {
					int id = ConnectionManager.getWorldServerConnection().readInt();
					int level = ConnectionManager.getWorldServerConnection().readInt();
					String name = ConnectionManager.getWorldServerConnection().readString();
					ClassType type = ClassType.values()[ConnectionManager.getWorldServerConnection().readByte()];
					String note = ConnectionManager.getWorldServerConnection().readString();
					int rank = ConnectionManager.getWorldServerConnection().readInt();
					boolean isOnline = ConnectionManager.getWorldServerConnection().readBoolean();
					long lastLoginTimer = ConnectionManager.getWorldServerConnection().readLong();
					GuildRank guildRank = null;
					int j = 0;
					while(j < rankList.size()) {
						if(rankList.get(j).getOrder() == rank) {
							guildRank = rankList.get(j);
							break;
						}
						j++;
					}
					memberList.add(new GuildMember(id, name, level, guildRank, isOnline, note, "", type, lastLoginTimer));
					i++;
				}
			}
			Mideas.joueur1().setGuild(new Guild(guildId, leaderId, guildName, information, motd, memberList, rankList));
			Mideas.joueur1().setGuildRank(Mideas.joueur1().getGuild().getMember(Mideas.joueur1().getId()).getRank());
			Mideas.joueur1().getGuild().getMember(Mideas.joueur1().getId()).setOnlineStatus(true);
			Mideas.joueur1().getGuild().initOnlineMembers();
			ChatFrame.addMessage(new Message("[Guild Message Of The Day] : ".concat(Mideas.joueur1().getGuild().getMotd()), false, MessageType.SELF, MessageType.GUILD.getColor()));
		}
		else if(packetId == PacketID.GUILD_NEW_MEMBER) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			String name = ConnectionManager.getWorldServerConnection().readString();
			String note = ConnectionManager.getWorldServerConnection().readString();
			String officerNote = ConnectionManager.getWorldServerConnection().readString();
			int rankOrder = ConnectionManager.getWorldServerConnection().readInt();
			GuildRank rank = Mideas.joueur1().getGuild().getRank(rankOrder);
			int level = ConnectionManager.getWorldServerConnection().readInt();
			boolean isOnline = ConnectionManager.getWorldServerConnection().readBoolean();
			ClassType type = ClassType.values()[ConnectionManager.getWorldServerConnection().readByte()];
			long lastLoginTimer = ConnectionManager.getWorldServerConnection().readLong();
			Mideas.joueur1().getGuild().addMember(new GuildMember(id, name, level, rank, isOnline, note, officerNote, type, lastLoginTimer));
			ChatFrame.addMessage(new Message(name.concat(" joined the guild."), false, MessageType.SELF));
		}
		else if(packetId == PacketID.GUILD_KICK_MEMBER) {
			String officerName = ConnectionManager.getWorldServerConnection().readString();
			int id = ConnectionManager.getWorldServerConnection().readInt();
			GuildMember member = Mideas.joueur1().getGuild().getMember(id);
			if(id != Mideas.joueur1().getId()) {
				ChatFrame.addMessage(new Message(new StringBuilder().append(member.getName()).append(" has been kicked out of the guild by ").append(officerName).toString(), false, MessageType.SELF));
				Mideas.joueur1().getGuild().removeMember(id);
			}
			else {
				ChatFrame.addMessage(new Message("you have been kicked out of the guild by ".concat(officerName), false, MessageType.SELF));
				Mideas.joueur1().setGuild(null);
			}
		}
		else if(packetId == PacketID.GUILD_ONLINE_PLAYER) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			GuildMember member = Mideas.joueur1().getGuild().getMember(id);
			if(member == null) {
				return;
			}
			member.setOnlineStatus(true);
			member.updateLastLoginTimerString();
			if(Mideas.joueur1().getFriend(id) == null && id != Mideas.joueur1().getId()) {
				ChatFrame.addMessage(new Message(" is now online.", member.getName(), false, MessageType.SELF, false));
			}
			Mideas.joueur1().getGuild().memberLoggedIn();
		}
		else if(packetId == PacketID.GUILD_OFFLINE_PLAYER) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			GuildMember member = Mideas.joueur1().getGuild().getMember(id);
			if(member == null) {
				return;
			}
			member.setOnlineStatus(false);
			member.setLastLoginTimer(Mideas.getLoopTickTimer());
			member.updateLastLoginTimerString();
			if(Mideas.joueur1().getFriend(id) == null) {
				ChatFrame.addMessage(new Message(member.getName().concat(" is now offline."), false, MessageType.SELF));
			}
			Mideas.joueur1().getGuild().memberLoggedOut();
		}
		else if(packetId == PacketID.GUILD_SET_MOTD) {
			String msg = ConnectionManager.getWorldServerConnection().readString();
			Mideas.joueur1().getGuild().setMotd(msg);
			ChatFrame.addMessage(new Message("[Guild Message Of The Day]: ".concat(msg), false, MessageType.SELF, MessageType.GUILD.getColor()));
		}
		else if(packetId == PacketID.GUILD_SET_INFORMATION) {
			String msg = ConnectionManager.getWorldServerConnection().readString();
			Mideas.joueur1().getGuild().setInformation(msg);
		}
		else if(packetId == PacketID.GUILD_MEMBER_LEFT) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			ChatFrame.addMessage(new Message(Mideas.joueur1().getGuild().getMember(id).getName().concat(" left the guild."), false, MessageType.SELF));
			Mideas.joueur1().getGuild().removeMember(id);
		}
		else if(packetId == PacketID.GUILD_SET_LEADER) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			GuildMember leader = Mideas.joueur1().getGuild().getMember(Mideas.joueur1().getGuild().getLeaderId());
			if(leader.getId() == Mideas.joueur1().getId()) {
				Mideas.joueur1().setGuildRank(Mideas.joueur1().getGuild().getRankList().get(1));
			}
			else if(id == Mideas.joueur1().getId()) {
				Mideas.joueur1().setGuildRank(Mideas.joueur1().getGuild().getRankList().get(0));
			}
			ChatFrame.addMessage(new Message(new StringBuilder().append(leader.getName()).append(" has made ").append(Mideas.joueur1().getGuild().getMember(id).getName()).append(" the new Guild Master.").toString(), false, MessageType.SELF));
			Mideas.joueur1().getGuild().getMember(id).setRank(Mideas.joueur1().getGuild().getRankList().get(0));
			leader.setRank(Mideas.joueur1().getGuild().getRankList().get(1));
			Mideas.joueur1().getGuild().setLeaderId(id);
		}
		else if(packetId == PacketID.GUILD_SET_MEMBER_NOTE) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			String note = ConnectionManager.getWorldServerConnection().readString();
			Mideas.joueur1().getGuild().getMember(id).setNoteSave(note);
		}
		else if(packetId == PacketID.GUILD_SET_MEMBER_OFFICER_NOTE) {
			int id = ConnectionManager.getWorldServerConnection().readInt();
			String officerNote = ConnectionManager.getWorldServerConnection().readString();
			Mideas.joueur1().getGuild().getMember(id).setOfficerNoteSave(officerNote);
		}
		else if(packetId == PacketID.GUILD_SET_JOURNAL) {
			GuildJournalEventType type = GuildJournalEventType.values()[ConnectionManager.getWorldServerConnection().readByte()];
			long timer = ConnectionManager.getWorldServerConnection().readLong();
			String player1Name = ConnectionManager.getWorldServerConnection().readString();
			String player2Name = null;
			int rankID = -1;
			if(type == GuildJournalEventType.MEMBER_DEMOTED || type == GuildJournalEventType.MEMBER_INVITED || type == GuildJournalEventType.MEMBER_KICKED || type == GuildJournalEventType.MEMBER_PROMOTED) {
				player2Name = ConnectionManager.getWorldServerConnection().readString();
			}
			if(type == GuildJournalEventType.MEMBER_DEMOTED || type == GuildJournalEventType.MEMBER_PROMOTED) {
				rankID = ConnectionManager.getWorldServerConnection().readInt();
			}
			GuildRank rank = Mideas.joueur1().getGuild().getRank(rankID);
			String rankName;
			if(rank == null) {
				rankName = "Deleted rank";
			}
			else {
				rankName = rank.getName();
			}
			Mideas.joueur1().getGuild().addEvent(new GuildEvent(timer, type, player1Name, player2Name, rankName));
		}
		else if(packetId == PacketID.GUILD_INIT_JOURNAL) {
			short length = ConnectionManager.getWorldServerConnection().readShort();
			int i = 0;
			while(i < length) {
				GuildJournalEventType type = GuildJournalEventType.values()[ConnectionManager.getWorldServerConnection().readByte()];
				long timer = ConnectionManager.getWorldServerConnection().readLong();
				String player1Name = ConnectionManager.getWorldServerConnection().readString();
				String player2Name = ConnectionManager.getWorldServerConnection().readString();
				int rankID = ConnectionManager.getWorldServerConnection().readInt();
				String rankName = null;
				GuildRank rank;
				if((rank = Mideas.joueur1().getGuild().getRank(rankID)) != null) {
					rankName = rank.getName();
				}
				else {
					rankName = "Rank deleted";
				}
				Mideas.joueur1().getGuild().addEvent(new GuildEvent(timer, type, player1Name, player2Name, rankName));
				i++;
			}
		}
	}
	
	public static void setMemberNote(int id, String note) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_SET_MEMBER_NOTE);
		ConnectionManager.getWorldServerConnection().writeInt(id);
		ConnectionManager.getWorldServerConnection().writeString(note);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void setMemberOfficerNote(int id, String officerNote) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_SET_MEMBER_OFFICER_NOTE);
		ConnectionManager.getWorldServerConnection().writeInt(id);
		ConnectionManager.getWorldServerConnection().writeString(officerNote);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void kickMember(int id) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_KICK_MEMBER);
		ConnectionManager.getWorldServerConnection().writeInt(id);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void leaveGuild() {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_LEAVE);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void setLeader(int id) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_SET_LEADER);
		ConnectionManager.getWorldServerConnection().writeInt(id);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void setInformation(String msg) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_SET_INFORMATION);
		ConnectionManager.getWorldServerConnection().writeString(msg);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void setMotd(String msg) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_SET_MOTD);
		ConnectionManager.getWorldServerConnection().writeString(msg);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void updatePermission(int rank_order, int value, String name) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_UPDATE_PERMISSION);
		ConnectionManager.getWorldServerConnection().writeInt(rank_order);
		ConnectionManager.getWorldServerConnection().writeInt(value);
		ConnectionManager.getWorldServerConnection().writeString(name);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void addMember(String name) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_INVITE_PLAYER);
		ConnectionManager.getWorldServerConnection().writeString(name);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void acceptRequest() {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_ACCEPT_REQUEST);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void declineRequest() {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.GUILD_DECLINE_REQUEST);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
