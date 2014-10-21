package com.sx.db.mapper;
import java.util.List;

import com.sx.entity.Book;
import com.sx.entity.Reader;

public interface ReaderTableMapper {
	List<Reader> getReaderList();
	
	List<Reader> getReaderByUserID(int UserID);
	List<Reader> getReaderByName(String Name);
	List<Reader> getReaderBySex(String Sex);
	List<Reader> getReaderByUserGrade(String UserGrade);
	
	void delReader(int UserID);
	
	void insertReader(Reader reader);
	
	
	
	
}
