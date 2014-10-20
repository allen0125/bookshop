package com.sx.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sx.db.DBlink;

/**
 * 获取数据库元数据的类，比如表名、列名等
 * 
 * @author sunjie
 *
 */
public class DbMetadata {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DbMetadata.class);

	/*
	 * 获取数据库中的表名
	 */
	public static List<String> getTables() {
		List<String> tableList = new ArrayList<>();
		Connection conn = DBlink.getConnection();
		ResultSet rs = null;
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				tableList.add(tableName);
			}
		} catch (SQLException e) {
			LOGGER.error("A SQLException was throwed when get DB's metadata.");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
		} finally {
			DBlink.closeConnection(rs, null, conn);
		}
		return tableList;
	}

	/*
	 * 获取数据库中的相应字段
	 */
	public static List<String> getColumns(String tableName) {
		List<String> columnList = new ArrayList<>();
		Connection conn = DBlink.getConnection();
		ResultSet rs = null;
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getColumns(null, null, tableName, null);
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME"); // 获取列名
				String dataType = rs.getString("DATA_TYPE");// 字段数据类型(对应java.sql.Types中的常量)
				String typeName = rs.getString("TYPE_NAME");// 字段类型名称(例如：VACHAR2)
				System.out
						.println(columnName + " " + dataType + ":" + typeName);
				columnList.add(columnName);
			}
		} catch (SQLException e) {
			LOGGER.error("A SQLException was throwed when get DB's metadata.");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
		} finally {
			DBlink.closeConnection(rs, null, conn);
		}
		return columnList;
	}
}
