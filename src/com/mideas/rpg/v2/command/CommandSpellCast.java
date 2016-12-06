package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.hud.RedAlertFrame;

public class CommandSpellCast extends Command {

	@Override
	public void read() {
		
	}
	
	public static void write(Spell spell) {
		if(Mideas.joueur1().getMana() >= spell.getManaCost()) {
			ConnectionManager.getConnection().writeShort(PacketID.SPELL_CAST);
			ConnectionManager.getConnection().writeInt(spell.getSpellId());
			ConnectionManager.getConnection().send();
			System.out.println(spell.getSpellId());
		}
		else {
			RedAlertFrame.addNewAlert("Vous n'avez pas assez de mana.");
		}
	}
 }
