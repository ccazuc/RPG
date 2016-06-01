package com.mideas.rpg.v2.hud;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.jdo.JDOStatement;
import com.mideas.rpg.v2.utils.Sha1;

public class LoginManager {

	public static boolean checkLogin(String account, String password) throws SQLException, NoSuchAlgorithmException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT password, id, rank, banned FROM account WHERE name = ?");
		statement.putString(account);
		statement.execute();
		if(statement.fetch()) {
			String passwords = statement.getString();
			int id = statement.getInt();
			int rank = statement.getInt();
			int banned = statement.getInt();
			if(Sha1.hash(password).equals(passwords)) {
				if(banned == 0) {
					Interface.setHasLoggedIn(true);
					Mideas.setAccountId(id);
					Mideas.setRank(rank);
					SelectScreen.mouseEvent();
					return true;
				}
			}
		}
		else {
		}
		return false;
	}
	
}
