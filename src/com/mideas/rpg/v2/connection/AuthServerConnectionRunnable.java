package com.mideas.rpg.v2.connection;

import com.mideas.rpg.v2.hud.LoginManager;
import com.mideas.rpg.v2.hud.LoginScreen;

public class AuthServerConnectionRunnable implements Runnable {

	private static boolean shouldConnect;
	private static String account;
	private static String password;
	private final static int LOOP_TIMER = 50;
	private static boolean run = true;
	
	@Override
	public void run() {
		System.out.println("AuthServerConnectionThread run");
		float delta;
		float timer;
		while(run) {
			timer = System.currentTimeMillis();
			if(shouldConnect) {
				if(LoginManager.checkLogin(account, password)) {
					LoginScreen.loginSuccess();
				}
				shouldConnect = false;
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
	
	public static void setShouldConnect(boolean we, String account, String password) {
		shouldConnect = we;
		AuthServerConnectionRunnable.account = account;
		AuthServerConnectionRunnable.password = password;
	}
}
