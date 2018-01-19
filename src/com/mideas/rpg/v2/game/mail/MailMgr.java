package com.mideas.rpg.v2.game.mail;

import java.util.ArrayList;

public class MailMgr {

	private final static ArrayList<Mail> mailList = new ArrayList<Mail>();
	public final static int SUBJECT_MAX_LENGTH = 55;
	public final static int UPDATE_EXPIRE_TIMER_FREQUENCE = 60000;
	
	public static void addMail(Mail mail)
	{
		mailList.add(0, mail);
	}
	
	public static void clearMailList()
	{
		mailList.clear();
	}
	
	public static void removeMail(long GUID)
	{
		int i = -1;
		while (++i < mailList.size())
			if (mailList.get(i).getGUID() == GUID)
			{
				mailList.remove(i);
				return;
			}
	}
	
	public static void mailOpened(long GUID)
	{
		int i = -1;
		while (++i < mailList.size())
			if (mailList.get(i).getGUID() == GUID)
			{
				mailList.get(i).readed();
				return;
			}
	}
	
	public static void updateMailExpireDateTimer()
	{
		int i = -1;
		while (++i < mailList.size())
			mailList.get(i).updateExpireDateString();
	}
	
	public static ArrayList<Mail> getMailList()
	{
		return (mailList);
	}
}
