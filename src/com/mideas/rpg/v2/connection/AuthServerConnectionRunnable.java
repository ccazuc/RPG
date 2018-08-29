package com.mideas.rpg.v2.connection;

import com.mideas.rpg.v2.command.CommandLogin;
import com.mideas.rpg.v2.files.logs.LogsMgr;
import com.mideas.rpg.v2.hud.LoginManager;
import com.mideas.rpg.v2.hud.LoginScreen;

public class AuthServerConnectionRunnable implements Runnable {

	private static boolean shouldConnectToAuth;
	private static boolean shouldConnectToWorld;
	private static String account;
	private static String password;
	private final static int LOOP_TIMER = 50;
	private static boolean run = true;
	private static int realmId;
	
	@Override
	public void run() {
		System.out.println("AuthServerConnectionThread run");
		float delta;
		float timer;
		while(run) {
			timer = System.currentTimeMillis();
			if(shouldConnectToAuth) {
				if(LoginManager.checkLogin(account, password)) {
					LoginScreen.loginSuccess();
				}
				shouldConnectToAuth = false;
			}
			if(shouldConnectToWorld) {
				CommandLogin.loginRealm(realmId);
				shouldConnectToWorld = false;
			}
			delta = System.currentTimeMillis()-timer;
			if(delta < LOOP_TIMER) {
				try {
					Thread.sleep(LOOP_TIMER-(long)delta);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setRun(boolean we) {
		run = we;
	}
	
	public static void cancelAuthConnection() {
		ConnectionManager.setAuthConnectionCanceled(true);
		ConnectionManager.closeAuth();
		LoginScreen.resetPassword();
		LoginScreen.getAlert().setInactive();
		LogsMgr.writeConnectionLog("Canceled connection to auth server.");
	}
	
	public static void connectToWorldServer(int realmId) {
		shouldConnectToWorld = true;
		AuthServerConnectionRunnable.realmId = realmId;
	}
	
	public static void connectToAuthServer(String account, String password) {
		shouldConnectToAuth = true;
		AuthServerConnectionRunnable.account = account;
		AuthServerConnectionRunnable.password = password;
	}
}
