/**
 * 
 */
package com.sx.db.mapper;

import com.sx.entity.BookBrowser;

/**
 * all operation about table "bookbrowse"
 * 
 * @author sunjie
 *
 */
public interface BookBrowseMapper {
	void insertRecord(BookBrowser bookBrowser);

	void deleteRecord(BookBrowser bookBrowser);
}
