package com.sx.view;

import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.sx.entity.Book;
import com.sx.fun.BookOp;
import java.awt.Color;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class BookManage extends JPanel {
	private JTextField textField;
	private JTable tableShow;
	DefaultTableModel defaultModel;
	private JPanel panel = null;
	private JLabel labelSearch;
	private JComboBox comboBox;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	
	/**
	 * Create the panel.
	 */
	public BookManage(int width) {
		setBackground(new Color(240, 240, 240));
		setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 1000, 33);
		add(panel);

		labelSearch = new JLabel("\u641C\u7D22\u5B57\u6BB5");
		labelSearch.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(labelSearch);

		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox.getSelectedItem().toString().trim().equals("ISBN")){
						
					}
					if (comboBox.getSelectedItem().toString().trim().equals("\u4E66\u540D")) {
						
					}
					if (comboBox.getSelectedItem().toString().trim().equals("\u4F5C\u8005")) {
						
					}
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"ISBN", "\u4E66\u540D", "\u4F5C\u8005"}));
		panel.add(comboBox);

		JLabel labelKeyWords = new JLabel("\u5173\u952E\u5B57");
		panel.add(labelKeyWords);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		JButton btnSearch = new JButton("\u67E5\u8BE2");
		panel.add(btnSearch);
		
		button = new JButton("\u6DFB\u52A0");
		panel.add(button);
		
		button_1 = new JButton("\u4FEE\u6539");
		panel.add(button_1);
		
		button_2 = new JButton("\u5220\u9664");
		panel.add(button_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 33, 1000, 600);
		add(scrollPane);

		scrollPane.setViewportView(getShowTable());
	}

	private JTable getShowTable() {
		if (tableShow != null) {
			return tableShow;
		}
		tableShow = new JTable();
		Object[][] data = new Object[][] {};
		String[] name = new String[] { "ID", "书名", "作者", "出版社", "出版日期", "单价",
				"数量", "总价", "ISBN", "图书分类", "图书语言", "开本", "装帧", "特征" };
		defaultModel = new DefaultTableModel(data, name);
		defaultModel.setColumnCount(14);
		getBookDetail();
		tableShow.setModel(defaultModel);

		return tableShow;
	}

	// 获取书籍详细信息，存入向量用以向JTable中添加
	private void getBookDetail() {
		List<Book> bookList = BookOp.getBooks();
		Book book = null;
		for (int i = 0; i < bookList.size(); i++) {
			Vector<Object> data = new Vector<>();
			book = bookList.get(i);
			data.add(i + 1);
			data.add(book.getBookName());
			data.add(book.getAuthor());
			data.add(book.getPress());
			data.add(book.getPressDate());
			data.add(book.getPrice());
			data.add(book.getCount());
			data.add(book.getTotalPrice());
			data.add(book.getISBN());
			data.add(book.getBookCategory());
			data.add(book.getLanguage());
			data.add(book.getSize());
			data.add(book.getBinding());
			data.add(book.getFeature());
			defaultModel.addRow(data);
		}
	}
}
