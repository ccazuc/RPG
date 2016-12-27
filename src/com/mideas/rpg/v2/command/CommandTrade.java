package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.item.CommandRequestItem;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.DragItem;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.RequestItem;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.hud.PopupFrame;
import com.mideas.rpg.v2.hud.TradeFrame;
import com.mideas.rpg.v2.game.item.stuff.Stuff;

public class CommandTrade extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if(packetId == PacketID.TRADE_NEW_CONFIRM) {
			Interface.setTradeFrameStatus(true);
			//System.out.println("trade confirmed");
		}
		else if(packetId == PacketID.TRADE_ADD_ITEM) {
			boolean self = ConnectionManager.getConnection().readBoolean();
			if(self) {
				DragItem type = DragItem.values()[ConnectionManager.getConnection().readByte()];
				int itemSlot = ConnectionManager.getConnection().readInt();
				int tradeSlot = ConnectionManager.getConnection().readInt();
				Item item = null;
				if(type == DragItem.BAG) {
					item = Mideas.joueur1().bag().getBag(itemSlot);
				}
				else if(type == DragItem.INVENTORY) {
					item = Mideas.joueur1().getStuff(itemSlot);
				}
				if(item == null) {
					System.out.println("Error in CommandTrade:TRADE_ADD_ITEM, item == null");
					return;
				}
				TradeFrame.addItem(item, tradeSlot);
			}
			else {
				int tradeSlot = ConnectionManager.getConnection().readInt();
				int itemId = ConnectionManager.getConnection().readInt();
				boolean hasGem = ConnectionManager.getConnection().readBoolean();
				if(hasGem) {
					
				}
				Item item = Item.getItem(itemId);
				if(item == null) {
					CommandRequestItem.write(new RequestItem(itemId, DragItem.TRADE, tradeSlot));
				}
			}
			TradeFrame.setTraceAcceptedOther(false);
			TradeFrame.setTraceAcceptedSelf(false);
		}
		else if(packetId == PacketID.TRADE_REQUEST) {
			//check if windows are open etc
			String name = ConnectionManager.getConnection().readString();
			TradeFrame.setName(name);
			PopupFrame.activateTradePopup();
		}
		else if(packetId == PacketID.TRADE_ACCEPT) {
			TradeFrame.setTraceAcceptedOther(true);
		}
		else if(packetId == PacketID.TRADE_UNACCEPT) {
			TradeFrame.setTraceAcceptedOther(false);
		}
		else if(packetId == PacketID.TRADE_REMOVE_ITEM) {
			int slot = ConnectionManager.getConnection().readInt();
			TradeFrame.addItem(null, slot+7);
			TradeFrame.setTraceAcceptedOther(false);
			TradeFrame.setTraceAcceptedSelf(false);
		}
		else if(packetId == PacketID.TRADE_CLOSE) {
			TradeFrame.setAllItemSelectable();
			TradeFrame.reset();
			Interface.setTradeFrameStatus(false);
		}
		else if(packetId == PacketID.TRADE_ADD_ITEM_ERROR) {
			int slot = ConnectionManager.getConnection().readInt();
			TradeFrame.getItem(slot).setIsSelectable(true);
			TradeFrame.addItem(null, slot);
		}
		else if(packetId == PacketID.TRADE_SEND_ALL_ITEMS) {
			System.out.println("TRADE: TRADE_SEND_ALL_ITEMS");
			TradeFrame.removedTradedItems();
			int i = 0;
			short packetID;
			int id;
			int amount;
			Item item;
			while(i < 6) {
				packetID = ConnectionManager.getConnection().readShort();
				if(packetID == PacketID.KNOWN_ITEM) {
					id = ConnectionManager.getConnection().readInt();
					amount = ConnectionManager.getConnection().readInt();
					if(Item.exists(id)) {
						Mideas.joueur1().addItem(Item.getItem(id), amount);
					}
				}
				else if(packetID == PacketID.UNKNOWN_ITEM) {
					item = ConnectionManager.getConnection().readItem();
					System.out.println(item.getStuffName());
					amount = ConnectionManager.getConnection().readInt();
					Mideas.joueur1().addItem(item, amount);
				}
				i++;
			}
		}
	}
	
	/*public static void writeAddItem(Item item, int slot, int amount) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getConnection().writeShort(PacketID.TRADE_ADD_ITEM);
		ConnectionManager.getConnection().writeInt(item.getId());
		ConnectionManager.getConnection().writeInt(slot);
		ConnectionManager.getConnection().writeInt(amount);
		if(item.isStuff() || item.isWeapon()) {
			Stuff stuff = (Stuff)item;
			if(stuff.getEquippedGem(1) != null || stuff.getEquippedGem(2) != null || stuff.getEquippedGem(3) != null) {
				ConnectionManager.getConnection().writeBoolean(true);
				sendGem(stuff, 1);
				sendGem(stuff, 2);
				sendGem(stuff, 3);
			}
			else {
				ConnectionManager.getConnection().writeBoolean(false);
			}
		}
		else {
			ConnectionManager.getConnection().writeBoolean(false);
		}
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}*/
	
	/*private static void sendGem(Stuff stuff, int slot) {
		if(stuff.getEquippedGem(slot) != null) {
			ConnectionManager.getConnection().writeInt(stuff.getEquippedGem(slot).getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
	}*/
	
	public static void requestNewTrade(String name) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getConnection().writeShort(PacketID.TRADE_NEW);
		ConnectionManager.getConnection().writeString(name);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void writeAccept() {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getConnection().writeShort(PacketID.TRADE_ACCEPT);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void writeRemovedItem(int i) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getConnection().writeShort(PacketID.TRADE_REMOVE_ITEM);
		ConnectionManager.getConnection().writeInt(i);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void writeCloseTrade() {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getConnection().writeShort(PacketID.TRADE_CLOSE);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void writeConfirm() {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getConnection().writeShort(PacketID.TRADE_NEW_CONFIRM);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void addItem(DragItem slotType, int bagSlot, int tradeSlot) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getConnection().writeShort(PacketID.TRADE_ADD_ITEM);
		ConnectionManager.getConnection().writeByte(slotType.getValue());
		ConnectionManager.getConnection().writeInt(bagSlot);
		ConnectionManager.getConnection().writeInt(tradeSlot);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
}
