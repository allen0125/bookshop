package com.sx.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sx.db.mapper.TestMapper;

public class TestDb {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestDb.class);

	public static void select() {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			TestMapper mapper = session.getMapper(TestMapper.class);
			List<String> list = mapper.getList("1");
			LOGGER.info(list.toString());
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
			LOGGER.debug("session close");
		}
	}
}
