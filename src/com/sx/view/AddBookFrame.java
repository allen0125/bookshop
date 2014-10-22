package com.sx.view;

import com.sx.entity.Book;
import com.sx.fun.BookOp;

public class AddBookFrame extends BookFrame {
	private static String frameName = "ÃÌº”Õº È";
	private static String btnName = "\u6DFB\u52A0";

	public AddBookFrame() {
		super(frameName, btnName);
	}

	public void click() {
		Book book = new Book();
		book.setBookName(textField_BookName.getText());
		book.setISBN(Long.valueOf(textField_BookISBN.getText()));
		book.setAuthor(textField_Author.getText());
		book.setCount(Integer.valueOf(textField_BookNum.getText()));
		book.setPrice(Double.valueOf(textField_Price.getText()));
		book.setPress(textField_press.getText());
		book.setPressDate(textField_press_date.getText());
		book.setTotalPrice(Double.valueOf(textField_total.getText()));
		book.setLanguage(textField_lang.getText());
		book.setBookCategory(textField_fenlei.getText());
		book.setBinding(textField_zhuangzhen.getText());
		book.setFeature(textField_tezhen.getText());
		book.setSize(textField_kaiben.getText());
		BookOp.insertBook(book);
		this.dispose();
	}
}
