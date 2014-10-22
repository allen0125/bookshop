package com.sx.view;
import com.sx.view.*;
import com.sx.entity.Book;
import com.sx.fun.*;

public class ModifyBookFrame extends BookFrame {
	private static String frameName = "ÐÞ¸ÄÍ¼Êé";
	private static String btnName = "ÐÞ¸Ä";
	private static int BID;
	public ModifyBookFrame() {
		super(frameName, btnName);
	}
	public void click(){
		Object nameObject = textField_BookName.getText();
		Object authorObject = textField_Author.getText();
		Object pressObject = textField_press.getText();
		Object pressdataObject = textField_press_date.getText();
		Object priceObject = textField_Price.getText();
		Object numObject = textField_BookNum.getText();
		Object totalObject =textField_total.getText();
		Object isbnObject = textField_BookISBN.getText();
		Object fenleiObject = textField_fenlei.getText();
		Object langObject = textField_lang.getText();
		Object siziObject = textField_kaiben.getText();
		Object zhuangzhenObject = textField_zhuangzhen.getText();
		Object tezhenObject = textField_tezhen.getText();
		Book book = null;
		
	}
}
