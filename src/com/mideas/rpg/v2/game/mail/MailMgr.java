package com.mideas.rpg.v2.game.mail;

import java.util.ArrayList;
import java.util.HashSet;

import com.mideas.rpg.v2.callback.CallbackMgr;

public class MailMgr {

	private final static ArrayList<Mail> mailList = new ArrayList<Mail>();
	private final static HashSet<Long> loadedMail = new HashSet<Long>();
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
	
	public static boolean wasLoadRequested(long GUID)
	{
		return (loadedMail.contains(GUID));
	}
	
	public static void addLoadedMail(long GUID)
	{
		loadedMail.add(GUID);
	}
	
	public static void clearLoadedMail()
	{
		loadedMail.clear();
	}
	
	public static Mail getMail(long GUID)
	{
		int i = -1;
		while (++i < mailList.size())
			if (mailList.get(i).getGUID() == GUID)
				return (mailList.get(i));
		return (null);
	}
	
	public static void removeMail(long GUID)
	{
		int i = -1;
		while (++i < mailList.size())
			if (mailList.get(i).getGUID() == GUID)
			{
				CallbackMgr.onMailDeleted(mailList.get(i));
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
