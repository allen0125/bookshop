package com.sx.fun;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sx.db.BookBrowseDb;
import com.sx.db.BookDb;
import com.sx.db.ReaderDb;
import com.sx.entity.Book;
import com.sx.entity.BookBrowser;
import com.sx.entity.Reader;

/**
 * 借书的相关操作
 * 
 * @author sunjie
 *
 */
public class BookBrowseOp {
	public static List<Book> getBorrowBooks() {
		return BookDb.getBookBorrowList();
	}

	public static List<Book> getBorrowBooksByISBN(long ISBN) {
		return BookDb.getBookBorrowListByISBN(ISBN);
	}

	public static List<Book> getBorrowBooksByAuthor(String author) {
		return BookDb.getBookBorrowListByAuthor(author);
	}

	public static List<Book> getBorrowBooksByBookName(String bookName) {
		return BookDb.getBookBorrowListByBookName(bookName);
	}

	public static void insertBookBrowsers(List<BookBrowser> bookBrowsers) {
		BookBrowseDb.insertBookBrowsers(bookBrowsers);
	}

	public static List<Book> getReaderBook(int UID) {
		return BookDb.getReaderBook(UID);
	}

	public static void deleteBookBrowsers(List<BookBrowser> bookBrowsers) {
		BookBrowseDb.deleteBookBrowsers(bookBrowsers);
	}

	public static void decBooks(Book book) {
		BookDb.decBooks(book);
	}

	public static void incBooks(Book book) {
		BookDb.incBooks(book);
	}

	public static void decReaderLimit(int UID, int borrowBooksCount) {
		ReaderDb.decReaderLimit(UID, borrowBooksCount);
	}

	public static void incReaderLimit(int UID, int borrowBooksCount) {
		ReaderDb.incReaderLimit(UID, borrowBooksCount);
	}
	
	public static void incReaderHis(int UID){
		ReaderDb.incReaderHis(UID);
	}

	/*
	 * get bookbrowser 获取读者借阅
	 */
	public static BookBrowser getBookBrowser(int UID) {
		return BookBrowseDb.getBookBrowser(UID);
	}

	/*
	 * 用户借书操作 （太面向过程的思维，学业不精，水了）
	 */
	public static List<BookBrowser> borrowBooks(Reader reader, List<Book> books) {
		List<BookBrowser> bookBrowsers = new ArrayList<>();

		// 存在借出，修改数据库
		for (Book book : books) {
			BookBrowser bookBrowser = new BookBrowser();
			bookBrowser.setUID(reader.getUID());
			bookBrowser.setBID(book.getBID());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = Date.valueOf(sdf.format(new java.util.Date()));
			bookBrowser.setBorrowDate(date);
			bookBrowsers.add(bookBrowser);
		}
		return bookBrowsers;
	}
}
