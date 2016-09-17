package com.mideas.rpg.v2.hud;

import java.security.NoSuchAlgorithmException;

import com.mideas.rpg.v2.command.CommandLogin;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.utils.Sha1;

public class LoginManager {

	public static boolean checkLogin(String account, String password) throws NoSuchAlgorithmException {	
		ConnectionManager.connect();
		CommandLogin.write(account, Sha1.hash(password));
		return false;
	}
	
}
