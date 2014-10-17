package com.sx.db.mapper;

/**
 * ����booktable��ص�
 */

import java.util.List;
import com.sx.entity.Book;

public interface BookTableMapper {
	// ��ȡ�����鼮�б�
	List<Book> getBookList();
	List<Book> getBookByISBN(long ISBN);
	List<Book> getBookByAuthor(String Author);
	List<Book> getBookByBookName(String bookName);
	
	void insertBook(Book book);
}
