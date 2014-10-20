package com.sx.db.mapper;

/**
 * 操作booktable相关的
 */

import java.util.List;
import com.sx.entity.Book;

public interface BookTableMapper {
	// 获取所有书籍列表
	List<Book> getBookList();

	// 根据ISBN/Author/BookName等信息查询
	List<Book> getBookByISBN(long ISBN);

	List<Book> getBookByAuthor(String Author);

	List<Book> getBookByBookName(String bookName);
	void delBook(long ISBN);
	void insertBook(Book book);
}
