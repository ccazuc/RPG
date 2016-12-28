package com.mideas.rpg.v2.command;

public class CommandSendEquippedItems extends Command {

	@Override
	public void read() {
		
	}
	
	/*public void write() {
		ConnectionManager.getConnection().writeShort(PacketID.SEND_EQUIPPED_ITEMS);
		int i = 0;
		while(i < 12) {
			if(i != 5 && i != 6) {
				if(Mideas.joueur1().getStuff(i) != null && StuffManager.exists(Mideas.joueur1().getStuff(i).getId()) && Mideas.joueur1().getStuff(i).getType() == StuffType.values()[(char)i] && Mideas.joueur1().canEquipStuff(StuffManager.getStuff(Mideas.joueur1().getStuff(i).getId()))) {
					ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(i).getId());
					sendGemDatas(i);
				}
				else {
					ConnectionManager.getConnection().writeInt(0);
					ConnectionManager.getConnection().writeInt(0);
					ConnectionManager.getConnection().writeInt(0);
					ConnectionManager.getConnection().writeInt(0);
				}
			}
			i++;
		}
		i = 12;
		if(Mideas.joueur1().getStuff(i) != null && StuffManager.exists(Mideas.joueur1().getStuff(i).getId()) && Mideas.joueur1().getStuff(i).isRing() && Mideas.joueur1().canEquipStuff(StuffManager.getStuff(Mideas.joueur1().getStuff(i).getId()))) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(i).getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
		i++;
		if(Mideas.joueur1().getStuff(i) != null && StuffManager.exists(Mideas.joueur1().getStuff(i).getId()) && Mideas.joueur1().getStuff(i).isRing() && Mideas.joueur1().canEquipStuff(StuffManager.getStuff(Mideas.joueur1().getStuff(i).getId()))) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(i).getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
		i++;
		if(Mideas.joueur1().getStuff(i) != null && StuffManager.exists(Mideas.joueur1().getStuff(i).getId()) && Mideas.joueur1().getStuff(i).isTrinket() && Mideas.joueur1().canEquipStuff(StuffManager.getStuff(Mideas.joueur1().getStuff(i).getId()))) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(i).getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
		i++;
		if(Mideas.joueur1().getStuff(i) != null && StuffManager.exists(Mideas.joueur1().getStuff(i).getId()) && Mideas.joueur1().getStuff(i).isTrinket() && Mideas.joueur1().canEquipStuff(StuffManager.getStuff(Mideas.joueur1().getStuff(i).getId()))) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(i).getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
		i++;
		if(Mideas.joueur1().getStuff(i) != null && WeaponManager.exists(Mideas.joueur1().getStuff(i).getId()) && Mideas.joueur1().getStuff(i).isMainHand() && Mideas.joueur1().canEquipStuff(StuffManager.getStuff(Mideas.joueur1().getStuff(i).getId()))) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(i).getId());
			sendGemDatas(i);
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
			ConnectionManager.getConnection().writeInt(0);
			ConnectionManager.getConnection().writeInt(0);
			ConnectionManager.getConnection().writeInt(0);
		}
		i++;
		if(Mideas.joueur1().getStuff(i) != null && WeaponManager.exists(Mideas.joueur1().getStuff(i).getId()) && Mideas.joueur1().getStuff(i).isOffHand() && Mideas.joueur1().canEquipStuff(StuffManager.getStuff(Mideas.joueur1().getStuff(i).getId()))) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(i).getId());
			sendGemDatas(i);
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
			ConnectionManager.getConnection().writeInt(0);
			ConnectionManager.getConnection().writeInt(0);
			ConnectionManager.getConnection().writeInt(0);
		}
		i++;
		if(Mideas.joueur1().getStuff(i) != null && WeaponManager.exists(Mideas.joueur1().getStuff(i).getId()) && Mideas.joueur1().getStuff(i).isRanged() && Mideas.joueur1().canEquipStuff(StuffManager.getStuff(Mideas.joueur1().getStuff(i).getId()))) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(i).getId());
			sendGemDatas(i);
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
			ConnectionManager.getConnection().writeInt(0);
			ConnectionManager.getConnection().writeInt(0);
			ConnectionManager.getConnection().writeInt(0);
		}
		ConnectionManager.getConnection().send();
	}*/
	
	/*private static void sendGemDatas(int slot) {
		sendSingleGemDatas(slot, 1);
		sendSingleGemDatas(slot, 2);
		sendSingleGemDatas(slot, 3);
	}*/
	
	/*private static void sendSingleGemDatas(int stuffSlot, int gemSlot) {
		if(Mideas.joueur1().getStuff(stuffSlot).getEquippedGem(gemSlot) != null && GemManager.exists(Mideas.joueur1().getStuff(stuffSlot).getEquippedGem(gemSlot).getId())) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(stuffSlot).getEquippedGem(gemSlot).getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
	}*/
}
