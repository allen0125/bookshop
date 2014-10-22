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

	/*
	 * 以下操作均和借书有关所以需要count>0
	 */
	// 获取所有可以借出的书籍：count>0的
	List<Book> getBookBorrowList();

	// 根据ISBN/Author/BookName等信息查询
	List<Book> getBookBorrowByISBN(long ISBN);

	List<Book> getBookBorrowByAuthor(String Author);

	List<Book> getBookBorrowByBookName(String bookName);

	void decBookCount(Book book); // 借书时候booktable中书籍数量-1

	void incBookCount(Book book); // 还书时候booktable中书籍数量+1
}
