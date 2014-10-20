package com.sx.db.mapper;

/**
 * ����booktable��ص�
 */

import java.util.List;
import com.sx.entity.Book;

public interface BookTableMapper {
	// ��ȡ�����鼮�б�
	List<Book> getBookList();

	// ����ISBN/Author/BookName����Ϣ��ѯ
	List<Book> getBookByISBN(long ISBN);

	List<Book> getBookByAuthor(String Author);

	List<Book> getBookByBookName(String bookName);
	void delBook(long ISBN);
	void insertBook(Book book);
}
