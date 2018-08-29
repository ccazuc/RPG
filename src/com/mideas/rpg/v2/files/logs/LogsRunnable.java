package com.mideas.rpg.v2.files.logs;

import java.util.ArrayList;

public class LogsRunnable implements Runnable
{

	private final static int LOOP_TIMER = 1000;
	private static boolean running = true;
	private static boolean shouldClose;
	private final ArrayList<Logger> loggerList;
	
	public LogsRunnable()
	{
		this.loggerList = new ArrayList<Logger>(LogsType.values().length);
		for (int i = 0; i < LogsType.values().length; ++i)
		{
			this.loggerList.add(new Logger(LogsType.values()[i].getPath()));
			if (this.loggerList.get(i).hasCreationFailed())
				this.loggerList.set(i, null);
		}
	}
	
	@Override
	public void run()
	{
		long time;
		long delta;
		while (running)
		{
			time = System.currentTimeMillis();
			for (int i = 0; i < this.loggerList.size(); ++i)
			{
				if (this.loggerList.get(i) == null)
					continue;
				this.loggerList.get(i).moveLogsFromQueue();
				this.loggerList.get(i).writeLogs();
			}
			delta = System.currentTimeMillis()-time;
			if (shouldClose)
			{
				running = false;
				for (int i = 0; i < this.loggerList.size(); ++i)
				{
					if (this.loggerList.get(i) != null && !this.loggerList.get(i).isEmpty())
					{
						running = true;
						break;
					}
				}
			}
			if (delta < LOOP_TIMER)
			{
				try {
					Thread.sleep((LOOP_TIMER-delta));
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addLog(LogsType type, String log)
	{
		if (this.loggerList.get(type.getId()) == null)
			return;
		this.loggerList.get(type.getId()).addLogToQueue(log);
	}
	
	public void close()
	{
		shouldClose = true;
	}
}
