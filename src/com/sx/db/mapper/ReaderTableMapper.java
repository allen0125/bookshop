package com.sx.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	void decReaderLimit(
			@Param(value = "borrowBooksCount") int borrowBooksCount,
			@Param(value = "UID") int UID);

	void incReaderLimit(
			@Param(value = "returnBooksCount") int returnBooksCount,
			@Param(value = "UID") int UID);

	void updateReader(Reader reader);
}
