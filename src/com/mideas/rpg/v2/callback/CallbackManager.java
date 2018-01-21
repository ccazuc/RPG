package com.mideas.rpg.v2.callback;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.game.mail.Mail;

public class CallbackManager {

	public static void onMailReceived()
	{
		Interface.getMailFrame().onMailReceived();
	}
	
	public static void onMailDeleted(Mail mail)
	{
		Interface.getMailFrame().onMailDeleted(mail);
	}
}
