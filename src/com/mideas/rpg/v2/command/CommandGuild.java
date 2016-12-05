package com.mideas.rpg.v2.command;

import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.guild.Guild;
import com.mideas.rpg.v2.game.guild.GuildMember;
import com.mideas.rpg.v2.game.guild.GuildRank;
import com.mideas.rpg.v2.hud.PopupFrame;
import com.mideas.rpg.v2.hud.social.guild.GuildFrame;

public class CommandGuild extends Command {
	
	@Override
	public void read() {
		byte packetId = ConnectionManager.getConnection().readByte();
		if(packetId == PacketID.GUILD_UPDATE_PERMISSION) {
			int rank_order = ConnectionManager.getConnection().readInt();
			int permission = ConnectionManager.getConnection().readInt();
			String name = ConnectionManager.getConnection().readString();
			Mideas.joueur1().getGuild().getRank(rank_order).setPermission(permission);
			Mideas.joueur1().getGuild().getRank(rank_order).setName(name);
			GuildFrame.fillDropDownMenuWithRank();
			Mideas.joueur1().setGuildRank(Mideas.joueur1().getGuildRank());
		}
		else if(packetId == PacketID.GUILD_INVITE_PLAYER) {
			String player_name = ConnectionManager.getConnection().readString();
			String guild_name = ConnectionManager.getConnection().readString();
			PopupFrame.activateGuildInvitationPopup(player_name, guild_name);
			ChatFrame.addMessage(new Message(" invited you to join "+guild_name, player_name, false, MessageType.SELF));
		}
		else if(packetId == PacketID.GUILD_INIT) {
			int i = 0;
			int guildId = ConnectionManager.getConnection().readInt();
			int leaderId = ConnectionManager.getConnection().readInt();
			String guildName = ConnectionManager.getConnection().readString();
			String information = ConnectionManager.getConnection().readString();
			String motd = ConnectionManager.getConnection().readString();
			int rankListSize = ConnectionManager.getConnection().readInt();
			ArrayList<GuildRank> rankList = new ArrayList<GuildRank>(rankListSize);
			while(i < rankListSize) {
				int rank_order = ConnectionManager.getConnection().readInt();
				String name = ConnectionManager.getConnection().readString();
				int permission = ConnectionManager.getConnection().readInt();
				rankList.add(new GuildRank(rank_order, permission, name));
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
					long lastLoginTimer = ConnectionManager.getConnection().readLong();
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
					int id = ConnectionManager.getConnection().readInt();
					int level = ConnectionManager.getConnection().readInt();
					String name = ConnectionManager.getConnection().readString();
					ClassType type = ClassType.values()[ConnectionManager.getConnection().readChar()];
					String note = ConnectionManager.getConnection().readString();
					int rank = ConnectionManager.getConnection().readInt();
					boolean isOnline = ConnectionManager.getConnection().readBoolean();
					long lastLoginTimer = ConnectionManager.getConnection().readLong();
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
			ChatFrame.addMessage(new Message("[Guild Message Of The Day] : "+Mideas.joueur1().getGuild().getMotd(), false, MessageType.SELF, MessageType.GUILD.getColor()));
		}
		else if(packetId == PacketID.GUILD_NEW_MEMBER) {
			int id = ConnectionManager.getConnection().readInt();
			String name = ConnectionManager.getConnection().readString();
			String note = ConnectionManager.getConnection().readString();
			String officerNote = ConnectionManager.getConnection().readString();
			int rankOrder = ConnectionManager.getConnection().readInt();
			GuildRank rank = Mideas.joueur1().getGuild().getRank(rankOrder);
			int level = ConnectionManager.getConnection().readInt();
			boolean isOnline = ConnectionManager.getConnection().readBoolean();
			ClassType type = ClassType.values()[ConnectionManager.getConnection().readChar()];
			long lastLoginTimer = ConnectionManager.getConnection().readLong();
			Mideas.joueur1().getGuild().addMember(new GuildMember(id, name, level, rank, isOnline, note, officerNote, type, lastLoginTimer));
			ChatFrame.addMessage(new Message(name+" joined the guild.", false, MessageType.SELF));
		}
		else if(packetId == PacketID.GUILD_KICK_MEMBER) {
			String officerName = ConnectionManager.getConnection().readString();
			int id = ConnectionManager.getConnection().readInt();
			GuildMember member = Mideas.joueur1().getGuild().getMember(id);
			if(id != Mideas.joueur1().getId()) {
				ChatFrame.addMessage(new Message(member.getName()+" has been kicked out of the guild by "+officerName, false, MessageType.SELF));
				Mideas.joueur1().getGuild().removeMember(id);
			}
			else {
				ChatFrame.addMessage(new Message("you have been kicked out of the guild by "+officerName, false, MessageType.SELF));
				Mideas.joueur1().setGuild(null);
			}
		}
		else if(packetId == PacketID.GUILD_ONLINE_PLAYER) {
			int id = ConnectionManager.getConnection().readInt();
			GuildMember member = Mideas.joueur1().getGuild().getMember(id);
			if(member == null) {
				return;
			}
			member.setOnlineStatus(true);
			member.updateLastLoginTimerString();
			if(Mideas.joueur1().getFriend(id) == null) {
				ChatFrame.addMessage(new Message(" is now online.", member.getName(), false, MessageType.SELF));
			}
			Mideas.joueur1().getGuild().memberLoggedIn();
		}
		else if(packetId == PacketID.GUILD_OFFLINE_PLAYER) {
			int id = ConnectionManager.getConnection().readInt();
			GuildMember member = Mideas.joueur1().getGuild().getMember(id);
			if(member == null) {
				return;
			}
			member.setOnlineStatus(false);
			member.setLastLoginTimer(System.currentTimeMillis());
			member.updateLastLoginTimerString();
			if(Mideas.joueur1().getFriend(id) == null) {
				ChatFrame.addMessage(new Message(member.getName()+" is now offline.", false, MessageType.SELF));
			}
			Mideas.joueur1().getGuild().memberLoggedOut();
		}
		else if(packetId == PacketID.GUILD_SET_MOTD) {
			String msg = ConnectionManager.getConnection().readString();
			Mideas.joueur1().getGuild().setMotd(msg);
			ChatFrame.addMessage(new Message("[Guild Message Of The Day]: "+msg, false, MessageType.SELF, MessageType.GUILD.getColor()));
		}
		else if(packetId == PacketID.GUILD_SET_INFORMATION) {
			String msg = ConnectionManager.getConnection().readString();
			Mideas.joueur1().getGuild().setInformation(msg);
		}
		else if(packetId == PacketID.GUILD_MEMBER_LEFT) {
			int id = ConnectionManager.getConnection().readInt();
			ChatFrame.addMessage(new Message(Mideas.joueur1().getGuild().getMember(id).getName()+" left the guild.", false, MessageType.SELF));
			Mideas.joueur1().getGuild().removeMember(id);
		}
		else if(packetId == PacketID.GUILD_SET_LEADER) {
			int id = ConnectionManager.getConnection().readInt();
			GuildMember leader = Mideas.joueur1().getGuild().getMember(Mideas.joueur1().getGuild().getLeaderId());
			if(leader.getId() == Mideas.joueur1().getId()) {
				Mideas.joueur1().setGuildRank(Mideas.joueur1().getGuild().getRankList().get(1));
			}
			else if(id == Mideas.joueur1().getId()) {
				Mideas.joueur1().setGuildRank(Mideas.joueur1().getGuild().getRankList().get(0));
			}
			ChatFrame.addMessage(new Message(leader.getName()+" has made "+Mideas.joueur1().getGuild().getMember(id).getName()+" the new Guild Master.", false, MessageType.SELF));
			Mideas.joueur1().getGuild().getMember(id).setRank(Mideas.joueur1().getGuild().getRankList().get(0));
			leader.setRank(Mideas.joueur1().getGuild().getRankList().get(1));
			Mideas.joueur1().getGuild().setLeaderId(id);
		}
		else if(packetId == PacketID.GUILD_SET_MEMBER_NOTE) {
			int id = ConnectionManager.getConnection().readInt();
			String note = ConnectionManager.getConnection().readString();
			Mideas.joueur1().getGuild().getMember(id).setNoteSave(note);
		}
		else if(packetId == PacketID.GUILD_SET_MEMBER_OFFICER_NOTE) {
			int id = ConnectionManager.getConnection().readInt();
			String officerNote = ConnectionManager.getConnection().readString();
			Mideas.joueur1().getGuild().getMember(id).setOfficerNoteSave(officerNote);
		}
	}
	
	public static void setMemberNote(int id, String note) {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_SET_MEMBER_NOTE);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().writeString(note);
		ConnectionManager.getConnection().send();
	}
	
