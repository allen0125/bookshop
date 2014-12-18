package com.sx.view;

import java.awt.TextField;
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

import org.omg.CORBA.PRIVATE_MEMBER;

import com.sx.fun.BookBrowseOp;
import com.sx.fun.BookOp;
import com.sx.entity.Book;
import com.sx.entity.BookReader;
import com.sx.entity.Reader;
import com.sx.fun.ReaderOp;
import com.sx.view.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
		comboBox.setModel(new DefaultComboBoxModel(new String[] {
				"\u7F16\u53F7", "\u59D3\u540D" }));
		panel.add(comboBox);

		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textField.requestFocusInWindow();
			}
		});
		panel.add(textField);
		textField.setColumns(20);

		JButton button = new JButton("\u67E5\u8BE2");// 查询按钮
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().equals("")) {
					readerlist = ReaderOp.getReaders();
					scrollPane.setViewportView(refreshTable(readerlist));
					return;
				}

				if (comboBox.getSelectedItem().toString().trim()
						.equals("\u7F16\u53F7")) {
					int UID = 0;
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
				if (comboBox.getSelectedItem().toString().trim()
						.equals("\u59D3\u540D")) {
					String Name = null;
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

		});
		panel.add(button);

		JButton button_1 = new JButton("\u6DFB\u52A0");// 添加
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddReaderFrame add_book = new AddReaderFrame("添加读者");
				add_book.setVisible(true);

			}
		});
		panel.add(button_1);

		JButton button_2 = new JButton("\u4FEE\u6539");// 修改
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ModifyReaderFrame modify = new ModifyReaderFrame();
				modify.setVisible(true);
				int selectedRow = tableShow.getSelectedRow();// 获得选中行的索引
				if (selectedRow != -1) // 存在选中行
				{
					Object ID = defaultModel.getValueAt(selectedRow, 1);
					Object Name = defaultModel.getValueAt(selectedRow, 2);
					Object Sex = defaultModel.getValueAt(selectedRow, 3);
					Object Grede = defaultModel.getValueAt(selectedRow, 4);
					Object HisNum = defaultModel.getValueAt(selectedRow, 5);
					Object LimNum = defaultModel.getValueAt(selectedRow, 6);

					modify.textField_rNum.setText(String.valueOf(ID));
					modify.textField_rName.setText((String) Name);
					modify.textField_rSex.setText((String) Sex);
					modify.textField_rGrade.setText((String) Grede);
					modify.textField_rHisNum.setText(String.valueOf(HisNum));
					modify.textField_rLimNum.setText(String.valueOf(LimNum));
				}
			}
		});
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
		
		JButton button_4 = new JButton("\u5DF2\u501F\u56FE\u4E66");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int uid;
				List<Book> books = null;
				
				BookName bookName = new BookName();
				bookName.setVisible(true);
				int selectedRow = tableShow.getSelectedRow();// 获得选中行的索引
				if (selectedRow != -1) // 存在选中行
				{
					uid = (int) defaultModel.getValueAt(selectedRow, 1);
					books = BookBrowseOp.getReaderBook(uid);
					Book book = books.get(0);
					book = books.get(0);
					bookName.textField.setText(book.getBookName());
				}
				
			}
		});
		panel.add(button_4);

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
		String[] name = new String[] { "编号", "读者号", "姓名", "性别", "年级", "历史借书数量",
				"最多借书数量"};
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
//			data.add(reader.getHistoryBook());
			defaultModel.addRow(data);
		}
	}
}
