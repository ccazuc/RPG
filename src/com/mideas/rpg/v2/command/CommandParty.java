package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.Party;
import com.mideas.rpg.v2.game.Unit;
import com.mideas.rpg.v2.hud.PartyFrame;

public class CommandParty extends Command {
	
	@Override
	public void read() {
		byte packetId = ConnectionManager.getConnection().readByte();
		if(packetId == PacketID.PARTY_ADD_MEMBER) {
			String name = ConnectionManager.getConnection().readString();
			PartyFrame.setRequestPending(true);
			PartyFrame.setRequestName(name);
		}
		else if(packetId == PacketID.PARTY_DECLINE_REQUEST) {
			String name = ConnectionManager.getConnection().readString();
			ChatFrame.addMessage(new Message(name+" declined your request.", false, MessageType.SELF));
		}
		else if(packetId == PacketID.PARTY_ACCEPT_REQUEST) {
			
		}
		else if(packetId == PacketID.PARTY_MEMBER_JOINED) {
			String name = ConnectionManager.getConnection().readString();
			int stamina = ConnectionManager.getConnection().readInt();
			int maxStamina = ConnectionManager.getConnection().readInt();
			int mana = ConnectionManager.getConnection().readInt();
			int maxMana = ConnectionManager.getConnection().readInt();
			int level = ConnectionManager.getConnection().readInt();
			int id = ConnectionManager.getConnection().readInt();
			ClassType type = ClassType.values()[ConnectionManager.getConnection().readChar()];
			//System.out.println(name+" "+stamina+" "+maxStamina+" "+mana+" "+maxMana+" "+level+" "+id);
			Mideas.joueur1().getParty().addMember(new Unit(id, stamina, maxStamina, mana, maxMana, level, name, type));
			//System.out.println("Member joined the party");
		}
		else if(packetId == PacketID.PARTY_NEW) {
			boolean isLeader = ConnectionManager.getConnection().readBoolean();
			String name = ConnectionManager.getConnection().readString();
			int stamina = ConnectionManager.getConnection().readInt();
			int maxStamina = ConnectionManager.getConnection().readInt();
			int mana = ConnectionManager.getConnection().readInt();
			int maxMana = ConnectionManager.getConnection().readInt();
			int level = ConnectionManager.getConnection().readInt();
			int id = ConnectionManager.getConnection().readInt();
			ClassType type = ClassType.values()[ConnectionManager.getConnection().readChar()];
			//System.out.println(name+" "+stamina+" "+maxStamina+" "+mana+" "+maxMana+" "+level+" "+id+" "+isLeader);
			Unit member = new Unit(id, stamina, maxStamina, mana, maxMana, level, name, type);
			Unit leader = isLeader ? Mideas.joueur1() : member;
			Mideas.joueur1().setParty(new Party(leader, member));
			//System.out.println("Party created");
		}
		else if(packetId == PacketID.PARTY_DISBAND) {
			Mideas.joueur1().setParty(null);
		}
		else if(packetId == PacketID.PARTY_MEMBER_LEFT) {
			if(Mideas.joueur1().getParty() != null) {
				Mideas.joueur1().getParty().removeMember(ConnectionManager.getConnection().readInt());
				Mideas.joueur1().getParty().updateMemberPosition();
			}
		}
		else if(packetId == PacketID.PARTY_LEFT) {
			Mideas.joueur1().setParty(null);
		}
		else if(packetId == PacketID.PARTY_SET_LEADER) {
			setLeader(ConnectionManager.getConnection().readInt());
		}
	}
	
	private static void setLeader(int id) {
		int i = 0;
		if(id == Mideas.joueur1().getId()) {
			Mideas.joueur1().getParty().setPartyLeader(Mideas.joueur1());
			PartyFrame.updateSize();
		}
		else {
			while(i < Mideas.joueur1().getParty().getMemberList().length) {
				if(Mideas.joueur1().getParty().getPartyMember(i) != null && Mideas.joueur1().getParty().getPartyMember(i).getId() == id) {
					Mideas.joueur1().getParty().setPartyLeader(Mideas.joueur1().getParty().getPartyMember(i));
					break;
				}
				i++;
			}
		}
	}

	public static void invitePlayer(String name) {
		if(!name.equals(Mideas.joueur1().getName())) {
			//if(Mideas.joueur1().getParty() == null || (Mideas.joueur1().getParty() != null && Mideas.joueur1().getParty().isPartyLeader())) {
				ConnectionManager.getConnection().writeByte(PacketID.PARTY);
				ConnectionManager.getConnection().writeByte(PacketID.PARTY_ADD_MEMBER);
				ConnectionManager.getConnection().writeString(name);
				ConnectionManager.getConnection().send();
			//}
			//else if(Mideas.joueur1().getParty() != null && !Mideas.joueur1().getParty().isPartyLeader()) {
				//ChatFrame.addMessage(new Message("You are not the party leader.", false, MessageType.SELF));
			//}
		}
		else {
			ChatFrame.addMessage(new Message("You can't invite yourself in a party.", false, MessageType.SELF));
		}
	}
	
	public static void setLeaderServer(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.PARTY);
		ConnectionManager.getConnection().writeByte(PacketID.PARTY_SET_LEADER);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
	
	public static void kickPlayer(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.PARTY);
		ConnectionManager.getConnection().writeByte(PacketID.PARTY_KICK_PLAYER);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
	
	public static void declineRequest() {
		ConnectionManager.getConnection().writeByte(PacketID.PARTY);
		ConnectionManager.getConnection().writeByte(PacketID.PARTY_DECLINE_REQUEST);
		ConnectionManager.getConnection().send();
	}
	
	public static void acceptRequest() {
		ConnectionManager.getConnection().writeByte(PacketID.PARTY);
		ConnectionManager.getConnection().writeByte(PacketID.PARTY_ACCEPT_REQUEST);
		ConnectionManager.getConnection().send();
	}
}
