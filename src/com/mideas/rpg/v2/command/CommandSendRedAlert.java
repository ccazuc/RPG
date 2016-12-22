package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.redalert.DefaultRedAlert;
import com.mideas.rpg.v2.hud.RedAlertFrame;

public class CommandSendRedAlert extends Command {

	@Override
	public void read() {
		boolean isKnown = ConnectionManager.getConnection().readBoolean();
		if(isKnown) {
			DefaultRedAlert alert = DefaultRedAlert.values()[ConnectionManager.getConnection().readByte()];
			RedAlertFrame.addNewAlert(alert);
		}
		else {
			String text = ConnectionManager.getConnection().readString();
			RedAlertFrame.addNewAlert(text);
		}
	}
}
