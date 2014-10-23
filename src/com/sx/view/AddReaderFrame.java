package com.sx.view;
import com.sx.entity.Reader;
import com.sx.fun.ReaderOp;

public class AddReaderFrame extends ReaderFrame {

	public AddReaderFrame(String framename) {
		super(framename);
		}

	public void click() {
		
		Reader reader = new Reader();
		reader.setName(textField_rName.getText());
		reader.setUID(Integer.valueOf(textField_rNum.getText()));
		reader.setSex(textField_rSex.getText());
		reader.setUserGrade(textField_rGrade.getText());
		reader.setHistoryCount(Integer.valueOf(textField_rHisNum.getText()));
		reader.setLimitCount(Integer.valueOf(textField_rLimNum.getText()));
		ReaderOp.insertReader(reader);
		this.dispose();
	}
	
}
