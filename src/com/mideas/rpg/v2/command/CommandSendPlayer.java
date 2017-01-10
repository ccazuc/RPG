package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.classes.Wear;
import com.mideas.rpg.v2.game.item.weapon.WeaponType;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.game.unit.Joueur;

public class CommandSendPlayer extends Command {
	
	@Override
	public void read() {
		ClassType classType = ClassType.values()[ConnectionManager.getConnection().readByte()];
		int id = ConnectionManager.getConnection().readInt();
		Wear wear = Wear.values()[ConnectionManager.getConnection().readByte()];
		WeaponType[] weaponType = new WeaponType[ConnectionManager.getConnection().readByte()];
		int i = 0;
		while(i < weaponType.length) {
			weaponType[i] = WeaponType.values()[ConnectionManager.getConnection().readByte()];
			i++;
		}
		int stamina = ConnectionManager.getConnection().readInt();
		int maxStamina = ConnectionManager.getConnection().readInt();
		int mana = ConnectionManager.getConnection().readInt();
		int maxMana = ConnectionManager.getConnection().readInt();
		int strength = ConnectionManager.getConnection().readInt();
		int armor = ConnectionManager.getConnection().readInt();
		int critical = ConnectionManager.getConnection().readInt();
		Mideas.setJoueur1(new Joueur(classType, id, wear, weaponType, stamina, mana, strength, armor, armor, critical, maxStamina, maxMana));
	}

}
