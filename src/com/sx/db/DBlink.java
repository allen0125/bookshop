package com.sx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBlink {
	private static final String DRIVERCLASS = "com.mysql.jdbc.Driver";
	// private static final String DRIVERCLASS =
	// "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/library";
	// private static final String URL =
	// "jdbc:oracle:thin:@localhost:1521:ORCL";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";

	static {
		try {
			Class.forName(DRIVERCLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * (封装)获得数据库连接的方法
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * (封装)断开数据库连接的方法
	 */
	public static void closeConnection(ResultSet rs, Statement state,
			Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (state != null)
				state.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}