package com.sx.db;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBTool {
	private static final Logger LOGGER = LoggerFactory.getLogger(DBTool.class);
	public static final SqlSessionFactory SQL_SESSION_FACTORY = init();

	private static SqlSessionFactory init() {
		String dataSourceConfiguration = "com/sx/config/MyBatisConfig.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources
					.getResourceAsStream(dataSourceConfiguration);
		} catch (IOException e) {
			LOGGER.error(e.toString());
			System.exit(-1);
		}
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
}
