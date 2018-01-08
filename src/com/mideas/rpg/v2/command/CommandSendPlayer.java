package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.files.config.ChatConfigManager;
import com.mideas.rpg.v2.game.classes.Wear;
import com.mideas.rpg.v2.game.item.weapon.WeaponType;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.game.unit.Joueur;

public class CommandSendPlayer extends Command {
	
	@Override
	public void read() {
		ClassType classType = ClassType.values()[ConnectionManager.getWorldServerConnection().readByte()];
		int id = ConnectionManager.getWorldServerConnection().readInt();
		String name = ConnectionManager.getWorldServerConnection().readString();
		Wear wear = Wear.values()[ConnectionManager.getWorldServerConnection().readByte()];
		WeaponType[] weaponType = new WeaponType[ConnectionManager.getWorldServerConnection().readByte()];
		int i = 0;
		while(i < weaponType.length) {
			weaponType[i] = WeaponType.values()[ConnectionManager.getWorldServerConnection().readByte()];
			i++;
		}
		int stamina = ConnectionManager.getWorldServerConnection().readInt();
		int maxStamina = ConnectionManager.getWorldServerConnection().readInt();
		int mana = ConnectionManager.getWorldServerConnection().readInt();
		int maxMana = ConnectionManager.getWorldServerConnection().readInt();
		int strength = ConnectionManager.getWorldServerConnection().readInt();
		int armor = ConnectionManager.getWorldServerConnection().readInt();
		int critical = ConnectionManager.getWorldServerConnection().readInt();
		Mideas.setJoueur1(new Joueur(classType, id, name, wear, weaponType, stamina, mana, strength, armor, armor, critical, maxStamina, maxMana));
		ChatConfigManager.loadConfig();
	}

}
