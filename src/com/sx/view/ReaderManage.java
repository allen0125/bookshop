package com.sx.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sx.entity.Reader;
import com.sx.fun.ReaderOp;

public class ReaderManage extends JPanel {
	private JTextField textField;
	private JTable tableShow;
	private int del_uid;
	List<Reader> readerlist;
	DefaultTableModel defaultModel;
	JScrollPane scrollPane;
	JComboBox comboBox;

	
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
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u7F16\u53F7", "\u59D3\u540D"}));
		panel.add(comboBox);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(20);
		
		JButton button = new JButton("\u67E5\u8BE2");//查询按钮
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					if (textField.getText().equals("")) {
						readerlist = ReaderOp.getReaders();
						scrollPane.setViewportView(refreshTable(readerlist));
						return;
					}

					if (comboBox.getSelectedItem().toString().trim().equals("\u7F16\u53F7")) {
						 int UID= 0;
						try {
							UID = Integer.parseInt(textField.getText());
							readerlist = ReaderOp.getReaderByUID(UID);
							scrollPane.setViewportView(refreshTable(readerlist));
							scrollPane.validate();
						} catch (NumberFormatException nume) {
							JOptionPane.showMessageDialog(null, "非法输入！", "错误",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					if (comboBox.getSelectedItem().toString().trim().equals("\u59D3\u540D")) {
						 String Name= null;
						try {
							Name = textField.getText();
							readerlist = ReaderOp.getReaderByName(Name);
							scrollPane.setViewportView(refreshTable(readerlist));
							scrollPane.validate();
						} catch (NumberFormatException nume) {
							JOptionPane.showMessageDialog(null, "非法输入！", "错误",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			
				
			}
		);
		panel.add(button);
		
		JButton button_1 = new JButton("\u6DFB\u52A0");//添加
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddReaderFrame add_book = new AddReaderFrame("添加读者");
				add_book.setVisible(true);
				
			}
		});
		panel.add(button_1);
		
		JButton button_2 = new JButton("\u4FEE\u6539");//修改
		panel.add(button_2);
		
		JButton button_3 = new JButton("\u5220\u9664");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					int selectedRow = tableShow.getSelectedRow();// 获得选中行的索引
					if (selectedRow != -1) // 存在选中行
					{
						del_uid = (int) defaultModel.getValueAt(selectedRow, 1);
						ReaderOp.delreader(del_uid);
						defaultModel.removeRow(selectedRow); 
					}
				}
			
			
		});
		panel.add(button_3);
		
		scrollPane = new JScrollPane();
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
			data.add(reader.getUID());
			data.add(reader.getName());
			data.add(reader.getSex());
			data.add(reader.getUserGrade());
			data.add(reader.getHistoryCount());
			data.add(reader.getLimitCount());
			
			defaultModel.addRow(data);
		}
	}
}
