package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.classes.Wear;
import com.mideas.rpg.v2.game.item.gem.GemBonusType;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.stuff.StuffType;

public class CommandStuff extends Command {
	
	@Override
	public void read() {
		StuffType type = StuffType.values()[ConnectionManager.getConnection().readChar()];
		int number = ConnectionManager.getConnection().readInt();
		int i = 0;
		ClassType[] classType = new ClassType[number];
		while(i < classType.length) {
			classType[i] = ClassType.values()[ConnectionManager.getConnection().readChar()];
			i++;
		}
		String spriteId = ConnectionManager.getConnection().readString();
		int id = ConnectionManager.getConnection().readInt();
		String name = ConnectionManager.getConnection().readString();
		int quality = ConnectionManager.getConnection().readInt();
		GemColor color1 = GemColor.values()[ConnectionManager.getConnection().readChar()];
		GemColor color2 = GemColor.values()[ConnectionManager.getConnection().readChar()];
		GemColor color3 = GemColor.values()[ConnectionManager.getConnection().readChar()];
		GemBonusType gemBonusType = GemBonusType.values()[ConnectionManager.getConnection().readChar()];
		int gemBonusValue = ConnectionManager.getConnection().readInt();
		int level = ConnectionManager.getConnection().readInt();
		Wear wear = Wear.values()[ConnectionManager.getConnection().readChar()];
		int critical = ConnectionManager.getConnection().readInt();
		int strength = ConnectionManager.getConnection().readInt();
		int stamina = ConnectionManager.getConnection().readInt();
		int armor = ConnectionManager.getConnection().readInt();
		int mana = ConnectionManager.getConnection().readInt();
		int sellPrice = ConnectionManager.getConnection().readInt();
		StuffManager.storeNewPiece(new Stuff(type, classType, spriteId, id, name, quality, color1, color2, color3, gemBonusType, gemBonusValue, level, wear, critical, strength, stamina, armor, mana, sellPrice));
	}
	
	public static void write(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.STUFF);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
}
