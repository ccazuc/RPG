package com.mideas.rpg.v2.jdo;

import java.sql.Connection;
import java.sql.SQLException;

public class JDO {
	
	protected Connection connection;
	
	/**
	 * Get the SQL connection
	 * @return
	 * @throws SQLException
	 */
	public final Connection getConnection() throws SQLException {
		if(!connection.isClosed()) {
			return connection;
		}
		throw new SQLException("Connection is closed");
	}
	
	/**
	 * Close the SQL connection
	 * @throws SQLException 
	 * @throws Exception
	 */
	public final void close() {
		try {
			connection.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a new prepared statement
	 * @param req
	 * @return
	 * @throws SQLException 
	 */
	public final JDOStatement prepare(final String request) throws SQLException {
		return new JDOStatement(request, this);
	}
	
}
