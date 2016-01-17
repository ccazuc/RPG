package com.mideas.rpg.v2.jdo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDOStatement {
	
	private PreparedStatement statement;
	private ResultSet result;
	private int index;
	
	/**
	 * Create new JDO Statement
	 * @param request
	 * @param jdo
	 * @throws SQLException
	 */
	protected JDOStatement(final String request, final JDO jdo) throws SQLException {
		statement = jdo.getConnection().prepareStatement(request);
	}
	
	/**
	 * Put boolean
	 * @param b
	 * @throws SQLException
	 */
	public final void putBoolean(final boolean b) throws SQLException {
		statement.setByte(++index, (byte)(b?1:0));
	}
	
	/**
	 * Put byte
	 * @param b
	 * @throws SQLException
	 */
	public final void putByte(final byte b) throws SQLException {
		statement.setByte(++index, b);
	}
	
	/**
	 * Put short
	 * @param s
	 * @throws SQLException
	 */
	public final void putShort(final short s) throws SQLException {
		statement.setShort(++index, s);
	}
	
	/**
	 * Put integer
	 * @param i
	 * @throws SQLException
	 */
	public final void putInt(final int i) throws SQLException {
		statement.setInt(++index, i);
	}
	
	/**
	 * Put long
	 * @param l
	 * @throws SQLException
	 */
	public final void putLong(final long l) throws SQLException {
		statement.setLong(++index, l);
	}
	
	/**
	 * Put float
	 * @param f
	 * @throws SQLException
	 */
	public final void putFloat(float f) throws SQLException {
		statement.setFloat(++index, f);
	}
	
	/**
	 * Put double
	 * @param d
	 * @throws SQLException
	 */
	public final void putDouble(final double d) throws SQLException {
		statement.setDouble(++index, d);
	}
	
	/**
	 * Put char
	 * @param c
	 * @throws SQLException
	 */
	public final void putChar(final char c) throws SQLException {
		statement.setString(++index, Character.toString(c));
	}
	
	/**
	 * Put string
	 * @param s
	 * @throws SQLException
	 */
	public final void putString(final String s) throws SQLException {
		statement.setString(++index, s);
	}
	
	/**
	 * Execute statement
	 * @throws SQLException
	 */
	public final void execute() throws SQLException {
		result = statement.executeQuery();
	}
	
	/**
	 * Return if there is a next result
	 * @return
	 * @throws SQLException
	 */
	public final boolean fetch() throws SQLException {
		index = 0;
		return result.next();
	}
	
	/**
	 * Get boolean
	 * @return
	 * @throws SQLException
	 */
	public final boolean getBoolean() throws SQLException {
		return result.getByte(++index) == 1;
	}
	
	/**
	 * Get byte
	 * @return
	 * @throws SQLException
	 */
	public final byte getByte() throws SQLException {
		return result.getByte(++index);
	}
	
	/**
	 * Get short
	 * @return
	 * @throws SQLException
	 */
	public final short getShort() throws SQLException {
		return result.getShort(++index);
	}
	
	/**
	 * Get integer
	 * @return
	 * @throws SQLException
	 */
	public final int getInt() throws SQLException {
		return result.getInt(++index);
	}
	
	/**
	 * Get long
	 * @return
	 * @throws SQLException
	 */
	public final long getLong() throws SQLException {
		return result.getLong(++index);
	}
	
	/**
	 * Get float
	 * @return
	 * @throws SQLException
	 */
	public final float getFloat() throws SQLException {
		return result.getFloat(++index);
	}
	
	/**
	 * Get double
	 * @return
	 * @throws SQLException
	 */
	public final double getDouble() throws SQLException {
		return result.getDouble(++index);
	}
	
	/**
	 * Get char
	 * @return
	 * @throws SQLException
	 */
	public final char getChar() throws SQLException {
		final String res = result.getString(++index);
		return res.length()>0?res.charAt(0):'\0';
	}
	
	/**
	 * Get string
	 * @return
	 * @throws SQLException
	 */
	public final String getString() throws SQLException {
		return result.getString(++index);
	}
	
	/**
	 * Close the statement
	 * @throws SQLException
	 */
	public final void close() throws SQLException {
		statement.close();
		result.close();
	}
	
}
