package com.mideas.rpg.v2.hud;

import java.security.NoSuchAlgorithmException;

import com.mideas.rpg.v2.command.CommandLogin;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.utils.Sha1;

public class LoginManager {

	public static boolean checkLogin(String account, String password) {	
		ConnectionManager.connect();
		try {
			CommandLogin.write(account, Sha1.hash(password));
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}
}
