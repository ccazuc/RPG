package com.mideas.rpg.v2.callback;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.game.mail.Mail;
import com.mideas.rpg.v2.game.mail.MailMgr;
import com.mideas.rpg.v2.game.redalert.DefaultRedAlert;
import com.mideas.rpg.v2.hud.RedAlertFrame;

public class CallbackManager {

	public static void onMailReceived()
	{
		Interface.getMailFrame().onMailReceived();
	}
	
	public static void onMailDeleted(Mail mail)
	{
		Interface.getMailFrame().onMailDeleted(mail);
	}
	
	public static void onMailSent()
	{
		Interface.getMailFrame().onMailSent();
		RedAlertFrame.addNewAlert(DefaultRedAlert.MAIL_SENT, RedAlertFrame.YELLOW);
		
	}
	
	public static void onMailLoaded(Mail mail)
	{
		Interface.getMailFrame().onMailLoaded(mail);
	}
	
	public static void onPlayerLoaded()
	{
		MailMgr.clearLoadedMail();
		MailMgr.clearMailList();
		Interface.getMailFrame().close();
	}
}
