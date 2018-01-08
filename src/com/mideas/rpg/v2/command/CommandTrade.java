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
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.hud.PopupFrame;
import com.mideas.rpg.v2.hud.TradeFrame;

public class CommandTrade extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getWorldServerConnection().readShort();
		if(packetId == PacketID.TRADE_NEW_CONFIRM) {
			Interface.setTradeFrameStatus(true);
			System.out.println("Trade confirmed");
		}
		else if(packetId == PacketID.TRADE_ADD_ITEM) {
			boolean self = ConnectionManager.getWorldServerConnection().readBoolean();
			if(self) {
				DragItem type = DragItem.values()[ConnectionManager.getWorldServerConnection().readByte()];
				int itemSlot = ConnectionManager.getWorldServerConnection().readInt();
				int tradeSlot = ConnectionManager.getWorldServerConnection().readInt();
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
				item.setIsSelectable(false);
				TradeFrame.addItem(item, tradeSlot);
			}
			else {
				int tradeSlot = ConnectionManager.getWorldServerConnection().readInt();
				int amount = ConnectionManager.getWorldServerConnection().readInt();
				int itemId = ConnectionManager.getWorldServerConnection().readInt();
				boolean hasGem = ConnectionManager.getWorldServerConnection().readBoolean();
				Item item = Item.getItem(itemId); //TODO: send ItemType and create corresponding temp item
				if(hasGem) {
					if(item == null) {
						item = new Stuff(itemId);
						CommandRequestItem.write(new RequestItem(itemId, DragItem.TRADE, tradeSlot));
					}
					int gem1Id = ConnectionManager.getWorldServerConnection().readInt();
					int gem2Id = ConnectionManager.getWorldServerConnection().readInt();
					int gem3Id = ConnectionManager.getWorldServerConnection().readInt();
					setGem(item, gem1Id, tradeSlot, 0);
					setGem(item, gem2Id, tradeSlot, 1);
					setGem(item, gem3Id, tradeSlot, 2);
				}
				else if(item == null) {
					item = new Item(itemId);
					CommandRequestItem.write(new RequestItem(itemId, DragItem.TRADE, tradeSlot));
				}
				item.setAmount(amount);
				TradeFrame.addItem(item, tradeSlot);
			}
			TradeFrame.setTraceAcceptedOther(false);
			TradeFrame.setTraceAcceptedSelf(false);
		}
		else if(packetId == PacketID.TRADE_REQUEST) {
			//check if windows are open etc
			String name = ConnectionManager.getWorldServerConnection().readString();
			TradeFrame.setName(name);
			PopupFrame.activateTradePopup(name);
		}
		else if(packetId == PacketID.TRADE_ACCEPT) {
			TradeFrame.setTraceAcceptedOther(true);
		}
		else if(packetId == PacketID.TRADE_UNACCEPT) {
			TradeFrame.setTraceAcceptedOther(false);
		}
		else if(packetId == PacketID.TRADE_REMOVE_ITEM) {
			int slot = ConnectionManager.getWorldServerConnection().readInt();
			if(slot >= 0 && slot <= 6) {
				if(TradeFrame.getItem(slot) != null) {
					TradeFrame.getItem(slot).setIsSelectable(true);
				}
			}
			TradeFrame.addItem(null, slot);
			if(slot <= 6) {
				TradeFrame.setTraceAcceptedSelf(false);
			}
			else {
				TradeFrame.setTraceAcceptedOther(false);
			}
		}
		else if(packetId == PacketID.TRADE_CLOSE) {
			System.out.println("Trade closed");
			TradeFrame.setAllItemSelectable();
			TradeFrame.reset();
			Interface.setTradeFrameStatus(false);
		}
		else if(packetId == PacketID.TRADE_ADD_ITEM_ERROR) {
			int slot = ConnectionManager.getWorldServerConnection().readInt();
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
				packetID = ConnectionManager.getWorldServerConnection().readShort();
				if(packetID == PacketID.KNOWN_ITEM) {
					id = ConnectionManager.getWorldServerConnection().readInt();
					amount = ConnectionManager.getWorldServerConnection().readInt();
					if(Item.exists(id)) {
						Mideas.joueur1().addItem(Item.getItem(id), amount);
					}
				}
				else if(packetID == PacketID.UNKNOWN_ITEM) {
					item = ConnectionManager.getWorldServerConnection().readItem();
					System.out.println(item.getStuffName());
					amount = ConnectionManager.getWorldServerConnection().readInt();
					Mideas.joueur1().addItem(item, amount);
				}
				i++;
			}
		}
	}
	
	/*public static void writeAddItem(Item item, int slot, int amount) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE_ADD_ITEM);
		ConnectionManager.getWorldServerConnection().writeInt(item.getId());
		ConnectionManager.getWorldServerConnection().writeInt(slot);
		ConnectionManager.getWorldServerConnection().writeInt(amount);
		if(item.isStuff() || item.isWeapon()) {
			Stuff stuff = (Stuff)item;
			if(stuff.getEquippedGem(1) != null || stuff.getEquippedGem(2) != null || stuff.getEquippedGem(3) != null) {
				ConnectionManager.getWorldServerConnection().writeBoolean(true);
				sendGem(stuff, 1);
				sendGem(stuff, 2);
				sendGem(stuff, 3);
			}
			else {
				ConnectionManager.getWorldServerConnection().writeBoolean(false);
			}
		}
		else {
			ConnectionManager.getWorldServerConnection().writeBoolean(false);
		}
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}*/
	
	/*private static void sendGem(Stuff stuff, int slot) {
		if(stuff.getEquippedGem(slot) != null) {
			ConnectionManager.getWorldServerConnection().writeInt(stuff.getEquippedGem(slot).getId());
		}
		else {
			ConnectionManager.getWorldServerConnection().writeInt(0);
		}
	}*/
	
	public static void requestNewTrade(String name) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE_NEW);
		ConnectionManager.getWorldServerConnection().writeString(name);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void writeAccept() {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE_ACCEPT);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void writeRemovedItem(int i) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE_REMOVE_ITEM);
		ConnectionManager.getWorldServerConnection().writeInt(i);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void writeCloseTrade() {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE_CLOSE);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void writeConfirm() {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE_NEW_CONFIRM);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void addItem(DragItem slotType, int bagSlot, int tradeSlot) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.TRADE_ADD_ITEM);
		ConnectionManager.getWorldServerConnection().writeByte(slotType.getValue());
		ConnectionManager.getWorldServerConnection().writeInt(bagSlot);
		ConnectionManager.getWorldServerConnection().writeInt(tradeSlot);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void setGem(Item item, int gemID, int tradeSlot, int gemSlot) {
		if(GemManager.exists(gemID)) {
			((Stuff)item).setEquippedGem(gemSlot, GemManager.getClone(gemID));
		}
		else {
			CommandRequestItem.write(new RequestItem(gemID, DragItem.TRADE, tradeSlot, gemSlot));
		}
	}
}
