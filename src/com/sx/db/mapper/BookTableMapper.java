package com.sx.db.mapper;

/**
 * 操作booktable相关的
 */

import java.util.List;
import com.sx.entity.Book;

public interface BookTableMapper {
	// 获取所有书籍列表
	List<Book> getBookList();
	List<Book> getBookByISBN(long ISBN);
	List<Book> getBookByAuthor(String Author);
	List<Book> getBookByBookName(String bookName);
	
	void insertBook(Book book);
}
