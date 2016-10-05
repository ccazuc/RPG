package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;

public class CommandWeapon extends Command {
	
	@Override
	public void read() {
		WeaponManager.storeNewPiece(ConnectionManager.getConnection().readWeapon());
	}

	public static void write(int id) {
		if(!ConnectionManager.getItemRequested().containsKey(id)) {
			ConnectionManager.getConnection().writeByte(PacketID.WEAPON);
			ConnectionManager.getConnection().writeInt(id);
			ConnectionManager.getConnection().send();
			ConnectionManager.getItemRequested().put(id, new Stuff(id));
		}
	}
}