package com.sx.view;
import java.util.List;

import com.sx.entity.Book;
import com.sx.entity.Reader;

import org.apache.poi.ss.formula.functions.Index;

import com.sx.fun.*;


public class ModifyReaderFrame extends ReaderFrame {
	private static String framename = "修改读者信息";
	public ModifyReaderFrame() {
		super(framename);
	}
	
	public void click(){
		String uIDObject = textField_rNum.getText();
		List<Reader>readers = ReaderOp.getReaderByUID(Integer.valueOf(uIDObject));
		Reader reader = readers.get(0);
		reader.setUID(Integer.valueOf((String)textField_rNum.getText()));
		reader.setName((String)textField_rName.getText());
		reader.setSex((String)textField_rSex.getText());
		reader.setUserGrade((String)textField_rGrade.getText());
		reader.setHistoryCount(Integer.valueOf((String)textField_rHisNum.getText()));
		reader.setLimitCount(Integer.valueOf((String)textField_rLimNum.getText()));
		ReaderOp.updateReader(reader);
		this.dispose();
		
	}
}
