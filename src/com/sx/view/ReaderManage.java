package com.sx.view;

import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sx.entity.Book;
import com.sx.entity.Reader;
import com.sx.fun.BookOp;
import com.sx.fun.ReaderOp;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReaderManage extends JPanel {
	private JTextField textField;
	private JTable tableShow;
	private int del_userid;
	List<Reader> readerlist;
	DefaultTableModel defaultModel;

	
	/**
	 * Create the panel.
	 */
	public ReaderManage(int width) {
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1000, 33);
		add(panel);
		
		JLabel label = new JLabel("\u641C\u7D22\u5B57\u6BB5\uFF1A");
		panel.add(label);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u7F16\u53F7", "\u59D3\u540D"}));
		panel.add(comboBox);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(20);
		
		JButton button = new JButton("\u67E5\u8BE2");
		panel.add(button);
		
		JButton button_1 = new JButton("\u6DFB\u52A0");
		panel.add(button_1);
		
		JButton button_2 = new JButton("\u4FEE\u6539");
		panel.add(button_2);
		
		JButton button_3 = new JButton("\u5220\u9664");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					int selectedRow = tableShow.getSelectedRow();// 获得选中行的索引
					if (selectedRow != -1) // 存在选中行
					{
						del_userid = Integer.valueOf((String) defaultModel.getValueAt(selectedRow, 2));
						ReaderOp.delreader(del_userid);
						defaultModel.removeRow(selectedRow); // 删除行 删除数据库未写
					}
				}
			
			
		});
		panel.add(button_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 31, 1000, 600);
		readerlist = ReaderOp.getReaders();
		scrollPane.setViewportView(getShowTable(readerlist));
		
		add(scrollPane);

			
		
	}
	
	
	
	private JTable refreshTable(List<Reader> readerlist) {
		if (tableShow == null) {
			tableShow = new JTable();
		}
		tableShow.removeAll();
		getReaderDetail(readerlist);
		tableShow.setModel(defaultModel);
		tableShow.validate();
		return tableShow;
	}
	
	
	
	
	private JTable getShowTable(List<Reader> readerlist) {
		if (tableShow != null) {
			return tableShow;
		}
		tableShow = new JTable();
		Object[][] data = new Object[][] {};
		String[] name = new String[] { "编号","读者号","姓名","性别","年级","历史借书数量","最多借书数量" };
		defaultModel = new DefaultTableModel(data, name);
		defaultModel.setColumnCount(7);
		getReaderDetail(readerlist);
		tableShow.setModel(defaultModel);

		return tableShow;
	}


	private void getReaderDetail(List<Reader> readerlist) {
		if (defaultModel.getRowCount() != 0) {
			defaultModel.setRowCount(0);
		}
		Reader reader = null;
		for (int i = 0; i < readerlist.size(); i++) {
			Vector<Object> data = new Vector<>();
			reader = readerlist.get(i);
			data.add(i + 1);
			data.add(reader.getUserID());
			data.add(reader.getName());
			data.add(reader.getSex());
			data.add(reader.getUserGrade());
			data.add(reader.getHistoryCount());
			data.add(reader.getLimitCount());
			
			defaultModel.addRow(data);
		}
	}
}
