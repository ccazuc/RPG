package com.mideas.rpg.v2.stresstest;

import com.mideas.rpg.v2.connection.Connection;

public class Stresstest {

	private final StresstestConnectionMgr connectionMgr;
	private int accountId;
	private final int id;
	
	public Stresstest(int id)
	{
		this.connectionMgr = new StresstestConnectionMgr(this);
		this.id = id;
	}
	
	public Connection getAuthConnection()
	{
		return (this.connectionMgr.getAuthConnection());
	}
	
	public Connection getWorldServerConnection()
	{
		return (this.connectionMgr.getWorldServerConnection());
	}
	
	public StresstestConnectionMgr getConnectionMgr()
	{
		return (this.connectionMgr);
	}
	
	public void setAccountId(int id)
	{
		this.accountId = id;
	}
	
	public int getAccountId()
	{
		return (this.accountId);
	}
	
	public int getId()
	{
		return (this.id);
	}
}
