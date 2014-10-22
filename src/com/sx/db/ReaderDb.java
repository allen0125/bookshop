package com.sx.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sx.db.mapper.BookTableMapper;
import com.sx.db.mapper.ReaderTableMapper;
import com.sx.entity.Book;
import com.sx.entity.Reader;

public class ReaderDb {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookDb.class);

	public static List<Reader> getReaderList() {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Reader> list = null;
		try {
			ReaderTableMapper mapper = session.getMapper(ReaderTableMapper.class);
			list = mapper.getReaderList();
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}
	
	

	public static List<Reader> getReaderByName(String name) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Reader> list = null;
		try {
			ReaderTableMapper mapper = session.getMapper(ReaderTableMapper.class);
			list = mapper.getReaderByName(name);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}
	
	public static List<Reader> getReaderByUID(int UID) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Reader> list = null;
		try {
			ReaderTableMapper mapper = session.getMapper(ReaderTableMapper.class);
			list = mapper.getReaderByUID(UID);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}
	
	
	public static void insertReaderList(List<Reader> readerlist) throws Exception {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			ReaderTableMapper mapper = session.getMapper(ReaderTableMapper.class);
			for (Reader reader : readerlist) {
				mapper.insertReader(reader);
			}
			session.commit();
		} finally {
			session.close();
		}
	}
	
	
//	添加单个读者
	
	
	public static void insertReader(Reader reader) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			ReaderTableMapper mapper = session.getMapper(ReaderTableMapper.class);
			mapper.insertReader(reader);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
	}
	public static void delReader(int UID) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			ReaderTableMapper delreader = session.getMapper(ReaderTableMapper.class);
			delreader.delReader(UID);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
	}
	
	
}
