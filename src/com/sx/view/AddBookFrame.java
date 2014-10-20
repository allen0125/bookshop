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

		BookOp.insertBook(book);

		this.dispose();
	}
}
