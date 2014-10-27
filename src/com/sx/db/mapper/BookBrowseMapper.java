/**
 * 
 */
package com.sx.db.mapper;

import java.util.List;

import com.sx.entity.Book;
import com.sx.entity.BookBrowser;
import com.sx.entity.BookReader;

/**
 * all operation about table "bookbrowse"
 * 
 * @author sunjie
 *
 */
public interface BookBrowseMapper {
	void insertRecord(BookBrowser bookBrowser);

	List<Book> getReaderBook(int UID);
	
	void deleteRecord(BookBrowser bookBrowser);
}
