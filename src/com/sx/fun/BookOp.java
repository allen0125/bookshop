package com.sx.fun;

import java.util.List;

import com.sx.db.BookDb;
import com.sx.entity.Book;

/**
 * �鼮������Ӧ�Ĳ�����
 * 
 * �����Ըĳɽӿڣ�
 * 
 * @author sunjie
 *
 */
public class BookOp {

	public static List<Book> getBooks() {
		return BookDb.getBookList();
	}

	public static void insertBook(Book book) {
		BookDb.insertBook(book);
	}

	public static void updateBook(Book book){
		BookDb.updateBook(book);
	}

	public static void insertBooks(List<Book> bookList) throws Exception {
		BookDb.insertBookList(bookList);
	}

	public static List<Book> getBookByISBN(long ISBN) {
		return BookDb.getBookByISBN(ISBN);
	}
	
	public static List<Book> getBookByBID(int BID) {
		return BookDb.getBookByISBN(BID);
	}

	public static List<Book> getBookByAuthor(String Author) {
		return BookDb.getBookByAuthor(Author);
	}

	public static List<Book> getBookByBookName(String BookName) {
		return BookDb.getBookByBookName(BookName);
	}

	public static void delBook(long ISBN) {
		BookDb.delBook(ISBN);
	}
}
