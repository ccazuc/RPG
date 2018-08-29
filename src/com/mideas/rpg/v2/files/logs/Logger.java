package com.mideas.rpg.v2.files.logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class Logger {

	public final static String FOLDER_PATH = "Logs/";
	public final ArrayList<String> waitingList;
	public final ArrayList<String> logList;
	private PrintWriter pw;
	private boolean creationFailed;
	private final static Calendar calendar = Calendar.getInstance();
	
	public Logger(String fileName)
	{
		this.waitingList = new ArrayList<String>();
		this.logList = new ArrayList<String>();
		File folder = new File(FOLDER_PATH);
		if (!folder.exists())
			folder.mkdirs();
		FileWriter fw;
		BufferedWriter bw;
		try
		{
			folder = new File(FOLDER_PATH + fileName);
			if (!folder.exists())
				folder.createNewFile();
			fw = new FileWriter(folder.getAbsolutePath(), true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.creationFailed = true;
			return;
		}
		bw = new BufferedWriter(fw);
		this.pw = new PrintWriter(bw);
	}
	
	public final void addLogToQueue(String log)
	{
		synchronized (this.waitingList)
		{
			this.waitingList.add(log);
		}
	}
	
	public final void moveLogsFromQueue()
	{
		synchronized (this.waitingList)
		{
			while (this.waitingList.size() > 0)
			{
				this.logList.add(this.waitingList.get(0));
				this.waitingList.remove(0);
			}
		}
	}
	
	private void executeLog(String log)
	{
		this.pw.println("[" + calendar.getTime() + "] " + log);
		this.pw.flush();
	}
	
	public final void writeLogs()
	{
		while (this.logList.size() > 0)
		{
			executeLog(this.logList.get(0));
			this.logList.remove(0);
		}
	}
	
	public final boolean isEmpty()
	{
		synchronized (this.waitingList)
		{
			return (this.waitingList.size() == 0 && this.logList.size() == 0);
		}
	}
	
	public final boolean hasCreationFailed()
	{
		return (this.creationFailed);
	}
}
