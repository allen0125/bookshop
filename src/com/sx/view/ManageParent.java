package com.sx.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.sx.entity.Book;

public abstract class ManageParent extends JPanel {

	JTextField textField;
	JTable tableShow; // show information of books
	DefaultTableModel defaultModel;
	JPanel panel = null;
	private JLabel labelSearch;
	JComboBox comboBox;
	JButton btnSearch;
	List<Book> bookList;
	JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public ManageParent() {
		init();
	}

	/*
	 * initialzation
	 */
	private void init() {
		setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 1000, 33);
		add(panel);

		labelSearch = new JLabel("\u641C\u7D22\u5B57\u6BB5");
		labelSearch.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(labelSearch);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "ISBN",
				"\u4E66\u540D", "\u4F5C\u8005" }));
		panel.add(comboBox);

		JLabel labelKeyWords = new JLabel("\u5173\u952E\u5B57");
		panel.add(labelKeyWords);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		btnSearch = new JButton("\u67E5\u8BE2");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClick();
			}
		});
		panel.add(btnSearch);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 33, 1000, 600);
		add(scrollPane);
		scrollPane.setViewportView(initShowTable(bookList));
	}

	abstract public void onClick();

	private JTable initShowTable(List<Book> bookList) {
		if (tableShow != null) {
			return tableShow;
		}
		tableShow = new JTable();
		Object[][] data = new Object[][] {};
		String[] name = new String[] { "ID", "ͼ����", "����", "����", "������", "��������",
				"����", "����", "�ܼ�", "ISBN", "ͼ�����", "ͼ������", "����", "װ֡", "����" };
		defaultModel = new DefaultTableModel(data, name);
		if (bookList != null) {
			getBookDetail(bookList);
		}
		tableShow.setModel(defaultModel);

		return tableShow;
	}

	/*
	 * ����
	 */
	protected JTable refreshTable(List<Book> bookList) {
		if (tableShow == null) {
			tableShow = new JTable();
		}
		tableShow.removeAll();
		getBookDetail(bookList);
		tableShow.setModel(defaultModel);
		tableShow.validate();
		return tableShow;
	}

	// ��ȡ�鼮��ϸ��Ϣ����������������JTable�����
	private void getBookDetail(List<Book> bookList) {
		if (defaultModel.getRowCount() != 0) {
			defaultModel.setRowCount(0);
		}
		// List<Book> bookList = BookOp.getBooks();
		Book book = null;
		for (int i = 0; i < bookList.size(); i++) {
			Vector<Object> data = new Vector<>();
			book = bookList.get(i);
			data.add(i + 1);
			data.add(book.getBID());
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
