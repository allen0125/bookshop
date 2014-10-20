package com.sx.fun;

import java.util.List;

import com.sx.db.BookDb;
import com.sx.entity.Book;

/**
 * 书籍管理相应的操作类
 * 
 * @author sunjie
 *
 */
public class BookOp {

	public static List<Book> getBooks() {
		return BookDb.getBookList();
	}

	public static void insertBooks(List<Book> bookList) throws Exception {
		BookDb.insertBookList(bookList);
	}
	
	
	public static List<Book> getBookByISBN(long ISBN) {
		return BookDb.getBookByISBN(ISBN);
	}

	public static List<Book> getBookByAuthor(String Author) {
		return BookDb.getBookByAuthor(Author);
	}

	public static List<Book> getBookByBookName(String BookName) {
		return BookDb.getBookByBookName(BookName);
	}
<<<<<<< HEAD
	
	public static void delBook(long ISBN){
		BookDb.delBook(ISBN);
	}
=======
>>>>>>> origin/master
}
