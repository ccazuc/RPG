package com.mideas.rpg.v2.jdo.wrapper;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mideas.rpg.v2.jdo.JDO;

public class MariaDB extends JDO {
	
	/**
	 * Create a connection to a MariaDB server
	 * @param host
	 * @param database
	 * @param user
	 * @param password
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MariaDB(final String host, final String database, final String user, final String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		this(host, (short)3306, database, user, password);
	}
	
	/**
	 * Create a connection to a MariaDB server
	 * @param host
	 * @param port
	 * @param database
	 * @param user
	 * @param password
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MariaDB(final String host, final int port, final String database, final String user, final String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	Class.forName("org.mariadb.jdbc.Driver").newInstance(); 
		connection = DriverManager.getConnection("jdbc:mariadb://"+host+":"+port+"/"+database, user, password);
	}
	
}