package com.sx.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sx.db.mapper.BookBrowseMapper;
import com.sx.db.mapper.BookTableMapper;
import com.sx.entity.Book;
import com.sx.entity.BookReader;

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

	public static void insertBookList(List<Book> bookList) {
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

	public static void insertBook(Book book) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			mapper.insertBook(book);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
	}
	
	public static void updateBook(Book book) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			mapper.updateBook(book);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
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
	
	public static List<Book> getReaderBook(int UID) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Book> list = null;
		try {
			BookBrowseMapper mapper = session.getMapper(BookBrowseMapper.class);
			list = mapper.getReaderBook(UID);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}
	
	public static List<Book> getBookByBID(int BID) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Book> list = null;
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			list = mapper.getBookByBID(BID);
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

	public static void delBook(long ISBN) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			BookTableMapper delbook = session.getMapper(BookTableMapper.class);
			delbook.delBook(ISBN);
			session.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			session.close();
		}
	}

	/*
	 * 以下get操作均和借书相关，需要count>0
	 */
	public static List<Book> getBookBorrowList() {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Book> bookList = null;
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			bookList = mapper.getBookBorrowList();
			session.commit();
		} finally {
			session.close();
		}
		return bookList;
	}

	public static List<Book> getBookBorrowListByISBN(long ISBN) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Book> bookList = null;
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			bookList = mapper.getBookBorrowByISBN(ISBN);
			session.commit();
		} finally {
			session.close();
		}
		return bookList;
	}

	public static List<Book> getBookBorrowListByAuthor(String author) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Book> bookList = null;
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			bookList = mapper.getBookBorrowByAuthor(author);
			session.commit();
		} finally {
			session.close();
		}
		return bookList;
	}

	public static List<Book> getBookBorrowListByBookName(String bookName) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		List<Book> bookList = null;
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			bookList = mapper.getBookBorrowByBookName(bookName);
			session.commit();
		} finally {
			session.close();
		}
		return bookList;
	}

	public static void decBooks(Book book) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			mapper.decBookCount(book);
			session.commit();
		} finally {
			session.close();
		}
	}

	public static void incBooks(Book book) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			BookTableMapper mapper = session.getMapper(BookTableMapper.class);
			mapper.incBookCount(book);
			session.commit();
		} finally {
			session.close();
		}
	}
}
