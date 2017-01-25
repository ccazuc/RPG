package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.Party;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.game.unit.Unit;
import com.mideas.rpg.v2.hud.PartyFrame;
import com.mideas.rpg.v2.hud.PopupFrame;

public class CommandParty extends Command {
	
	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if(packetId == PacketID.PARTY_ADD_MEMBER) {
			String name = ConnectionManager.getConnection().readString();
			PopupFrame.activatePartyInvitationPopup(name);
			ChatFrame.addMessage(new Message(" invited you to join a party.", name, false, MessageType.SELF, false));
		}
		else if(packetId == PacketID.PARTY_DECLINE_REQUEST) {
			String name = ConnectionManager.getConnection().readString();
			ChatFrame.addMessage(new Message(name.concat(" declined your request."), false, MessageType.SELF));
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
			Mideas.joueur1().getParty().addMember(new Unit(id, stamina, maxStamina, mana, maxMana, level, name, type));
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
				ConnectionManager.getConnection().startPacket();
				ConnectionManager.getConnection().writeShort(PacketID.PARTY);
				ConnectionManager.getConnection().writeShort(PacketID.PARTY_ADD_MEMBER);
				ConnectionManager.getConnection().writeString(name);
				ConnectionManager.getConnection().endPacket();
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
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.PARTY);
		ConnectionManager.getConnection().writeShort(PacketID.PARTY_SET_LEADER);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void kickPlayer(int id) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.PARTY);
		ConnectionManager.getConnection().writeShort(PacketID.PARTY_KICK_PLAYER);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void declineRequest() {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.PARTY);
		ConnectionManager.getConnection().writeShort(PacketID.PARTY_DECLINE_REQUEST);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void acceptRequest() {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.PARTY);
		ConnectionManager.getConnection().writeShort(PacketID.PARTY_ACCEPT_REQUEST);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
}
