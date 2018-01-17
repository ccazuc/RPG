package com.mideas.rpg.v2.game.mail;

import java.util.ArrayList;

public class MailMgr {

	private final static ArrayList<Mail> mailList = new ArrayList<Mail>();
	public final static int SUBJECT_MAX_LENGTH = 55;
	
	public static void addMail(Mail mail)
	{
		mailList.add(mail);
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
	
	public static ArrayList<Mail> getMailList()
	{
		return (mailList);
	}
}
