package com.sx.entity;

public class Reader {
	
	private int UID;
	private String Name;
	private String Sex;
	private String UserGrade;
	private int HistoryCount;
	private int LimitCount;
	
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
	
	
}
