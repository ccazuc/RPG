package com.mideas.rpg.v2.chat;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.CommandAddItem;
import com.mideas.rpg.v2.command.CommandLoadCharacter;
import com.mideas.rpg.v2.command.CommandLogout;
import com.mideas.rpg.v2.command.chat.CommandListPlayer;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.shop.ShopManager;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellBarManager;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.hud.ContainerFrame;

public class ChatCommandOther {

	public static void chatCommandOther(String[] datas) {
		if(datas.length >= 1) {
			if(datas[0].equals(".quit")) {
				Mideas.saveAllStats();
				CommandLogout.write();
				ConnectionManager.close();
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
						ChatFrame.addMessage(new Message(spell.getDefaultDamage()+" damage", false, Color.yellow));
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
						ChatFrame.addMessage(new Message("You have "+Mideas.joueur1().bag().getNumberItemInBags(id)+" "+Item.getItem(id).getStuffName()+" in your bags.", false, Color.yellow));
					}
					else {
						ChatFrame.addMessage(new Message("That item doesn't exist.", false, Color.yellow));
					}
				}
			}
			else if(datas[0].equals(".update")) {
				Mideas.joueur1().getGold();
				Mideas.joueur1().getExp();
				ShopManager.getShopList().clear();
				Mideas.getConfig();
				ShopManager.loadStuffs();
				CharacterStuff.getEquippedBags();
				//CharacterStuff.getBagItems();
				//CharacterStuff.getEquippedItems();
				ContainerFrame.setBagchange(true);
				SpellBarManager.loadSpellBar();
				Mideas.joueur1().bag().setBagChange(true);
				CommandLoadCharacter.write(1);
				Interface.setStuffFullyLoaded(false);
			}
			else if(datas[0].equals(".reloadui")) {
				Mideas.loadingScreen();
			}
			else if(datas[0].equals(".additem")) {
				if(Mideas.getRank() >= 1) {
					if(datas.length >= 2) {
						if(Mideas.isInteger(datas[1])) {
							int number = 1;
							int character_id = Mideas.joueur1().getId();
							int item_id = Integer.valueOf(datas[1]);
							if(datas.length >= 3) {
								if(Mideas.isInteger(datas[2])) {
									number = Integer.valueOf(datas[2]);
									if(datas.length >= 4) {
										if(Mideas.isInteger(datas[3])) {
											character_id = Integer.valueOf(datas[3]);
										}
										else {
											ChatFrame.addMessage(new Message("Invalid value for [character_id]", false, Color.yellow));
											return;
										}
									}
								}
								else {
									ChatFrame.addMessage(new Message("Invalid value for [amount]", false, Color.yellow));
									return;
								}
							}
							CommandAddItem.write(character_id, item_id, number);
						}
						else {
							ChatFrame.addMessage(new Message("Invalid value for [item_id]", false, Color.yellow));
							return;
						}
					}
				}
				else {
					ChatFrame.addMessage(new Message("You don't have the right to do this.", false, Color.yellow));
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
			else if(datas[0].equals(".who")) {
				CommandListPlayer.write();
			}
			else if(datas[0].equals(".help")) {
				ChatFrame.addMessage(new Message(".[joueur1, joueur2] [set, get] [stamina, mana, gold, experience] [value]", false, Color.yellow));
				ChatFrame.addMessage(new Message(".lookspell [spell_id]", false, Color.yellow));
				ChatFrame.addMessage(new Message(".itemnumber [item_id]", false, Color.yellow));
				ChatFrame.addMessage(new Message(".additem [item_id] [amount] [character_id]", false, Color.yellow));
				ChatFrame.addMessage(new Message(".deleteitem [item_id] [amount]", false, Color.yellow));
				ChatFrame.addMessage(new Message(".reloadui Reload the UI", false, Color.yellow));
				ChatFrame.addMessage(new Message(".update Reload game's datas", false, Color.yellow));
				ChatFrame.addMessage(new Message(".quit Exit the game", false, Color.yellow));
				ChatFrame.addMessage(new Message(".logout Logout your character", false, Color.yellow));
				ChatFrame.addMessage(new Message(".who List the online players", false, Color.yellow));
			}
			else {
				ChatCommandManager.UnknwownCommand();
			}
		}
	}
}
