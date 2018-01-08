package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.item.DragItem;
import com.mideas.rpg.v2.game.item.RequestItem;
import com.mideas.rpg.v2.game.item.container.Container;
import com.mideas.rpg.v2.game.item.container.ContainerManager;

public class CommandSendContainer extends Command {
	
	@Override
	public void read() {
		byte length = ConnectionManager.getWorldServerConnection().readByte();
		int i = 0;
		while(i < length) {
			byte size = ConnectionManager.getWorldServerConnection().readByte();
			int id = ConnectionManager.getWorldServerConnection().readInt();
			if(ContainerManager.exists(id)) {
				Mideas.joueur1().bag().setEquippedBag(i, ContainerManager.getClone(id));
			}
			else if(id != 0) {
				Mideas.joueur1().bag().setEquippedBag(i, new Container(id, size));
				CommandRequestItem.write(new RequestItem(id, DragItem.EQUIPPED_CONTAINER, i));
			}
			i++;
		}
	}
}
