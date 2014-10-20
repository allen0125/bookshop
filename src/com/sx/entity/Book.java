package com.sx.entity;

/**
 * book类
 * 
 * @author sunjie
 *
 */
public class Book {
	private int BID; // book id
	private String bookName;
	private String author;
	private String press;
	private String pressDate; //
	private double price; // single price
	private int count; // 书籍数量
	private double totalPrice;
	private long ISBN;
	private String bookCategory; // 书籍类别
	private String language;
	private String size; // 16开、32开什么的
	private String binding; // 装帧
	private String feature; // 特征

	public int getBID() {
		return BID;
	}

	public void setBID(int bID) {
		BID = bID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getPressDate() {
		return pressDate;
	}

	public void setPressDate(String pressDate) {
		this.pressDate = pressDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public long getISBN() {
		return ISBN;
	}

	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}

	public String getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String toString() {
		String retString = "BID=" + BID + "\tBookName=" + bookName
				+ "\tAuthor=" + author + "\tPress=" + press + "\tPressDate="
				+ pressDate + "\tPrice=" + price + "\tCount=" + count
				+ "\nTotalPrice=" + totalPrice + "\tISBN=" + ISBN
				+ "\tBookCategory=" + bookCategory + "\tLanguage=" + language
				+ "\tSize=" + size + "\tBinding=" + binding + "\tFeature="
				+ feature;
		return retString;
	}
}
