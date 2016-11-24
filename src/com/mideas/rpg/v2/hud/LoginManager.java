package com.mideas.rpg.v2.hud;

import java.security.NoSuchAlgorithmException;

import com.mideas.rpg.v2.command.CommandLogin;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.utils.Hash;

public class LoginManager {

	public static boolean checkLogin(String account, String password) {
		System.out.println("LoginManager:checkLogin()");
		LoginScreen.resetPassword();
		ConnectionManager.connectAuthServer();
		try {
			CommandLogin.write(account, Hash.hash(password));
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}
}
