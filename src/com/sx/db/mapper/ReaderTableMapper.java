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
	List<Reader> getReaderByUID(int UID);
	
	void delReader(int UID);
	
	void insertReader(Reader reader);
	void updateReader(Reader reader);
	
	
	
}
