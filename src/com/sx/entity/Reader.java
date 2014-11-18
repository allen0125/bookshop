package com.sx.entity;

import java.util.List;

import com.sx.fun.BookBrowseOp;

public class Reader {
	
	private int UID;
	private String Name;
	private String Sex;
	private String UserGrade;
	private int HistoryCount;
	private int LimitCount;
	private String HistoryBook;
	
	public int getUID() {
		return UID;
	}
	public void setUID(int uid) {
		UID = uid;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getUserGrade() {
		return UserGrade;
	}
	public void setUserGrade(String userGrade) {
		UserGrade = userGrade;
	}
	public int getHistoryCount() {
		return HistoryCount;
	}
	public void setHistoryCount(int historyCount) {
		HistoryCount = historyCount;
	}
	public int getLimitCount() {
		return LimitCount;
	}
	public void setLimitCount(int limitCount) {
		LimitCount = limitCount;
	}
	public String toString() {
		String retString = "UID=" + UID+ "\tName=" + Name
				+ "\tSex=" + Sex + "\tUserGrade=" + UserGrade + "HistoryCount="
				+ HistoryCount + "LimitCount=" + LimitCount;
		return retString;
	}
	public String toCString() {
		String retString = "  ���ߺţ�" + UID+ "  ����������" + Name
				+ "  �����Ա�" + Sex + "  �����꼶��" + UserGrade + "  ��ʷ����������"
				+ HistoryCount + "  ����������" + LimitCount;
		return retString;
	}
	
}
