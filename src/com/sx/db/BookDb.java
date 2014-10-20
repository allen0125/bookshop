package com.sx.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.db.SQLBuilder;

import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;
import com.sx.db.mapper.BookTableMapper;
import com.sx.entity.Book;
import com.sx.fun.BookOp;

public class BookDb {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookDb.class);

	public static List<Book> getBookList() {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Book> list = null;
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			list = mapper.getBookList();
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}

	public static void insertBookList(List<Book> bookList) throws Exception {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			for (Book book : bookList) {
				mapper.insertBook(book);
			}
			session.commit();
		} finally {
			session.close();
		}
	}
	

	public static List<Book> getBookByISBN(long ISBN) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Book> list = null;
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			list = mapper.getBookByISBN(ISBN);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}

	public static List<Book> getBookByAuthor(String Author) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Book> list = null;
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			list = mapper.getBookByAuthor(Author);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}

	public static List<Book> getBookByBookName(String BookName) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Book> list = null;
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			list = mapper.getBookByBookName(BookName);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}
	public static void delBook(long ISBN){
		try {
			SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
			BookTableMapper delbook = session.getMapper(BookTableMapper.class);
			delbook.delBook(ISBN);
			session.commit();
			session.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
