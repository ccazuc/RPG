package com.mideas.rpg.v2.hud;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.jdo.JDOStatement;
import com.mideas.rpg.v2.utils.Sha1;

public class LoginManager {

	public static boolean checkLogin(String account, String password) throws SQLException, NoSuchAlgorithmException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT password, id, rank, banned, ban_duration FROM account WHERE name = ?");
		statement.putString(account);
		statement.execute();
		if(statement.fetch()) {
			String passwords = statement.getString();
			int id = statement.getInt();
			int rank = statement.getInt();
			int banned = statement.getInt();
			int banDuration = statement.getInt();
			if(Sha1.hash(password).equals(passwords)) {
				if(banned == 0) {
					Interface.setHasLoggedIn(true);
					Mideas.setAccountId(id);
					Mideas.setRank(rank);
					SelectScreen.mouseEvent();
					return true;
				}
				else {
					if(banDuration == -1) {
						LoginScreen.getAlert().setActive();
						LoginScreen.getAlert().setText("Vous avez été bannis de façon permanente.");
					}
					else {
						LoginScreen.getAlert().setActive();
						LoginScreen.getAlert().setText("Vous avez été bannis de façon temporaire.");
					}
				}
			}
			else {
				LoginScreen.getAlert().setActive();
				LoginScreen.getAlert().setText("Informations incorrectes.");
			}
		}
		else {
			LoginScreen.getAlert().setActive();
			LoginScreen.getAlert().setText("Informations incorrectes.");
		}
		LoginScreen.getPassword().resetText();
		return false;
	}
	
}