	public static void setMemberOfficerNote(int id, String officerNote) {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_SET_MEMBER_OFFICER_NOTE);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().writeString(officerNote);
		ConnectionManager.getConnection().send();
	}
	
	public static void kickMember(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_KICK_MEMBER);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
	
	public static void leaveGuild() {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_LEAVE);
		ConnectionManager.getConnection().send();
	}
	
	public static void setLeader(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_SET_LEADER);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
	
	public static void setInformation(String msg) {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_SET_INFORMATION);
		ConnectionManager.getConnection().writeString(msg);
		ConnectionManager.getConnection().send();
	}
	
	public static void setMotd(String msg) {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_SET_MOTD);
		ConnectionManager.getConnection().writeString(msg);
		ConnectionManager.getConnection().send();
	}
	
	public static void updatePermission(int rank_order, int value, String name) {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_UPDATE_PERMISSION);
		ConnectionManager.getConnection().writeInt(rank_order);
		ConnectionManager.getConnection().writeInt(value);
		ConnectionManager.getConnection().writeString(name);
		ConnectionManager.getConnection().send();
	}
	
	public static void addMember(String name) {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_INVITE_PLAYER);
		ConnectionManager.getConnection().writeString(name);
		ConnectionManager.getConnection().send();
	}
	
	public static void acceptRequest() {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_ACCEPT_REQUEST);
		ConnectionManager.getConnection().send();
	}
	
	public static void declineRequest() {
		ConnectionManager.getConnection().writeByte(PacketID.GUILD);
		ConnectionManager.getConnection().writeByte(PacketID.GUILD_DECLINE_REQUEST);
		ConnectionManager.getConnection().send();
	}
}
