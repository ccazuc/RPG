package com.mideas.rpg.v2.chat;

import java.sql.SQLException;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.ShopManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellBarManager;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.hud.ContainerFrame;

public class ChatCommandOther {

	public static void chatCommandOther(String[] datas) throws SQLException {
		if(datas.length >= 1) {
			if(datas[0].equals(".quit")) {
				Mideas.saveAllStats();
				System.exit(0);
			}
			else if(datas[0].equals(".logout")) {
				Mideas.setCharacterId(0);
				Mideas.setJoueur1Null();
				Interface.closeAllFrame();
			}
			else if(datas[0].equals(".lookspell")) {
				if(datas.length >= 2) {
					if(SpellManager.exists(Integer.valueOf(datas[1])) && Integer.valueOf(datas[1]) != -1) {
						int id = Integer.valueOf(datas[1]);
						Spell spell = SpellManager.getShortcutSpell(id).getSpell();
						ChatFrame.addMessage(new Message(spell.getName()+": ", false, Color.yellow));
						ChatFrame.addMessage(new Message(spell.getBaseDamage()+" damage", false, Color.yellow));
						ChatFrame.addMessage(new Message(spell.getManaCost()+" mana", false, Color.yellow));
					}
					else {
						ChatFrame.addMessage(new Message("That spell doesn't exist.", false, Color.yellow));
					}
				}
			}
			else if(datas[0].equals(".itemnumber")) {
				if(datas.length >= 2) {
					if(Item.exists(Integer.valueOf(datas[1]))) {
						int id = Integer.valueOf(datas[1]);
						ChatFrame.addMessage(new Message("You have "+Mideas.bag().getNumberItemInBags(id)+" "+Item.getItem(id).getStuffName()+" in your bags.", false, Color.yellow));
					}
					else {
						ChatFrame.addMessage(new Message("That item doesn't exist.", false, Color.yellow));
					}
				}
			}
			else if(datas[0].equals(".update")) {
				Mideas.getGold();
				Mideas.getExp();
				Mideas.getConfig();
				ShopManager.getShopList().clear();
				ShopManager.loadStuffs();
				CharacterStuff.getEquippedBags();
				CharacterStuff.getBagItems();
				CharacterStuff.getEquippedItems();
				ContainerFrame.setBagchange(true);
				SpellBarManager.loadSpellBar();
				Mideas.bag().setBagChange(true);
			}
			else if(datas[0].equals(".additem")) {
				if(datas.length >= 2) {
					int number = 1;
					if(Item.exists(Integer.valueOf(datas[1]))) {
						if(datas.length >= 3) {
							if(Integer.valueOf(datas[2]) >= 1 && Integer.valueOf(datas[2]) <= 255) {
								number = Integer.valueOf(datas[2]);
							}
						}
						if(Item.getItem(Integer.valueOf(datas[1])).isStackable()) {
							Mideas.joueur1().addItem(Item.getItem(Integer.valueOf(datas[1])), number);
						}
						else {
							
						}
					}
					else {
						ChatFrame.addMessage(new Message("That item doesn't exist", false, Color.yellow));
					}
				}
			}
			else if(datas[0].equals(".deleteitem")) {
				if(datas.length >= 2) {
					int number = 1;
					if(Item.exists(Integer.valueOf(datas[1]))) {
						if(datas.length >= 3) {
							if(Integer.valueOf(datas[2]) >= 1 && Integer.valueOf(datas[2]) <= 255) {
								number = Integer.valueOf(datas[2]);
							}
						}
						Mideas.joueur1().deleteItem(Item.getItem(Integer.valueOf(datas[1])), number);
					}
					else {
						ChatFrame.addMessage(new Message("That item doesn't exist", false, Color.yellow));
					}
				}
			}
			else {
				ChatCommandManager.UnknwownCommand();
			}
		}
	}
}
