package com.sx.db.mapper;

import java.util.List;

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

	void decReaderLimit(int borrowBooksCount, int UID);
	void incReaderLimit(int returnBooksCount, int UID);
}
