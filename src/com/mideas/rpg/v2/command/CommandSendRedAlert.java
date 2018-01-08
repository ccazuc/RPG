package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.redalert.DefaultRedAlert;
import com.mideas.rpg.v2.hud.RedAlertFrame;

public class CommandSendRedAlert extends Command {

	@Override
	public void read() {
		boolean isKnown = ConnectionManager.getWorldServerConnection().readBoolean();
		if(isKnown) {
			DefaultRedAlert alert = DefaultRedAlert.values()[ConnectionManager.getWorldServerConnection().readByte()];
			RedAlertFrame.addNewAlert(alert);
		}
		else {
			String text = ConnectionManager.getWorldServerConnection().readString();
			RedAlertFrame.addNewAlert(text);
		}
	}
}
