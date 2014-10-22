package com.sx.fun;

import java.util.List;

import com.sx.entity.Book;
import com.sx.entity.Reader;
import com.sx.db.BookDb;
import com.sx.db.ReaderDb;


public class ReaderOp {
	public static List<Reader> getReaders() {
		return ReaderDb.getReaderList();
	}
	public static List<Reader> getReaderByName(String Name){
		return ReaderDb.getReaderByName(Name);
		}
	public static void delreader(int UID){
		ReaderDb.delReader(UID);
	}
	
	public static List<Reader> getReaderByUID(int UID){
		return ReaderDb.getReaderByUID(UID);
	}
}
