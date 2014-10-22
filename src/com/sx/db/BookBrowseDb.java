package com.sx.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.sx.db.mapper.BookBrowseMapper;
import com.sx.entity.BookBrowser;

public class BookBrowseDb {
	public static void insertBookBrowsers(List<BookBrowser> bookBrowsers) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			BookBrowseMapper mapper = session.getMapper(BookBrowseMapper.class);
			for (BookBrowser bb : bookBrowsers) {
				mapper.insertRecord(bb);
			}
			session.commit();
		} finally {
			session.close();
		}
	}

	public static void deleteBookBrowsers(List<BookBrowser> bookBrowsers) {
		SqlSession session = DBTool.SQL_SESSION_FACTORY.openSession();
		try {
			BookBrowseMapper mapper = session.getMapper(BookBrowseMapper.class);
			for (BookBrowser bb : bookBrowsers) {
				mapper.deleteRecord(bb);
			}
			session.commit();
		} finally {
			session.close();
		}
	}
}
