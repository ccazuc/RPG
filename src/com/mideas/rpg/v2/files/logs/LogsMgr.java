package com.mideas.rpg.v2.files.logs;

public class LogsMgr
{
	private static LogsRunnable logsRunnable;
	private static Thread logsThread;
	
	public static void createThread()
	{
		logsRunnable = new LogsRunnable();
		logsThread = new Thread(logsRunnable);
		logsThread.start();
	}
	
	public static void closeThread()
	{
		logsRunnable.close();
	}
	
	public static void writeConnectionLog(String log)
	{
		logsRunnable.addLog(LogsType.CONNECTION, log);
	}
	
	public static void writeMiscLog(String log)
	{
		logsRunnable.addLog(LogsType.MISC, log);
	}
}
