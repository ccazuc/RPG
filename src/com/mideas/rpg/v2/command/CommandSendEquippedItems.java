package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.stuff.StuffType;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;

public class CommandSendEquippedItems extends Command {

	@Override
	public void read() {
		
	}
	
	public void write() {
		ConnectionManager.getConnection().writeByte(PacketID.SEND_EQUIPPED_ITEMS);
		ConnectionManager.getConnection().writeInt(Mideas.getCharacterId());
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
	}
	
	private static void sendGemDatas(int slot) {
		if(Mideas.joueur1().getStuff(slot).getEquippedGem1() != null && GemManager.exists(Mideas.joueur1().getStuff(slot).getEquippedGem1().getId())) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(slot).getEquippedGem1().getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
		if(Mideas.joueur1().getStuff(slot).getEquippedGem2() != null && GemManager.exists(Mideas.joueur1().getStuff(slot).getEquippedGem2().getId())) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(slot).getEquippedGem2().getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
		if(Mideas.joueur1().getStuff(slot).getEquippedGem3() != null && GemManager.exists(Mideas.joueur1().getStuff(slot).getEquippedGem3().getId())) {
			ConnectionManager.getConnection().writeInt(Mideas.joueur1().getStuff(slot).getEquippedGem3().getId());
		}
		else {
			ConnectionManager.getConnection().writeInt(0);
		}
	}
}
