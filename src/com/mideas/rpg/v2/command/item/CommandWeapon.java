package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.item.gem.GemBonusType;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponSlot;
import com.mideas.rpg.v2.game.item.weapon.WeaponType;

public class CommandWeapon extends Command {
	
	@Override
	public void read() {
		int id = ConnectionManager.getConnection().readInt();
		String name = ConnectionManager.getConnection().readString();
		String spriteId = ConnectionManager.getConnection().readString();
		int number = ConnectionManager.getConnection().readInt();
		int i = 0;
		ClassType[] classType = new ClassType[number];
		while(i < classType.length) {
			classType[i] = ClassType.values()[ConnectionManager.getConnection().readChar()];
			i++;
		}
		WeaponType type = WeaponType.values()[ConnectionManager.getConnection().readChar()];
		WeaponSlot slot = WeaponSlot.values()[ConnectionManager.getConnection().readChar()];
		int quality = ConnectionManager.getConnection().readInt();
		GemColor color1 = GemColor.values()[ConnectionManager.getConnection().readChar()];
		GemColor color2 = GemColor.values()[ConnectionManager.getConnection().readChar()];
		GemColor color3 = GemColor.values()[ConnectionManager.getConnection().readChar()];
		GemBonusType gemBonusType = GemBonusType.values()[ConnectionManager.getConnection().readChar()];
		int gemBonusValue = ConnectionManager.getConnection().readInt();
		int level = ConnectionManager.getConnection().readInt();
		int armor = ConnectionManager.getConnection().readInt();
		int stamina = ConnectionManager.getConnection().readInt();
		int mana = ConnectionManager.getConnection().readInt();
		int critical = ConnectionManager.getConnection().readInt();
		int strength = ConnectionManager.getConnection().readInt();
		int sellPrice = ConnectionManager.getConnection().readInt();
		WeaponManager.storeNewPiece(new Stuff(id, name, spriteId, classType, type, slot, quality, color1, color2, color3, gemBonusType, gemBonusValue, level, armor, stamina, mana, critical, strength, sellPrice));
	}

	public static void write(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.WEAPON);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
}