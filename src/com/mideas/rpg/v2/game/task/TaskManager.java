package com.mideas.rpg.v2.game.task;

import java.util.ArrayList;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.CommandPing;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.game.guild.Guild;
import com.mideas.rpg.v2.game.mail.MailMgr;

public class TaskManager {

	private final static ArrayList<Task> taskList = new ArrayList<Task>();
	
	@SuppressWarnings("unused")
	private final static Task updateRamTask = new Task(Mideas.RAM_UPDATE_FREQUENCE) {
		
		@Override
		public void action() {
			Mideas.setUsedRam(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
		}
	};
	@SuppressWarnings("unused")
	private final static Task pingTask = new Task(Mideas.PING_FREQUENCE) {
		
		@Override
		public boolean condition() {
			return ConnectionManager.isConnected();
		}
		
		@Override
		public void action() {
			CommandPing.write();
		}
	};
	@SuppressWarnings("unused")
	private final static Task checkLastPingTimerTask = new Task(Mideas.TIMEOUT_TIMER) {
		
		@Override
		public boolean condition() {
			return CommandPing.getPingStatus();
		}
		
		@Override
		public void action() {
			CommandPing.setPingStatus(false);
			ConnectionManager.disconnect();
		}
		
		@Override
		public long getLastTickTimer() {
			return CommandPing.getTimer();
		}
	};
	@SuppressWarnings("unused")
	private final static Task gcTask = new Task(Mideas.GC_FREQUENCE) {
	
		@Override
		public void action() {
			System.gc();
		}
	};
	@SuppressWarnings("unused")
	private final static Task guildUpdateLastLoginTimerTask = new Task(Guild.LAST_ONLINE_TIMER_UPDATE_FREQUENCE) {
	
		@Override
		public boolean condition() {
			return Mideas.joueur1() != null && Mideas.joueur1().getGuild() != null;
		}
		
		@Override
		public void action() {
			Mideas.joueur1().getGuild().updateGuildTimer();
		}
	};
	@SuppressWarnings("unused")
	private final static Task mailUpdateExpireTimerTask = new Task(MailMgr.UPDATE_EXPIRE_TIMER_FREQUENCE)
	{
	
		@Override
		public boolean condition()
		{
			return (MailMgr.getMailList().size() != 0);
		}
		
		@Override
		public void action()
		{
			MailMgr.updateMailExpireDateTimer();
		}
	};
	
	public static void executeTask() {
		int i = 0;
		while(i < taskList.size()) {
			taskList.get(i).event();
			i++;
		}
	}
	
	public static void addTask(Task task) {
		taskList.add(task);
	}
}
